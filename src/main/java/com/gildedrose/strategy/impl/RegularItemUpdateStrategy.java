package com.gildedrose.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gildedrose.config.ServiceParams;
import com.gildedrose.model.Item;
import com.gildedrose.service.ItemSellInService;
import com.gildedrose.service.QualityService;
import com.gildedrose.service.impl.ItemSellInServiceImpl;
import com.gildedrose.service.impl.QualityServiceImpl;
import com.gildedrose.strategy.ItemStrategy;

@Component
public class RegularItemUpdateStrategy implements ItemStrategy {

	private static final int QUALITY_DECREASE_RATE = 1;
	
	@Autowired
	private ItemSellInService itemSellInService;
	
	@Autowired
	private QualityService qualityService;
	
	public RegularItemUpdateStrategy() {
		this.itemSellInService = new ItemSellInServiceImpl();
		this.qualityService = new QualityServiceImpl();
	}

	@Override
	public Item update(Item item) {
		item = itemSellInService.updateSellIn(item);
		
		if (item.sellIn <= 0)  {
			item = this.qualityService.decrement(item, 2*QUALITY_DECREASE_RATE);

		} else {
			item = this.qualityService.decrement(item, QUALITY_DECREASE_RATE);
		}
				
		if (item.quality < ServiceParams.STANDARD_MIN_QUALITY) {
			this.qualityService.set(item, ServiceParams.STANDARD_MIN_QUALITY);
		}
		
		return item;
	}
}
