package com.gildedrose.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gildedrose.config.ServiceParams;
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

@Component
public class BackstagedPassesItemUpdateStrategy implements ItemStrategy {

	private static final int ZERO_DAYS = 0;
	private static final int SIX_DAYS = 6;
	private static final int TEN_DAYS = 10;
	private static final int STANDARD_QUALITY_INCREASE_RATE = 1;
	private static final int FIRST_QUALITY_INCREASE_RATE = 2;
	private static final int SECOND_QUALITY_INCREASE_RATE = 3;

	@Autowired
	private ItemConverterService itemConverterService;
	
	@Autowired
	private ItemSellInService itemSellInService;
	
	@Autowired
	private QualityService qualityService;
	
	public BackstagedPassesItemUpdateStrategy() {
		this.itemConverterService = new ItemConverterServiceImpl();
		this.itemSellInService = new ItemSellInServiceImpl();
		this.qualityService = new QualityServiceImpl();
	}
	

	@Override
	public Item update(Item item) {
		ItemEnum itemEnum = itemConverterService.convertFromName(item.name);
		if (itemEnum != ItemEnum.BACKSTAGED_PASSES) {
			throw new UnprocessableUpdateItemException(item.name,"Item " + item.name + " is not of " + ItemEnum.REGULAR + "type" );
		}
		
		item = itemSellInService.updateSellIn(item);
		
		if (item.sellIn > TEN_DAYS) {
			item = this.qualityService.increment(item, STANDARD_QUALITY_INCREASE_RATE);
		}
		
		if (item.sellIn >= SIX_DAYS && item.sellIn <= TEN_DAYS) {
			item = this.qualityService.increment(item, FIRST_QUALITY_INCREASE_RATE);
		}
		
		if (item.sellIn < SIX_DAYS) {
			item = this.qualityService.increment(item, SECOND_QUALITY_INCREASE_RATE);
		}
		
		if (item.sellIn == ZERO_DAYS) {
			item = this.qualityService.set(item, ServiceParams.STANDARD_MIN_QUALITY);
		}
		
		
		if (item.quality < ServiceParams.STANDARD_MIN_QUALITY) {
			item.quality = ServiceParams.STANDARD_MIN_QUALITY;
		}
		
		return item;
	}
}
