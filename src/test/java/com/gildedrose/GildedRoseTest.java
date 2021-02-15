package com.gildedrose;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GildedRoseTest {

    private static final String AGED_BRIE = "Aged Brie";
    private static final int MAX_QUALITY = 50;
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    private static final int SULFURAS_QUALITY = 80;

    @Test
    public void sellIn_decreases_atEOD() {

        //Given
        Item item = anItem(10, 10);

        //When
        aGildedRose(item).updateQuality();

        //Then
        assertEquals(9, item.sellIn);
    }

    @Test
    public void quality_decreases_atEOD() {

        //Given
        Item notExpiredItem = anItem(10, 10);

        //When
        aGildedRose(notExpiredItem).updateQuality();

        //Then
        assertEquals(9, notExpiredItem.quality);
    }

    @Test
    public void quality_decreasesTwiceAsFast_whenExpired() {

        //Given
        Item expiredItem = anItem(0, 10);
        Item expiredItemForDays = anItem(-2, 10);

        //When
        aGildedRose(expiredItem, expiredItemForDays).updateQuality();

        //Then
        assertEquals(8, expiredItem.quality);
        assertEquals(8, expiredItemForDays.quality);
    }

    @Test
    public void quality_isNeverNegative() {

        //Given
        Item notExpiredItemWithNoQuality = anItem(1, 0);
        Item expiredItemWithNoQuality = anItem(0, 0);
        Item expiredItemWithQuality = anItem(0, 1);

        //When
        aGildedRose(expiredItemWithNoQuality, expiredItemWithQuality, notExpiredItemWithNoQuality).updateQuality();

        //Then
        assertEquals(0, expiredItemWithNoQuality.quality);
        assertEquals(0, expiredItemWithQuality.quality);
        assertEquals(0, notExpiredItemWithNoQuality.quality);
    }

    @Test
    public void quality_increases_forAgedBrie() {

        //Given
        Item notExpiredAgedBrie = anItem(AGED_BRIE, 10, 1);
        Item expiredAgedBrie = anItem(AGED_BRIE, 0, 1);

        //When
        aGildedRose(expiredAgedBrie, notExpiredAgedBrie).updateQuality();

        //Then
        assertEquals(3, expiredAgedBrie.quality);
        assertEquals(2, notExpiredAgedBrie.quality);
    }

    @Test
    public void quality_neverExceedsMaxValue_forAgedBrie() {

        //Given
        Item notExpiredAgedBrie = anItem(AGED_BRIE, 10, MAX_QUALITY);
        Item expiredAgedBrie = anItem(AGED_BRIE, 0, MAX_QUALITY - 1);

        //When
        aGildedRose(expiredAgedBrie, notExpiredAgedBrie).updateQuality();

        //Then
        assertEquals(MAX_QUALITY, expiredAgedBrie.quality);
        assertEquals(MAX_QUALITY, notExpiredAgedBrie.quality);
    }

    @Test
    public void quality_neverDecreases_forSulfuras() {

        //Given
        Item notExpiredSulfuras = anItem(SULFURAS, 10, SULFURAS_QUALITY);
        Item expiredSulfuras = anItem(SULFURAS, 0, SULFURAS_QUALITY);
        Item expiredSulfurasForDays = anItem(SULFURAS, -1, SULFURAS_QUALITY);

        //When
        aGildedRose(notExpiredSulfuras, expiredSulfuras, expiredSulfurasForDays).updateQuality();

        //Then
        assertEquals(SULFURAS_QUALITY, notExpiredSulfuras.quality);
        assertEquals(SULFURAS_QUALITY, expiredSulfuras.quality);
        assertEquals(SULFURAS_QUALITY, expiredSulfurasForDays.quality);
    }

    @Test
    public void sellIn_neverDecreases_forSulfuras() {

        //Given
        Item notExpiredSulfuras = anItem(SULFURAS, 10, 10);
        Item expiredSulfuras = anItem(SULFURAS, 0, 10);

        //When
        aGildedRose(notExpiredSulfuras, expiredSulfuras).updateQuality();

        //Then
        assertEquals(10, notExpiredSulfuras.sellIn);
        assertEquals(0, expiredSulfuras.sellIn);
    }

    @Test
    public void quality_increases_MoreThanTenDaysBeforeConcert_forBackstage() {

        //Given
        Item elevenDaysBeforeConcert = anItem(BACKSTAGE, 11, 10);

        //When
        aGildedRose(elevenDaysBeforeConcert).updateQuality();

        //Then
        assertEquals(11, elevenDaysBeforeConcert.quality);
    }

    @Test
    public void quality_increasesByTwo_BetweenFiveAndTenDaysBeforeConcert_forBackstage() {

        //Given
        Item tenDaysBeforeConcert = anItem(BACKSTAGE, 10, 10);
        Item sixDaysBeforeConcert = anItem(BACKSTAGE, 6, 10);

        //When
        aGildedRose(tenDaysBeforeConcert, sixDaysBeforeConcert).updateQuality();

        //Then
        assertEquals(12, tenDaysBeforeConcert.quality);
        assertEquals(12, sixDaysBeforeConcert.quality);
    }

    @Test
    public void quality_increasesByThree_LessThanFiveDaysBeforeConcert_forBackstage() {

        //Given
        Item fiveDaysBeforeConcert = anItem(BACKSTAGE, 5, 10);
        Item oneDayBeforeConcert = anItem(BACKSTAGE, 1, 10);

        //When
        aGildedRose(fiveDaysBeforeConcert, oneDayBeforeConcert).updateQuality();

        //Then
        assertEquals(13, fiveDaysBeforeConcert.quality);
        assertEquals(13, oneDayBeforeConcert.quality);
    }

    @Test
    public void quality_dropsToZero_AfterTheConcert_forBackstage() {

        //Given
        Item oneDayAfterTheConcert = anItem(BACKSTAGE, 0, 10);
        Item twoDaysAfterTheConcert = anItem(BACKSTAGE, -1, 10);

        //When
        aGildedRose(oneDayAfterTheConcert, twoDaysAfterTheConcert).updateQuality();

        //Then
        assertEquals(0, oneDayAfterTheConcert.quality);
        assertEquals(0, twoDaysAfterTheConcert.quality);
    }

    @Test
    public void quality_neverExceedsMaxValue_forBackstage() {

        //Given
        Item elevenDaysBeforeConcert = anItem(BACKSTAGE, 11, MAX_QUALITY - 1);
        Item tenDaysBeforeConcert = anItem(BACKSTAGE, 10, MAX_QUALITY - 1);
        Item sixDaysBeforeConcert = anItem(BACKSTAGE, 6, MAX_QUALITY - 1);
        Item fiveDaysBeforeConcert = anItem(BACKSTAGE, 5, MAX_QUALITY - 1);
        Item oneDayBeforeConcert = anItem(BACKSTAGE, 1, MAX_QUALITY - 1);

        //When
        aGildedRose(elevenDaysBeforeConcert, tenDaysBeforeConcert, sixDaysBeforeConcert,
                fiveDaysBeforeConcert, oneDayBeforeConcert
        ).updateQuality();

        //Then
        assertEquals(MAX_QUALITY, elevenDaysBeforeConcert.quality);
        assertEquals(MAX_QUALITY, tenDaysBeforeConcert.quality);
        assertEquals(MAX_QUALITY, sixDaysBeforeConcert.quality);
        assertEquals(MAX_QUALITY, fiveDaysBeforeConcert.quality);
        assertEquals(MAX_QUALITY, oneDayBeforeConcert.quality);
    }

    private Item anItem(String name, int sellIn, int quality) {
        return new Item(name, sellIn, quality);
    }

    private Item anItem(int sellIn, int quality) {
        return anItem("foo", sellIn, quality);
    }

    private GildedRose aGildedRose(Item... items) {
        return new GildedRose(items);
    }
}
