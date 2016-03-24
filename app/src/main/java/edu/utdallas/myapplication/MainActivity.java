package edu.utdallas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import Adapter.PersonAdapter;

/**
 * Main activity class
 * Created by Peiyang on 3/20/16.
 */
public class MainActivity extends AppCompatActivity {
    FileIO fileIO = new FileIO();
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

        FileIO.init(getApplicationContext());
        // Generate some test data in personList
        try {
            personList = FileIO.loadFile();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        PersonAdapter adapter = new PersonAdapter(this, personList);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Item "+position+" clicked.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Person person = personList.get(position);
                Bundle extra = new Bundle();
                extra.putSerializable("person", person);
                extra.putInt("mode", 0);
                extra.putInt("position",position);
                intent.putExtras(extra);
                startActivityForResult(intent, 1000);


            }
        });
    }

    /**
     * 所有的Activity对象的返回值都是由这个方法来接收
     * requestCode:    表示的是启动一个Activity时传过去的requestCode值
     * resultCode：表示的是启动后的Activity回传值时的resultCode值
     * data：表示的是启动后的Activity回传过来的Intent对象
     * zhangyue
     */

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == 1001)
        {
            //get person
            Person person = (Person)data.getExtras().getSerializable("person");
            String mode = data.getExtras().getString("saveMode");
            if (mode.equals("save")){
                personList.set(data.getExtras().getInt("position"),person);
            }else if (mode.equals("add")){
                personList.add(person);
            }
        }
        //delete one contact
        if (requestCode == 1000 && resultCode == 1002){
            personList.remove(data.getExtras().getInt("position"));
        }
        //save data list
        try {
            FileIO.saveFile(personList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //refresh
        PersonAdapter adapter = new PersonAdapter(this, personList);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
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
            startActivityForResult(intent, 1000);
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
