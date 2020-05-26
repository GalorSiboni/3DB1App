package com.example.project1_galor_siboni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MongoLogin extends AppCompatActivity {

    private EditText editUserName, editPass;
    private TextView loginText;
    private Button registerBTN, loginBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initViews();

        loginText.setText( "Login via MongoDB" );

        registerBTN.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MongoLogin.this, MongoProfileEdit.class );
                intent.putExtra( "mongoUserName", editUserName.getText().toString() );
                intent.putExtra( "mongoPassword", editPass.getText().toString() );
                startActivity( intent );
                finish();
            }
        } );
    }


    private void initViews(){
        // initialise views
        editUserName = findViewById( R.id.editUser );
        editPass = findViewById( R.id.editPassword );
        registerBTN = findViewById( R.id.bottom );
        loginBTN = findViewById( R.id.bottom );
        loginText = findViewById( R.id.logInText );
    }
}
