package com.gildedrose.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gildedrose.exception.UnprocessableUpdateItemException;
import com.gildedrose.model.Item;
import com.gildedrose.model.ItemEnum;
import com.gildedrose.service.ItemConverterService;
import com.gildedrose.service.ItemSellInService;
import com.gildedrose.service.QualityService;
import com.gildedrose.service.impl.ItemConverterServiceImpl;
import com.gildedrose.service.impl.ItemSellInServiceImpl;
import com.gildedrose.service.impl.QualityServiceImpl;
import com.gildedrose.strategy.ItemStrategy;

/**
 * Update procedure for Sulfuras item.
 * 
 * created at 25/04/2024
 * @version 1.0
 */
@Component
public class SulfurasItemUpdateStrategy implements ItemStrategy {

	private static final int MAX_LEGENDARY_QUALITY = 80;
	
	@Autowired
	private ItemConverterService itemConverterService;
	
	@Autowired
	private ItemSellInService itemSellInService;
	
	@Autowired
	private QualityService qualityService;
	
	public SulfurasItemUpdateStrategy() {
		this.itemConverterService = new ItemConverterServiceImpl();
		this.itemSellInService = new ItemSellInServiceImpl();
		this.qualityService = new QualityServiceImpl();
	}
	

	/**
	 * It decreases by one day the selling date and decreases quality by 2 (If the selling date reaches 0, then it decreases the quality by 4
	 * If quality reaches  value 0,then, it will always remain this value (It will never gets negative)
	 * 
	 * @param An item
	 * @return The same item but with the updated values (Same reference)
	 */
	@Override
	public Item update(Item item) {
		ItemEnum itemEnum = itemConverterService.convertFromName(item.name);
		if (itemEnum != ItemEnum.SULFURAS) {
			throw new UnprocessableUpdateItemException(item.name,"Item " + item.name + " is not of " + ItemEnum.SULFURAS + "type" );
		}
		
		item = itemSellInService.updateSellIn(item);
		
		item = qualityService.set(item, MAX_LEGENDARY_QUALITY);
		
		return item;
	}
}
