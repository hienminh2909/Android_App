package com.example.rfid_bai_giu_xe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class lichsuAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Lichsuxe> Lichsuxe;
    public lichsuAdapter(Context context, int layout, ArrayList<Lichsuxe> Lichsuxe){
        this.context= context;
        this.layout=layout;
        this.Lichsuxe = Lichsuxe;
    }

    @Override
    public int getCount(){
        return Lichsuxe.size();
    }
    @Override
    public Object getItem(int position){
        return Lichsuxe.get(position);
    }
    @Override public long getItemId(int position){
        return position;
    }

    private class ViewHolder {
        TextView tvBienSo;
        TextView tvID;
        TextView tv_tgXevao;
        TextView tv_tgXera;
        TextView tv_action;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Tối ưu bằng ViewHolder pattern
        final lichsuAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new lichsuAdapter.ViewHolder();
            LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);

            holder.tvBienSo = convertView.findViewById(R.id.tvBienSo);
            holder.tvID = convertView.findViewById(R.id.tvID);
            holder.tv_tgXera = convertView.findViewById(R.id.tv_tgXera);
            holder.tv_tgXevao = convertView.findViewById(R.id.tv_tgXevao);
            holder.tv_action = convertView.findViewById(R.id.tv_action);
            convertView.setTag(holder);
        }
        else {
            holder = (lichsuAdapter.ViewHolder) convertView.getTag();
        }

        final Lichsuxe xe = Lichsuxe.get(position);


        holder.tvBienSo.setText("Biển số: " + xe.getBienSo());
        holder.tvID.setText("ID: " + xe.getId());
        holder.tv_tgXevao.setText("Vào lúc: " + xe.getVaoLuc());
        holder.tv_tgXera.setText("Ra lúc: " + xe.getVaoLuc());
        holder.tv_action.setText("Action:" + xe.getAction());


        return convertView;
    }
}
