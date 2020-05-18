package com.example.afinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.CreateProfile.CreateGenderActivity;
import com.example.afinal.Database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    EditText register_username, register_email, register_password, register_confirmpassword;
    //Declaration ImageButton
    ImageButton register_profilePic;
    private static final int GALLERY_CODE = 1;
    private Uri mImageUri;
    //Declaration Button
    Button register_buttoncreateaccount;
    TextView register_login;
    DatabaseHelper mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDb=new DatabaseHelper( RegisterActivity.this );
        //this method is used to connect XML views to its Objects
        register_confirmpassword = findViewById(R.id.register_confirmPassword);
        register_username = findViewById(R.id.register_userName);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_buttoncreateaccount = findViewById(R.id.register_buttonCreateAccount);
        register_profilePic = findViewById(R.id.register_profilePic);

        //this method used to set Login TextView click event
        register_login = findViewById(R.id.register_login);
        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //to upload picture from gallery
        register_profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });

        register_buttoncreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {
                    //DataBase part of the registration
                    String username=register_username.getText().toString().trim();
                    String email=register_email.getText().toString().trim();
                    String password=register_password.getText().toString().trim();
                    String configpassword=register_confirmpassword.getText().toString().trim();
                    if(password.equals( configpassword )){
                        long val=mDb.adduser(username,email,password);
                        if(val>0){
                            Toast.makeText( RegisterActivity.this,"your have registered",Toast.LENGTH_LONG).show();
                            //User Logged in Successfully Launch You home screen activity
                            Intent moveTolog = new Intent(RegisterActivity.this, CreateGenderActivity.class);
                            moveTolog.putExtra("username",register_username.getText().toString());
                            startActivity(moveTolog);
                            Toast toast = Toast.makeText(RegisterActivity.this, "Successfully SignUp", Toast.LENGTH_SHORT);toast.show();
                            //finish();
                        }//end if
                        else {
                            Toast.makeText( RegisterActivity.this,"error",Toast.LENGTH_LONG).show();
                        }//end else
                    }//end if
                    else
                    {
                        Toast.makeText( RegisterActivity.this,"password no validation",Toast.LENGTH_LONG).show();
                    }//end else
                }//end if
            }
        }
        );
    }//end onCreate()
    private boolean Validate() {
        boolean valid = false;

        //get values from EditText fields
        String usernamevalue = register_username.getText().toString();
        String emailvalue = register_email.getText().toString();
        String passwordvalue = register_password.getText().toString();
        String confirmpasswordvalue = register_confirmpassword.getText().toString();


        if (usernamevalue.isEmpty() || emailvalue.isEmpty() || passwordvalue.isEmpty() || confirmpasswordvalue.isEmpty()) {
            valid = false;
            if (usernamevalue.isEmpty())
                register_username.setError("Empty value");
            if (emailvalue.isEmpty())
                register_email.setError("Empty value");
            if (passwordvalue.isEmpty())
                register_password.setError("Empty value");
            if (confirmpasswordvalue.isEmpty())
                register_confirmpassword.setError("Empty value");
            return valid;
        } else {
            if (usernamevalue.length() <= 4) {
                valid = false;
                register_username.setError("short username < 5");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailvalue).matches()) {
                valid = false;
                register_email.setError("Please enter valid email!");
            } else if (!passwordvalue.equals(confirmpasswordvalue)) {
                register_confirmpassword.setError("Passwords are not matching!");
                register_confirmpassword.setFocusable(true);
                valid = false;
                register_password.setError("Passwords are not matching!");
            } else if (passwordvalue.length() <= 7) {
                valid = false;
                register_password.setError("short password < 8");
            }
            else {
                if (!passwordvalue.matches("(?=.*[0-9]).*")) {
                    valid = false;
                    register_password.setError("password should contain digits");
                }
                else if (!passwordvalue.matches("(?=.*[a-z]).*")) {
                    valid = false;
                    register_password.setError("password should contain lower case letter");
                }
                 else if (!passwordvalue.matches("(?=.*[A-Z]).*")) {
                    valid = false;
                    register_password.setError("password should contain upper case letter");
                }
                else if (!passwordvalue.matches("(?=.*[~!@#$%^&*()_/]).*")) {
                    valid = false;
                    register_password.setError("password should contain special character letter");
                }
                else {
                    valid = true;
                }
            }
        }
        return valid;
    }//end Validate()
}//end class