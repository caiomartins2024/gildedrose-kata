package com.gildedrose.exception;

/**
 * Exception to be thrown if there was an issue during the updating processing of an item
 * 
 * @see com.gildedrose.service.UpdateService
 * @see com.gildedrose.service.impl.UpdateServiceImpl
 */
public class UnprocessableUpdateItemException extends RuntimeException {
    private String name;
	
    /**
     * 
     * @param name - Item's name
     * @param message
     */
	public UnprocessableUpdateItemException(String name, String message) {
		super(message);
		this.name = name;		
	}
}
