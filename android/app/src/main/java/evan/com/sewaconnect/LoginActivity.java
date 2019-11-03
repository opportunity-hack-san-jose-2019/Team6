package evan.com.sewaconnect;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import evan.com.sewaconnect.R;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText userEditText, passwordEditText;
    private AlertDialog mAlertDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        userEditText = findViewById(R.id.edittext_username);
        Drawable profileIcon =  AppCompatResources.getDrawable(this, R.drawable.icon_profile);
        userEditText.setCompoundDrawablesWithIntrinsicBounds(profileIcon, null, null, null);

        passwordEditText = findViewById(R.id.edittext_password);
        Drawable passwordIcon =  AppCompatResources.getDrawable(this, R.drawable.icon_password);
        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(passwordIcon, null, null, null);



        final TextView signUp = (TextView) findViewById(R.id.textview_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        final Button signIn = (Button) findViewById(R.id.login_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    private void signIn(){

        String username = userEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        //Checks if username and password fields are filled out
        if(checkIfStringNull(username) || checkIfStringNull(password)){
            Toast.makeText(LoginActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
        }else{
            closeKeyboard(this);
            userEditText.clearFocus();
            passwordEditText.clearFocus();
            setProgressDialog();

            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, Move to mainActivity
                                Log.d(TAG, "signInWithEmail:success");
                                mAlertDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                LoginActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        mAlertDialog.dismiss();
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }
                    });

            }
        }



    private boolean checkIfStringNull(String string) {
        if (string.equals("")) {
            return true;
        } else {
            return false;
        }

    }

    private void setProgressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.alertDialogTheme);
        builder.setView(R.layout.alert_dialog_login_progress);
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();

    }

    public void closeKeyboard(FragmentActivity activity){
        View view = activity.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null){
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}
