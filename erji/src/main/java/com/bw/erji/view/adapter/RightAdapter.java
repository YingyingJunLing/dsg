package com.bw.erji.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.erji.MainActivity;
import com.bw.erji.R;
import com.bw.erji.model.bean.RightBean;

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.ViewHolder> {
    Context context;
    RightBean rightBean;
    public RightAdapter(Context context, RightBean rightBean)
    {
        this.context = context ;
        this.rightBean =rightBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.right, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.name.setText(rightBean.getData().get(position).getName());
        Glide.with(context)
                .load(rightBean.getData().get(position).getList().get(position).getIcon())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return rightBean.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img ;
        public ViewHolder(View itemView) {
            super(itemView);
            img =   itemView.findViewById(R.id.right_img);
            name =  itemView.findViewById(R.id.right_name);
        }
    }
}
