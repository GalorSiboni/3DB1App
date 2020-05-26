package com.example.project1_galor_siboni;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseMain extends AppCompatActivity {
        private EditText editEmail, editPass;
        private TextView loginText;

        //Firebase
        private FirebaseAuth auth;
        private Button logBTN,regBTN;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );
            logBTN = findViewById( R.id.logBTN );
            regBTN = findViewById( R.id.regBTN );
            editEmail = findViewById( R.id.editUser );
            editPass = findViewById( R.id.editPassword );
            loginText = findViewById( R.id.logInText );

            loginText.setText( "Login via FireBase");

            //Firebase
            auth = FirebaseAuth.getInstance();

            logBTN.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    loginFunc(v);
                    }
            } );
            regBTN.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    regFunc(v);
                    }
            } );
        }

            public void loginFunc(View view) {

                auth.signInWithEmailAndPassword(editEmail.getText().toString(), editPass.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText( FirebaseMain.this, "login ok.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent( FirebaseMain.this, ProfileView.class );
                                    intent.putExtra( "uid", task.getResult().getUser().getUid());
                                    startActivity( intent );
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText( FirebaseMain.this, "login failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });

            }

            public void regFunc(View view){
                if (editEmail.getText().toString().isEmpty() || editPass.getText().toString().isEmpty())
                    Toast.makeText( FirebaseMain.this,"Please fill the lines.",
                            Toast.LENGTH_SHORT).show();

                else
                auth.createUserWithEmailAndPassword(editEmail.getText().toString(),editPass.getText().toString())
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                        // Sign in success, update UI with the signed-in user's information
                            Toast.makeText( FirebaseMain.this,"REG ok.",
                            Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent( FirebaseMain.this, ProfileEdit.class );
                            intent.putExtra( "uid", task.getResult().getUser().getUid());
                            startActivity( intent );
                            finish();
                        }else{
                        // If sign in fails, display a message to the user.
                        Toast.makeText( FirebaseMain.this,"REG failed.",
                        Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
    }
