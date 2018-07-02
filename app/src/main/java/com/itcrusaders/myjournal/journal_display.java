package com.itcrusaders.myjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itcrusaders.myjournal.recyclerView.Item;
import com.itcrusaders.myjournal.recyclerView.myAdapter;

import java.util.ArrayList;

public class journal_display extends AppCompatActivity {

    private static final String TAG = journal_display.class.getSimpleName();



    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;


    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_display);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        reference=database.getReference("journal");



        Intent thisIntent = getIntent();
        if(thisIntent!=null) {
            FloatingActionButton mBtn_add = (FloatingActionButton) findViewById(R.id.btn_add_journal_item);

            mBtn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addIntent();
                }
            });
        }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private ArrayList<Item> items;

    private void showData(DataSnapshot dataSnapshot) {
        items = new ArrayList<>();
        int cnt =0;
        for(DataSnapshot ds :dataSnapshot.getChildren())
        {

            String journalText = ds.child("mJournalText").getValue().toString();
            String journalTitle = ds.child("mJournalTitle").getValue().toString();
            String tag = ds.getKey();


           Item item = new Item(tag,journalTitle,journalText);

           items.add(item);

        }

        myAdapter adapter = new myAdapter(this,items);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }


    private void addIntent() {

        Intent addJournal = new Intent(this, journal_insert.class);

        startActivity(addJournal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.btn_logout)
        {
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut()
    {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                      public void onComplete(@NonNull Task task) {

                        finish();

                    }
                });

    }


}
