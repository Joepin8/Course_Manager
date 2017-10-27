package ikpmd.westgeestoonk.course_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Spinner;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ikpmd.westgeestoonk.course_manager.Database.DatabaseHelper;
import ikpmd.westgeestoonk.course_manager.Models.Course_Model;
import ikpmd.westgeestoonk.course_manager.View.CourseListAdapter;

public class VakkenOverzichtActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseHelper databaseHelper;
    private ListView lv;
    private CourseListAdapter cAdapter;
    private EditText zoekVeld;
    private ArrayList<Course_Model> courses;
    private Spinner spinnerJaar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vakken_overzicht);
        setTitle("Vakken");
        fAuth = FirebaseAuth.getInstance();
        databaseHelper = databaseHelper.getHelper(this, fAuth.getUid());
        uploadCijfers();
        lv = (ListView) findViewById(R.id.listview);
        zoekVeld = (EditText) findViewById(R.id.zoekVeld);
        spinnerJaar = (Spinner) findViewById(R.id.spinnerJaar);

        initList();
        initSpinner();

        spinnerJaar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if(item.equals("Alle jaren")) {
                    initList(); // Reset de listView
                } else if (item.equals("Jaar 1")) {
                    filterVakkenVanJaar("1");
                } else if (item.equals("Jaar 2")) {
                    filterVakkenVanJaar("2");
                } else if (item.equals("Jaar 3")) {
                    filterVakkenVanJaar("3 of 4");
                } else if (item.equals("Jaar 4")) {
                    filterVakkenVanJaar("3 of 4");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openVakInfoVoorVak(position);
            }
        });

        zoekVeld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")) {
                    initList(); //Reset de listView
                } else {
                    filterVakkenMetNaam(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initSpinner() {
        List<String> jaren = new ArrayList<>(Arrays.asList("Alle jaren", "Jaar 1", "Jaar 2", "Jaar 3", "Jaar 4"));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jaren);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJaar.setAdapter(dataAdapter);
    }

    private void filterVakkenMetNaam(String s) {
        for(Iterator<Course_Model> iterator = courses.iterator(); iterator.hasNext(); ) {
            Course_Model c = iterator.next();
            if(!c.getNaam().toLowerCase().contains(s)) {
                iterator.remove();
            }
        }

        cAdapter.notifyDataSetChanged();
    }

    public void filterVakkenVanJaar(String jaar) {
        initList(); //Reset de listview zodat alle jaren er weer in staan
        for(Iterator<Course_Model> iterator = courses.iterator(); iterator.hasNext(); ) {
            Course_Model c = iterator.next();
            if(!c.getJaar().equals(jaar)) {
                iterator.remove();
            }
        }

        cAdapter.notifyDataSetChanged();
    }

    public void openVakInfoVoorVak(int i) {
        Intent intent = new Intent(getApplicationContext(),VakInfo.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("course", courses.get(i));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void initList() {
        courses = databaseHelper.getAllCourses();
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

