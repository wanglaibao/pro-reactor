package com.laibao.reactor.exception;

public class CustomException extends RuntimeException{
    private static final long serialVersionUID = -3157889567656088293L;

    public CustomException(String type) {
        System.out.println(type + ": throw CustomException!");
    }
}
