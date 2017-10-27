package ikpmd.westgeestoonk.course_manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ikpmd.westgeestoonk.course_manager.Models.Course_Model;

public class VakInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Course_Model vak;
    private TextView Vaktitel;
    private TextView EC;
    private TextView Periode;
    private TextView Toetsing;
    private TextView Toetsmoment;
    private TextView Cijfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vak_info);


        Bundle bundle = getIntent().getExtras();
        vak = (Course_Model) bundle.getSerializable("course");

        setTitle(vak.getVakcode());

        Vaktitel = (TextView) findViewById(R.id.VakTitel);
        EC = (TextView) findViewById(R.id.EC);
        Periode = (TextView) findViewById(R.id.Periode);
        Toetsing = (TextView) findViewById(R.id.Toetsing);
        Toetsmoment = (TextView) findViewById(R.id.ToetsMoment);
        Cijfer = (TextView) findViewById(R.id.Cijfer);

        Vaktitel.setText(vak.getNaam());
        EC.setText(String.valueOf(vak.getEC()));
        Periode.setText(String.valueOf(vak.getPeriode()));
        Toetsing.setText(vak.getToetsing().toString());
        Toetsmoment.setText(vak.getToetsmoment());
        Cijfer.setText(vak.getCijfer());      // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}

