package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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
import com.example.fitnessenthusiasts.activities.Databases.Session;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
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

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    ProgressDialog dialog;


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
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        dialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loggin In");
        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

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

//        pairs[0] = new Pair<View, String>(image, "logo_image");
//        pairs[1] = new Pair<View, String>(logoText, "logo_text");
//        pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
//        pairs[3] = new Pair<View, String>(email, "user_tran");
//        pairs[4] = new Pair<View, String>(password, "pass_tran");
//        pairs[5] = new Pair<View, String>(login_btn, "button_tran");
//        pairs[6] = new Pair<View, String>(signup_Btn, "login_signup_tran");

        Bundle b = ActivityOptions.makeSceneTransitionAnimation(Login.this).toBundle();
        startActivity(intent, b);

    }



    public void loggingIn(View view){
        String _email = email.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        dialog.show();

        auth.signInWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    database.getReference().child("Users").child(auth.getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                                        Session session = new Session(Login.this);
                                        session.saveSession(user);
                                        Toast.makeText(Login.this, "Signed In", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        startActivity(new Intent(Login.this,HomeScreen.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }else{
                    Toast.makeText(Login.this, "ERROR INCORRECT INFO", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
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



}