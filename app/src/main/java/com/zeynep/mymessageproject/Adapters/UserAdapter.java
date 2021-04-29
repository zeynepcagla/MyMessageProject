package com.zeynep.mymessageproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zeynep.mymessageproject.Model.User;
import com.zeynep.mymessageproject.R;
import com.zeynep.mymessageproject.Model.User;

import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList,Context context){
        this.userList=userList;
        this.context=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_item_row,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user=userList.get(position);
        holder.txtUsername.setText(user.name_surname);
        if(user.getImageURL().equals("default")){
            holder.user_profile_image.setImageResource(R.drawable.defult_user_image);
        }else{
//hazır kütüphane!!!
            Glide.with(context)
                    .load(user.getImageURL())
                    .override(64, 64)
                    .into(holder.user_profile_image);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,user.name_surname +" kullanıcısı tıklandı ",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return  userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        CircleImageView user_profile_image;
        TextView txtUsername;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    user_profile_image=itemView.findViewById((R.id.userImage));
    txtUsername=itemView.findViewById(R.id.txtUsername);

        }
    }
}
