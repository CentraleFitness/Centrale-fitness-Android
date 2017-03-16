package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fitness.centrale.centralefitness.requests.RegisterRequest;

/**
 * Created by remy on 16/03/17.
 */

public class RegisterActivity extends AppCompatActivity {

    TextView loginText;
    EditText login;
    TextView passwordText;
    EditText password;
    TextView emailText;
    EditText email;
    EditText firstName;
    EditText lastName;
    EditText phone;
    Button submit;
    Button loginButton;


    public void initComponents(){
        loginText = (TextView) findViewById(R.id.RegisterLoginLbl);
        login = (EditText) findViewById(R.id.RegisterLogin);

        passwordText = (TextView) findViewById(R.id.RegisterPasswordLbl);
        password = (EditText) findViewById(R.id.RegisterPassword);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        emailText = (TextView) findViewById(R.id.RegisterEmail);
        email = (EditText) findViewById(R.id.RegisterEmail);
        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        firstName = (EditText) findViewById(R.id.RegisterFirstName);
        lastName = (EditText) findViewById(R.id.RegisterLastName);

        phone = (EditText) findViewById(R.id.RegisterPhone);
        phone.setInputType(InputType.TYPE_CLASS_PHONE);

        submit = (Button) findViewById(R.id.RegisterSubmit);
        loginButton  = (Button) findViewById(R.id.RegisterLoginButton);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginStr = login.getText().toString();
                String passStr = password.getText().toString();
                String emailStr = email.getText().toString();
                String firstNameStr = firstName.getText().toString();
                String lastNameStr = lastName.getText().toString();
                String phoneStr = phone.getText().toString();
                ResponseObject obj = new RegisterRequest(loginStr, passStr, emailStr, lastNameStr, firstNameStr, phoneStr).prepareRequest();
                if (!obj.isAnError()){
                    //TODO : Save le token
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}
