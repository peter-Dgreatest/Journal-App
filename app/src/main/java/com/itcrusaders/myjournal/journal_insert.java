package com.itcrusaders.myjournal;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itcrusaders.myjournal.recyclerView.Item;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class journal_insert extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_insert);

        databaseReference = FirebaseDatabase.getInstance().getReference();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.save_menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() ==  R.id.btn_save){

            EditText mJournal_text = findViewById(R.id.journal_note);
            EditText mJournalTitle = findViewById(R.id.journal_title);



            String enteredTask = mJournalTitle.getText().toString();
            if(TextUtils.isEmpty(enteredTask)){
                Toast.makeText(this, "You must enter a task first", Toast.LENGTH_LONG).show();
                return false;
            }
            if(enteredTask.length() < 6){
                Toast.makeText(this, "Task count must be more than 6", Toast.LENGTH_LONG).show();
                return false;
            }

            database = FirebaseDatabase.getInstance();

            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy-H-M");
            String formattedDate = df.format(c);

            reference=database.getReference("journal/"+formattedDate);


            Item item1 = new Item(mJournalTitle.getText().toString(),mJournal_text.getText().toString());
            reference.setValue(item1);

            mJournalTitle.setText("");
            mJournal_text.setText("");

            Toast.makeText(this,"Saved Successfully",Toast.LENGTH_SHORT).show();
            finish();





            //x Toast.makeText(this,myRef.child("id").toString(),Toast.LENGTH_LONG);
        }
        return super.onOptionsItemSelected(item);

    }


}
