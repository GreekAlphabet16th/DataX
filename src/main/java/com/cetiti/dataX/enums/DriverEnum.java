package com.cetiti.dataX.enums;

public enum DriverEnum {
    MYSQL("com.mysql.jdbc.Driver",1),
    ORACLE("oracle.jdbc.driver.OracleDriver",2);

    private int num;
    private String value;
    DriverEnum(String value, int num){
        this.value = value;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public static String getValue(int num) {
        for(DriverEnum driverEnum : DriverEnum.values()){
            if(driverEnum.getNum() == num){
                return driverEnum.value;
            }
        }
        return null;
    }
}
