package com.zeynep.mymessageproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeynep.mymessageproject.Adapters.UserAdapter;
import com.zeynep.mymessageproject.Model.Chat;
import com.zeynep.mymessageproject.Model.User;
import com.zeynep.mymessageproject.R;

import java.util.ArrayList;
import java.util.List;


public class MesajFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter kullaniciAdapter;
    private List<User> mKullanicilar;
    private List<String> userList = new ArrayList<>();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_mesaj, container, false);

        recyclerView=view.findViewById(R.id.mesajlistesirecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mKullanicilar=new ArrayList<>();
        kullaniciAdapter =  new UserAdapter(getContext(),mKullanicilar,true);
        recyclerView.setAdapter((kullaniciAdapter));

        FirebaseDatabase.getInstance().getReference("Mesajlar").child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            userList.clear();
                            if (snapshot.exists()){
                                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                    Chat  chat =snapshot1.getValue(Chat.class);
                                    if (chat.getGonderen().equals(firebaseUser.getUid())){
                                        userList.add(chat.getAlici());
                                    }
                                    if (chat.getAlici().equals(firebaseUser.getUid())){
                                        userList.add(chat.getGonderen());
                                    }
                                }
                            }
                        KullaniciOku();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return view;
    }

    private void KullaniciOku() {

        FirebaseDatabase.getInstance().getReference("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mKullanicilar.clear();

                        for (DataSnapshot snapshot1 : snapshot.getChildren()){// database deki kullanılcıların alt çocuklarını oku
                            User kullanici =snapshot1.getValue(User.class);

                             for  (String id : userList){
                                 if(kullanici.getId().equals(id) && !mKullanicilar.contains(kullanici)){
                                     mKullanicilar.add(kullanici);
                                     if(mKullanicilar.size() !=0){

                                     }
                                     else {
                                         mKullanicilar.add(kullanici);
                                     }

                                 }
                             }
                                                mKullanicilar.add(kullanici);

                        }
                        //kullanıcı listesini yenile komutu
                        kullaniciAdapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}