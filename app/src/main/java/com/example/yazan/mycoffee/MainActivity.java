package com.example.yazan.mycoffee;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaouan.revealator.Revealator;
import com.jaouan.revealator.animations.AnimationListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     * Total price of order
     */
    public static TextView mTotalPriceTextView;
    /**
     * animation component
     */
    View mFab;
    View mPlaneImageView;
    View mPlaneLayout;
    View mInputsLayout;
    View mSkyLayout;
    View mSentLayout;
    View mCheckImageView;
    /**
     * Customer Name
     */
    EditText mCustomerNameEditText;
    /**
     * Table number
     */
    private String mTableNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // attach fab animation component
        mFab = findViewById(R.id.fab);
        mPlaneImageView = findViewById(R.id.plane);
        mPlaneLayout = findViewById(R.id.plane_layout);
        mInputsLayout = findViewById(R.id.inputs_layout);
        mSkyLayout = findViewById(R.id.sky_layout);
        mSentLayout = findViewById(R.id.sent_layout);
        mCheckImageView = findViewById(R.id.check);

        // attach Customer Name
        mCustomerNameEditText = (EditText) findViewById(R.id.customer_name);

        // attach table spinner
        Spinner tableSpinner = (Spinner) findViewById(R.id.tables_numbers_Spinner);

        // attach RecyclerView
        RecyclerView drinkRecyclerView = (RecyclerView) findViewById(R.id.list);

        //attach Total Price
        mTotalPriceTextView = (TextView) findViewById(R.id.total_price);

        //list of Drinks contains 3 drinks
        List<Drink> drinkList = new ArrayList<>();
        drinkList.add(new Drink("2 ", R.drawable.coffee, "Coffee"));
        drinkList.add(new Drink("1 ", R.drawable.tea, "Tea"));
        drinkList.add(new Drink("1.5 ", R.drawable.nescafe, "Nescafe"));

        //Create Drink Adapter from Drink List
        DrinkAdapter drinkAdapter = new DrinkAdapter(this, drinkList);

        //setup Drink RecyclerView with Drink Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        drinkRecyclerView.setLayoutManager(layoutManager);
        drinkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        drinkRecyclerView.setNestedScrollingEnabled(false);
        drinkRecyclerView.setAdapter(drinkAdapter);


        //setup the table Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.number_of_tables, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        tableSpinner.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        tableSpinner.setAdapter(spinnerAdapter);

        //setup listener to the table Spinner for user selection
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                mTableNumber = (String) adapterView.getItemAtPosition(pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Setup listener for fab button
        mFab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (DrinkAdapter.mTotalPrice == 0 || mTableNumber.equals("UNKNOWN")) {

                    if (DrinkAdapter.mTotalPrice == 0) {
                        Toast.makeText(MainActivity.this, "Please Select Drinks !", Toast.LENGTH_SHORT).show();

                    } else if (mTableNumber.equals("UNKNOWN")) {
                        Toast.makeText(MainActivity.this, "Please Select Table Number !", Toast.LENGTH_SHORT).show();

                    }

                    return;
                }
                send();
            }
        });

    }


    /**
     * Helper method for {@link #flyAway} method
     * <p>
     * create final summary of the order
     *
     * @return Order Summary
     */
    public String createOrderSummary() {

        //order summary
        String orderSummary = "";

        //getting user name
        String customerName = mCustomerNameEditText.getText().toString();

        //check if customer enter his name OR not,then append to order summary
        if (customerName.matches("")) {
            orderSummary += "Customer Name : Customer ";

        } else {
            orderSummary += "Customer Name :" + customerName;
        }

        //get instance of current date and time
        Calendar calendar = Calendar.getInstance();

        //create format for current time and date
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // append current time and date to order summary
        String time = timeFormat.format(calendar.getTime());
        String date = dateFormat.format(calendar.getTime());
        orderSummary += "\n" + "Time : " + time;
        orderSummary += "\n" + "Date : " + date;

        //append table number to order summary
        orderSummary += "\n" + "Table Number : " + mTableNumber;

        //append user drinks selection to order summary
        orderSummary += "\n" + "Order : ";
        if (DrinkAdapter.mCoffeeQuantity != 0) {
            orderSummary += DrinkAdapter.mCoffeeQuantity + " Coffee, ";
        }

        if (DrinkAdapter.mTeaQuantity != 0) {
            orderSummary += DrinkAdapter.mTeaQuantity + " Tea, ";
        }

        if (DrinkAdapter.mNescafeQuantity != 0) {
            orderSummary += DrinkAdapter.mNescafeQuantity + " Nescafe, ";
        }

        //append Total price to order summary
        orderSummary += "\n" + "Total Price : " + DrinkAdapter.mTotalPrice + "$";
        orderSummary += "\n" + "Thank You !";

        return orderSummary;
    }


    /**
     * Send something.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void send() {
        // - Prepare views visibility.
        mCheckImageView.setVisibility(View.INVISIBLE);
        mSentLayout.setVisibility(View.INVISIBLE);
        // - Rotate fab.
        final RotateAnimation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimation.setDuration(280);
        mFab.startAnimation(rotateAnimation);
        // - Hide inputs layout.
        final Animator circularReveal = ViewAnimationUtils.createCircularReveal(mInputsLayout,
                (int) (mFab.getX() + mFab.getWidth() / 2), (int) (mFab.getY() + mFab.getHeight() / 2),
                mInputsLayout.getHeight(), 0);
        circularReveal.setDuration(250);
        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // - Update views visibility.
                mInputsLayout.setVisibility(View.INVISIBLE);
                // - Fly away.
                flyAway();
            }
        });
        circularReveal.start();
    }

    /**
     * Starts fly animation.
     */
    private void flyAway() {
        // - Combine rotation and translation animations.
        final RotateAnimation rotateAnimation = new RotateAnimation(0, 180);
        rotateAnimation.setDuration(1000);
        mPlaneImageView.startAnimation(rotateAnimation);
        mPlaneImageView.setVisibility(View.INVISIBLE);
        Revealator.reveal(mSentLayout)
                .from(mPlaneLayout)
                .withTranslateDuration(1000)
                .withCurvedTranslation(new PointF(-1200, 0))
                .withRevealDuration(200)
                .withHideFromViewAtTranslateInterpolatedTime(.5f)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // - Display checked icon.
                        final ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                        scaleAnimation.setInterpolator(new BounceInterpolator());
                        scaleAnimation.setDuration(500);
                        scaleAnimation.setAnimationListener(new AnimationListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mInputsLayout.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        // - Restore inputs layout.
                                          retoreInputsLayout();

                                        // Use an intent to launch an email app.
                                        // Send the order summary to the email body.
                                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                                        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                                        String[] to = {"myCoffee@gmail.com"};
                                        intent.putExtra(Intent.EXTRA_EMAIL, to);
                                        intent.putExtra(Intent.EXTRA_SUBJECT,
                                                "Order Summary");
                                        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());

                                        //check if there is Activity to handle this intent
                                        if (intent.resolveActivity(getPackageManager()) != null) {
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Sorry you do not have email app !", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }, 1000);
                            }
                        });
                        mCheckImageView.startAnimation(scaleAnimation);
                        mCheckImageView.setVisibility(View.VISIBLE);
                    }
                }).start();
    }

    /**
     * Restores inputs layout.
     */
    private void retoreInputsLayout() {
        mInputsLayout.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                final Animator circularReveal = ViewAnimationUtils.createCircularReveal(mInputsLayout, (int) (mFab.getX() + mFab.getWidth() / 2), (int) (mFab.getY() + mFab.getHeight() / 2), 0, mInputsLayout.getHeight());
                circularReveal.setDuration(250);
                circularReveal.start();

                mInputsLayout.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

}
