package com.gotprint.services.notes.exception;

import com.gotprint.services.notes.bo.ErrorObject;


public class ValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ErrorObject error;
	
	public ValidationException(String message,ErrorObject error){
		super(message);
		this.error = error;
	}

    public ErrorObject getError()
    {
        return error;
    }

    public void setError(ErrorObject error)
    {
        this.error = error;
    }

	
	
}
