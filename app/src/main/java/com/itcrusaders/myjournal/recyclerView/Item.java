package com.itcrusaders.myjournal.recyclerView;

public class Item {


    private String mJournalText;
    private String mJournalTitle;
    private String id;


    public Item(String id,String title,String text)
    {
        this.id=id;
        this.mJournalText=text;
        this.mJournalTitle=title;
    }

    public Item(String title,String text)
    {
        this.mJournalText=text;
        this.mJournalTitle=title;
    }

    public Item()
    {

    }

    public void setmJournalText(String journalText)
    {
        this.mJournalText=journalText;

    }

    public void setmJournalTitle(String journalTitle)
    {
        this.mJournalText=journalTitle;

    }

    public String getmJournalTitle()
    {
       return mJournalTitle;
    }
    public String getmJournalText()
    {
        return mJournalText;
    }

    public String getId() {
        return id;
    }
}
