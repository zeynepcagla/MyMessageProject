package com.zeynep.mymessageproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeynep.mymessageproject.Model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesajActivity extends AppCompatActivity {

    private ImageButton geritusu;
    private TextView    kullaniciadi;
    private EditText   mesajgirdi;
    private ImageView  fotoekle,gonder;
    private CircleImageView profilresim;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj);
        geritusu = findViewById(R.id.geritusu);
        kullaniciadi = findViewById(R.id.kullaniciAdiMesaj);
        profilresim = findViewById(R.id.profilresim);
        fotoekle = findViewById(R.id.fotoEkleMesaj);
        gonder = findViewById(R.id.gonderBtn);

        kullaniciBilgisiAl();

    }

    private void kullaniciBilgisiAl() {
    intent = getIntent();
    final String userId = intent.getStringExtra("userId");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user =snapshot.getValue(User.class);
                kullaniciadi.setText(user.getName_surname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}