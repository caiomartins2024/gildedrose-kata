package com.gildedrose.service;

import com.gildedrose.model.Item;

public interface QualityService {
	public Item increment(Item Item, int qualityIncrease);
	public Item set(Item item, int quality);
	public Item decrement(Item item, int qualityIncrease);
}
