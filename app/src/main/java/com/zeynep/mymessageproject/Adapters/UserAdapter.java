package com.zeynep.mymessageproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeynep.mymessageproject.MesajActivity;
import com.zeynep.mymessageproject.Model.Chat;
import com.zeynep.mymessageproject.Model.User;
import com.zeynep.mymessageproject.ProfilimActivity;
import com.zeynep.mymessageproject.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> mKullanicilar;
    private Context mcontext;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private Boolean isChat;
    private String  sonMesajim;



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
          sonGonderilenMesaj(kullanici.getId(),holder.sonmesaj);
      }else {
          holder.sonmesaj.setVisibility(View.GONE);
      }


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
            holder.mesajGonder.setVisibility(View.GONE);
            holder.sonmesaj.setVisibility(View.GONE);


        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"Shared Preferences" Adapter ve Fragmentler  arası bilgi akışını sağlayan komut
                SharedPreferences.Editor editor = mcontext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profilId",kullanici.getId()); // hangi profil ise onun Id si ile işlem yapacak
                editor.apply();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, MesajActivity.class);
                intent.putExtra("userId",kullanici.getId());
                mcontext.startActivity(intent);


            }
        });
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



private void sonGonderilenMesaj(final String id, TextView sonmesaj){
        sonMesajim = "default";


    FirebaseDatabase.getInstance().getReference("Mesajlar").child(firebaseUser.getUid())
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    for (DataSnapshot snapshot1 : datasnapshot.getChildren()){
                        Chat chat = snapshot1.getValue(Chat.class);

                        if (chat.getAlici().equals(firebaseUser.getUid()) && chat.getGonderen().equals(id) ||
                            chat.getAlici().equals(id) && chat.getGonderen().equals(firebaseUser.getUid())){
                            sonMesajim = chat.getMesaj();
                        }
                    }
                    switch (sonMesajim){
                        case "default":
                            sonmesaj.setText("Bir Merhaba Söyle");
                            break;
                        default:
                            sonmesaj.setText(sonMesajim);
                            break;
                    }
                    sonMesajim = "default";

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

}
    public class ViewHolder extends RecyclerView.ViewHolder  {

        public ImageView profilresim,mesajGonder;
        public TextView kullaniciAdi,sonmesaj;
        public CircleImageView online,offline;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    profilresim=itemView.findViewById((R.id.resimKullanici));
    kullaniciAdi=itemView.findViewById(R.id.kullaniciAdi);
    online=itemView.findViewById(R.id.online);
    offline=itemView.findViewById(R.id.offline);
    mesajGonder=itemView.findViewById(R.id.mesajGonder);
    sonmesaj=itemView.findViewById(R.id.sonmesaj);
        }
    }
}
