package com.cetiti.dataX.enums;

public enum SqlType {
    MYSQL("mysql",0),
    ORACLE("oracle",1);

    private int num;
    private String value;

    private SqlType(String value, int num){
        this.value = value;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public static String getValue(int num) {
        for(SqlType sqlType : SqlType.values()){
            if(sqlType.getNum() == num){
                return sqlType.value;
            }
        }
        return null;
    }


}
