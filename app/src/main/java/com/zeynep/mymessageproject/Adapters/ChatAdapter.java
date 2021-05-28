package com.zeynep.mymessageproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zeynep.mymessageproject.Model.Chat;
import com.zeynep.mymessageproject.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public static final int Mesaj_Sag = 0;
    public static final int Mesaj_Sal = 1;

    private Context mcontext;
    private List<Chat> mMesaj;




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

    }


    @Override
    public int getItemCount() {
        return mMesaj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilresim,mesajgonder;
        public ImageView goruldutik,iletilditik;
        public TextView mesaj,saat,tarih;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mesaj = itemView .findViewById(R.id.mesaj);
            tarih = itemView .findViewById(R.id.tarih);
            saat = itemView .findViewById(R.id.saat);
            goruldutik = itemView .findViewById(R.id.goruldutik);
            iletilditik = itemView .findViewById(R.id.iletilditik);


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
