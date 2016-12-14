package com.company;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EmptyStackException;

/**
 * Created by yagami on 19-11-2016.
 */
	class Query1Author implements Query1Criteria{
    private ArrayList<RecordClass> authorsrecord,sincesomegivenyear,between2yearforauthor;
    private EntityResolver entityResolver ;
    public Query1Author(File File) throws FileNotFoundException, XMLStreamException {
        entityResolver=new EntityResolver(File);
    }

    public ArrayList<RecordClass> sortbydatereverse(String author,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
        authorsrecord = entityResolver.resolveentityforquery1(author,www);
        Collections.sort(authorsrecord, new Comparator<RecordClass>() {
            @Override
            public int compare(RecordClass o1, RecordClass o2) {
                if(o1.year>o2.year)
                    return -1;
                else if(o1.year==o2.year)
                    return 0;
                else
                    return 1;
            }
        });
        return authorsrecord;
    }
    public ArrayList<RecordClass> sortbyrelevance(String author,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
        /*authorsrecord = entityResolver.resolveentityforquery1(author);
        System.out.println(authorsrecord.size());
        return authorsrecord;
        */
    	return sortbydatereverse(author,www);
    }
    public ArrayList<RecordClass> between2year(Integer date1,Integer date2,String author,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
    	authorsrecord=new ArrayList<RecordClass>();
    	between2yearforauthor=new ArrayList<>();
    	authorsrecord=entityResolver.resolveentityforquery1(author,www);
        for(RecordClass recordClass:authorsrecord)
        {
            if(recordClass.year>=date1&&recordClass.year<=date2)
            {
                between2yearforauthor.add(recordClass);
            }
        }
        return between2yearforauthor;
    }

    public ArrayList<RecordClass> sincesomegivernyear(Integer date1,String author,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
        sincesomegivenyear=new ArrayList<>();
        authorsrecord=entityResolver.resolveentityforquery1(author,www);
        for(RecordClass recordClass:authorsrecord)
        {
            if(recordClass.year>=date1)
            {
                sincesomegivenyear.add(recordClass);
            }
        }
        return sincesomegivenyear;
    }
  public ArrayList<RecordClass> onlyauthors(String author,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
      return entityResolver.resolveentityforquery1(author,www);
  }
}