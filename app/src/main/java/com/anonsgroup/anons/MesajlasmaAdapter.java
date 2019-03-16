package com.anonsgroup.anons;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import com.anonsgroup.anons.models.Messages;
import com.anonsgroup.anons.models.Rooms;
import com.anonsgroup.anons.models.SenderUsers;

public class MesajlasmaAdapter extends RecyclerView.Adapter<MesajlasmaAdapter.ViewHolders> {
        Context context;
        List<SenderUsers> mdata;
        private String imageUrl;
        final int MSG_TYPE_LEFT=0;
        final int MSG_TYPE_RIGHT=0;


@NonNull
@Override
public MesajlasmaAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater=LayoutInflater.from(context);
    View v=inflater.inflate(R.layout.activity_mesaj_ekran,viewGroup,false);
    return new MesajlasmaAdapter.ViewHolders(v);
        }

@Override
public void onBindViewHolder(@NonNull MesajlasmaAdapter.ViewHolders viewHolders, int i) {


        }

@Override
public int getItemCount() {
        return 0;
        }

public class ViewHolders extends RecyclerView.ViewHolder{



    public ViewHolders(@NonNull View itemView) {
        super(itemView);

    }
}
}

