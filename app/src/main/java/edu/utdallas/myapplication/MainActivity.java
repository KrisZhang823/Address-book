package edu.utdallas.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.PersonAdapter;

/**
 * Main activity class
 * Created by Peiyang on 3/20/16.
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Person> personList;

    /* Initialize main activity on start
     * Author: Peiyang Shangguan
     * Created on: 03/20/2016
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Generate some test data in personList
        personList = generateData();

        PersonAdapter adapter = new PersonAdapter(this, personList);

        FileIO.init(getApplicationContext());
        // Generate some test data in personList
        personList = generateData();
        try {
            FileIO.saveFile(personList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        personList = null;
        try {
            personList = FileIO.loadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(personList == null) {
            personList = new ArrayList<>();
        }
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Item "+position+" clicked.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);


                Person person = personList.get(position);
//                person.setFirstName("Yue");
//                person.setLastName("Zhang");
//                person.setEmail("example@gmail.com");
//                person.setPhone("4692745048");
//                person.setAddDay(12);
//                person.setAddMonth(3);
//                person.setAddYear(2016);

                Bundle extra = new Bundle();
                extra.putSerializable("person",person);
                extra.putInt("mode",0);

                intent.putExtras(extra);
                startActivity(intent);


            }
        });

//        // Initialize fab
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    /* Create menu on action bar
     * Author: Peiyang Shangguan
     * Created on: 03/20/2016
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Set event listeners to action bar button
     * Author: Peiyang Shangguan
     * Created on: 03/20/2016
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, DetailActivity.class);
            Bundle extra = new Bundle();
            extra.putInt("mode",2);
            intent.putExtras(extra);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Add button clicked.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Generate some test data
     * Author: Peiyang Shangguan
     * Created on: 03/20/2016
     */
    private ArrayList<Person> generateData() {
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person("Abby", "Lee", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Amanda", "Jackson", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Bob", "Zhang", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Kevin", "Mosby", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Danny", "Green", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Tony", "Parker", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Tim", "Duncan", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Kevin", "Leonard", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Alan", "Smith", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Marco", "Reus", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Robert", "Lewandowski", "12345","example@gmail.com",2016,3,22));
        list.add(new Person("Mats", "Hummels", "12345","example@gmail.com",2016,3,22));
        return list;
    }
}
