package com.cetiti.dataX.enums;

public enum SqlType {
    MYSQL("mysql",1),
    ORACLE("oracle",2);

    private String value;
    private int num;
    private SqlType(String value, int num){
        this.value = value;
        this.num = num;
    }

    public static String getValue(int num) {
        for(SqlType sqlType : SqlType.values()){
            if(sqlType.getNum() == num){
                return sqlType.value;
            }
        }
        return null;
    }

    public int getNum() {
        return num;
    }
}
