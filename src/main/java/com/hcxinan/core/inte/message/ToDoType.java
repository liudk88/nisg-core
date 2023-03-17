package com.hcxinan.core.inte.message;

public enum ToDoType {
    add("新增代办",0), update("更新代办",1), delete("删除代办",2);

    private String name;

    private Integer value;

    ToDoType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
