package com.example.yazan.mycoffee;

/**
 * Created by yazan on 1/16/17.
 */

public class Drink {


    /**
     * drink price
     */

    private String mDrinkPrice;
    /**
     * drink image
     */
    private int mImageResourceId;

    /**
     * drink name
     */
    private String mDrinkName;


    /**
     * construct new {@link Drink} object
     *
     * @param drinkPrice      is price of drink
     * @param imageResourceId is the resourced of drink image
     * @param drinkName       is drink name
     */
    public Drink(String drinkPrice, int imageResourceId, String drinkName) {
        this.mDrinkPrice = drinkPrice;
        this.mImageResourceId = imageResourceId;
        this.mDrinkName = drinkName;


    }

    /**
     * @return drink price
     */
    public String getDrinkPrice() {
        return mDrinkPrice;
    }

    /**
     * @return image resource id
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * @return drink name
     */
    public String getDrinkName() {
        return mDrinkName;
    }


}



