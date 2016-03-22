package edu.utdallas.myapplication;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private KeyListener dateKey;
    private Drawable background;
    private Fragment thisFragment;


    private Person person;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view,container,false);
        thisFragment = this;
        Bundle extra = getActivity().getIntent().getExtras();
        person = (Person)extra.getSerializable("person");
        mode = extra.getInt("mode");
        return view;
    }

    @Override
    public  void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        

        if (mode == 0){
            initView();
        }else if(mode == 1){
            initEdit();
        }else{
            initEdit();
        }



        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_50);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        NavigationListen(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to edit this contact?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //delete person
                                initEdit();
                                fab.setImageResource(R.drawable.cancel248);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

    }

    public void initView(){
        firstName = (TextView) getView().findViewById(R.id.firstname);
        lastName = (TextView) getView().findViewById(R.id.lastName);
        phone = (TextView) getView().findViewById(R.id.phone);
        email = (TextView) getView().findViewById(R.id.email);
        date = (TextView) getView().findViewById(R.id.date);

        background = firstName.getBackground();
        firstNameKey = firstName.getKeyListener();
        lastNameKey = lastName.getKeyListener();
        phoneKey = phone.getKeyListener();
        emailKey = email.getKeyListener();
        dateKey = date.getKeyListener();

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


        onDateSet(person.getAddYear(),person.getAddMonth(),person.getAddDay());
        date.setKeyListener(null);
        date.setBackground(null);


    }

    public void initEdit(){
        firstName.setHint("First Name");
        lastName.setHint("Last Name");
        phone.setHint("Phone Number");
        email.setHint("Example@gmail.com");
        date.setHint("MM/DD/YYYY");


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
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setDefaultDate(person.getAddYear(), person.getAddMonth(), person.getAddDay());
                datePicker.setCallBackFragment(thisFragment);
                datePicker.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
        date.setBackground(background);
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
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                //dialog
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to delete this contact and exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //delete person
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
            return true;
        }
    };

    public void NavigationListen(Toolbar toolbar){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog do you want save
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to save this contact and exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
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

        // Update string in view
        date.setText(str);
    }
}
