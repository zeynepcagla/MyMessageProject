package com.zeynep.mymessageproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//giris ekranı
public class LoginActivity extends AppCompatActivity {

    Context context=this;
    EditText editTextUsername,editTextPassword;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        editTextUsername=findViewById(R.id.editTextUsername);
        editTextPassword=findViewById(R.id.editTextPassword);

      /* // kullanıcı bir kere giriş yaptıktan sonra direk ana sayfa yönlendirilir
        if(mAuth !=null) {
            Intent intent = new Intent(context, HomeActivity.class);

            //kullanıcı bir kere giriş yaptıktan sonra geri tuşuna bastığında bir daha logine yönlendirmez
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } //if*/

    }
    public  void login(View view){
        String username=editTextUsername.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if(username.isEmpty()){
            Toast.makeText(context,"Kullanıcı Adı Alanı Boş Geçilemez " , Toast.LENGTH_SHORT).show();

            editTextUsername.requestFocus();
            return;
        }

        else if(password.isEmpty()){
            Toast.makeText(context,"Şifre Alanı Boş Geçilemez " , Toast.LENGTH_SHORT).show();
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){

                    mUser=mAuth.getCurrentUser();
                    Intent intent=new Intent(context, HomeActivity.class);
                    intent.putExtra("uid",mUser.getUid());

                    //kullanıcı bir kere giriş yaptıktan sonra geri tuşuna bastığında bir daha logine yönlendirmez
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else{

                    Toast.makeText(context,"Kullanıcı adı veya şifre hatalı  " , Toast.LENGTH_LONG).show();
                    //şifre kutuucuğunun içini boşalt
                    editTextPassword.setText("");
                    //git şifre kutucuğuna konumlan
                    editTextPassword.requestFocus();
                }
            }
        });

    }
    public  void  register(View view){

        Intent intent=new Intent(context,MainActivity.class);
        startActivity(intent);
        finish();
    }

}