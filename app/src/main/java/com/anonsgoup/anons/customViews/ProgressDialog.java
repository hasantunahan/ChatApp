package com.anonsgoup.anons.customViews;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonsgoup.anons.R;

public class ProgressDialog extends DialogFragment {
    private ImageView imageView_1,imageView_2,imageView_3;
    int position=0;
    private TextView message;
    private String mesaj;
    final private Handler handler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
        changeImageSlider();
        }
    };

    public ProgressDialog() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_progress_dialog, container);
        message = view.findViewById(R.id.DialogTest);
        imageView_1 =  view.findViewById(R.id.bir_cizgili);
        imageView_2 =  view.findViewById(R.id.iki_cizgili);
        imageView_3 =  view.findViewById(R.id.uc_cizgili);


        message.setText(mesaj);
        getDialog().setCancelable(false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeImageSlider();
    }

    public boolean isShowing(){
        return getShowsDialog();
    }

    public void hide(){
        this.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        handler.removeCallbacks(runnable);
    }

    private void changeImageSlider() {
        position++;
        if (position>3){
            position=1;
        }
        switch (position)
        {
            case 1:{imageView_2.setVisibility(View.INVISIBLE);
                imageView_3.setVisibility(View.INVISIBLE);
                imageView_1.setVisibility(View.VISIBLE);  }break;
            case 2:imageView_2.setVisibility(View.VISIBLE);break;
            case 3:{  imageView_3.setVisibility(View.VISIBLE);}break;
            default:{position=1;}
        }

        handler.postDelayed(runnable, 700);
    }

    public String getMessage() {
        return message.getText().toString();
    }

    public void setMessage(String message) {
        this.mesaj = message;
    }
}
