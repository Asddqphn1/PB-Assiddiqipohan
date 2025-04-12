package com.example.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.models.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {



    ImageView profile;
    LinearLayout addTask;
    FirebaseAuth auth;
    FirebaseUser fUser;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    List<Task> myTask;
    RecyclerView recyclerView;
    TextView pending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_home);


        recyclerView = findViewById(R.id.daftarTugas);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();



        myTask = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, myTask);
        recyclerView.setAdapter(myAdapter);
        String uid = fUser.getUid();
        databaseReference.child(uid).child("task").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myTask.clear();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                  Task task = dataSnapshot.getValue(Task.class);
                  myTask.add(task);
                }

                // 🔥 Wajib refresh RecyclerView biar data muncul
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Firebase error: " + error.getMessage());
            }
        });
        databaseReference.child(uid).child("task").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long jumlahData = snapshot.getChildrenCount();
                pending = findViewById(R.id.pending);
                pending.setText(String.valueOf(jumlahData));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        profile = findViewById(R.id.userIcon);
        addTask = findViewById(R.id.addTask);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AddTask.class);
                startActivity(intent);
            }
        });


    }
}