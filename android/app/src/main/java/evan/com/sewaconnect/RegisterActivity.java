package evan.com.sewaconnect;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.constraint.Constraints.TAG;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText editTextUser, editTextPassword, editTextEmail, editTextConfirmPassword;
    private String email, username, password, passwordConfirm;
    private Map<String, String> attributes = new HashMap<>();
    private Button mCreateAccount;
    private ProgressBar mProgressBar;

    private String mDeviceToken;
    private String arnStorage;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //Get device token and send it to firebase realtime
        initGetDeviceToken();

        TextView appName = findViewById(R.id.actRegister_appname);
        Drawable appNameIcon = AppCompatResources.getDrawable(this, R.drawable.icon_sewaconnect);
        appName.setCompoundDrawablesWithIntrinsicBounds(null, null, appNameIcon, null);

        RelativeLayout goBackToLogin = findViewById(R.id.actRegister_relativelayout_login);
        goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editTextEmail = (EditText) findViewById(R.id.actRegister_edittext_email);
        editTextUser = (EditText) findViewById(R.id.actRegister_edittext_username);
        editTextPassword = (EditText) findViewById(R.id.actRegister_edittext_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.actRegister_edittext_confirm_password);
        mCreateAccount = (Button) findViewById(R.id.actRegister_button_create_account);
        mProgressBar = (ProgressBar) findViewById(R.id.actRegister_progressbar);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });








    }

    private void initGetDeviceToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                mDeviceToken = instanceIdResult.getToken();
                Log.d(TAG, "Firebase Device Token: Success: " + mDeviceToken);
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Firebase Device Token: Failure: " + e);
            }
        });

        //Send this to firebase
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    private void signUp(){

        email = editTextEmail.getText().toString().trim();
        username = editTextUser.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if(checkIfStringNull(username) || checkIfStringNull(password)){
            Toast.makeText(RegisterActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
        }else{

            Log.d(TAG, "signUp: USERNAME: " + username);
            Log.d(TAG, "signUp: PASSWORD: " + password);
            mCreateAccount.setText("");
            mProgressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                addUserToFirestoreDB();
                                Toast.makeText(RegisterActivity.this, "User signed up",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e(TAG, "onComplete: ERROR SIGNINGUP: " + task.getException());
                                mCreateAccount.setText(R.string.create_new_account);
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(RegisterActivity.this, "Sign up error.", Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
    }

    private void addUserToFirestoreDB(){

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.d(TAG, "addUserToFirestoreDB: CURRENT USER: " + currentuser);

        Map<String, Object> newUser = new HashMap<>();
        newUser.put("email", email);
        newUser.put("userId", currentuser);
        newUser.put("username", username);


        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .add(newUser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(RegisterActivity.this, "Registered User", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private boolean checkIfStringNull(String string) {
        if(string.equals("")){
            return true;
        }else{
            return false;
        }
    }
}
