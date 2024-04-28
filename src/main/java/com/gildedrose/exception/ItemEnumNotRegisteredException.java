package com.gildedrose.exception;

/**
 * Exception to be thrown for items that couldn't have been operated int he ItemConverterService
 * 
 * @see com.gildedrose.service.ItemConverterService
 * @see com.gildedrose.service.impl.IgtemConverterServiceImpl
 */
public class ItemEnumNotRegisteredException extends RuntimeException {
	private String name;
	
	public ItemEnumNotRegisteredException(String name, String message) {
		super(message);
		this.name = name;		
	}
}
