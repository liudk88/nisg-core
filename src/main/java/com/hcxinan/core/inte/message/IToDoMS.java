package com.hcxinan.core.inte.message;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
*@Description 代办实现接口，实现代办功能
*@Param 
*@Return 
*@Author liudk
*@DateTime 21-2-4 上午9:20
*/
public interface IToDoMS<T extends IToDo> {
    /**
     *@Description 根据单个业务实体推送一条代办信息
     *@Param [entity]:生成代表信息的对象
     *@Return java.lang.String
     *@Author liudk
     *@DateTime 21-2-4 上午11:12
     */
    default String addToDoByEntity(Object entity){
        return addToDo(getToDo(entity,ToDoType.add));
    }
    /**
     *@Description 根据多个业务实体推送多条代办信息
     *@Param [ToDo]：代表生成代办信息的对象结合
     *@Return java.lang.String[]:代办信息的主键集合
     *@Author liudk
     *@DateTime 21-2-4 上午9:49
     */
    default String[] addToDosByEntitys(List<Object> entitys){
        List<T> ToDos = entitys.stream().map(o->getToDo(o,ToDoType.add)).collect(Collectors.toList());
        return addToDos(ToDos);
    }

    default void updateToDoByEntity(Object entity){
        updateToDo(getToDo(entity,ToDoType.update));
    }

    default void deleteToDoByEntity(List entitys){
        List<T> toDos= (List<T>) entitys.stream().map(entity->getToDo(entity,ToDoType.delete)).collect(Collectors.toList());
        deleteToDo(toDos);
    }
    
    /**
    *@Description 根据实体生成代办实体，每个项目应该结合代表接口及模块业务去实现
    *@Param [entity:业务实体,operaType:操作类型,add-新增,update-更新,delete-删除]
    *@Return com.hcxinan.core.inte.message.IToDo
    *@Author liudk
    *@DateTime 21-2-4 下午1:06
    */
    T getToDo(Object entity,ToDoType toDoType);
    //添加待办
    String addToDo(T toDo);
    //批量添加待办
    String[] addToDos(List<T> toDos);
    //查询代办
    Optional<T> selectToDo(String toDoId);
    //更新代表信息
    void updateToDo(T toDo);
    //批量删除代表信息
    void deleteToDo(List<T> toDos);
    //根据代办主键批量删除代表信息
    void deleteToDoById(List<String> toDoIds);
}
