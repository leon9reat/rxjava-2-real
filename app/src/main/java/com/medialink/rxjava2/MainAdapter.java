package com.medialink.rxjava2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medialink.rxjava2.model.Pimpinan;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Pimpinan> mListPimpinan = new ArrayList<>();

    public MainAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Pimpinan> list) {
        mListPimpinan = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.rv_main_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pimpinan item = mListPimpinan.get(position);
        holder.tvNmPimpinan.setText(item.getNmPimpinan());
        holder.tvJabatan.setText(item.getJabatan());
    }

    @Override
    public int getItemCount() {
        return mListPimpinan == null ? 0 : mListPimpinan.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nm_pimpinan)
        TextView tvNmPimpinan;
        @BindView(R.id.tv_jabatan)
        TextView tvJabatan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
