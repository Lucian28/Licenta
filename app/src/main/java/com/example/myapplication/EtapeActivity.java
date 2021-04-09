package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Adapter.EtapeAdapter;
import com.example.myapplication.Adapter.TaskAdapter;
import com.example.myapplication.Model.Etapa;
import com.example.myapplication.Model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EtapeActivity extends AppCompatActivity {

    TextView titlu;
    TextView descriere;

    RecyclerView recyclerView;


    private TaskAdapter taskAdapter;
    private List<Task> mTasks;

    FirebaseDatabase db;

    private String etapaid;
    private String etapaDescriere;
    private String etapaName;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etape);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EtapeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        db = FirebaseDatabase.getInstance();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        titlu = findViewById(R.id.titlu);
        descriere = findViewById(R.id.descriere);




        intent = getIntent();
        etapaid = intent.getStringExtra("etapaid");
        etapaName = intent.getStringExtra("etapaTitlu");
        etapaDescriere = intent.getStringExtra("etapaDescriere");


        titlu.setText(etapaName);
        descriere.setText(etapaDescriere);


        mTasks = new ArrayList<Task>();

        readTask(etapaid);




    }

    private void readTask(String etapaid) {

        DatabaseReference reference = db.getReference("Tasks").child(etapaid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mTasks.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Task task = dataSnapshot.getValue(Task.class);

                    Log.d("TAG", "Value is: " + dataSnapshot.getValue());

                    mTasks.add(task);

                }
                taskAdapter = new TaskAdapter(EtapeActivity.this, mTasks);
                recyclerView.setAdapter(taskAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}