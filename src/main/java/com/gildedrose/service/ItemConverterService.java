package com.gildedrose.service;

import com.gildedrose.exception.ItemEnumNotRegisteredException;
import com.gildedrose.model.ItemEnum;

/**
 * Operations between an Item and its Enum
 * 
 * 
 * created at 25/04/2024
 * @see com.gildedrose.model.Item
 * @see com.gildedrose.model.ItemEnum
 * @version 1.0
 */
public interface ItemConverterService {
	/**
	 * It gets the ItemEnum from the item name
	 * @param name
	 * @throws ItemEnumNotRegisteredException - If the respective ItemEnum couldn't be found
	 * @return
	 */
	public ItemEnum convertFromName(String name) throws ItemEnumNotRegisteredException;
}
