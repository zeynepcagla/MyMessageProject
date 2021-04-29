package com.zeynep.mymessageproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeynep.mymessageproject.Adapters.UserAdapter;
import com.zeynep.mymessageproject.Model.User;
import com.zeynep.mymessageproject.R;




import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {

RecyclerView recyclerView;
List<User> userList;
FirebaseAuth mAuth;

UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_users, container, false);
        mAuth = FirebaseAuth.getInstance();

        userList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rcyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        readUsers();
        return  view;

    }
    public void readUsers(){
        DatabaseReference databaseReference=
                FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        userList.clear();;
        for(DataSnapshot ds: snapshot.getChildren()){
            User user=ds.getValue(User.class);
            if (!user.getId().equals(mAuth.getUid()))
                userList.add(user);
        }
        userAdapter=new UserAdapter(userList,getContext());
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
    }

}