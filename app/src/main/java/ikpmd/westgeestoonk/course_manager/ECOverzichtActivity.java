package ikpmd.westgeestoonk.course_manager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import ikpmd.westgeestoonk.course_manager.Database.DatabaseHelper;
import ikpmd.westgeestoonk.course_manager.Models.Course_Model;
import ikpmd.westgeestoonk.course_manager.View.CourseListAdapter;


public class ECOverzichtActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseHelper databaseHelper;
    private PieChart chart;
    private final int MAX_EC = 240;
    private int behaaldeEc;
    private ArrayList<Course_Model> courses;
    private ArrayList<Course_Model> nogTeHalenCourses = new ArrayList<>();
    private TextView tvTeHalen;
    private ListView lv;
    private CourseListAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ec_overzicht);
        setTitle("Mijn EC's");
        fAuth = FirebaseAuth.getInstance();
        databaseHelper = databaseHelper.getHelper(getApplicationContext(), fAuth.getUid());
        courses = databaseHelper.getAllCourses();
        chart = (PieChart) findViewById(R.id.chart);
        behaaldeEc = berekenBehaaldeEC();
        lv = (ListView) findViewById(R.id.lvCourses);
        tvTeHalen = (TextView) findViewById(R.id.tvHalen);
        setupChart();
        tvTeHalen.setText("Je moet nog voor " + nogTeHalenCourses.size() + " vak(ken) slagen.");

        cAdapter = new CourseListAdapter(getApplicationContext(), 0, nogTeHalenCourses);
        lv.setAdapter(cAdapter);

    }

    private int berekenBehaaldeEC() {
        int ec = 0;
        for(Course_Model c : courses) {
            if(c.isGehaald()) ec += c.getEC();
            else
                nogTeHalenCourses.add(c);
        }
        return ec;
    }

    private void setupChart() {
        chart.setTouchEnabled(false);
        chart.getDescription().setText(" ");
        chart.getDescription().setTextSize(24);
        chart.getLegend().setEnabled(false);
        chart.setDrawSlicesUnderHole(true);
        chart.setTransparentCircleAlpha(Color.rgb(130, 130, 130));
        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        setData(this.behaaldeEc);

    }

    private void setData(int behaaldeEc) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(behaaldeEc, "Behaalde EC's"));
        entries.add(new PieEntry(MAX_EC - behaaldeEc, "Nog te behalen EC's"));

        ArrayList<Integer> colors = new ArrayList<>();
        if (behaaldeEc <10) {
            colors.add(Color.rgb(244,81,30));
        } else if (behaaldeEc < 40){
            colors.add(Color.rgb(235,0,0));
        } else if  (behaaldeEc < 50) {
            colors.add(Color.rgb(253,216,53));
        } else {
            colors.add(Color.rgb(67,160,71));
        }
        colors.add(Color.rgb(255,0,0));

        PieDataSet dataSet = new PieDataSet(entries, "EC's");
        dataSet.setValueTextSize(24);
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.invalidate();

    }

}
