package ikpmd.westgeestoonk.course_manager;

import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import ikpmd.westgeestoonk.course_manager.Database.DatabaseHelper;
import ikpmd.westgeestoonk.course_manager.Database.DatabaseInfo;
import ikpmd.westgeestoonk.course_manager.GSON.GsonRequest;
import ikpmd.westgeestoonk.course_manager.GSON.VolleyHelper;
import ikpmd.westgeestoonk.course_manager.Models.Course_Model;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginBtn;
    private Button newAccountBtn;
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseHelper databaseHelper;
    private TextView tvEmail;
    private TextView tvWachtwoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        databaseHelper = databaseHelper.getHelper(this, fAuth.getUid());
        setTitle("Inloggen");
        emailEditText = (EditText) findViewById(R.id.emailET);
        passwordEditText = (EditText) findViewById(R.id.passwordET);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        newAccountBtn = (Button) findViewById(R.id.newAccountBtn);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvWachtwoord = (TextView) findViewById(R.id.tvWachtwoord);
        
        loginBtn.setText("Inloggen");
        newAccountBtn.setText("Nieuw account");
        tvEmail.setText("Email:");
        tvWachtwoord.setText("Wachtwoord:");
        emailEditText.setText("");
        fAuth.signOut();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "Login btn fired");
                if(emailEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Inloggen mislukt", Toast.LENGTH_SHORT).show();
                } else {
                    fAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Log.d("DEBUG", "ingelogd");
                                Toast.makeText(getApplicationContext(), "Ingelogd", Toast.LENGTH_SHORT).show();
                                 getAllCourses();
                                Intent intent = new Intent(getApplicationContext(), VakkenOverzichtActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Inloggen mislukt", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        newAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NieuwAccountActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getAllCourses() {
        DatabaseReference childRef = rootRef.child("vakken");
        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getCourses((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getCourses(Map<String, Object> snapshot) {

        Type type = new TypeToken<Course_Model>(){}.getType();
        String url = rootRef.toString();
        Object[] courseKeys = null;

        // Haalt alle keys op om een webrequest naar te doen.
        for (Map.Entry<String, Object> entry : snapshot.entrySet()){
            Map singleCourse = (Map) entry.getValue();
            courseKeys = singleCourse.keySet().toArray();
        }
        url = rootRef.toString() + "/vakken/vakken/";
        Log.d("DEBUG", "Objecten ophalen vanaf " + url);
        for(Object o : courseKeys) {
            GsonRequest<Course_Model> request = new GsonRequest<Course_Model>(url + o.toString() + ".json", type, null, new Response.Listener<Course_Model>() {
                @Override
                public void onResponse(Course_Model response) {
                    addCourseToDatabase(response);
                    Log.wtf("DEBUG", "vak: " + response.getVakcode() + " EC: " + response.getEC());
                }
            });
            VolleyHelper.getInstance(this).addToRequestQueue(request);
        }


    }

    private void addCourseToDatabase(Course_Model course) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseInfo.CourseColumn.NAAM, course.getNaam());
        cv.put(DatabaseInfo.CourseColumn.EC, course.getEC());
        cv.put(DatabaseInfo.CourseColumn.VAKCODE, course.getVakcode());
        cv.put(DatabaseInfo.CourseColumn.TOETSING, course.getToetsing().toString());
        cv.put(DatabaseInfo.CourseColumn.PERIODE, course.getPeriode());
        cv.put(DatabaseInfo.CourseColumn.TOETSMOMENT, course.getToetsmoment());
        cv.put(DatabaseInfo.CourseColumn.CIJFER, course.getCijfer());
        cv.put(DatabaseInfo.CourseColumn.JAAR, course.getJaar());
        databaseHelper.insert(DatabaseInfo.CourseTables.COURSE, null, cv);
    }

}
