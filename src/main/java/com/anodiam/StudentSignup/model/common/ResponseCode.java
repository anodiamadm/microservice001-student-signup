package com.anodiam.StudentSignup.model.common;

public enum ResponseCode {
    SUCCESS(0, "SUCCESS: Microsvc001: Student Signup: Student registration successful!"),
    FAILURE(1, "ERR: Microsvc001: Student Signup: Failure with exception message: "),
    EMAIL_FORMAT_INVALID(2, "ERR: Microsvc001: Student Signup: Invalid Username / Email format used " +
            "for Sign Up!"),
    USER_ALREADY_EXISTS(3, "ERR: Microsvc001: Student Signup: Username / Email used for Sign Up is " +
            "already registered!"),
    PASSWORD_SHORT(4, "ERR: Microsvc001: Student Signup: Password cannot be < 6 characters!"),
    PASSWORD_INVALID(5, "ERR: Microsvc001: Student Signup: Password must be 6 to 100 characters " +
            "long and contain capital alphabet, number and special character!");

    private Integer id;
    private String message;

    ResponseCode(Integer id, String message){
        this.id = id;
        this.message=message;
    }

    public Integer getID(){
        return id;
    }
    public String getMessage() {return message;}
}