package com.itcrusaders.myjournal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class journal_edit extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    private Context mContext;

    EditText mJournalTitle,mJournalText;
    String tag;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_edit);

        database = FirebaseDatabase.getInstance();
        reference=database.getReference("journal");
        mContext=getApplicationContext();

        mJournalText = (EditText) findViewById(R.id.journalEditNote);
        mJournalTitle = (EditText) findViewById(R.id.journal_edit_title);

        intent = getIntent();
        if(intent.hasExtra("tag"))
        {
            tag= intent.getStringExtra("tag");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    displayEvent(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else
        {
            finish();
        }


    }

    private void displayEvent(DataSnapshot dataSnapshot) {

        mJournalTitle.setText(String.valueOf(dataSnapshot.child(tag).child("mJournalTitle").getValue()));

        mJournalText.setText(String.valueOf(dataSnapshot.child(tag).child("mJournalText").getValue()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.btn_save)
        {
            reference.child(tag).child("mJournalTitle").setValue(String.valueOf(mJournalTitle.getText()));

            reference.child(tag).child("mJournalText").setValue(String.valueOf(mJournalText.getText()));

            Toast.makeText(mContext,"Updated Successfully",Toast.LENGTH_LONG).show();

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
