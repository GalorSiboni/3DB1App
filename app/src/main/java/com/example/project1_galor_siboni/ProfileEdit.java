package com.example.project1_galor_siboni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.UUID;

public class ProfileEdit extends AppCompatActivity {
    // views for button
    private Button btnSelect, btnUpload, btnSubmit;
    //editText views
    private EditText Name, ID, Age, Phone;


    private String s0 = "", uid, imageUrl;
    private int s1 = 0, s2 = 0, s3 = 0;
    private boolean editPhotoFlag = false;

    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    StorageReference storageReference;
    private StorageTask uploadTask;

    // instance for firebase db and dbReference
    FirebaseDatabase db;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile_edit );

        // initialise views and make btnUpload Gone
         initViews();

        // get the Firebase  storage reference
        storageReference = FirebaseStorage.getInstance().getReference().child( "ImageFolder" );

        Intent intent = getIntent();
        uid = intent.getStringExtra( "uid" );

        // get the Firebase  database reference
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users").child( uid );

        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
                btnUpload.setVisibility( View.VISIBLE );
                btnSelect.setVisibility( View.GONE );
                editPhotoFlag = true;
            }
        });

        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPhotoFlag) {
                    if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText( ProfileEdit.this, "Upload in progress..", Toast.LENGTH_SHORT ).show();
                    } else {
                        uploadImage();
                        btnSelect.setVisibility( View.VISIBLE );
                        btnUpload.setVisibility( View.GONE );
                        editPhotoFlag = true;
                    }
                }}});
    // on pressing btnSubmit we move to profileView
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s0 = Name.getText().toString();
                if(TextUtils.isDigitsOnly(ID.getText().toString()))
                    s1 = Integer.parseInt(ID.getText().toString());
                else Toast.makeText( ProfileEdit.this, "ID can contain digits only!", Toast.LENGTH_SHORT ).show();
                if(TextUtils.isDigitsOnly(Age.getText().toString()))
                    s2 = Integer.parseInt(Age.getText().toString());
                else Toast.makeText( ProfileEdit.this, "Age can contain digits only!", Toast.LENGTH_SHORT ).show();
                if(TextUtils.isDigitsOnly(Phone.getText().toString()))
                    s3 = Integer.parseInt(Phone.getText().toString());
                else Toast.makeText( ProfileEdit.this, "Phone Number can contain digits only!", Toast.LENGTH_SHORT ).show();
                if (!s0.trim().isEmpty() && s1 != 0 && s2 != 0 && s3 != 0) {
                    final User user = new User( s0, s1, s2, s3, imageUrl );
                    users.setValue( user );
                    Toast.makeText( ProfileEdit.this, "Register Success!", Toast.LENGTH_SHORT ).show();
                    Intent intent = new Intent( ProfileEdit.this, ProfileView.class );
                    intent.putExtra( "uid", uid );
                    startActivity( intent );
                    finish();
                }

               else{
                    Toast.makeText(ProfileEdit.this,"Please fill all the slots",Toast.LENGTH_SHORT).show();
                    }
        }});
    }

    // Select Image method
    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    // UploadImage method
    private void uploadImage()
    {
        if(filePath != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle( "Uploading..." );
            progressDialog.show();

            final StorageReference ref = storageReference.child("image/" + UUID.randomUUID() );
            uploadTask = ref.putFile(filePath).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText( ProfileEdit.this, "Upload Success", Toast.LENGTH_SHORT ).show();
                    ref.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            imageUrl = url;
                        }
                    } );
                }})
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText( ProfileEdit.this, "Failed "+e.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    } )
                    .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploading " +(int)progress + "%");
                        }
                    } );
        }
    }
    private void initViews(){
        // initialise views
        btnSelect = findViewById(R.id.choosImg);
        btnUpload = findViewById(R.id.uploudImg);
        btnSubmit = findViewById(R.id.submitBTN);
        imageView = findViewById(R.id.Image);
        Name = findViewById( R.id.Name );
        ID = findViewById( R.id.ID );
        Age = findViewById( R.id.Age );
        Phone = findViewById( R.id.PhoneNumber );
        btnUpload.setVisibility( View.GONE );
    }
}
