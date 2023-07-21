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

public class MallAdapter extends FirebaseRecyclerAdapter<MallModel,MallAdapter.mallviewholder> {

    public MallAdapter(@NonNull FirebaseRecyclerOptions<MallModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull mallviewholder holder, int position, @NonNull MallModel model) {
        holder.name.setText(model.getSname());
        holder.loc.setText(model.getLoc());
        //Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
        Glide.with(holder.img.getContext()).load("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Bashundhara_city.jpg/250px-Bashundhara_city.jpg").into(holder.img);
    }

    @NonNull
    @Override
    public mallviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mall,parent,false);
        return new mallviewholder(view);
    }

    class mallviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView name,loc;
        public mallviewholder(@NonNull View itemView) {
            super(itemView);

            img=(CircleImageView)itemView.findViewById(R.id.img4);
            name=(TextView)itemView.findViewById(R.id.sname);
            loc=(TextView)itemView.findViewById(R.id.sloc);

        }
    }

}
