package com.robotsoftware.orderingapp.activities.views.layouts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.robotsoftware.orderingapp.R;
import com.robotsoftware.orderingapp.activities.controllers.Validate;
import com.robotsoftware.orderingapp.activities.controllers.services.Progress;
import com.robotsoftware.orderingapp.activities.models.UserOrder;
import com.robotsoftware.orderingapp.database.DatabaseSQLite;

public class OrderCartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "OrderCartActivity";
    /**
     * this fields attribute
     */
    private ProgressDialog mProgress;
    private EditText mFieldName;
    private RadioButton mRadioCoffee, mRadioMilk, mRadioSpaghetti, mRadioBread;
    private Button mBtnDecrement, mBtnIncrement, mBtnOrder;
    private String resultName = null;
    private String resultOrder = null;
    private int resultQuantity = 0;
    private int resultPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);

        //call methods class
        setupToolbar();
        initViews();
        initBtnOnClick();
    }

    /**
     * this for setup toolbar
     */
    private void setupToolbar() {

        Toolbar toolbar = findViewById(R.id.support_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "setupToolbar():Success");
    }

    /**
     * this for inflate menu layout to action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem orderCart = menu.findItem(R.id.action_cart);
        orderCart.setVisible(false);    //hide order cart

        return true;
    }

    /**
     * this for item on menu action bar is selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get item id
        int itemId = item.getItemId();

        if (itemId == R.id.action_about) {
            //intent to activity about
            Intent aboutIntent = new Intent(OrderCartActivity.this, ActivityAboutApp.class);
            aboutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(aboutIntent);

            Log.d(TAG, "onOptionsItemSelected(MenuItem item):AboutAppIntent:Success");
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * this for initially layout resources
     */
    private void initViews() {

        //Progress
        mProgress = new ProgressDialog(OrderCartActivity.this);

        //Edit Text
        mFieldName = findViewById(R.id.fieldName);

        //Check Box
        mRadioCoffee = findViewById(R.id.radioCoffee);
        mRadioMilk = findViewById(R.id.radioMilk);
        mRadioSpaghetti = findViewById(R.id.radioSpaghetti);
        mRadioBread = findViewById(R.id.radioBread);

        //Button
        mBtnDecrement = findViewById(R.id.btnDecrement);
        mBtnIncrement = findViewById(R.id.btnIncrement);
        mBtnOrder = findViewById(R.id.btnOrder);

        Log.d(TAG, "initViews():Success");
    }

    /**
     * this for initially btn set onclick
     */
    private void initBtnOnClick() {
        mBtnDecrement.setOnClickListener(OrderCartActivity.this);
        mBtnIncrement.setOnClickListener(OrderCartActivity.this);
        mBtnOrder.setOnClickListener(OrderCartActivity.this);

        Log.d(TAG, "initBtnOnClick():Success");
    }

    /**
     * this for do action of btn
     */
    @Override
    public void onClick(View view) {

        //get id btn
        int getBtnId = view.getId();

        //do action for button
        if (getBtnId == R.id.btnDecrement) {
            doDecrement();

            Log.d(TAG, "onClick(View view):Decrement:Success");
        } else if (getBtnId == R.id.btnIncrement) {
            doIncrement();

            Log.d(TAG, "onClick(View view):Increment:Success");
        } else if (getBtnId == R.id.btnOrder) {
            doOrder();

            Log.d(TAG, "onClick(View view):doOrder:Success");
        }
    }

    /**
     * this do action decrement
     */
    private void doDecrement() {
        if (resultQuantity == 0) {
            return;
        }
        resultQuantity = resultQuantity - 1;
        displayQuantity(resultQuantity);
    }

    /**
     * this do action increment
     */
    private void doIncrement() {
        if (resultQuantity == 100) {
            return;
        }
        resultQuantity = resultQuantity + 1;
        displayQuantity(resultQuantity);
    }

    /**
     * this display quantity on screen
     */
    private void displayQuantity(int resultQuantity) {
        TextView quantity = findViewById(R.id.displayQuantity);
        quantity.setText("" + resultQuantity);
    }

    /**
     * this validate order user
     */
    private boolean validateOrder() {

        boolean valid = false;

        if (mRadioCoffee.isChecked()) {
            valid = true;
            resultOrder = mRadioCoffee.getText().toString();
            resultPrice = resultQuantity * 5;
        } else if (mRadioMilk.isChecked()) {
            valid = true;
            resultOrder = mRadioMilk.getText().toString();
            resultPrice = resultQuantity * 10;
        } else if (mRadioSpaghetti.isChecked()) {
            valid = true;
            resultOrder = mRadioSpaghetti.getText().toString();
            resultPrice = resultQuantity * 25;
        } else if (mRadioBread.isChecked()) {
            valid = true;
            resultOrder = mRadioBread.getText().toString();
            resultPrice = resultQuantity * 3;
        }

        return valid;
    }

    /**
     * this do action order
     */
    private void doOrder() {
        //instantiate
        Validate validate = new Validate();
        resultName = mFieldName.getText().toString().trim();

        //control flow check user values for order
        if (validate.validateName(mFieldName, resultName)) {
            if (validateOrder()) {
                if (validate.validateQuantity(resultQuantity)) {
                    displayPrice(resultPrice);
                    new Progress().showProgressDialog(mProgress);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new Progress().hideProgressDialog(mProgress);
                            alertConfirmOrder(OrderCartActivity.this);
                        }
                    }, 3000L);

                    Log.d(TAG, "displayPrice(int resultPrice):Success");
                } else {
                    Toast.makeText(OrderCartActivity.this, "Min order 1", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OrderCartActivity.this, "Please, Choose your order", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * this display price on screen
     *
     * @param resultPrice
     */
    private void displayPrice(int resultPrice) {
        TextView price = findViewById(R.id.displayPrice);
        price.setText("$ " + resultPrice + ".00");
    }

    /**
     * this show alert dialog for confirm order
     */
    private AlertDialog.Builder alertConfirmOrder(Context context) {
        //inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_confirm_order, null);

        //initially resources
        TextView mName, mOrder, mQuantity, mPrice;
        mName = view.findViewById(R.id.alertName);
        mOrder = view.findViewById(R.id.alertOrder);
        mQuantity = view.findViewById(R.id.alertQuantity);
        mPrice = view.findViewById(R.id.alertPrice);

        //settings up
        mName.setText(": " + resultName);
        mOrder.setText(": " + resultOrder);
        mQuantity.setText(": " + resultQuantity + " Qty");
        mPrice.setText(": $" + resultPrice + ".00");

        new AlertDialog.Builder(context)
                .setTitle("Confirm Order")
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Progress().showProgressDialog(mProgress);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new Progress().hideProgressDialog(mProgress);

                                //insert to database
                                try {
                                    //instantiate
                                    DatabaseSQLite dbSQL = new DatabaseSQLite(OrderCartActivity.this);
                                    UserOrder order = new UserOrder();

                                    //setup
                                    order.setName(resultName);
                                    order.setChecked(resultOrder);
                                    order.setQuantity(String.valueOf(resultQuantity));
                                    order.setPrice(String.valueOf(resultPrice));

                                    //start insert
                                    dbSQL.addDataOrder(order);
                                } catch (SQLiteException exc) {
                                    Log.e(TAG, "insert data to database sqlite:Failure");
                                }

                                Toast.makeText(OrderCartActivity.this, "Order Success, Thank's", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(OrderCartActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);

                                Log.d(TAG, "insert data to database sqlite:Success");
                            }
                        }, 3000L);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();

        Log.d(TAG, "alertConfirmOrder():Showing:Success");

        return new AlertDialog.Builder(context);
    }
}
