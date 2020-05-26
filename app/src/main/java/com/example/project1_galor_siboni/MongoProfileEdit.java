package com.example.project1_galor_siboni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;

public class MongoProfileEdit extends AppCompatActivity {
    // views for button
    private Button btnSelect, btnUpload, btnSubmit;
    //editText views
    private EditText Name, ID, Age, Phone;
    private String userName, password;


    private String s0 = "";
    private int s1 = 0, s2 = 0, s3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile_edit );


        Intent intent = getIntent();
        userName = intent.getStringExtra( "mongoUserName" );
        password = intent.getStringExtra( "mongoPassword" );

        try {
            createUser( userName, password );
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btnSubmit.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                    s0 = Name.getText().toString();
                    if(TextUtils.isDigitsOnly(ID.getText().toString()) && !ID.getText().toString().trim().equals( "" ))
                        s1 = Integer.parseInt(ID.getText().toString().trim());
                    else {
                        Toast.makeText( MongoProfileEdit.this, "ID can contain digits only!", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                    if(TextUtils.isDigitsOnly(Age.getText().toString()) && !Age.getText().toString().trim().equals( "" ))
                        s2 = Integer.parseInt(Age.getText().toString().trim());
                    else {
                        Toast.makeText( MongoProfileEdit.this, "Age can contain digits only!", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                    if(TextUtils.isDigitsOnly(Phone.getText().toString()) && !Phone.getText().toString().trim().equals( "" ))
                        s3 = Integer.parseInt(Phone.getText().toString().trim());
                    else {
                        Toast.makeText( MongoProfileEdit.this, "Phone Number can contain digits only!", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                    if (!s0.trim().isEmpty() && s1 != 0 && s2 != 0 && s3 != 0) {
                        final User user = new User( s0, s1, s2, s3, null );
                        try {
                            sendUser( user );
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    else{
                        Toast.makeText(MongoProfileEdit.this,"Please fill all the slots",Toast.LENGTH_SHORT).show();
                    }
                }});
            }



    public void sendUser(User user) throws ExecutionException, InterruptedException {
        RetrieveFeedTask r = new RetrieveFeedTask();
        boolean pushType = taskExecute( r, "profileEdit" );
        boolean name = taskExecute( r, user.getName() );
        boolean id = taskExecute( r, "" + user.getID() );
        boolean age = taskExecute( r, "" + user.getAge() );
        boolean phone = taskExecute( r, "" + user.getPhone() );
        if(pushType == false || name == false || id == false || age == false || phone == false){
            Toast.makeText( MongoProfileEdit.this, "failed to save profile",
                    Toast.LENGTH_SHORT ).show();
        }
        else{
            Toast.makeText( MongoProfileEdit.this, "Register Complete!", Toast.LENGTH_SHORT ).show();
            Intent intent = new Intent( MongoProfileEdit.this, ProfileView.class );
            intent.putExtra( "mongoUserName", userName );
            intent.putExtra( "mongoPassword", password );
            startActivity( intent );
            finish();
        }
    }

    private void createUser(String userName, String password) throws ExecutionException, InterruptedException {
        RetrieveFeedTask r = new RetrieveFeedTask();
        boolean pushType = taskExecute( r, "createUser" );
        boolean username = taskExecute( r,  userName);
        boolean pass = taskExecute( r,  password);
        if(pushType == false || username == false || pass == false){
            Toast.makeText( MongoProfileEdit.this, "failed to create user",
                    Toast.LENGTH_SHORT ).show();
        }
    }


    private boolean taskExecute(RetrieveFeedTask r, String str) throws ExecutionException, InterruptedException {
        r.execute( str );
        if (r.get() == "falsh") {
            Toast.makeText( MongoProfileEdit.this, "failed to push " + str,
                    Toast.LENGTH_SHORT ).show();
            return false;
        }
        else return true;

        }

    private void initViews(){
            // initialise views
            btnSelect = findViewById(R.id.choosImg);
            btnUpload = findViewById(R.id.uploudImg);
            btnSubmit = findViewById(R.id.submitBTN);
            Name = findViewById( R.id.Name );
            ID = findViewById( R.id.ID );
            Age = findViewById( R.id.Age );
            Phone = findViewById( R.id.PhoneNumber );
            btnSelect.setVisibility( View.GONE );
            btnUpload.setVisibility( View.GONE );
    }
}
