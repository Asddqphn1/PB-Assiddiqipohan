package com.example.register;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    TextView username, email, password, nim;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser fUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();

        username = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        password = findViewById(R.id.profilePassword);
        nim = findViewById(R.id.profileNIM);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        if (fUser != null){
            String userId = fUser.getUid();

            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String nama = snapshot.child("username").getValue(String.class);
                        String emailuser = snapshot.child("userEmail").getValue(String.class);
                        String nimuser = snapshot.child("userNIM").getValue(String.class);
                        String passworduser = snapshot.child("userPassword").getValue(String.class);


                        username.setText(nama.toUpperCase());
                        email.setText(emailuser);
                        password.setText(passworduser);
                        nim.setText(nimuser);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




    }
}