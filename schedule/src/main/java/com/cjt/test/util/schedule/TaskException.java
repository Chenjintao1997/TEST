package com.cjt.test.util.schedule;

/**
 * @Author: chenjintao
 * @Date: 2019/9/20 15:01
 */
public class TaskException extends RuntimeException {
    private String code;

    private String message;

    public TaskException() {
        super();
    }

    public TaskException(String message) {
        super(message);
        this.message = message;
    }

    public TaskException(Exception e) {
        super(e);
    }

    public TaskException(String message, Exception e) {
        super(message, e);
        this.message = message;
    }

    public TaskException(String code, String message) {
        super(message);
        this.code = code;
    }

    public TaskException(String code, String message, Exception e) {
        super(message, e);
        this.code = code;
        this.message = message;
    }


}
