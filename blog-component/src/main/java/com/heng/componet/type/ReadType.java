package com.heng.componet.type;

public enum  ReadType {

    Mysql(1),
    Oracle(2),
    Redis(3),
    ES(4),
    Hbase(5),
    Hive(6),
    Hadoop(7),
    Kafika(8);

    private Integer type;

    ReadType(Integer type){
        this.type = type;
    }
}
