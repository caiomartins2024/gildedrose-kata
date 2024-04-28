package com.gildedrose.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gildedrose.exception.ItemEnumNotRegisteredException;
import com.gildedrose.model.ItemEnum;
import com.gildedrose.service.ItemConverterService;

@Service
public class ItemConverterServiceImpl implements ItemConverterService{
	private static final Map<String, ItemEnum> itemEnumMapping = new HashMap<>();
	
	static {
		itemEnumMapping.put("Aged Brie", ItemEnum.AGED_BRIE);
		itemEnumMapping.put("Backstage passes to a TAFKAL80ETC concert", ItemEnum.BACKSTAGED_PASSES);
		itemEnumMapping.put("Conjured", ItemEnum.CONJURED);
		itemEnumMapping.put("Sulfuras, Hand of Ragnaros", ItemEnum.SULFURAS);
	}
	
	public ItemEnum convertFromName(String name) {
		ItemEnum itemEnum = itemEnumMapping.get(name);
		
		if (itemEnum == null) {
			throw new ItemEnumNotRegisteredException(name, "Item "+ name + " not found");
		}
		
		return itemEnum;
	}	
}
