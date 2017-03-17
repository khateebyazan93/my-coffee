package com.example.yazan.mycoffee;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.yazan.mycoffee.MainActivity.mTotalPriceTextView;

/**
 * Created by yazan on 1/16/17.
 */

    /**
     * Created by yazan on 1/16/17.
     */

    public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

        /**Drink list*/
        private List<Drink> mDrinksList;

/**
 * context of App
 * */
        private Context mContext;


        /**
         * Total price of order
         */
        public static double mTotalPrice = 0;

        /**
         * number of coffee quantity
         */
        public static int mCoffeeQuantity =0;

        /**
         * number of Tee quantity
         */
        public static int mTeaQuantity = 0;

        /**
         * number of Tee quantity
         */
        public static int mNescafeQuantity = 0;


        /**
         * construct new {@link DrinkAdapter} object
         *
         * @param context    is  context of APp
         * @param drinksList is a list of drinks
         */
        public DrinkAdapter(Context context ,List<Drink> drinksList){
            this.mDrinksList =drinksList;
            this.mContext = context;

        }

        /**
         * Drink view holder class
         * */
        public static class DrinkViewHolder extends  RecyclerView.ViewHolder{

            /**
             * component of Drink list
             * */
            public ImageView mDrinkImageView;
            public CheckBox mDrinknameCheckBox;
            public TextView mDrinkpriceTextView;
            public Button mPlusButton;
            public Button mMinusButton;
            public TextView mDrinkQuantityTextView;

            /**
             *
             * construct new {@link DrinkViewHolder} object
             *
             * @param itemView    is  a list item that inflated from layout resources
             */
            public DrinkViewHolder(View itemView) {
                super(itemView);
                //attach component of Drink list
                mDrinkImageView = (ImageView) itemView.findViewById(R.id.drink_image);
                mDrinkpriceTextView = (TextView) itemView.findViewById(R.id.drink_price);
                mPlusButton = (Button) itemView.findViewById(R.id.plus);
                mDrinkQuantityTextView = (TextView) itemView.findViewById(R.id.drink_quantity);
                mMinusButton = (Button) itemView.findViewById(R.id.minus);
                mDrinknameCheckBox = (CheckBox)  itemView.findViewById(R.id.drink_name);



            }



        }



        @Override
        public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item , parent , false);

            return new DrinkViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final DrinkViewHolder holder, final int position) {
            //get drink position within the list
            Drink drinks = mDrinksList.get(position);

            //setup value if  drink image + drink name + drink price
            holder.mDrinkImageView.setImageResource(drinks.getImageResourceId());
            holder.mDrinkpriceTextView.setText(drinks.getDrinkPrice() + "$");
            holder.mDrinknameCheckBox.setText(drinks.getDrinkName());


              // setup listener for Plus Button
            holder.mPlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position == 0){
                      coffeePlus(holder);

                    }else if(position == 1){
                        teaPlus(holder);

                    }else if(position == 2){
                        nescafePlus(holder);

                    }
                }
            });

            // setup listener for Minus Button
            holder.mMinusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position == 0){
                        coffeaMinus(holder);

                    }else if(position == 1){
                        teaMinus(holder);

                    }else if(position == 2){
                        nescafeMinus(holder);

                    }
                }
            });

            // setup listener for drink name
            holder.mDrinknameCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position == 0){
                        coffeeCheckBox(holder);

                    }else if(position == 1){
                        teaCheckBox(holder);

                    }else if(position == 2){
                        nescafeCheckBox(holder);

                    }
                }
            });

        }


        /**
         * increase coffee quantity by 1
         */
        public void coffeePlus(DrinkViewHolder holder) {
            //Getting user selection
            boolean cofeeIsChecked = holder.mDrinknameCheckBox.isChecked();

            //exit if coffee quantity equal 10 OR the user doesn't select coffee
            if (mCoffeeQuantity == 10 || !cofeeIsChecked) {
                if (!cofeeIsChecked) {
                    Toast.makeText(mContext, "Please Select Coffee to Order The Quantity !", Toast.LENGTH_SHORT).show();
                } else if (mCoffeeQuantity == 10) {
                    Toast.makeText(mContext, "You Reached to The Max Order", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // increase and display coffee quantity
            mCoffeeQuantity += 1;
            holder.mDrinkQuantityTextView.setText(mCoffeeQuantity + " Cup");

            //Increase and display Total price
            mTotalPrice = mTotalPrice + 2 /*price of 1 cup of coffee*/;
           mTotalPriceTextView.setText(mTotalPrice + "$");


        }

        /**
         * decrease coffee quantity by 1
         *
         */
        public void coffeaMinus(DrinkViewHolder holder) {
            //Getting user selection
            boolean cofeeIsChecked = holder.mDrinknameCheckBox.isChecked();

            //exit if coffee quantity equal 0 OR the user doesn't select coffee
            if (mCoffeeQuantity == 1 || !cofeeIsChecked) {
                if (!cofeeIsChecked) {
                    Toast.makeText(mContext, "Please Select Coffee to Order The Quantity !", Toast.LENGTH_SHORT).show();

                } else if (mCoffeeQuantity == 1) {
                    Toast.makeText(mContext, "You Reached to The Minimum Order", Toast.LENGTH_SHORT).show();

                }

                return;
            }

            // // decrease and display coffee quantity
            mCoffeeQuantity -= 1;
            holder.mDrinkQuantityTextView.setText(mCoffeeQuantity + " Cup");

            //decrease and display Total price
            mTotalPrice = mTotalPrice - 2 /*price of 1 cup pf coffee*/;
            mTotalPriceTextView.setText(mTotalPrice + "$");

        }

        /**
         * increase Tee quantity by 1
         *
         */
        public void teaPlus(DrinkViewHolder holder) {

            //Getting user selection
            boolean teeIsChecked = holder.mDrinknameCheckBox.isChecked();

            //exit if Tee quantity equal 10 OR the user doesn't select Tee
            if (mTeaQuantity == 10 || !teeIsChecked) {
                if (!teeIsChecked) {
                    Toast.makeText(mContext, "Please Select Tee to Order The Quantity !", Toast.LENGTH_SHORT).show();

                } else if (mTeaQuantity == 10) {
                    Toast.makeText(mContext, "You Reached to The max Order", Toast.LENGTH_SHORT).show();

                }
                return;
            }

            //increase and display Tee Quantity
            mTeaQuantity += 1;
            holder.mDrinkQuantityTextView.setText(mTeaQuantity + " Cup");

            //increase and display total price
            mTotalPrice = mTotalPrice + 1/* price of 1 cup of Tee*/;
            mTotalPriceTextView.setText(mTotalPrice + "$");


        }

        /**
         * decrease Tee quantity by 1
         *
         */
        public void teaMinus(DrinkViewHolder holder) {
            //Getting user selection
            boolean teeIsChecked = holder.mDrinknameCheckBox.isChecked();
            // exit if Tee quantity equal 1 OR the user doesn't select the Tee
            if (mTeaQuantity == 1 || !teeIsChecked) {

                if(!teeIsChecked){
                    Toast.makeText(mContext, "Please Select Tee to Order The Quantity !", Toast.LENGTH_SHORT).show();
                }
                if(mTeaQuantity == 1){
                    Toast.makeText(mContext, "You Reached to The Minimum Order", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // decrease and display the Tee quantity
            mTeaQuantity -= 1;
            holder.mDrinkQuantityTextView.setText(mTeaQuantity + " Cup");

            //decrease and display the total price
            mTotalPrice = mTotalPrice - 1/* price of 1 cup of Tee*/;
            mTotalPriceTextView.setText(mTotalPrice + "$");


        }

        /**
         * increase Nescafe quantity by 1
         *
         */
        public void nescafePlus(DrinkViewHolder holder) {
            //Getting user selection
            boolean nescafeIsChecked = holder.mDrinknameCheckBox.isChecked();

            //exit if  nescafe quantity equal 10 OR the user doesn't select the Nescafe
            if (mNescafeQuantity == 10 || !nescafeIsChecked) {
                if(!nescafeIsChecked){
                    Toast.makeText(mContext, "Please Select Nescafe to Order The Quantity !", Toast.LENGTH_SHORT).show();
                }
                if(mNescafeQuantity == 10){
                    Toast.makeText(mContext, "You Reached to The max Order", Toast.LENGTH_SHORT).show();

                }
                return;
            }

            //increase and display nescafe quantity
            mNescafeQuantity += 1;
            holder.mDrinkQuantityTextView.setText(mNescafeQuantity + " Cup");

            //increase and display total price
            mTotalPrice = mTotalPrice + 1.5/*price of 1 cup of nescafe*/;
            mTotalPriceTextView.setText(mTotalPrice + "$");


        }

        /**
         * decrease Nescafe quantity by 1
         *
         */
        public void nescafeMinus(DrinkViewHolder holder) {
            //Getting user selection
            boolean nescafeIsChecked = holder.mDrinknameCheckBox.isChecked();

            //exit if the nescafe quantity equal 1 OR the user doesn't select the Nescafe
            if (mNescafeQuantity == 1 || !nescafeIsChecked) {
                if(!nescafeIsChecked){
                    Toast.makeText(mContext, "Please Select Nescafe to Order The Quantity !", Toast.LENGTH_SHORT).show();

                }
                if(mNescafeQuantity == 1){
                    Toast.makeText(mContext, "You Reached to The Minimum Order", Toast.LENGTH_SHORT).show();

                }
                return;
            }

            //decrease and display the nescafe quantity
            mNescafeQuantity -= 1;
            holder.mDrinkQuantityTextView.setText(mNescafeQuantity + " Cup");

            //decrease and display the total price
            mTotalPrice = mTotalPrice - 1.5/* price of 1 cup of nescafe*/;
            mTotalPriceTextView.setText(mTotalPrice + "$");

        }



        public void coffeeCheckBox(DrinkViewHolder holder){

           if (holder.mDrinknameCheckBox.isChecked()) {
               //update and display coffee quantity
               mCoffeeQuantity = 1;
               holder.mDrinkQuantityTextView.setText(mCoffeeQuantity + " Cup");

               //increase and display total price
               mTotalPrice = mTotalPrice + 2;
               MainActivity.mTotalPriceTextView.setText(mTotalPrice + "$");

             //  mDrinkOrderName += mCoffeeQuantity +" Coffe";

           } else if (!holder.mDrinknameCheckBox.isChecked()) {

               //calculate coffee price and subtract from total price
               double price = mCoffeeQuantity * 2;
               mTotalPrice = mTotalPrice - price;
               MainActivity.mTotalPriceTextView.setText(mTotalPrice + "$");

               //update and display coffee quantity
               mCoffeeQuantity = 0;
               holder.mDrinkQuantityTextView.setText(mCoffeeQuantity + " Cup");
           }

            }
        public void teaCheckBox(DrinkViewHolder holder){

            if(holder.mDrinknameCheckBox.isChecked()){

                //update and display Tee quantity
                mTeaQuantity = 1;
                holder.mDrinkQuantityTextView.setText(mTeaQuantity + " Cup");

                //increase and display the total price
                mTotalPrice =  mTotalPrice + 1;
               MainActivity.mTotalPriceTextView.setText(mTotalPrice + "$");

               // mDrinkOrderName += mTeaQuantity +" Tea";

            }else if(!holder.mDrinknameCheckBox.isChecked()){

                //calculate the Tee price and subtract from total price
                double price = mTeaQuantity * 1;
                mTotalPrice = mTotalPrice - price;
                MainActivity.mTotalPriceTextView.setText(mTotalPrice + "$");

                //update and display Tee quantity
                mTeaQuantity = 0;
                holder.mDrinkQuantityTextView.setText(mTeaQuantity + " Cup");

            }

        }
        public void nescafeCheckBox(DrinkViewHolder holder){
            if(holder.mDrinknameCheckBox.isChecked()){
                //update and display nescafe quantity
                mNescafeQuantity = 1;
                holder.mDrinkQuantityTextView.setText(mNescafeQuantity + " Cup");

                //increase and display total price
                mTotalPrice = mTotalPrice + 1.5;
                MainActivity.mTotalPriceTextView.setText(mTotalPrice + "$");

               // mDrinkOrderName += mNescafeQuantity +" Nescafe,";


            }else if(!holder.mDrinknameCheckBox.isChecked()){
                //calculate the nescafe price and subtract from total price
                double price = mNescafeQuantity * 1.5;
                mTotalPrice = mTotalPrice - price;
               MainActivity.mTotalPriceTextView.setText(mTotalPrice + "$");

                //update and display nescafe quantity
                mNescafeQuantity = 0;
                holder.mDrinkQuantityTextView.setText(mNescafeQuantity + " Cup");

            }
        }

        @Override
        public int getItemCount() {
            return mDrinksList.size();
        }


    }
