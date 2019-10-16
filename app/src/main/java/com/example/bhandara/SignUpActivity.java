package com.example.bhandara;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    MaterialEditText edtphone,edtname,edtpassword;
    Button btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtname=(MaterialEditText)findViewById(R.id.upedtName);
        edtphone=(MaterialEditText)findViewById(R.id.upedtPhone);
        edtpassword=(MaterialEditText)findViewById(R.id.upedtPassword);
        btnsignup=(Button)findViewById(R.id.upsign);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReferenceFromUrl("https://bhandara-eee72.firebaseio.com/User");
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final ProgressDialog mDialog=new ProgressDialog(SignUpActivity.this);
            mDialog.setMessage("Please wait...");
            mDialog.show();
            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(edtphone.getText().toString()).exists())
                    {
                        mDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Phone Number already Register", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mDialog.dismiss();
                        String Name=edtname.getText().toString();
                        String Password=edtpassword.getText().toString();
                        table_user.child(edtphone.getText().toString()).child("Name").setValue(Name);
                        table_user.child(edtphone.getText().toString()).child("Password").setValue(Password);
                        Toast.makeText(SignUpActivity.this, "Sign up Successfull", Toast.LENGTH_SHORT).show();
                        finish();
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
