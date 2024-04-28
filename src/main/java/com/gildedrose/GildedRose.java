package com.gildedrose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gildedrose.model.Item;
import com.gildedrose.service.UpdateService;
import com.gildedrose.service.impl.UpdateServiceImpl;

/**
 * Class in charge of updating items according to the current update stratagies/polices
 * 
 * @see com.gildedrose.service.UpdateService
 * @see com.gildedrose.strategy.ItemStrategy
 *  
 * created at 01/12/2023
 * @version !.0
 */
@Service
class GildedRose {
    Item[] items;
    
    @Autowired
    private UpdateService updateService;
    

    public GildedRose(Item[] items) {
        this.items = items;
        updateService = new UpdateServiceImpl();
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
        	updateService.update(items[i]);        	
        }
    }
}
