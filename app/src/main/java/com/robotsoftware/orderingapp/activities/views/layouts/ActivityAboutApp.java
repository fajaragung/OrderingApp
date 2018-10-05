package com.robotsoftware.orderingapp.activities.views.layouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.robotsoftware.orderingapp.R;

public class ActivityAboutApp extends AppCompatActivity {

    private static final String TAG = "ActivityAboutApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        //call method class
        setupToolbar();
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
            Intent cartIntent = new Intent(ActivityAboutApp.this, OrderCartActivity.class);
            cartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(cartIntent);

            Log.d(TAG, "onOptionsItemSelected(MenuItem item):OrderCartIntent:Success");
        }

        return super.onOptionsItemSelected(item);
    }
}
