package com.example.project1_galor_siboni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MongoProfileView extends AppCompatActivity {

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
        userName = intent.getStringExtra( "mongoUserName" );
        password = intent.getStringExtra( "mongoPassword" );

        String imageUrl = "https://zdnet3.cbsistatic.com/hub/i/r/2018/02/16/8abdb3e1-47bc-446e-9871-c4e11a46f680/resize/1200x900/66e2d67a951cc4a3a60be76f56e0d105/mongo-db-logo.png";
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
            Toast.makeText( MongoProfileView.this, "failed to get Profile " + str,
                    Toast.LENGTH_SHORT ).show();
            return "false";
        }
        else return result;

    }

}
