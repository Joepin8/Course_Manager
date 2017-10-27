package ikpmd.westgeestoonk.course_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ikpmd.westgeestoonk.course_manager.Enums.Toetsing;
import ikpmd.westgeestoonk.course_manager.Models.Course_Model;

public class VakInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Course_Model vak;
    private TextView Vaktitel;
    private TextView EC;
    private TextView Periode;
    private TextView Toetsing;
    private TextView Toetsmoment;
    private Spinner TekstCijfer;
    private EditText DecimaalCijfer;


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
        Spinner TekstCijfer = (Spinner) findViewById(R.id.TekstCijfer);
        EditText DecimaalCijfer = (EditText) findViewById(R.id.DecimaalCijfer);

        Vaktitel.setText(vak.getNaam());
        EC.setText(String.valueOf(vak.getEC()));
        Periode.setText(String.valueOf(vak.getPeriode()));
        Toetsing.setText(vak.getToetsing().toString());
        Toetsmoment.setText(vak.getToetsmoment());



        // Spinner click listener
        TekstCijfer.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Geen Cijfer");
        categories.add("Voldoende");
        categories.add("Onvoldoende");



              // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        TekstCijfer.setAdapter(dataAdapter);

        if(vak.getToetsing().equals(ikpmd.westgeestoonk.course_manager.Enums.Toetsing.Portfolio) ||
                vak.getToetsing().equals(ikpmd.westgeestoonk.course_manager.Enums.Toetsing.Praktijkopdracht)||
                vak.getToetsing().equals(ikpmd.westgeestoonk.course_manager.Enums.Toetsing.Projectoplevering)||
                vak.getToetsing().equals(ikpmd.westgeestoonk.course_manager.Enums.Toetsing.Assessment)){
            DecimaalCijfer.setVisibility(View.GONE);

        }
        else{
            TekstCijfer.setVisibility(View.GONE);
        }
        switch(vak.getCijfer()){
            case "Geen cijfer": TekstCijfer.setSelection(0);
                                break;
            case "Voldoende": TekstCijfer.setSelection(1);
                                break;
            case "Onvoldoende": TekstCijfer.setSelection(2);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        vak.setCijfer(item, getApplicationContext(), FirebaseAuth.getInstance().getUid());

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), VakkenOverzichtActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

