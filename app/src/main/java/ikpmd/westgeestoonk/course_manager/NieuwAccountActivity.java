package ikpmd.westgeestoonk.course_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class NieuwAccountActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button createAccountBtn;
    private Button cancelBtn;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private TextView emailTV;
    private TextView wachtwoordTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nieuw_account);
        setTitle("Nieuw account");
        emailEditText = (EditText) findViewById(R.id.emailET);
        passwordEditText = (EditText) findViewById(R.id.passwordET);
        createAccountBtn = (Button) findViewById(R.id.createAccountBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        emailTV = (TextView) findViewById(R.id.tvEmail);
        wachtwoordTV = (TextView) findViewById(R.id.tvWachtwoord);

        createAccountBtn.setText("Account aanmaken");
        cancelBtn.setText("Annuleren");
        emailTV.setText("Email:");
        wachtwoordTV.setText("Wachtwoord:");

        emailEditText.setText(" ");

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
                                fAuth.signOut();
                            } else {
                                Toast.makeText(getApplicationContext(), "Kon account niet aanmaken", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
