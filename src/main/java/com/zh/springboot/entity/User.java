package com.zh.springboot.entity;

/**
 * 用户模型
 */
public class User {
    /**
     * 用户的ID
     */
    private int id;

    /**
     * 用户的名称
     */
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
