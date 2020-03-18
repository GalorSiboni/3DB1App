package com.example.project1_galor_siboni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.squareup.picasso.Picasso;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileView extends AppCompatActivity {
    private String uid, imageUrl;

    //textViews views
    private TextView Name, ID, Age, Phone;


    // view for image view
    private ImageView imageView;

    // instance for firebase db and dbReference
    FirebaseDatabase db;
    DatabaseReference users;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile_view );

        initViews();

        Intent intent = getIntent();
        uid = intent.getStringExtra( "uid" );

        // get the Firebase  database reference
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users").child( uid );

        users.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Name.setText("Name: " + user.getName());
                ID.setText("ID: " + user.getID());
                Age.setText("Age: " + user.getAge());
                Phone.setText("Phone Number: 0" + user.getPhone());
                if (!user.getImageUrl().trim().isEmpty() || user.getImageUrl() != null) {
                    imageUrl = user.getImageUrl();
                    Picasso.get().load( Uri.parse( imageUrl ) ).into( imageView );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        }

    private void initViews() {
        // initialise views
        imageView = findViewById( R.id.Image );
        Name = findViewById( R.id.Name );
        ID = findViewById( R.id.ID );
        Age = findViewById( R.id.Age );
        Phone = findViewById( R.id.PhoneNumber );
    }

    }
