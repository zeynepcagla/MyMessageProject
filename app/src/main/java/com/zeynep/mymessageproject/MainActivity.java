package com.zeynep.mymessageproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
//baslangıc ekranı
public class MainActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    Context context=this;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();

    }
    public void login(View view){
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
        finish();


    }
    public void register(View v){

        Intent intent =  new Intent(context,RegisterActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onStart() {
        super.onStart();
//bak!!
//
/*

        if(firebaseUser !=null) {
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("uid", firebaseUser.getUid());
            startActivity(intent);
        }
*/


    }


}