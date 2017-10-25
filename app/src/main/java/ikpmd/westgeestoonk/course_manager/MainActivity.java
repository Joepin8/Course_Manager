package ikpmd.westgeestoonk.course_manager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import ikpmd.westgeestoonk.course_manager.Models.Course_Model;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginBtn;
    private Button newAccountBtn;
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();

        emailEditText = (EditText) findViewById(R.id.emailET);
        passwordEditText = (EditText) findViewById(R.id.passwordET);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        newAccountBtn = (Button) findViewById(R.id.newAccountBtn);

        loginBtn.setText("Inloggen");
        newAccountBtn.setText("Nieuw account");
        getAllCourses();
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
                Intent intent = new Intent(getApplicationContext(), NieuwAccount.class);
                startActivity(intent);
            }
        });

    }

    public void getAllCourses() {
        String[] courses = new String[]{"IARCH", "ICOMMP"};

        DatabaseReference courseRef = rootRef.child("jaar1");
        courseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("DEBUG", dataSnapshot.getValue().toString());
                getCourses((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getCourses(Map<String, Object> snapshot) {

        ArrayList<Course_Model> alCourses = new ArrayList<>();
        for (Map.Entry<String, Object> entry : snapshot.entrySet()){

            Map singleUser = (Map) entry.getValue();
            Log.wtf("DEBUG", singleUser.get("ec").toString());
        }


    }

}
