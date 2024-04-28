package com.gildedrose.service.impl;

import org.springframework.stereotype.Service;

import com.gildedrose.model.Item;
import com.gildedrose.service.QualityService;

/**
 * Operations over a quality of an item
 * 
 * 
 * created at 25/04/2024
 * @version 1.0
 */
@Service
public class QualityServiceImpl implements QualityService {

	/**
	 * It increments the quality of an item with the value passed in the param
	 * @param Item
	 * @param qualityIncrease - The amount of quality to be increased
	 * @return The item with the updated value 
	 */
	@Override
	public Item increment(Item item, int qualityIncrease) {
		item.quality = item.quality + qualityIncrease;
		return item;
	}
	
	/**
	 * It decrements the quality of an item with the value passed in the param
	 * @param Item
	 * @param qualityDecrease - The amount of quality to be decreased
	 * @return The item with the updated value 
	 */
	public Item decrement(Item item, int qualityDecrease) {
		item.quality = item.quality - qualityDecrease;
		return item;
	}

	/**
	 * It sets the quality of an item with the exactly amount passed in the param
	 * @param Item
	 * @param quality - The amount of quality to be exactly set
	 * @return The item with the updated value 
	 */
	@Override
	public Item set(Item item, int quality) {
		item.quality = quality;
		return item;
	}
	
}
