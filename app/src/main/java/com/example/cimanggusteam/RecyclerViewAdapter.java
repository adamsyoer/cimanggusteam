package com.example.cimanggusteam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cimanggusteam.model.Antrian;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Antrian> listAntrian;
    private Context context;

    public RecyclerViewAdapter(ArrayList<Antrian> listAntrian, Context context) {
        this.listAntrian = listAntrian;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView NoAntrian, JenisKendaraan, PlatKendaraan , WaktuSteam;
        private LinearLayout ListItem;
        private ImageView motor,moge,mobil;

        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita
            NoAntrian = itemView.findViewById(R.id.no_antrian);
            JenisKendaraan = itemView.findViewById(R.id.jenis_kendaraan);
            PlatKendaraan = itemView.findViewById(R.id.plat_kendaraan);
            WaktuSteam = itemView.findViewById(R.id.waktu_steam);
            ListItem = itemView.findViewById(R.id.list_item);
            motor = itemView.findViewById(R.id.gambar_motor);
            moge = itemView.findViewById(R.id.gambar_moge);
            mobil = itemView.findViewById(R.id.gambar_mobil);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String NoAntrian = listAntrian.get(position).getNourut();
        final String JenisKendaraan = listAntrian.get(position).getJeniskendaraan();
        final String PlatKendaraan = listAntrian.get(position).getPlatkendaraan();
        final String WaktuSteam = listAntrian.get(position).getWaktusteam();

        //Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)
        holder.NoAntrian.setText("No. Antrian : "+NoAntrian);
        holder.JenisKendaraan.setText("Jenis Kendaraan : "+JenisKendaraan);
        holder.PlatKendaraan.setText("Plat Kendaraan : "+PlatKendaraan);
        holder.WaktuSteam.setText("Waktu Steam :"+WaktuSteam);

        if (JenisKendaraan.equals("motor")){
           holder.motor.setVisibility(View.VISIBLE);
        }else if(JenisKendaraan.equals("moge")){
            holder.moge.setVisibility(View.VISIBLE);
        }else if(JenisKendaraan.equals("mobil")){
            holder.mobil.setVisibility(View.VISIBLE);
        }

        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /*
                  Kodingan untuk membuat fungsi Edit dan Delete,
                  yang akan dibahas pada Tutorial Berikutnya.
                 */
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listAntrian.size();
    }

}
