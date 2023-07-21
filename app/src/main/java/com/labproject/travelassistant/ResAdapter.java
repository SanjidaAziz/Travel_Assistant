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

public class ResAdapter extends FirebaseRecyclerAdapter<ResModel,ResAdapter.resviewholder> {

    public ResAdapter(@NonNull FirebaseRecyclerOptions<ResModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull resviewholder holder, int position, @NonNull ResModel model) {
        holder.name.setText(model.getRname());
        //holder.type.setText(model.getRtype());
        holder.loc.setText(model.getLocation());
        holder.rating.setText(model.getRating());
        //Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
        Glide.with(holder.img.getContext()).load("https://media-cdn.tripadvisor.com/media/photo-s/13/d3/55/04/the-white-canary-cafe.jpg").into(holder.img);
    }

    @NonNull
    @Override
    public resviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_res,parent,false);
       return new resviewholder(view);
    }

    class resviewholder extends RecyclerView.ViewHolder
    {   CircleImageView img;
        TextView name,loc,rating;
        public resviewholder(@NonNull View itemView) {
            super(itemView);

            img=(CircleImageView)itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.rname);
            //type=(TextView)itemView.findViewById(R.id.rtype);
            loc=(TextView)itemView.findViewById(R.id.rloc);
            rating=(TextView)itemView.findViewById(R.id.rat);
        }
    }

}
