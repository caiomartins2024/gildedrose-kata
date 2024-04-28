package com.gildedrose.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gildedrose.exception.ItemEnumNotRegisteredException;
import com.gildedrose.model.Item;
import com.gildedrose.model.ItemEnum;
import com.gildedrose.service.ItemConverterService;
import com.gildedrose.service.UpdateService;
import com.gildedrose.strategy.ItemStrategy;
import com.gildedrose.strategy.impl.AgedBrieItemUpdateStrategy;
import com.gildedrose.strategy.impl.BackstagedPassesItemUpdateStrategy;
import com.gildedrose.strategy.impl.ConjuredItemUpdateStrategy;
import com.gildedrose.strategy.impl.RegularItemUpdateStrategy;
import com.gildedrose.strategy.impl.SulfurasItemUpdateStrategy;

/**
 * Updates the data of an item according to the current day.
 * 
 * @see com.gilderose.strategy.impl
 * 
 * created at 25/04/2024
 * @version 1.0
 */
@Service
public class UpdateServiceImpl implements UpdateService {

	private final static Map<ItemEnum, ItemStrategy> updateStrategies = new HashMap<>();
	
	static {
		updateStrategies.put(ItemEnum.REGULAR, new RegularItemUpdateStrategy());
		updateStrategies.put(ItemEnum.AGED_BRIE, new AgedBrieItemUpdateStrategy());
		updateStrategies.put(ItemEnum.BACKSTAGED_PASSES, new BackstagedPassesItemUpdateStrategy());
		updateStrategies.put(ItemEnum.SULFURAS, new SulfurasItemUpdateStrategy());
		updateStrategies.put(ItemEnum.CONJURED, new ConjuredItemUpdateStrategy());
	}
	
	@Autowired
    private ItemConverterService itemConverterService;
	
	public UpdateServiceImpl() {
		itemConverterService = new ItemConverterServiceImpl();
	}
	
	@Override
	public Item update(Item item) {
		ItemEnum itemEnum = null;
		
		try {
			itemEnum = itemConverterService.convertFromName(item.name);
		} catch (ItemEnumNotRegisteredException ienotRegEx) {
			itemEnum = ItemEnum.REGULAR;
		}
		
		Item itemUpdated = updateStrategies.get(itemEnum).update(item);
		
		return itemUpdated;
	}	
}
