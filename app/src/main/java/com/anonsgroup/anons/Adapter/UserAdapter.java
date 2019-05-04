package com.anonsgroup.anons.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.anonsgroup.anons.MesajEkran;
import com.anonsgroup.anons.R;
import com.anonsgroup.anons.models.ArkadaslarimModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<ArkadaslarimModel> mList;
    private List<ArkadaslarimModel> mList2;


    public UserAdapter(Context context, List<ArkadaslarimModel> mList,List<ArkadaslarimModel> mList2) {
        this.context = context;
        this.mList = mList;
        this.mList2=mList2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view = LayoutInflater.from(context).inflate(R.layout.arkadas_listesi_li,viewGroup,false);

        return new UserAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ArkadaslarimModel user =mList.get(i);
        viewHolder.username.setText(user.getUsername());
        viewHolder.durum.setText(user.getDurum());
      //TODO:firebase foto çekilcek yer
        if(user.getProfilURL().equals("default")){
            viewHolder.image.setImageResource(R.drawable.kullaniciprofildefault);
        }else{
            Glide.with(context).load(user.getProfilURL()).into(viewHolder.image);
        }


        //mesajlasma
        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context, MesajEkran.class);
            intent.putExtra("userid",user.getUsername());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView image;
        public TextView durum;

        public ViewHolder (View itemView){
            super(itemView);

            username=itemView.findViewById(R.id.arkadasListKisiAdiTextView);
            image=itemView.findViewById(R.id.arkadasListProfilImage);
            durum=itemView.findViewById(R.id.arkadasListesiKullaniciAdiTextView);

        }

    }

    @Override
    public Filter getFilter() {

        return mlistFilter;
    }

    private  Filter mlistFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ArkadaslarimModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mList2);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ArkadaslarimModel item : mList2) {
                    if (item.getUsername().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }


            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
             mList.clear();
             mList.addAll((List) results.values);
             notifyDataSetChanged();
        }

    };
}
