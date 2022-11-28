package com.devsuperior.crud.exeptions;

public class DatabaseExeption extends RuntimeException{

    public DatabaseExeption(String msg){
        super(msg);
    }
}
