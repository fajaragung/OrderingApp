package com.robotsoftware.orderingapp.activities.views.layouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.robotsoftware.orderingapp.R;
import com.robotsoftware.orderingapp.activities.controllers.adapter.OrderAdapter;
import com.robotsoftware.orderingapp.activities.models.UserOrder;
import com.robotsoftware.orderingapp.database.DatabaseSQLite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DatabaseSQLite dbSQL;
    ArrayList<UserOrder> userOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //call methods class
        setupToolbar();
        initViews();
        initOnClick();
        initDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //call method class
        setupCardOrder();
    }

    @Override
    protected void onStop() {
        super.onStop();

        dbSQL.close();
    }

    /**
     * this for setup toolbar
     */
    private void setupToolbar() {

        Toolbar toolbar = findViewById(R.id.support_toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "setupToolbar():Success");
    }

    /**
     * this for inflate menu layout to action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    /**
     * this for item on menu action bar is selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //get item id
        int itemId = item.getItemId();

        //do action if item id selected
        if (itemId == R.id.action_cart) {
            //intent to activity order cart
            Intent cartIntent = new Intent(MainActivity.this, OrderCartActivity.class);
            cartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(cartIntent);

            Log.d(TAG, "onOptionsItemSelected(MenuItem item):OrderCartIntent:Success");
        } else if (itemId == R.id.action_about) {
            //intent to activity about
            Intent aboutIntent = new Intent(MainActivity.this, ActivityAboutApp.class);
            aboutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(aboutIntent);

            Log.d(TAG, "onOptionsItemSelected(MenuItem item):AboutAppIntent:Success");
        } else if (itemId == R.id.action_help) {

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * this for initially layout resources
     */
    private void initViews() {

    }

    /**
     * this for initially button onclick
     */
    private void initOnClick() {

    }

    /**
     * this for initially database data
     */
    private void initDatabase() {

        //instantiate
        dbSQL = new DatabaseSQLite(MainActivity.this);
    }

    /**
     * this method for setup adapter to activity
     */
    private void setupCardOrder() {

        userOrder = dbSQL.displaysDataOrder();

        RecyclerView myRecyclerView = findViewById(R.id.myRecyclerView);
        OrderAdapter orderAdapter = new OrderAdapter(userOrder, MainActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        myRecyclerView.setLayoutManager(layoutManager);

        myRecyclerView.setAdapter(orderAdapter);

        Log.d(TAG, "setupCardOrder():Show data on database SQLite:Success");
    }
}
