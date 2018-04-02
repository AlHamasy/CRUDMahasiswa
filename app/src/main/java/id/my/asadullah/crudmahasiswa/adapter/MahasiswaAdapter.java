package id.my.asadullah.crudmahasiswa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.my.asadullah.crudmahasiswa.DataMahasiswa;
import id.my.asadullah.crudmahasiswa.MainActivity;
import id.my.asadullah.crudmahasiswa.R;
import id.my.asadullah.crudmahasiswa.UpdateHapusActivity;

/**
 * Created by asadullah on 1/11/18.
 */

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MyViewHolder> {

    Context con;
    List<DataMahasiswa> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtnim, txtnama, txtjurusan;

        public MyViewHolder(View itemView) {
            super(itemView);

            //todo -- inisialisasi variable dngn id
            txtnama = itemView.findViewById(R.id.txtNama);
            txtnim = itemView.findViewById(R.id.txtNim);
            txtjurusan = itemView.findViewById(R.id.txtJurusan);
        }
    }

    public MahasiswaAdapter(List <DataMahasiswa> dataMhs) {
        data = dataMhs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //TODO 1 -- menghubungkan dengan layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mahasiswa,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MahasiswaAdapter.MyViewHolder holder, final int position) {

        //todo -- yang akan ditampilkan di MainActivity
        Log.d("nama", data.get(position).getMahasiswa_nama());
        holder.txtnim.setText(data.get(position).getMahasiswa_nim());
        holder.txtnama.setText(data.get(position).getMahasiswa_nama());
        holder.txtjurusan.setText(data.get(position).getMahasiswa_jurusan());

        //todo -- kirim data dengan intent
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent kirimData = new Intent (holder.itemView.getContext(), UpdateHapusActivity.class);
                kirimData.putExtra("id",data.get(position).getMahasiswa_id());
                kirimData.putExtra("nim",data.get(position).getMahasiswa_nim());
                kirimData.putExtra("nama",data.get(position).getMahasiswa_nama());
                kirimData.putExtra("jurusan",data.get(position).getMahasiswa_jurusan());
                holder.itemView.getContext().startActivity(kirimData);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
