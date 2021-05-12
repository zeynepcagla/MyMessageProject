package com.zeynep.mymessageproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
Context context=this;
DatabaseReference reference;
AppCompatButton btnSave;
private FirebaseAuth mAuth;
FirebaseUser fUser;
    ProgressBar mProgressBar;
    private EditText edtNameSurename,edtPhone,edtEmail,edtPassword,edtPasswordRetry;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnSave=findViewById(R.id.btnSave);
        mAuth=FirebaseAuth.getInstance();
        mProgressBar=findViewById(R.id.mProgres);
        mProgressBar.setVisibility(View.GONE);
        edtNameSurename=findViewById(R.id.edtName_Surname);
        edtPhone=findViewById(R.id.edtPhone);
        edtEmail=findViewById(R.id.edtMail);
        edtPassword=findViewById(R.id.edtPassword);
        edtPasswordRetry=findViewById(R.id.edtPasswordRetTry);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameSurename=edtNameSurename.getText().toString();
                String phone=edtPhone.getText().toString();
                String email=edtEmail.getText().toString();
                String password=edtPassword.getText().toString();
                String passwordRetry=edtPasswordRetry.getText().toString();

                if(nameSurename.trim().isEmpty()){
                    Toast.makeText(context,"Adı Soyadı Alanı Boş Geçilemez ",Toast.LENGTH_LONG).show();
                    //önce mesaj verecez sonra imleci o kutucuğa gönderecez
                    edtNameSurename.requestFocus();


                }

               else if(phone.trim().isEmpty()){
                    Toast.makeText(context,"Telefon Alanı Boş Geçilemez ",Toast.LENGTH_LONG).show();
                    //önce mesaj verecez sonra imleci o kutucuğa gönderecez
                    edtPhone.requestFocus();


                }

               else if(email.trim().isEmpty()){
                    Toast.makeText(context,"Email  Alanı Boş Geçilemez ",Toast.LENGTH_LONG).show();
                    //önce mesaj verecez sonra imleci o kutucuğa gönderecez
                    edtEmail.requestFocus();


                }

             else if(password.trim().isEmpty()){
                    Toast.makeText(context,"Şifre Alanı Boş Geçilemez ",Toast.LENGTH_LONG).show();
                    //önce mesaj verecez sonra imleci o kutucuğa gönderecez
                    edtPassword.requestFocus();


                }


               else if(passwordRetry.trim().isEmpty()){
                    Toast.makeText(context,"Şifre Tekrarı Alanı Boş Geçilemez ",Toast.LENGTH_LONG).show();
                    //önce mesaj verecez sonra imleci o kutucuğa gönderecez
                    edtPasswordRetry.requestFocus();

                }



                //şifre ile şifre tekrarı uyuşmuyorsa hata varecez
            else if(!password.equals(passwordRetry)){
                    Toast.makeText(context,"Şifre İle Şifre Tekrarı Uyuşmuyor ",Toast.LENGTH_LONG).show();
                    //önce mesaj verecez sonra imleci o kutucuğa gönderecez
                    edtPasswordRetry.requestFocus();

                }
               else {
                    register(nameSurename,phone,email,password);
                }
            }
        });


    }

    public void register(String nameSurname,String phone, String email, String password) {
    mProgressBar.setVisibility(View.VISIBLE);
    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            fUser=mAuth.getCurrentUser();
            String uid=fUser.getUid();
             reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            HashMap<String, String> mMap=  new HashMap<>();
            mMap.put("id",uid);
            mMap.put("name_surname",nameSurname);
            mMap.put("phone",phone);
            mMap.put("imageURL","defalut");
            mMap.put("durum","offline");


                       reference.setValue(mMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(context,"Kullanıcı Eklendi ",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(context, HomeActivity.class);
                                //kullanıcı bir kere giriş yaptıktan sonra geri tuşuna bastığında bir daha logine yönlendirmez
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(context,"Kullanıcı Eklerken Hata Oluştu ",Toast.LENGTH_LONG).show();
                            }

                        }
                    });


        }
        else{

            Toast.makeText(context,"Sorun Oluştu ",Toast.LENGTH_LONG).show();
        }


    }
});

    }
        public  void back(View view){
        Intent intent=new Intent(context,MainActivity.class);
        startActivity(intent);
       finish();
    }
}