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
public class AgedBrieItemUpdateStrategy implements ItemStrategy {

	private static final int QUALITY_INCREMENT = 1;
	
	@Autowired
	private ItemConverterService itemConverterService;
	
	@Autowired
	private ItemSellInService itemSellInService;

	@Autowired
	private QualityService qualityService;
	
	public AgedBrieItemUpdateStrategy() {
		itemConverterService = new ItemConverterServiceImpl();
		itemSellInService = new ItemSellInServiceImpl();
		qualityService = new QualityServiceImpl();
	}

	@Override
	public Item update(Item item) {
		ItemEnum itemEnum = itemConverterService.convertFromName(item.name);
		if (itemEnum != ItemEnum.AGED_BRIE) {
			throw new UnprocessableUpdateItemException(item.name,"Item " + item.name + " is not of " + ItemEnum.AGED_BRIE + "type" );
		}
		
		item = this.itemSellInService.updateSellIn(item);
		
		if (item.quality <= ServiceParams.MAX_GENERAL_QUALITY) {
			item = qualityService.increment(item, QUALITY_INCREMENT);
		}
		
		return item;
	}
}
