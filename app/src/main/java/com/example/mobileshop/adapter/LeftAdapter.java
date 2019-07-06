package com.example.mobileshop.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Integer> postions = new ArrayList<>();
    private List<String> data = new ArrayList<>();
    //构造方法
    public LeftAdapter(Context context,List<String> data1) {
        data.clear();
        this.context = context;
        this.data = data1;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public LeftAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = null;
        View view = inflater.inflate(R.layout.left_menu_layout,viewGroup,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(LeftAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(data.get(i));
        //viewHolder.imageView.setBackground(ContextCompat.getDrawable(context,R.drawable.image));
        if (i%2 == 0) {
            postions.add(i);
        }
        System.out.println(postions);
        if (postions.contains(i)) {
            viewHolder.imageView.setBackground(ContextCompat.getDrawable(context,R.drawable.image));
        }else {
            viewHolder.imageView.setBackground(ContextCompat.getDrawable(context,R.drawable.hh));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //内部类
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_left)
        TextView textView;
        @BindView(R.id.left_image)
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            //使ButterKnife生效
            ButterKnife.bind(this,itemView);
        }
    }
}
