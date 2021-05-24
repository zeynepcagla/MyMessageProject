package com.zeynep.mymessageproject.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zeynep.mymessageproject.Adapters.UserAdapter;
import com.zeynep.mymessageproject.Model.User;
import com.zeynep.mymessageproject.R;

import java.util.ArrayList;
import java.util.List;


public class AramaFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter kullaniciAdapter;
    private List<User> mKullanicilar =new ArrayList<>();
    private EditText aramaBar;

    private FirebaseUser mevcutkullanici = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_arama, container, false);
        recyclerView=view.findViewById(R.id.reycler_arama);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        kullaniciAdapter =  new UserAdapter(getContext(),mKullanicilar,true);
        recyclerView.setAdapter((kullaniciAdapter));

        aramaBar = view.findViewById(R.id.aramaBar);

        UserOku();
        //Arama search
        aramaBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               Arama(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;

    }

    private void UserOku() {

        FirebaseDatabase.getInstance().getReference("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(aramaBar.getText().toString().equals("")){
                            mKullanicilar.clear();;

                            for (DataSnapshot snapshot1 : snapshot.getChildren()){// database deki kullanılcıların alt çocuklarını oku
                                User kullanici =snapshot1.getValue(User.class);
                                if(!kullanici.getId().equals(mevcutkullanici.getUid()))
                                    mKullanicilar.add(kullanici);
                            }
                        }


                        kullaniciAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }




    private void Arama(String s) {
        Query sorgu = FirebaseDatabase.getInstance().getReference("Users").orderByChild("name_surname")
                .startAt(s).endAt(s+"\uf8ff");
        sorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mKullanicilar.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    User kullanici = snapshot1.getValue(User.class);
                    mKullanicilar.add(kullanici);

                }
                kullaniciAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


}}