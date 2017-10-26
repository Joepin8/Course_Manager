package ikpmd.westgeestoonk.course_manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ikpmd.westgeestoonk.course_manager.Database.DatabaseHelper;
import ikpmd.westgeestoonk.course_manager.Models.Course_Model;
import ikpmd.westgeestoonk.course_manager.View.CourseListAdapter;

public class VakkenOverzichtActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseHelper databaseHelper;
    private ListView lv;
    private CourseListAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vakken_overzicht);
        setTitle("Vakken");
        fAuth = FirebaseAuth.getInstance();
        databaseHelper = databaseHelper.getHelper(this, fAuth.getUid());
        uploadCijfers();
        ArrayList<Course_Model> courses = databaseHelper.getAllCourses();
        lv = (ListView) findViewById(R.id.listview);

        cAdapter = new CourseListAdapter(getApplicationContext(), 0, courses);
        lv.setAdapter(cAdapter);




    }

    public void uploadCijfers() {
        DatabaseReference gebruikerRef = FirebaseDatabase.getInstance().getReference().child("gebruikers").child(fAuth.getUid());
        Map<String, String> cijfers = new HashMap<String, String>();
        for(Course_Model c : databaseHelper.getAllCourses()) {
            if(!c.getCijfer().equals("Geen cijfer")) {
                cijfers.put(c.getVakcode(), c.getCijfer());
            }
        }
        gebruikerRef.setValue(cijfers);
    }

}

