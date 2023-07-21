package com.labproject.travelassistant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class HtlAdapter extends FirebaseRecyclerAdapter<HtlModel,HtlAdapter.htlviewholder> {

    public HtlAdapter(@NonNull FirebaseRecyclerOptions<HtlModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull htlviewholder holder, int position, @NonNull HtlModel model)
    {
        holder.name.setText(model.getHname());
        holder.loc.setText(model.getLoc());
        holder.rating.setText(model.getRat());
        //Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
        Glide.with(holder.img.getContext()).load("https://pix10.agoda.net/hotelImages/124/1246280/1246280_16061017110043391702.jpg?s=1024x768").into(holder.img);
    }

    @NonNull
    @Override
    public htlviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_htl,parent,false);
        return new htlviewholder(view);
    }

    class htlviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView name,loc,rating;
        public htlviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.img2);
            name=(TextView)itemView.findViewById(R.id.hname);
            loc=(TextView)itemView.findViewById(R.id.hloc);
            rating=(TextView)itemView.findViewById(R.id.hrat);
        }
    }
}
