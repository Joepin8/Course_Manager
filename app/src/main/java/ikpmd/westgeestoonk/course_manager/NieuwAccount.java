package ikpmd.westgeestoonk.course_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NieuwAccount extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button createAccountBtn;
    private Button cancelBtn;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        emailEditText = (EditText) findViewById(R.id.emailET);
        passwordEditText = (EditText) findViewById(R.id.passwordET);
        createAccountBtn = (Button) findViewById(R.id.createAccountBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        createAccountBtn.setText("Account aanmaken");
        cancelBtn.setText("Annuleren");

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")) {
                } else {
                    fAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Log.d("DEBUG", "ingelogd");
                                Toast.makeText(getApplicationContext(), "Account aangemaakt", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Kon account niet aanmaken", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
