package com.itcrusaders.myjournal.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JournalManipulation {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("journals");

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public int saveItem(Journal_items items)
    {
        String mail_id = user.getEmail().replace("gmail.com","");
        myRef.child(mail_id).child(items.journal_date).setValue(items);

        return 1;
    }


    public Journal_items getJournalItem()
    {

        return  new Journal_items(null,null,null);
    }

    public void updateJournalItem(Journal_items items)
    {

    }

    public int deleteJournal(int Id)
    {

        return 1;
    }

}
