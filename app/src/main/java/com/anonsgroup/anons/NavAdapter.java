package com.anonsgroup.anons;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.MyViewHolder>{
    Context context;
    LayoutInflater inflater;
    ArrayList<NavigationDrawerItem> mDataList;
    public  NavigationDrawerItem tiklanilan;
    LinearLayout tiklanan;
    private static final int engellenenler = 0,begendigiAonslar=1,uygulamayiPaylas=2,lisanslar=3,gizlilikKosullari=4,gorusveOneriler=5,yardim=6,ayarlar=7;
    NavAdapter(Context c,ArrayList<NavigationDrawerItem>data){
        this.context=c;
        this.inflater=LayoutInflater.from(context);
        mDataList=data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.navigation_tek_satir,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        NavigationDrawerItem tiklanilan = mDataList.get(position);
        myViewHolder.setData(tiklanilan,position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView baslik;
        ImageView resim;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tiklanan=itemView.findViewById(R.id.lineerItem);
            baslik=itemView.findViewById(R.id.title);
            resim=itemView.findViewById(R.id.imgIcon);
            tiklanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (baslik.getText().equals("yardım")) {

                    }
                    Toast.makeText(itemView.getContext(), "sdfghjkl", Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void setData(NavigationDrawerItem tiklanilan, final int position) {
            this.baslik.setText(tiklanilan.getBaslik());
            this.resim.setImageResource(tiklanilan.getResimID());

            tiklanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position== engellenenler)
                        Toast.makeText(itemView.getContext(), "1", Toast.LENGTH_SHORT).show();

                    else if (position==begendigiAonslar)
                        Toast.makeText(itemView.getContext(), "2", Toast.LENGTH_SHORT).show();

                    else if (position==uygulamayiPaylas){
                        Intent shareIntent =new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String sharedBody="body";
                        String shareSub="sub";
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT,sharedBody);
                        context.startActivity(Intent.createChooser(shareIntent,"Paylaş"));
                        Toast.makeText(itemView.getContext(), "3", Toast.LENGTH_SHORT).show();}
                    else if (position==lisanslar)
                        Toast.makeText(itemView.getContext(), "4", Toast.LENGTH_SHORT).show();
                    else if (position==gizlilikKosullari)
                        Toast.makeText(itemView.getContext(), "5", Toast.LENGTH_SHORT).show();
                    else if (position==gorusveOneriler)
                        Toast.makeText(itemView.getContext(), "6", Toast.LENGTH_SHORT).show();
                    else if (position==yardim)
                        Toast.makeText(itemView.getContext(), "7", Toast.LENGTH_SHORT).show();

                }

            });

        }
    }

}
