package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;


import javax.xml.stream.XMLStreamException;

public interface Query1Criteria {
	  public abstract ArrayList<RecordClass> sortbydatereverse(String author,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException;
	    public abstract ArrayList<RecordClass>sortbyrelevance(String author,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException;
	    public abstract ArrayList<RecordClass> between2year(Integer date1,Integer date2,String author,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException;
	    public abstract ArrayList<RecordClass> sincesomegivernyear(Integer date1,String author,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException;

}
