package com.enav.cazaunchollo.cazaunchollo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class CommentViewHolder extends RecyclerView.ViewHolder{

    //public Toolbar toolbarCard2;
    public TextView person_name;
    public TextView textView_comment;
    public TextView comment_date;

    public CommentViewHolder(View v){

        super(v);
        //toolbarCard2 = (Toolbar)itemView.findViewById(R.id.toolbarCard2);
        person_name = (TextView)itemView.findViewById(R.id.person_name);
        textView_comment = (TextView) itemView.findViewById(R.id.textView_comment);
        comment_date = (TextView) itemView.findViewById(R.id.comment_date);

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

    public TextView getTextView_comment() {
        return textView_comment;
    }

    public void setTextView_comment(TextView textView_comment) {
        this.textView_comment = textView_comment;
    }

    public TextView getComment_date() {
        return comment_date;
    }

    public void setComment_date(TextView comment_date) {
        this.comment_date = comment_date;
    }
}
