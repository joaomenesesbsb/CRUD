package com.devsuperior.crud.exeptions;

public class RecourceNotFoundExeption extends  RuntimeException{

    public RecourceNotFoundExeption(String msg){
        super(msg);
    }
}
