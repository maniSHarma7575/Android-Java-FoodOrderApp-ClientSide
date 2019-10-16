package com.example.bhandara;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bhandara.Common.Common;
import com.example.bhandara.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {

    MaterialEditText edtphone,edtpassword;
    Button btnsignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtphone=(MaterialEditText)findViewById(R.id.edtPhone);
        edtpassword=(MaterialEditText)findViewById(R.id.edtPassword);
        btnsignin=findViewById(R.id.insign);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReferenceFromUrl("https://bhandara-eee72.firebaseio.com/User");
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog=new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(edtphone.getText().toString()).exists()) {
                            mDialog.dismiss();

                            User user = dataSnapshot.child(edtphone.getText().toString()).getValue(User.class);
                            user.setPhone(edtphone.getText().toString());

                            if (dataSnapshot.child(edtphone.getText().toString()).child("Password").getValue(String.class).equals(edtpassword.getText().toString())) {
                               Intent homeIntent=new Intent(SignInActivity.this,Home.class);
                                Common.currentUser=user;
                                startActivity(homeIntent);
                                finish();

                            } else {
                                Toast.makeText(SignInActivity.this, "Sign in Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "Account Dosen't exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
