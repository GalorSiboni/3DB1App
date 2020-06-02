package com.example.project1_galor_siboni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SqlLogin extends AppCompatActivity {

    private EditText editUserName, editPass;
    private TextView loginText;
    private Button registerBTN, loginBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initViews();

        loginText.setText( "Login via SQL");

        registerBTN.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( SqlLogin.this, SqlProfileEdit.class );

                String userName = editUserName.getText().toString();
                String password = editPass.getText().toString();
                if (!userName.isEmpty() && !password.isEmpty() ) {
                    intent.putExtra( "sqlUserName", userName );
                    intent.putExtra( "sqlPassword", password );
                    startActivity( intent );
                    finish();
                }
                else
                    Toast.makeText( SqlLogin.this, "Username and Password cannot be empty!", Toast.LENGTH_SHORT ).show();

            }
        } );
        loginBTN.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( SqlLogin.this, SqlProfileView.class );
                startActivity( intent );
                finish();
                String userName = editUserName.getText().toString();
                String password = editPass.getText().toString();
                if (!userName.isEmpty() && !password.isEmpty() ) {
                    intent.putExtra( "sqlUserName", userName );
                    intent.putExtra( "sqlPassword", password );
                    startActivity( intent );
                    finish();
                }
                else
                    Toast.makeText( SqlLogin.this, "Username and Password cannot be empty!", Toast.LENGTH_SHORT ).show();

            }
        } );
    }


    private void initViews(){
        // initialise views
        editUserName = findViewById( R.id.editUser );
        editPass = findViewById( R.id.editPassword );
        registerBTN = findViewById( R.id.regBTN );
        loginBTN = findViewById( R.id.logBTN );
        loginText = findViewById( R.id.logInText );
    }
}
