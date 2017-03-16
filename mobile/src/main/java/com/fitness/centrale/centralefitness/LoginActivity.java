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

import com.fitness.centrale.centralefitness.requests.AuthWithCredsRequest;

/**
 * Created by remy on 16/03/17.
 */

public class LoginActivity extends AppCompatActivity {


    TextView warningLabel;
    EditText loginEdit;
    EditText passwordEdit;
    Button submitButton;
    Button registerButton;


    private void initComponents(){
        warningLabel = (TextView) findViewById(R.id.LoginWarningLabel);
        loginEdit = (EditText) findViewById(R.id.LoginLogin);
        passwordEdit = (EditText) findViewById(R.id.LoginPassword);
        passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        submitButton = (Button) findViewById(R.id.LoginSubmit);
        registerButton = (Button) findViewById(R.id.LoginRegister);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                ResponseObject obj = new AuthWithCredsRequest(login, password).prepareRequest();
                if (!obj.isAnError()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    warningLabel.setText("Bad credentials");
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }
}
