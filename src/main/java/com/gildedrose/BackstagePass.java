package com.gildedrose;

public class BackstagePass extends InventoryItem {
    public BackstagePass(Item item) {
        super(item);
    }

    @Override
    void age() {
        increaseQuality();
        if (item.sellIn < 11) {
            increaseQuality();
        }
        if (item.sellIn < 6) {
            increaseQuality();
        }
        decreaseSellIn();
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }
}
