package com.zeynep.mymessageproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.zeynep.mymessageproject.Adapters.ChatAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeynep.mymessageproject.Model.Chat;
import com.zeynep.mymessageproject.Model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesajActivity extends AppCompatActivity {

    private ImageButton geritusu;
    private CircleImageView profilresim;
    private TextView   kullaniciadi;
    private EditText mesajgirdi;
    private ImageView fotoekle,gonder;
    private Intent intent;
    private FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

    private  StringBuilder saat,tarih;

    private RecyclerView recyclerView;
    private ChatAdapter userAdapter;
    private List<Chat> mMesaj = new ArrayList<>();

    private  ValueEventListener value;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj);
        saat=new StringBuilder();
        tarih= new StringBuilder();

        Date bugun = Calendar.getInstance().getTime();
        SimpleDateFormat formatte = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatte.format(bugun);
        tarih.append(date);

        Date saatzaman = Calendar.getInstance().getTime();
        SimpleDateFormat saatformat = new SimpleDateFormat("hh:mm");
        String saati = saatformat.format(saatzaman);
        saat.append(saati);


        recyclerView=findViewById(R.id.mesajrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        userAdapter = new ChatAdapter(getApplicationContext(),mMesaj);
        recyclerView.setAdapter(userAdapter);

        geritusu = findViewById(R.id.geritusu);
        profilresim = findViewById(R.id.profilresim);
        kullaniciadi = findViewById(R.id.kullaniciAdiMesaj);
        mesajgirdi = findViewById(R.id.mesajGirdiAlani);
        fotoekle = findViewById(R.id.fotoEkleMesaj);
        gonder = findViewById(R.id.gonderBtn);

        kullaniciBilgisiAl();


    }


    private void kullaniciBilgisiAl() {
        intent = getIntent();
        final String userId = intent.getStringExtra("userId");

        FirebaseDatabase.getInstance().getReference("Users").child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User kullanici = snapshot.getValue(User.class);
                        kullaniciadi.setText(kullanici.getName_surname());
                        mesajOku(firebaseUser.getUid(),userId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mesajgirdisi = mesajgirdi.getText().toString();

                if (!mesajgirdisi.equals("")){

                    Mesajgonderen(firebaseUser.getUid(),userId,mesajgirdisi);
                    MesajAlan(firebaseUser.getUid(),userId,mesajgirdisi);

                } else{
                    Toast.makeText(MesajActivity.this, "Mesaj Kısmı Boş", Toast.LENGTH_SHORT).show();
                }
                mesajgirdi.setText("");


            }
        });
    }



    private void mesajOku(final String benimid, final String aliciID) {
        FirebaseDatabase.getInstance().getReference("Mesajlar").child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mMesaj.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){

                            Chat chat= snapshot1.getValue(Chat.class);

                            if(chat.getAlici().equals(benimid) && chat.getGonderen().equals(aliciID) ||
                                    chat.getAlici().equals(aliciID) && chat.getGonderen().equals(benimid)){

                                mMesaj.add(chat);

                            }

                        }
                        userAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void Mesajgonderen(String gonderen, String alici, String mesaj) {

        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("gonderen", gonderen);
        hashMap.put("alici", alici);
        hashMap.put("mesaj", mesaj);
        hashMap.put("resim", "");
        hashMap.put("goruldu", false);
        hashMap.put("saat", saat.toString());
        hashMap.put("tarih",tarih.toString());


        FirebaseDatabase.getInstance().getReference().child("Mesajlar").child(firebaseUser.getUid()).push().setValue(hashMap);


    }

    private void MesajAlan(String gonderen, String alici, String mesaj) {

        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("gonderen", gonderen);
        hashMap.put("alici", alici);
        hashMap.put("mesaj", mesaj);
        hashMap.put("resim", "");
        hashMap.put("goruldu", false);
        hashMap.put("saat", saat.toString());
        hashMap.put("tarih",tarih.toString());

        FirebaseDatabase.getInstance().getReference().child("Mesajlar").child(alici).push().setValue(hashMap);


    }

    private void goruldu(){
        intent = getIntent();
        final String userId = intent.getStringExtra("userId");

        reference= FirebaseDatabase.getInstance().getReference("Mesajlar").child(userId);
        value = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot snapshot1: snapshot.getChildren()){
                Chat chat = snapshot1.getValue(Chat.class);
                if (chat.getAlici().equals(firebaseUser.getUid())){
                    HashMap<String, Object> hashMap = new HashMap();
                    hashMap.put("goruldu", true);
                    snapshot1.getRef().updateChildren(hashMap);
                }
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        goruldu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(value);
    }

}//end



