package com.anonsgroup.anons;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.anonsgroup.anons.models.Anons;
import com.anonsgroup.anons.models.SenderUsers;

import org.w3c.dom.Text;

public class MesajlasmaAdapter extends RecyclerView.Adapter<MesajlasmaAdapter.ViewHolders> {
        Context context;
        List<SenderUsers> mdata;
public MesajlasmaAdapter(Context context, List<SenderUsers> mdata) {
        this.context = context;
        this.mdata = mdata;
        }


@NonNull
@Override
public MesajlasmaAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater=LayoutInflater.from(context);
    View v=inflater.inflate(R.layout.arkadas_listesi_li,viewGroup,false);
    return new MesajlasmaAdapter.ViewHolders(v);
        }

@Override
public void onBindViewHolder(@NonNull MesajlasmaAdapter.ViewHolders viewHolders, int i) {
final SenderUsers user=mdata.get(i);
        viewHolders.profilPhoto.setImageResource(mdata.get(i).getSenderPhoto());
        viewHolders.adSoyad.setText(mdata.get(i).getName()+" "+mdata.get(i).getSurname());
        viewHolders.kullaniciAdi.setText(mdata.get(i).getUserName());
        //TODO: burası silinip yerine sadece userİd gelecek
        viewHolders.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent =new Intent(context,MesajEkran.class);
        intent.putExtra("photo",user.getSenderPhoto());
        intent.putExtra("name",user.getName());
        intent.putExtra("surname",user.getSurname());
        context.startActivity(intent);

        }
        });

        }

@Override
public int getItemCount() {
        return mdata.size();
        }

public class ViewHolders extends RecyclerView.ViewHolder{

    ImageView profilPhoto;
    TextView adSoyad;
    TextView kullaniciAdi;

    public ViewHolders(@NonNull View itemView) {
        super(itemView);
        profilPhoto=itemView.findViewById(R.id.arkadasListProfilImage);
        adSoyad=itemView.findViewById(R.id.arkadasListKisiAdiTextView);
        kullaniciAdi=itemView.findViewById(R.id.arkadasListesiKullaniciAdiTextView);
    }
}
}

