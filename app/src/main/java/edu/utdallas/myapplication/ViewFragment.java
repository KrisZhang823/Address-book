package edu.utdallas.myapplication;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by kriszhang on 3/21/16.
 */
public class ViewFragment extends Fragment implements DatePickerFragment.NoticeDialogListener{
    private int mode = 0;//0 = view 1 = edit 2 = add

    private TextView firstName;
    private TextView lastName ;
    private TextView phone;
    private TextView email;
    private TextView date;
    private KeyListener firstNameKey;
    private KeyListener lastNameKey;
    private KeyListener phoneKey;
    private KeyListener emailKey;
    private Drawable background;
    private Fragment thisFragment;

    private Person person;
    private Person curPerson = new Person();
    private int position;
    private int year;
    private int month;
    private int day;
    private int curYear;
    private int curMonth;
    private int curDay;

    private Toast mToast;   // Keep track of toast

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        thisFragment = this;
        Bundle extra = getActivity().getIntent().getExtras();
        mode = extra.getInt("mode");
        if (mode == 0){
            position = extra.getInt("position");
            person = (Person)extra.getSerializable("person");
            day = person.getAddDay();
            month = person.getAddMonth();
            year = person.getAddYear();
        }else if(mode == 2){
            person = new Person();
            Calendar now = Calendar.getInstance();
            year = now.get(Calendar.YEAR);
            month = now.get(Calendar.MONTH);
            day = now.get(Calendar.DAY_OF_MONTH);
            person.setAddDay(day);
            person.setAddMonth(month);
            person.setAddYear(year);
        }
        return view;
    }

    @Override
    public  void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Add back button

        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        NavigationListen(toolbar);
        final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        initPerson();

        if (mode == 0){
            initView();
            fab.setImageResource(R.drawable.ic_mode_edit_black_24dp);
        }else if(mode == 1){
            initEdit();
            fab.setImageResource(R.drawable.ic_close_black_24dp);
        }else{
            initAdd();
            fab.setImageResource(R.drawable.ic_close_black_24dp);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode == 0) {
                    mode = 1;
                    fab.setImageResource(R.drawable.ic_close_black_24dp);
                    initEdit();
                    if(mToast != null)  mToast.cancel();
                    mToast = Toast.makeText(getActivity().getApplicationContext(), "Now editing..", Toast.LENGTH_SHORT);
                    mToast.show();
                } else if (mode == 1) {
                    getContact();
                    if (isModified()) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage("Are you sure you want to discard this modification?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mode = 0;
                                        fab.setImageResource(R.drawable.ic_mode_edit_black_24dp);
                                        initView();
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    } else {
                        mode = 0;
                        fab.setImageResource(R.drawable.ic_mode_edit_black_24dp);
                        initView();
                    }
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Are you sure you want to discard this add?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //delete person
                                    mode = 0;
                                    fab.setImageResource(R.drawable.ic_mode_edit_black_24dp);
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                }

            }
        });
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    msg += "Click delete";
                    break;
            }
            if(msg.equals("Click delete")) {
                //dialog
                if(mode != 2){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Are you sure you want to delete this contact and exit?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //delete person
                                    Intent intent = new Intent();
                                    intent.putExtra("position",position);
                                    getActivity().setResult(1002,intent);
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }else{
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Are you sure you want to cancel adding a contact?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }

            }
            return true;
        }
    };

    //back to last view
    public void NavigationListen(Toolbar toolbar){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
    }

    public void initPerson(){
        firstName = (TextView) getView().findViewById(R.id.firstname);
        lastName = (TextView) getView().findViewById(R.id.lastName);
        phone = (TextView) getView().findViewById(R.id.phone);
        email = (TextView) getView().findViewById(R.id.email);
        date = (TextView) getView().findViewById(R.id.date);
    }

    public void initView(){
        background = firstName.getBackground();
        firstNameKey = firstName.getKeyListener();
        lastNameKey = lastName.getKeyListener();
        phoneKey = phone.getKeyListener();
        emailKey = email.getKeyListener();

        firstName.setText(person.getFirstName());
        firstName.setKeyListener(null);
        firstName.setBackground(null);

        lastName.setText(person.getLastName());
        lastName.setKeyListener(null);
        lastName.setBackground(null);

        phone.setText(person.getPhone());
        phone.setKeyListener(null);
        phone.setBackground(null);

        email.setText(person.getEmail());
        email.setKeyListener(null);
        email.setBackground(null);

        date.setText(transformDate(year, month, day));
    }

    public void initAdd(){
        firstName.setHint("First Name");
        lastName.setHint("Last Name");
        phone.setHint("Phone Number");
        email.setHint("example@gmail.com");

        date.setText(transformDate(year, month, day));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setDefaultDate(year, month, day);
                datePicker.setCallBackFragment(thisFragment);
                datePicker.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
    }

    public void initEdit(){
        firstName.setHint("First Name");
        lastName.setHint("Last Name");
        phone.setHint("Phone Number");
        email.setHint("example@gmail.com");

        firstName.setKeyListener(firstNameKey);
        firstName.setBackground(background);

        lastName.setKeyListener(lastNameKey);
        lastName.setBackground(background);

        phone.setKeyListener(phoneKey);
        phone.setBackground(background);

        email.setKeyListener(emailKey);
        email.setBackground(background);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setDefaultDate(year, month, day);
                datePicker.setCallBackFragment(thisFragment);
                datePicker.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
    }

    // Called when a date is picked
    public void onDateSet(int year, int month, int day) {
        // Update birth date

        person.setAddYear(year);
        person.setAddMonth(month);
        person.setAddDay(day);

        // Transform into string
        String str = transformDate(year,month,day);

        // Update string in view
        date.setText(str);
    }

    public String transformDate(int year,int month,int day){
        String str = "";
        switch (month) {
            case 0: str = "Jan"; break;
            case 1: str = "Feb"; break;
            case 2: str = "Mar"; break;
            case 3: str = "Apr"; break;
            case 4: str = "May"; break;
            case 5: str = "Jun"; break;
            case 6: str = "Jul"; break;
            case 7: str = "Aug"; break;
            case 8: str = "Sep"; break;
            case 9: str = "Oct"; break;
            case 10: str = "Nov"; break;
            case 11: str = "Dec"; break;
        }
        str = str + " " + day + ", " + year;
        return str;
    }

    private void getContact() {
        curPerson.setFirstName(firstName.getText().toString());
        curPerson.setLastName(lastName.getText().toString());
        curPerson.setPhone(phone.getText().toString());
        curPerson.setEmail(email.getText().toString());
        curDay = person.getAddDay();
        curMonth = person.getAddMonth();
        curYear = person.getAddYear();
        curPerson.setAddYear(curYear);
        curPerson.setAddMonth(curMonth);
        curPerson.setAddDay(curDay);
    }

    private boolean isModified(){
        return !(person.getFirstName().equals(curPerson.getFirstName()) && person.getLastName().equals(curPerson.getLastName())
                && person.getEmail().equals(curPerson.getEmail()) && person.getPhone().equals(curPerson.getPhone()) &&
                day == curDay && month == curMonth && year == curYear);
    }

    // Return 0 if all correct, 1 if no input for first name, 2 if future date
    private int isLegalContact(){
        if(firstName.getText().toString().length() <= 0){
            return 1;
        }
        // Check date(should not be future)
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if(curYear > year) {
            return 2;
        } else if(curMonth > month) {
            return 2;
        } else if(curDay > day){
            return 2;
        } else {
            return 0;
        }
    }

    private void exitAndSave(){
        initPerson();
        if (mode == 0){
            Intent intent = new Intent();
            intent.putExtra("saveMode","view");
            getActivity().finish();
        }else if(mode == 1){
            Intent intent = new Intent();
            intent.putExtra("person", curPerson);
            intent.putExtra("saveMode","save");
            intent.putExtra("position",position);
            getActivity().setResult(1001, intent);
            getActivity().finish();
        }else{
            Intent intent = new Intent();
            intent.putExtra("person", curPerson);
            intent.putExtra("saveMode","add");
            getActivity().setResult(1001, intent);
            getActivity().finish();
        }
    }

    // Return true will enable back, otherwise disable back
    public boolean onBack() {
        //view contact mode
        if (mode == 0) {
            exitAndSave();
            return true;
        }
        //edit contact mode
        if (mode == 1) {
            initPerson();
            getContact();
            //If modified save and exit
            if (isModified()) {
                switch (isLegalContact()) {
                    case 0:
                        if(mToast != null)  mToast.cancel();
                        mToast = Toast.makeText(getActivity().getApplicationContext(), "Contact Saved!", Toast.LENGTH_SHORT);
                        mToast.show();
                        exitAndSave();
                        return true;
                    case 1:
                        if(mToast != null)  mToast.cancel();
                        mToast = Toast.makeText(getActivity().getApplicationContext(), "Please Enter a Name!", Toast.LENGTH_SHORT);
                        mToast.show();
                        return false;
                    case 2:
                        if(mToast != null)  mToast.cancel();
                        mToast = Toast.makeText(getActivity().getApplicationContext(), "You cannot select future date!", Toast.LENGTH_SHORT);
                        mToast.show();
                        return false;
                }
            }else{
                //If no modification back to view mode
                initView();
                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.setImageResource(R.drawable.ic_mode_edit_black_24dp);
                if(mToast != null)  mToast.cancel();
                mToast = Toast.makeText(getActivity().getApplicationContext(), "No change made", Toast.LENGTH_SHORT);
                mToast.show();
                mode = 0;
                return true;
            }
        } else {
            getContact();
            switch (isLegalContact()) {
                case 0:
                    if(mToast != null)  mToast.cancel();
                    mToast = Toast.makeText(getActivity().getApplicationContext(), "Contact Added!", Toast.LENGTH_SHORT);
                    mToast.show();
                    exitAndSave();
                    return true;
                case 1:
                    if(mToast != null)  mToast.cancel();
                    mToast = Toast.makeText(getActivity().getApplicationContext(), "Please Enter a Name!", Toast.LENGTH_SHORT);
                    mToast.show();
                    return false;
                case 2:
                    if(mToast != null)  mToast.cancel();
                    mToast = Toast.makeText(getActivity().getApplicationContext(), "You cannot select future date!", Toast.LENGTH_SHORT);
                    mToast.show();
                    return false;
            }
        }
        return true;
    }
}
