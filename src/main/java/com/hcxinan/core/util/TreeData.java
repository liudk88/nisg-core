package com.hcxinan.core.util;

import com.alibaba.fastjson.annotation.JSONField;
import com.hcxinan.core.inte.util.IDependency;
import com.hcxinan.core.inte.util.ITree;
import com.hcxinan.core.inte.util.ITreeHelper;
import lombok.Data;

import java.util.*;

/**
 * @author liudk
 * @Description: 属性结果类， T：需要绑定的数据类型，E:节点的数据类型
 * @date 21-8-25 下午5:42
 */
@Data
public class TreeData<KEY, DATA> implements ITree<KEY, DATA>{
    //树节点ID号
    private KEY id;
    // 树的的父亲节点ID
    private KEY pid;
    // 节点名称
    private String label;

    private DATA data;//绑定的数据
    @JSONField(serialize=false)
    private TreeData<KEY, DATA> parent;//父节点

    private List<ITree<KEY, DATA>> children =new ArrayList<>();//子节点
    @JSONField(serialize=false)
    private List<DATA> sonDatas=new ArrayList<>();//子节点数据

    private TreeData(){};

    public TreeData(KEY id, KEY pid, String label) {
        this.id = id;
        this.pid = pid;
        this.label = label;
    }
    /**
     *@Description 可以获取所有子孙节点的数据
     *@Param []
     *@Return java.util.List<T>
     *@Author liudk
     *@DateTime 20-8-8 下午7:00
     */
    @JSONField(serialize=false)
    public List<DATA> getDescendantsDatas() {
        if(children ==null || children.size()==0){
            return null;
        }else{
            List<DATA> descendantsDatas=new ArrayList<>();
            for(ITree<KEY, DATA> son: children){
                descendantsDatas.add(son.getData());
                List<DATA> sencondDescendantsDatas=son.getDescendantsDatas();
                if(sencondDescendantsDatas!=null && sencondDescendantsDatas.size()>0){
                    descendantsDatas.addAll(sencondDescendantsDatas);
                }
            }
            return descendantsDatas;
        }
    }

    public void addSon(TreeData<KEY, DATA> son){
        children.add(son);
        son.setParent(this);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        if(id!=null){
            sb.append(", id="+id.toString());
        }
        if(pid!=null){
            sb.append(", pid="+pid.toString());
        }
        if(label !=null){
            sb.append(", name="+ label.toString());
        }
        sb.append("}");
        if(sb.length()>0){
            return "TreeData{"+sb.substring(1)+"}";
        }else{
            return "TreeData{}";
        }
    }

    /**
     *@Description 把带有复杂关联信息的数据进行树状结构化，带上关联信息
     *@Param [datas, helper]
     *@Return java.util.Map<java.lang.String,com.szelink.safesystem.quota.engine.util.TreeData<T>>
     *@Author liudk
     *@DateTime 20-3-10 下午6:06
     */
    public static <KEY, DATA> Map<KEY,ITree<KEY, DATA>> treeData(Collection<DATA> datas, ITreeHelper<KEY, DATA> helper) {
        if(datas == null || datas.size()==0 || helper==null){
            return null;
        }
        Map<KEY,ITree<KEY, DATA>> mapTreeData=new LinkedHashMap<>();//通过树节点，可以获取到其对应的树状数据的map
        for(DATA cdata:datas){//遍历数据，每个数据通过helper的实现来构造指定节点
            ITree<KEY, DATA> treeData=helper.getTree(cdata);
            if(treeData.getId()==null){
                throw new NullPointerException("树节点的id不能为空！");
            }
            treeData.setData(cdata);
            mapTreeData.put(treeData.getId(),treeData);//绑定节点id和节点
        }
        //建立树节点依赖关系
        for(Map.Entry<KEY,ITree<KEY, DATA>> entry:mapTreeData.entrySet()){
            ITree<KEY, DATA> tree=entry.getValue();
            KEY pid= tree.getPid();//节点父id
            if(pid!=null && mapTreeData.containsKey(pid)){//说明当前节点是有父节点的，获取父节点，把当前节点放到父节点下
                ITree<KEY, DATA> parent=mapTreeData.get(pid);
                parent.addChild(tree);
                tree.setParent(parent);
                //对绑定数据建立依赖关系
                DATA sonData=tree.getData();
                DATA pData=parent.getData();
//                parent.sonDatas.add(sonData);
                //如果数据是实现了依赖接口的，那么数据间可以直接构建依赖关系
                if(sonData instanceof IDependency){
                    try{
                        ((IDependency) sonData).setParent(parent.getData());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(pData instanceof IDependency){
                    try{
                        IDependency dependency= (IDependency) pData;
                        dependency.addChild(sonData);
//                        ((IDependency) parent.getData()).addChild(sonData);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }
        return mapTreeData;
    }
    //返回树，只留下一级节点就是树了（其他数据的节点可以通过父节点一级级往下展开来获取）
    public static <KEY, DATA> List<ITree<KEY, DATA>> getTreeData(Collection<DATA> datas, ITreeHelper<KEY, DATA> helper) {
        Map<KEY,ITree<KEY, DATA>> mapTreeData= treeData(datas,helper);
        return getTreeData(mapTreeData);
    }

    public static <KEY, DATA> List<ITree<KEY, DATA>> getTreeData(Map<KEY,ITree<KEY, DATA>> mapTreeData) {
        List list=new ArrayList();
        for(ITree<KEY, DATA> node:mapTreeData.values()){
            if(node.parent()==null){
                list.add(node);
            }
        }
        return list;
    }

    //返回树，只留下一级节点的数据（其他数据的节点可以通过父节点一级级往下展开来获取），只有当树绑定的数据类型都实现了IDependency接口，这个方法才有价值
    public static <DATA extends IDependency,KEY> List<DATA> getTreeDatas(Collection<DATA> datas, ITreeHelper<KEY, DATA> helper) {
        Map<KEY,ITree<KEY, DATA>> mapTreeData= treeData(datas,helper);
        List<DATA> lev1_datas=new ArrayList<>();
        Iterator<KEY> it = mapTreeData.keySet().iterator();
        while(it.hasNext()) {
            KEY key=it.next();
            ITree<KEY, DATA> node=mapTreeData.get(key);
            if(node.getPid()==null || "".equals(node.getPid())){
                lev1_datas.add(node.getData());
            }
        }
        return lev1_datas;
    }

    @Override
    public ITree<KEY, DATA> parent() {
        return parent;
    }

    @Override
    public void setParent(ITree<KEY, DATA> parent) {
        this.parent= (TreeData<KEY, DATA>) parent;
    }

    @Override
    public TreeData addChild(ITree<KEY, DATA> child) {
        children.add(child);
        return this;
    }

    @Override
    public List<ITree<KEY, DATA>> getChildren(){
        return children;
    }
}
