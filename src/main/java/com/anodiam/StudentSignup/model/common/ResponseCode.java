package com.anodiam.StudentSignup.model.common;

public enum ResponseCode {
    SUCCESS(0),
    BLANK(2),
    INVALID(3),
    WARNING(4),
    FAILURE(5),
    DUPLICATE(1062),
    UNAUTHORIZED(401);

    private Integer id;

    ResponseCode(Integer id){
        this.id = id;
    }

    public Integer getID(){
        return id;
    }

}