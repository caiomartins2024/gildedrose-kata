package com.gildedrose.service.impl;

import org.springframework.stereotype.Service;

import com.gildedrose.config.ServiceParams;
import com.gildedrose.model.Item;
import com.gildedrose.service.ItemSellInService;

@Service
public class ItemSellInServiceImpl implements ItemSellInService {

	@Override
	public Item updateSellIn(Item item) {
		item.sellIn = item.sellIn - ServiceParams.STANDARD_SELL_IN_DECREMENT;
		return item;
	}	
}
