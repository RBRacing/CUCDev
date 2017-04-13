package com.enav.cazaunchollo.cazaunchollo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class CommentViewHolder extends RecyclerView.ViewHolder{

    //public Toolbar toolbarCard2;
    public TextView person_name;


    public CommentViewHolder(View v){

        super(v);
        //toolbarCard2 = (Toolbar)itemView.findViewById(R.id.toolbarCard2);
        person_name = (TextView)itemView.findViewById(R.id.person_name);

        /*
        likeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.like(v, getPosition());
            }
        });*/
        /*
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("RecyclerView", "onClick：" + getPosition());
                // DatabaseReference ref =  FirebaseDatabase.getInstance().getReference().child("offers");
                //Log.d("KEY", String.valueOf(ref.getRef(String.valueOf(getPosition())).getKey()));
                MainActivity.recogerIDyLanzarActivity(v, getPosition());
            }
        });*/

    }

    public TextView getPerson_name() {
        return person_name;
    }

    public void setPerson_name(TextView person_name) {
        this.person_name = person_name;
    }
}
