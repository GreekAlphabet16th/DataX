package com.cetiti.dataX.enums;

public enum Driver {
    MYSQL("com.mysql.jdbc.Driver",1),
    ORACLE("oracle.jdbc.driver.OracleDriver",2);

    private int num;
    private String value;
    private Driver(String value, int num){
        this.value = value;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public static String getValue(int num) {
        for(Driver driver : Driver.values()){
            if(driver.getNum() == num){
                return driver.value;
            }
        }
        return null;
    }
}
