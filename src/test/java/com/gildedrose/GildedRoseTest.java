package com.gildedrose;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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
        assertThat(item.sellIn, equalTo(9));

    }

    @Test
    public void quality_decreases_atEOD() {

        //Given
        Item notExpiredItem = anItem(10, 10);

        //When
        aGildedRose(notExpiredItem).updateQuality();

        //Then
        assertThat(notExpiredItem.quality, equalTo(9));

    }

    @Test
    public void quality_decreasesTwiceAsFast_whenExpired() {

        //Given
        Item expiredItem = anItem(0, 10);
        Item expiredItemForDays = anItem(-2, 10);

        //When
        aGildedRose(expiredItem, expiredItemForDays).updateQuality();

        //Then
        assertThat(expiredItem.quality, equalTo(8));
        assertThat(expiredItemForDays.quality, equalTo(8));

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
        assertThat(expiredItemWithNoQuality.quality, equalTo(0));
        assertThat(expiredItemWithQuality.quality, equalTo(0));
        assertThat(notExpiredItemWithNoQuality.quality, equalTo(0));
    }

    @Test
    public void quality_increases_forAgedBrie() {

        //Given
        Item notExpiredAgedBrie = anItem(AGED_BRIE, 10, 1);
        Item expiredAgedBrie = anItem(AGED_BRIE, 0, 1);

        //When
        aGildedRose(expiredAgedBrie, notExpiredAgedBrie).updateQuality();

        //Then
        assertThat(expiredAgedBrie.quality, equalTo(3));
        assertThat(notExpiredAgedBrie.quality, equalTo(2));
    }

    @Test
    public void quality_neverExceedsMaxValue_forAgedBrie() {

        //Given
        Item notExpiredAgedBrie = anItem(AGED_BRIE, 10, MAX_QUALITY);
        Item expiredAgedBrie = anItem(AGED_BRIE, 0, MAX_QUALITY - 1);

        //When
        aGildedRose(expiredAgedBrie, notExpiredAgedBrie).updateQuality();

        //Then
        assertThat(expiredAgedBrie.quality, equalTo(MAX_QUALITY));
        assertThat(notExpiredAgedBrie.quality, equalTo(MAX_QUALITY));
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
        assertThat(notExpiredSulfuras.quality, equalTo(SULFURAS_QUALITY));
        assertThat(expiredSulfuras.quality, equalTo(SULFURAS_QUALITY));
        assertThat(expiredSulfurasForDays.quality, equalTo(SULFURAS_QUALITY));
    }

    @Test
    public void sellIn_neverDecreases_forSulfuras() {

        //Given
        Item notExpiredSulfuras = anItem(SULFURAS, 10, 10);
        Item expiredSulfuras = anItem(SULFURAS, 0, 10);

        //When
        aGildedRose(notExpiredSulfuras, expiredSulfuras).updateQuality();

        //Then
        assertThat(notExpiredSulfuras.sellIn, equalTo(10));
        assertThat(expiredSulfuras.sellIn, equalTo(0));
    }

    @Test
    public void quality_increases_MoreThanTenDaysBeforeConcert_forBackstage() {

        //Given
        Item elevenDaysBeforeConcert = anItem(BACKSTAGE, 11, 10);

        //When
        aGildedRose(elevenDaysBeforeConcert).updateQuality();

        //Then
        assertThat(elevenDaysBeforeConcert.quality, equalTo(11));
    }

    @Test
    public void quality_increasesByTwo_BetweenFiveAndTenDaysBeforeConcert_forBackstage() {

        //Given
        Item tenDaysBeforeConcert = anItem(BACKSTAGE, 10, 10);
        Item sixDaysBeforeConcert = anItem(BACKSTAGE, 6, 10);

        //When
        aGildedRose(tenDaysBeforeConcert, sixDaysBeforeConcert).updateQuality();

        //Then
        assertThat(tenDaysBeforeConcert.quality, equalTo(12));
        assertThat(sixDaysBeforeConcert.quality, equalTo(12));
    }

    @Test
    public void quality_increasesByThree_LessThanFiveDaysBeforeConcert_forBackstage() {

        //Given
        Item fiveDaysBeforeConcert = anItem(BACKSTAGE, 5, 10);
        Item oneDayBeforeConcert = anItem(BACKSTAGE, 1, 10);

        //When
        aGildedRose(fiveDaysBeforeConcert, oneDayBeforeConcert).updateQuality();

        //Then
        assertThat(fiveDaysBeforeConcert.quality, equalTo(13));
        assertThat(oneDayBeforeConcert.quality, equalTo(13));
    }

    @Test
    public void quality_dropsToZero_AfterTheConcert_forBackstage() {

        //Given
        Item oneDayAfterTheConcert = anItem(BACKSTAGE, 0, 10);
        Item twoDaysAfterTheConcert = anItem(BACKSTAGE, -1, 10);

        //When
        aGildedRose(oneDayAfterTheConcert, twoDaysAfterTheConcert).updateQuality();

        //Then
        assertThat(oneDayAfterTheConcert.quality, equalTo(0));
        assertThat(twoDaysAfterTheConcert.quality, equalTo(0));
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
        assertThat(elevenDaysBeforeConcert.quality, equalTo(MAX_QUALITY));
        assertThat(tenDaysBeforeConcert.quality, equalTo(MAX_QUALITY));
        assertThat(sixDaysBeforeConcert.quality, equalTo(MAX_QUALITY));
        assertThat(fiveDaysBeforeConcert.quality, equalTo(MAX_QUALITY));
        assertThat(oneDayBeforeConcert.quality, equalTo(MAX_QUALITY));
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
