package com.zeynep.mymessageproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zeynep.mymessageproject.Fragments.AramaFragment;
import com.zeynep.mymessageproject.Fragments.HomeFragment;

import java.util.HashMap;

//Ana Ekranımız
public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment = null;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationliste);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentacilacagicerceve,new HomeFragment()).commit();

    }
    //üst Açılır Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ust_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profil:
                Intent intent = new Intent(HomeActivity.this,ProfilimActivity.class);
                startActivity(intent);
        }
        switch (item.getItemId()){
            case R.id.ayarlar:
                Intent intent = new Intent(HomeActivity.this,AyarlarActivity.class);
                startActivity(intent);

        }
        switch (item.getItemId()){
            case R.id.exit:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
        }
        return true;
    }

    //alt menu
    private BottomNavigationView.OnNavigationItemSelectedListener navigationliste =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {

                        case R.id.home:
                            fragment = new HomeFragment();
                            break;

                        case R.id.arama:
                            fragment = new AramaFragment();
                            break;
                    }
                    if (fragment !=null) {

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentacilacagicerceve,fragment).commit();
                    }

                    return true;
                }
            };

    private void online (final String durum){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> mMap=  new HashMap<>();
        mMap.put("durum",durum);
        db.updateChildren(mMap);

    }

    @Override
    protected void onStop() {
        super.onStop();
        online("offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        online("online");
    }
}