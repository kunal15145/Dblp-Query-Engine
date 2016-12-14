package com.company;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by yagami on 12-11-2016.
 */

public abstract class Query1dblp {

    ArrayList<RecordClass> authorsrecord = new ArrayList<>();
    ArrayList<RecordClass> between2yearforauthor = new ArrayList<>();
    ArrayList<RecordClass> sincesomegivenyear;
    ArrayList<ArrayList<String>> wwwrecords = new ArrayList<>();
    ArrayList<RecordClass> temp = new ArrayList<>();
    ArrayList<RecordClass> temp1 = new ArrayList<>();


    public abstract ArrayList<RecordClass> sortbydatereverse(String author) throws FileNotFoundException, XMLStreamException;
    public abstract ArrayList<RecordClass> sortbyrelavence(String matchedword,String author) throws FileNotFoundException, XMLStreamException;
    public abstract ArrayList<RecordClass> between2year(Integer date1,Integer date2,String author) throws FileNotFoundException, XMLStreamException;
    public abstract ArrayList<RecordClass> sincesomegivernyear(Integer date1,String author) throws FileNotFoundException, XMLStreamException;

}