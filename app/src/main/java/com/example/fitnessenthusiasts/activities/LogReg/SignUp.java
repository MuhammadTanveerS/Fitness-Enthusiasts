package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    //Continue Later not Working now
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    //Data Variables
    TextInputLayout fullName, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.example.fitnessenthusiasts.R.layout.activity_sign_up);

        //Hooks
        backBtn = findViewById(R.id.notif_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.title_text);

        //Hooks for getting data
        fullName = findViewById(R.id.signup_fname);
        username = findViewById(R.id.signup_uname);
        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);


    }

    public void check(View view) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("test");
        reference.setValue("Hi");
    }

    public void callNextSignupScreen(View view) {

        if(!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()){
            return;
        }

        //get data from fields
        String fname = fullName.getEditText().getText().toString().trim();
        String uname = username.getEditText().getText().toString().trim();
        String mail = email.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        Intent intent = new Intent(getApplicationContext(), SignUp2nd.class);

        //Send data to next activity
        intent.putExtra("Full Name",fname);
        intent.putExtra("User Name",uname);
        intent.putExtra("Email",mail);
        intent.putExtra("Password",pass);

        //Add Transition
//        Pair[] pairs = new Pair[4];
//
//        pairs[0] = new Pair<View, String>(backBtn, "backarrow_tran");
//        pairs[1] = new Pair<View, String>(next, "next_tran");
//        pairs[2] = new Pair<View, String>(login, "login_signup_tran");
//        pairs[3] = new Pair<View, String>(titleText, "title_tran");

        Bundle b = ActivityOptions.makeSceneTransitionAnimation(SignUp.this).toBundle();
        startActivity(intent, b);

    }

    //Validating Fumctions

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No White Spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //String checkEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        }  else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
//                "(?=.*[0-9])" +    //at least 1 digit
//                "(?=.*[a-z])" +    //at least 1 lower case letter
//                "(?=.*[A-Z])" +    //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +    //any letter
//                "(?=.*[@#$%^&+=])" +  //at least 1 special character
//                "(?=\\S+$)" +    //no white spaces
                ".{4,}" +    //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        }  else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }


}