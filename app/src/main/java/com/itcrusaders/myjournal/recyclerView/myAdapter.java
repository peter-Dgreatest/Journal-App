package com.itcrusaders.myjournal.recyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itcrusaders.myjournal.R;
import com.itcrusaders.myjournal.journal_edit;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.CustomViewHolder> {



    public myAdapter(Context context, ArrayList<Item> items) {
        this.items = items;
        this.mContext = context;

        database = FirebaseDatabase.getInstance();
        reference=database.getReference("journal");
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_layout, parent, false);
            return new CustomViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            Item item = items.get(position);

             journalText.setText(item.getmJournalText());
             journalTitle.setText(item.getmJournalTitle());

             layoutholder.setTag(item.getId());


    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    @Override
    public int getItemViewType(int position) {

        return position;
    }




    private Context mContext;
    private ArrayList<Item> items;


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void deleteItem(String tag)
    {
        reference.child(tag).removeValue();
    }
    FirebaseDatabase database;
    DatabaseReference reference;

    TextView journalText;
    TextView journalTitle;
    View layoutholder;

    class CustomViewHolder extends RecyclerView.ViewHolder{



        public CustomViewHolder(View itemView) {
            super(itemView);

            journalText = (TextView) itemView.findViewById(R.id.journalNote);
            journalTitle = (TextView) itemView.findViewById(R.id.journal_title);
            layoutholder = (View) itemView.findViewById(R.id.layoutholder);

            // Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    builder.setMessage(""+String.valueOf(((EditText)v.findViewById(R.id.journal_title)).getText()))
                            .setCancelable(true)
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteItem(v.getTag().toString());
                                }
                            })
                            .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(mContext,journal_edit.class);
                                    intent.putExtra("tag",v.getTag().toString());

                                    mContext.startActivity(intent);
                                }
                            });

                    AlertDialog dialog = builder.create();

                    dialog.show();

                }
            });


        }
    }
}
