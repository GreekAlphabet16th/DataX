package com.cetiti.dataX.enums;

public enum Sex {

    MALE(0,"男"), FEMALE(1,"女");
    private int id;
    private String name;

    private Sex(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Sex getSex(int id){
        for(Sex sex : Sex.values()){
            if(sex.getId() == id){
                return sex;
            }
        }
        return null;
    }
}
