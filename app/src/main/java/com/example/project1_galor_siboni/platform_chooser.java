package com.example.project1_galor_siboni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class platform_chooser extends AppCompatActivity {

    private TextView headline;
    private ImageButton firebaseBTN, sqlBTN, mongoBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_platform_chooser );

            headline = findViewById( R.id.textView );
            firebaseBTN = findViewById( R.id.firebaseBTN );
            sqlBTN = findViewById( R.id.sqlBTN );
            mongoBTN = findViewById( R.id.mongoBTN );

            firebaseBTN.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    Intent FireBase_Intent = new Intent( platform_chooser.this, FirebaseMain.class );
                    startActivity( FireBase_Intent );
                }

            } );
            sqlBTN.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    Intent SQL_Intent = new Intent( platform_chooser.this, SqlLogin.class );
                    startActivity( SQL_Intent );
                }

            } );
            mongoBTN.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    Intent MongoDB_Intent = new Intent( platform_chooser.this, MongoLogin.class );
                    startActivity( MongoDB_Intent );
                }

            } );
        }
}