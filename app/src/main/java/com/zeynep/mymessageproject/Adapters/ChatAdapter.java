package com.zeynep.mymessageproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zeynep.mymessageproject.Model.Chat;
import com.zeynep.mymessageproject.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public static final int Mesaj_Sag = 0;
    public static final int Mesaj_Sal = 1;

    private Context mcontext;
    private List<Chat> mMesaj;

    int mesajkonumu=-1;




    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



    public ChatAdapter(Context mcontext, List <Chat> mMesaj){
        this.mcontext = mcontext;
        this.mMesaj = mMesaj;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == Mesaj_Sag){
            View view = LayoutInflater.from(mcontext).inflate(R.layout.sag,parent,false);
            return new ChatAdapter.ViewHolder(view);
        }else{

            View view = LayoutInflater.from(mcontext).inflate(R.layout.sol,parent,false);
            return new ChatAdapter.ViewHolder(view);
        }






    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        final Chat chat = mMesaj.get(position);

      if (position == mMesaj.size()-1){
          if (chat.getGoruldu()){
              holder.goruldutik.setVisibility(View.VISIBLE);
              holder.iletilditik.setVisibility(View.GONE);

          }else
          {
              holder.iletilditik.setVisibility(View.VISIBLE);
              holder.goruldutik.setVisibility(View.GONE);


          }

      }// if end
      else{
          holder.goruldutik.setVisibility(View.GONE);

      }
        holder.mesaj.setText(chat.getMesaj());
        holder.saat.setText(chat.getSaat());
        holder.tarih.setText(chat.getTarih());
        Glide.with(mcontext).load(chat.getResim()).into(holder.mesajresim);

        if(chat.getResim().equals("")){
            holder.mesajresim.setVisibility(View.GONE);
        }
//bir süre üzerine basılı tutulduğu zaman işlem yapar
        holder.mesaj.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
            mesajkonumu=position;
            notifyDataSetChanged();

    return  false;
            }
        });
        if (mesajkonumu==position){
          holder.card.setBackgroundColor(Color.parseColor("#D89B9B9B"));
          holder.sil.setVisibility(View.VISIBLE);
            holder.copy.setVisibility(View.VISIBLE);

        }
        holder.sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesajSil(position);
            }
        });

    }

    private void mesajSil(int position) {
        String msg= mMesaj.get(position).getMesaj();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mesajlar").child(firebaseUser.getUid());
        Query sorgu =reference.orderByChild("mesaj").equalTo(msg);
        sorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    snapshot1.getRef().removeValue();
                    Toast.makeText(mcontext, "Mesaj Silindi", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(position); //mesaj sildikten sonra sayfayı günceller
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getItemCount() {

        return mMesaj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilresim,mesajgonder;
        public ImageView goruldutik,iletilditik,mesajresim,sil,copy;
        public TextView mesaj,tarih,saat;
        public CardView card;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mesaj = itemView .findViewById(R.id.mesaj);
            tarih = itemView .findViewById(R.id.tarih);
            saat = itemView.findViewById(R.id.saat);
            goruldutik = itemView .findViewById(R.id.goruldutik);
            iletilditik = itemView .findViewById(R.id.iletilditik);
            mesajresim = itemView .findViewById(R.id.mesajresim);
            card = itemView .findViewById(R.id.card);
            sil = itemView .findViewById(R.id.sil);
            copy = itemView .findViewById(R.id.copy);



        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mMesaj.get(position).getGonderen().equals(firebaseUser.getUid())){
            return Mesaj_Sag;
        }else {
            return Mesaj_Sal;
        }
    }
}
