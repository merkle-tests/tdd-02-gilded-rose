package com.gildedrose;

public class InventoryItem {
    protected final Item item;

    protected InventoryItem(Item item) {
        this.item = item;
    }

    public static InventoryItem createInventoryItem(Item item) {
        if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
            return new Sulfuras(item);
        }
        if (item.name.equals("Aged Brie")) {
            return new AgedBrie(item);
        }
        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            return new BackstagePass(item);
        }
        return new InventoryItem(item);
    }

    void age() {
        decreaseQuality();
        decreaseSellIn();
        if (item.sellIn < 0) {
            decreaseQuality();
        }
    }

    protected void decreaseSellIn() {
        item.sellIn = item.sellIn - 1;
    }

    private void decreaseQuality() {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
    }

    protected void increaseQuality() {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }
}
