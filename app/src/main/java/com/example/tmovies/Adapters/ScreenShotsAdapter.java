package com.example.tmovies.Adapters;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScreenShotsAdapter extends RecyclerView.Adapter<ScreenShotsAdapter.item> {
    Context context;
    List<String> sslist;

    public ScreenShotsAdapter(Context context, List<String> sslist) {
        this.context = context;
        this.sslist = sslist;
    }

    @NonNull
    @Override
    public item onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new item(LayoutInflater.from(context).inflate(R.layout.item_screenshots , viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull item item, int positon) {
        Picasso.get().load(sslist.get(positon)).into(item.img_ss_Movie);

        item.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_image_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageView movieImg = dialog.findViewById(R.id.movieImg);
                Picasso.get().load(sslist.get(positon)).into(movieImg);

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sslist.size();
    }

    public class item extends RecyclerView.ViewHolder{
        ImageView img_ss_Movie;
        public item(@NonNull View itemView) {
            super(itemView);
            img_ss_Movie = itemView.findViewById(R.id.img_ss_Movie);
        }
    }
}
