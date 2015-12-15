package com.gotprint.services.notes.exception;

public class ApplicationException extends Exception
{
    /**
     * ApplicationException
     */
    private static final long serialVersionUID = 6446457688886941527L;

    private String code;

    private String message;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * constructs the message for any application exception
     * 
     * @param code error code
     * @param message error message
     */

    public ApplicationException(String msg, String code)
    {
        super(msg);
        this.message = msg;
        this.code = code;
    }
}
