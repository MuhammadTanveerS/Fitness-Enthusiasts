package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.HomeScreen;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    //Variables
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout email, password;
    Button login_btn, signup_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.example.fitnessenthusiasts.R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        //hooks
        image = findViewById(R.id.topImage);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        login_btn = findViewById(R.id.Login_btn);
        signup_Btn = findViewById(R.id.Signup_btn);

    }

    public void signUp(View view) {
        Intent intent = new Intent(Login.this, SignUp.class);

        Pair[] pairs = new Pair[7];

        pairs[0] = new Pair<View, String>(image, "logo_image");
        pairs[1] = new Pair<View, String>(logoText, "logo_text");
        pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
        pairs[3] = new Pair<View, String>(email, "user_tran");
        pairs[4] = new Pair<View, String>(password, "pass_tran");
        pairs[5] = new Pair<View, String>(login_btn, "button_tran");
        pairs[6] = new Pair<View, String>(signup_Btn, "login_signup_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent, options.toBundle());

    }

    //Testing a code
    public void login(View view) {

        /*
        --------------------------------
        ADD
        PROGRESS
        BAR
        LATER
        ---------------------------------
         */

        //Checking internet Connection
        //Get it to work later
        if (!isconnected(this)) {
            showCustomDialog();
        }


        String _username = email.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        //Database
        Query checkUser = FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com").getReference("Users").orderByChild("username").equalTo(_username);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    email.setError(null);
                    email.setErrorEnabled(false);

                    String systemPassword = dataSnapshot.child(_username).child("password").getValue(String.class);
                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullname = dataSnapshot.child(_username).child("fullName").getValue(String.class);
                        String _userName = dataSnapshot.child(_username).child("username").getValue(String.class);
                        String _dob = dataSnapshot.child(_username).child("date").getValue(String.class);
                        String _email = dataSnapshot.child(_username).child("email").getValue(String.class);
                        String _pass= dataSnapshot.child(_username).child("password").getValue(String.class);
                        String _phoneNo = dataSnapshot.child(_username).child("phoneNo").getValue(String.class);
                        String _gender = dataSnapshot.child(_username).child("gender").getValue(String.class);
                        String _image = dataSnapshot.child(_username).child("Data").child("image").getValue(String.class);

                        //Store in Shared Preferences
                        SPManager spManager = new SPManager(Login.this);
                        spManager.createLoginSession(_fullname, _userName, _email, _phoneNo, _pass, _dob,_gender, _image);

                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));

                        //Toast.makeText(Login.this, _fullname + "\n" + _userName + "\n" + _dob + "\n" + _email + "\n" + _phoneNo, Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void loggingIn(View view){
        String _email = email.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        auth.signInWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
//                    Toast.makeText(Login.this, "Successssssssss", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this,HomeScreen.class));
                }else{
                    Toast.makeText(Login.this, "Successssssssss", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /*
    Method for checking
    Internet Connection
     */

    private boolean isconnected(Login login) {

        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to Internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), StartUpScreen.class));
                        finish();
                    }
                }).show();

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null){
            startActivity(new Intent(Login.this,HomeScreen.class));
        }
    }
}