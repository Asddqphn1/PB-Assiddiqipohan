package com.example.register;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DaftarTugasHolder extends RecyclerView.ViewHolder {

    TextView nomor, daftarTugas, pritoritasTugas, tenggatTugas, waktuTugas;

    public DaftarTugasHolder(@NonNull View itemView) {
        super(itemView);
        nomor = itemView.findViewById(R.id.nomorTugas);
        daftarTugas = itemView.findViewById(R.id.daftarTugas);
        pritoritasTugas = itemView.findViewById(R.id.priority);
        tenggatTugas = itemView.findViewById(R.id.tanggal);
        waktuTugas = itemView.findViewById(R.id.waktu);
    }
}
