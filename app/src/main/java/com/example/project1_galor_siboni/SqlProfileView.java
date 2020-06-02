package com.example.project1_galor_siboni;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SqlProfileView extends AppCompatActivity {

    private TextView Name, ID, Age, Phone;
    private String userName, password;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile_view );

        Name = findViewById( R.id.Name );
        ID = findViewById( R.id.ID );
        Age = findViewById( R.id.Age );
        Phone = findViewById( R.id.PhoneNumber );
        imageView = findViewById( R.id.Image );

        Intent intent = getIntent();
        userName = intent.getStringExtra( "sqlUserName" );
        password = intent.getStringExtra( "sqlPassword" );

        String imageUrl = "https://i.pinimg.com/originals/6c/05/fe/6c05feb252ee53ff55c321b3ba082b24.jpg";
        Picasso.get().load( Uri.parse( imageUrl ) ).into( imageView );
        JSONObject type = new JSONObject();
        JSONObject userOBJ = new JSONObject();
        JSONArray userJsonArray = new JSONArray();
        try {
            type.put("type", "getProfile");
            userOBJ.put("userName", userName);
            userOBJ.put("password", password);
            userJsonArray.put( type );
            userJsonArray.put( userOBJ );
            String userJsonStr = taskExecute(userJsonArray.toString());
            userOBJ = new JSONObject( userJsonStr );
            Name.setText("Name: " +  userOBJ.getString("name") );
            ID.setText( "ID: " +  userOBJ.getString("id") );
            Age.setText( "Age: " +  userOBJ.getString("age") );
            Phone.setText( "Phone: " +  userOBJ.getString("phone") );


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private String taskExecute( String str) throws ExecutionException, InterruptedException {
        RetrieveFeedTask r = new RetrieveFeedTask();
        r.execute( str );
        String result = r.get();
        if (result == "falsh") {
            Toast.makeText( SqlProfileView.this, "failed to get Profile " + str,
                    Toast.LENGTH_SHORT ).show();
            return "false";
        }
        else return result;

    }

}
