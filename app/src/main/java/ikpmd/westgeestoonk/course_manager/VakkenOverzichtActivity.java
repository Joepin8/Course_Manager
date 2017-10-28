package ikpmd.westgeestoonk.course_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import android.widget.TextView;
import android.widget.Toast;


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
    private TextView mijnEC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vakken_overzicht);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.vakken_action_bar);
        fAuth = FirebaseAuth.getInstance();
        databaseHelper = databaseHelper.getHelper(this, fAuth.getUid());
        lv = (ListView) findViewById(R.id.listview);
        zoekVeld = (EditText) findViewById(R.id.zoekVeld);
        spinnerJaar = (Spinner) findViewById(R.id.spinnerJaar);
        mijnEC = (TextView) findViewById(R.id.mijnECs);
        initList();
        initSpinner();

        mijnEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ECOverzichtActivity.class);
                startActivity(intent);
            }
        });

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

    public void initSpinner() {
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

    private void filterVakkenVanJaar(String jaar) {
        initList(); //Reset de listview zodat alle jaren er weer in staan
        for(Iterator<Course_Model> iterator = courses.iterator(); iterator.hasNext(); ) {
            Course_Model c = iterator.next();
            if(!c.getJaar().contains(jaar)) {
                iterator.remove();
            }
        }

        cAdapter.notifyDataSetChanged();
    }

    private void openVakInfoVoorVak(int i) {
        Intent intent = new Intent(getApplicationContext(),VakInfo.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("course", courses.get(i));
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void initList() {
        courses = databaseHelper.getAllCourses();
        cAdapter = new CourseListAdapter(getApplicationContext(), 0, courses);
        lv.setAdapter(cAdapter);
    }



}

