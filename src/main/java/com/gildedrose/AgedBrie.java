package com.gildedrose;

public class AgedBrie extends InventoryItem {
    protected AgedBrie(Item item) {
        super(item);
    }

    @Override
    void age() {
        increaseQuality();
        decreaseSellIn();
        if (item.sellIn < 0) {
            increaseQuality();
        }
    }
}
