package org.elberjsn.encurtador_link.exception;

public class CustomError extends Exception{
    private int statusCode;
    private String message;

    public CustomError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public String getMessage() {
        return message; 
    }
    public CustomError(String message,int statusCode) {
        super(message+statusCode);
        this.message = message;
    }
    public CustomError(String message,Throwable e) {
        super(message,e);
        this.message = message;
    }
}
