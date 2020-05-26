package com.example.project1_galor_siboni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SqlLogin extends AppCompatActivity {

    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        loginText = findViewById( R.id.logInText );

        loginText.setText( "Login via SQL");

    }
}
