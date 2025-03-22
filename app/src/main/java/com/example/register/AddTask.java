package com.example.register;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.register.models.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddTask extends AppCompatActivity {

    Button tambahTugas;

    EditText editTextDate, editTextTime, taskBaru;
    RadioButton red, yellow, green;
    String priority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        red = findViewById(R.id.red);
        yellow = findViewById(R.id.yellow);
        green = findViewById(R.id.green);
        tambahTugas = findViewById(R.id.buttonTambahtugas);
        editTextTime = findViewById(R.id.editTime);
        taskBaru = findViewById(R.id.taskBaru);
        editTextDate = findViewById(R.id.editTextDate);

        editTextDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year1, month1, dayOfMonth) -> {
                        String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        editTextDate.setText(selectedDate);
                    },
                    year, month, day);

            datePickerDialog.show();
        });
        editTextTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute1) -> {
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                        editTextTime.setText(selectedTime);
                    },
                    hour, minute, true); // 'true' untuk format 24 jam

            timePickerDialog.show();

        });
        tambahTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (red.isChecked()) {
                    priority = "High Priority";
                } else if (yellow.isChecked()) {
                    priority = "Medium Priority";
                } else {
                    priority = "Low priority";
                }
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String tugas = String.valueOf(taskBaru.getText());
                String tanggal = String.valueOf(editTextDate.getText());
                String waktu = String.valueOf(editTextTime.getText());
                String prioritas = priority;
                FirebaseUser fUser = auth.getCurrentUser();
                assert fUser != null;
                String uid = fUser.getUid();
                if (tugas.isEmpty()) {
                    Toast.makeText(AddTask.this, "Tugas tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (tanggal.isEmpty()) {
                    Toast.makeText(AddTask.this, "Tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (waktu.isEmpty()) {
                    Toast.makeText(AddTask.this, "Waktu tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (prioritas.isEmpty()) {
                    priority = "Low Priority";
                } else {


                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int nomorTugas = (int) snapshot.getChildrenCount() + 1; // Dapatkan jumlah task lalu tambah 1

                            Task task = new Task(nomorTugas, tugas, tanggal, waktu, prioritas);

                            databaseReference.push().setValue(task).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(AddTask.this, "Tugas ditambahkan", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(AddTask.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(AddTask.this, "Gagal menambah tugas", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddTask.this, "Gagal membaca database", Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });
    }
}