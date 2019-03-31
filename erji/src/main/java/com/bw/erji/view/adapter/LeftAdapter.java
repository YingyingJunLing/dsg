package com.bw.erji.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.erji.MainActivity;
import com.bw.erji.R;
import com.bw.erji.model.bean.LfteBean;

import java.util.List;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {
    LfteBean lfteBean;
    Context context ;
    private List<LfteBean.DataBean> data;

    public LeftAdapter(Context context, LfteBean lfteBean) {
        this.context = context;
        this.lfteBean = lfteBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.left, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        data = lfteBean.getData();
        viewHolder.left_name.setText(lfteBean.getData().get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.CallBack(i,data);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lfteBean.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView left_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            left_name = itemView.findViewById(R.id.left_name);
        }
    }
    public interface ClickListener{
        void CallBack(int position,List<LfteBean.DataBean> data );
    }
    ClickListener listener;
    public void ClickListener(ClickListener listener)
    {
        this.listener =listener;
    }
}
