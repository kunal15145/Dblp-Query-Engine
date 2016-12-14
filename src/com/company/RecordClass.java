package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordClass {
    ArrayList<String> authors = new ArrayList<>();
    String publications;
    HashMap<Integer,String> publicationtodates = new HashMap<>();
    String pages;
    int year;
    String volume;
    String journal;
    String url;

    public RecordClass(ArrayList<String> author,String publication, HashMap<Integer, String> publicationtodate, String page, int years, String volumes, String journals, String urls) {
        authors=author;
        publications=publication;
        publicationtodates=publicationtodate;
        pages=page;
        year=years;
        volume=volumes;
        journal=journals;
        url=urls;
    }
}
