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
public class ConjuredItemUpdateStrategy implements ItemStrategy {

	private static final int CONJURED_DECREASE_RATE = 2;

	@Autowired
	private ItemConverterService itemConverterService;
	
	@Autowired
	private ItemSellInService itemSellInService;

	@Autowired
	private QualityService qualityService;
	
	public ConjuredItemUpdateStrategy() {
		this.itemConverterService = new ItemConverterServiceImpl();
		this.itemSellInService = new ItemSellInServiceImpl();
		this.qualityService = new QualityServiceImpl();
	}
	
	@Override
	public Item update(Item item) {
		ItemEnum itemEnum = itemConverterService.convertFromName(item.name);
		if (itemEnum != ItemEnum.CONJURED) {
			throw new UnprocessableUpdateItemException(item.name,"Item " + item.name + " is not of " + ItemEnum.CONJURED + "type" );
		}
		
		item = itemSellInService.updateSellIn(item);
		
		if (item.sellIn <= 0)  {
			item = this.qualityService.decrement(item, 2*CONJURED_DECREASE_RATE);

		} else {
			item = this.qualityService.decrement(item, CONJURED_DECREASE_RATE);
		}
		
		if (item.quality < ServiceParams.STANDARD_MIN_QUALITY) {
			this.qualityService.set(item, ServiceParams.STANDARD_MIN_QUALITY);
		}
		
		return item;
	}
}
