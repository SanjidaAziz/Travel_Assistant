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

public class SpotAdapter extends FirebaseRecyclerAdapter<SpotModel,SpotAdapter.tviewholder> {

    public SpotAdapter(@NonNull FirebaseRecyclerOptions<SpotModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull tviewholder holder, int position, @NonNull SpotModel model) {
        holder.name.setText(model.getTname());
        holder.loc.setText(model.getLoc());
        //Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
        Glide.with(holder.img.getContext()).load("https://assetsds.cdnedge.bluemix.net/sites/default/files/feature/images/coxs-bazar-web_0.gif").into(holder.img);
    }

    @NonNull
    @Override
    public tviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spot,parent,false);
        return new tviewholder(view);
    }

    class tviewholder extends RecyclerView.ViewHolder
    {   CircleImageView img;
        TextView name,loc;
        public tviewholder(@NonNull View itemView) {
            super(itemView);

            img=(CircleImageView)itemView.findViewById(R.id.img3);
            name=(TextView)itemView.findViewById(R.id.tname);

            loc=(TextView)itemView.findViewById(R.id.tloc);

        }
    }

}
