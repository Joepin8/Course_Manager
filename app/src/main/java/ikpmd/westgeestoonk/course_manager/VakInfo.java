package ikpmd.westgeestoonk.course_manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ikpmd.westgeestoonk.course_manager.Models.Course_Model;

public class VakInfo extends AppCompatActivity {
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
        Cijfer.setText(vak.getCijfer());
    }
}
