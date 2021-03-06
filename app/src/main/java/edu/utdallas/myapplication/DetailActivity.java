package edu.utdallas.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {

    private ViewFragment viewFragment;

    @Override
     /* Create the view of editing activity and add a fragment
    * Author: Yue Zhang
    * Created on: 03/20/2016
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //show edit page
        FragmentManager fragmentManager = getFragmentManager();
        viewFragment = new ViewFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contacts, viewFragment, "view_fragment");
        fragmentTransaction.show(viewFragment);
        fragmentTransaction.commit();
    }

    @Override
     /* Add menu icons to toolbar
    * Author: Yue Zhang
    * Created on: 03/20/2016
    */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    /* Add click listener of toolbar
    * Author: Yue Zhang
    * Created on: 03/20/2016
    */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_delete:
                break;
        }
        return true;
    }

    @Override
    /* Add menu icons to toolbar
    * Author: Yue Zhang
    * Created on: 03/20/2016
    */
    public void onBackPressed() {
        if (viewFragment.onBack()) {
            super.onBackPressed();
        }
    }
}