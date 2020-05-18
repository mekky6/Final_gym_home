package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.Database.DatabaseHelper;
import com.example.afinal.Profiles.MainProfileActivity;

public class LoginActivity extends AppCompatActivity {

    public String username;
    //Declaration EditTexts
    EditText loginEmail, loginPassword;
    //Declaration Button
    Button buttonLogin;
    TextView signUpText;
     DatabaseHelper mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
        mDb=new DatabaseHelper( LoginActivity.this );

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);


        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        signUpText = (TextView) findViewById(R.id.signUpText);
        //this method used to set signup TextView click event
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(validate ()){
                    //DataBase part of the registration
                    String User = loginEmail.getText().toString().trim();
                    String password = loginPassword.getText().toString().trim();
                    Boolean res = mDb.CheckUser( User, password );
                    if (res == true){
                        //User Logged in Successfully Launch You home screen activity
                        Intent login=new Intent(LoginActivity.this, MainProfileActivity.class);
                        login.putExtra("username",User);
                        startActivity(login);
                    }//end if
                    else {
                        Toast.makeText( LoginActivity.this, "Email or Password are not valid !", Toast.LENGTH_LONG ).show();
                    }//end else
                }//end if
               /* else {
                    Toast toast=Toast. makeText(LoginActivity.this,"Email or Password are not valid !",Toast. LENGTH_SHORT);
                    toast.show ();
                }
                */


            }
        } );
    }
    private boolean validate() {
        boolean valid = false;
        //Get values from EditText fields
        String emailValue = loginEmail.getText().toString();
        String passwordValue = loginPassword.getText().toString();

        //Handling validation for Valid Email field

        if (emailValue.isEmpty()||passwordValue.isEmpty()) {
            valid = false;
            if (emailValue.isEmpty())
            loginEmail.setError("Empty Email!");
            if (passwordValue.isEmpty())
            loginPassword.setError("Empty password!");
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            valid = false;
            loginEmail.setError("Please enter valid email!");
        }

        else {
                if (passwordValue.length() >7) {
                    valid = true;
                    loginPassword.setError(null);
                    loginEmail.setError(null);
                } else {
                    valid = false;
                    loginPassword.setError("Password is to short!");
                }
            }
        return valid;
    }
}
