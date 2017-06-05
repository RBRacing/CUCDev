package com.enav.cazaunchollo.cazaunchollo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class CommentViewHolder extends RecyclerView.ViewHolder{

    public TextView person_name;
    public TextView textView_comment;
    public TextView comment_date;

    public CommentViewHolder(View v){

        super(v);
        person_name = (TextView)itemView.findViewById(R.id.person_name);
        textView_comment = (TextView) itemView.findViewById(R.id.textView_comment);
        comment_date = (TextView) itemView.findViewById(R.id.comment_date);

    }

}
