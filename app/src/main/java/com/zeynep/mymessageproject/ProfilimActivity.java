package com.zeynep.mymessageproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeynep.mymessageproject.Model.User;

public class ProfilimActivity extends AppCompatActivity {


    private  TextView phone;
    private  TextView name_surname;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilim);

        name_surname = findViewById(R.id.user_name);
        phone = findViewById(R.id.number);

   // kullaniciBilgileriniAl();


    }

   private void kullaniciBilgileriniAl(){
        intent = getIntent();
        final  String userId= intent.getStringExtra("userId");

       FirebaseDatabase.getInstance().getReference("Users").child(userId)
       .addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               User user  = snapshot.getValue(User.class);
               name_surname.setText(user.getName_surname());


           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }
}