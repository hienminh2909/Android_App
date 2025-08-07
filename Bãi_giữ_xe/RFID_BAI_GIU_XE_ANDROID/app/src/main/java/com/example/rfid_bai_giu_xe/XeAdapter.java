package com.example.rfid_bai_giu_xe;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import com.google.firebase.database.*;
import java.util.List;
public class XeAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<XeDangGui> danhSachXe;
    private DatabaseReference dbRef;
    public XeAdapter(Context context, int layout, ArrayList<XeDangGui> danhSachXe, DatabaseReference dbRef){
        this.context= context;
        this.layout=layout;
        this.danhSachXe = danhSachXe;
        this.dbRef = dbRef;
    }

    @Override
    public int getCount(){
        return danhSachXe.size();
    }
    @Override
    public Object getItem(int position){
        return danhSachXe.get(position);
    }
    @Override public long getItemId(int position){
        return position;
    }

    private class ViewHolder {
        TextView tvBienSo;
        TextView tvID;
        TextView tvThoiGian;

        ImageButton btn_delete;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Tối ưu bằng ViewHolder pattern
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);

            holder.tvBienSo = convertView.findViewById(R.id.tvBienSo);
            holder.tvID = convertView.findViewById(R.id.tvID);
            holder.tvThoiGian = convertView.findViewById(R.id.tvThoiGian);
            holder.btn_delete = convertView.findViewById(R.id.btn_delete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        XeDangGui xedanggui = danhSachXe.get(position);

        holder.btn_delete.setVisibility(View.GONE);

        convertView.setOnLongClickListener(v ->{
            holder.btn_delete.setVisibility(View.VISIBLE);
            return true;
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btn_delete.getVisibility() == View.VISIBLE) {
                    holder.btn_delete.setVisibility(View.GONE);
                }
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = xedanggui.getId();
                danhSachXe.remove(position);
                notifyDataSetChanged();

                dbRef.child("vehicles").child(Id).removeValue((error, ref) -> {
                       if(error == null){
                           Toast.makeText(context,"Xe đã được xóa",Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(context,"Loi",Toast.LENGTH_SHORT).show();
                       }
                       holder.btn_delete.setVisibility(View.GONE);
                });
            }
        });
        final XeDangGui xe = danhSachXe.get(position);


            holder.tvBienSo.setText("Biển số: " + xe.getBienSo());
            holder.tvID.setText("ID: " + xe.getId());
            holder.tvThoiGian.setText("Vào lúc: " + xe.getVaoLuc());


        return convertView;
    }


}
