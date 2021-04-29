package com.zeynep.mymessageproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.zeynep.mymessageproject.Adapters.ViewPagerAdapters;
import com.zeynep.mymessageproject.Fragments.MessagesFragment;
import com.zeynep.mymessageproject.Fragments.ProfileFragment;
import com.zeynep.mymessageproject.Fragments.UsersFragment;
import com.google.android.material.tabs.TabLayout;
//mesaj ekranımız
public class MessageActivity extends AppCompatActivity {
ViewPagerAdapters viewPagerAdapters;
ViewPager viewPager;
TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        viewPagerAdapters=new ViewPagerAdapters(getSupportFragmentManager());
        viewPagerAdapters.addFragment(new UsersFragment(),"USERS");
        viewPagerAdapters.addFragment(new MessagesFragment(),"MESSAGE");
        viewPagerAdapters.addFragment(new ProfileFragment(),"PROFİLE");
        viewPager.setAdapter(viewPagerAdapters);
        tabLayout.setupWithViewPager(viewPager);

    }
}