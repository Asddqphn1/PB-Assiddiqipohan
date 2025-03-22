package com.example.register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.register.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<DaftarTugasHolder> {
    Context context;
    ArrayList<Task> items;

    public MyAdapter(Context context, List<Task> items){
        this.context = context;
        this.items = (ArrayList<Task>) items;
    }

    @NonNull
    @Override
    public DaftarTugasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaftarTugasHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.isidaftar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarTugasHolder holder, int position) {
        holder.nomor.setText(String.valueOf(items.get(position).getNomor()));
        holder.daftarTugas.setText(items.get(position).getTask());
        holder.pritoritasTugas.setText(items.get(position).getPriority());
        holder.tenggatTugas.setText(items.get(position).getTanggal() + "📅");
        holder.waktuTugas.setText(items.get(position).getWaktu() + "🕛");
    }

    @Override
    public int getItemCount() {
        System.out.println("Total items in Adapter: " + items.size());
        return items.size(); // FIX: Mengembalikan jumlah data yang benar
    }
}
