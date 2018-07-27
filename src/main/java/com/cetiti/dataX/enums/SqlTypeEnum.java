package com.cetiti.dataX.enums;

public enum SqlTypeEnum {
    MYSQL("mysql",1),
    ORACLE("oracle",2);

    private String value;
    private int num;
    SqlTypeEnum(String value, int num){
        this.value = value;
        this.num = num;
    }

    public static String getValue(int num) {
        for(SqlTypeEnum sqlTypeEnum : SqlTypeEnum.values()){
            if(sqlTypeEnum.getNum() == num){
                return sqlTypeEnum.value;
            }
        }
        return null;
    }

    public int getNum() {
        return num;
    }
}
