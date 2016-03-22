package edu.utdallas.myapplication;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

/** FileIO class
 * Implements reading and saving file functions
 * Created by Peiyang on 3/22/16.
 */
public class FileIO {
    // Initialize record file
    private static File f = null;

    /* Initialize file
     * Author: Peiyang Shangguan
     * Created on: 03/20/2016
     */
    public static void init(Context context) {
        // Initialize record file
        String fileName = "persons.txt ";
        try {
            f = new File(context.getFilesDir(), fileName);
            if(!f.exists()) {
                f.createNewFile();
            }
        }catch(Exception e) {
            // if any I/O error occurs
            e.printStackTrace();
        }
    }

    /* Helper method to read records from file
     * Author: Peiyang Shangguan
     * Created on: 03/20/2016
     */
    public static ArrayList<Person> loadFile() throws FileNotFoundException {
        // Create a list of persons
        ArrayList<Person> personList = new ArrayList<>();

        // Build a scanner to save file content into cycleList
        Scanner sc = new Scanner(f);
        sc.useDelimiter("\t");
        while(sc.hasNextLine()) {
            Person p = new Person();
            p.setFirstName(sc.next());
            p.setLastName(sc.next());
            p.setPhone(sc.next());
            p.setEmail(sc.next());
            p.setAddYear(sc.nextInt());
            p.setAddMonth(sc.nextInt());
            p.setAddDay(sc.nextInt());

            personList.add(p);
            sc.nextLine();
        }
        sc.close();

        return personList;
    }

    /* Helper method to save records to file
     * Author: Peiyang Shangguan
     * Created on: 03/20/2016
     */
    public static void saveFile(ArrayList<Person> personList) throws IOException {
        // Use a FileWriter to write cycles into file
        FileWriter fw = new FileWriter(f);
        int size = personList.size();
        for(Person p : personList) {
            fw.write(p.getFirstName() + "\t");
            fw.write(p.getLastName() + "\t");
            fw.write(p.getPhone() + "\t");
            fw.write(p.getEmail() + "\t");
            fw.write(p.getAddYear() + "\t");
            fw.write(p.getAddMonth() + "\t");
            fw.write(p.getAddDay() + "\t");

            // No next line after last record
            if(size-- != 1)
                fw.write("\n");
        }
        fw.close();
    }
}
