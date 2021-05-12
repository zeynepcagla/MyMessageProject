package com.zeynep.mymessageproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zeynep.mymessageproject.Model.User;
import com.zeynep.mymessageproject.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> mKullanicilar;
    private Context mcontext;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private Boolean isChat;

    public UserAdapter(Context mcontext, List<User> mKullanicilar, boolean isChat) {
        this.mKullanicilar=mKullanicilar;
        this.mcontext=mcontext;
        this.isChat=isChat;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.kullanici_ogesi,parent,false);
        return  new UserAdapter.ViewHolder(view);
    }

    @Override
    //okuma işlemi
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      final  User kullanici=mKullanicilar.get(position);
        if (isChat){
            if (kullanici.getDurum().equals("online")){
                holder.online.setVisibility(View.VISIBLE);
            }else{
                holder.online.setVisibility(View.GONE);
            }
        }
      holder.kullaniciAdi.setText(kullanici.getName_surname());
        if(kullanici.getId().equals(firebaseUser.getUid())){
            holder.profilresim.setVisibility(View.GONE);
            holder.kullaniciAdi.setVisibility(View.GONE);
            holder.online.setVisibility(View.GONE);
            holder.offline.setVisibility(View.GONE);
        }
        if(kullanici.getImageURL().equals("default")){
            holder.profilresim.setImageResource(R.drawable.defult_user_image);
        }else{
//hazır kütüphane!!!
            Glide.with(mcontext)
                    .load(kullanici.getImageURL())
                    .override(64, 64)
                    .into(holder.profilresim);
        }

    }

    @Override
    public int getItemCount() {
        return  mKullanicilar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public ImageView profilresim;
        public TextView kullaniciAdi;
        public CircleImageView online,offline;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    profilresim=itemView.findViewById((R.id.resimKullanici));
    kullaniciAdi=itemView.findViewById(R.id.kullaniciAdi);
    online=itemView.findViewById(R.id.online);
    offline=itemView.findViewById(R.id.offline);

        }
    }
}
