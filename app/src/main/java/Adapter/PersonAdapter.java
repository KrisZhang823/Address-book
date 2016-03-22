package Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.utdallas.myapplication.Person;
import edu.utdallas.myapplication.R;

/**
 * Person adapter for list view
 * Created by Peiyang on 3/19/16.
 */
public class PersonAdapter extends ArrayAdapter<Person> {
    private final Activity context;
    private final ArrayList<Person> persons;

    public PersonAdapter(Activity context, ArrayList<Person> persons){
        super(context, R.layout.person_item, persons);

        this.context = context;
        this.persons = persons;
    }

    static class ViewHolder {
        TextView firstName;
        TextView lastName;
        TextView phone;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;

        if(convertView == null) {
            // Get root view
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.person_item, null, true);

            // Get view elements
            viewHolder = new ViewHolder();
            viewHolder.firstName = (TextView) convertView.findViewById(R.id.firstName);
            viewHolder.lastName = (TextView) convertView.findViewById(R.id.lastName);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.phone);

            // Store the view holder with view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set values
        Person person = persons.get(position);
        viewHolder.firstName.setText(person.getFirstName());
        viewHolder.lastName.setText(person.getLastName());
        viewHolder.phone.setText(person.getPhone());

        return convertView;
    }
}
