package com.company;

import com.sun.org.apache.regexp.internal.RE;

import javax.xml.stream.XMLStreamException;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.PackedColorModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by yagami on 19-11-2016.
 */
public class Query1Publ implements Query1Criteria{
    EntityResolver entityResolver ;
    public Query1Publ(File File) throws FileNotFoundException, XMLStreamException {
        entityResolver = new EntityResolver(File);
    }

    public ArrayList<RecordClass> sortbydatereverse(String titletag,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
        ArrayList<RecordClass> authorsrecord;
        authorsrecord = entityResolver.resolveentityforquery1part2(titletag);
        Collections.sort(authorsrecord, new Comparator<RecordClass>() {
            @Override
            public int compare(RecordClass o1, RecordClass o2) {
                if (o1.year > o2.year) {
                    return -1;
                } else if (o1.year == o2.year)
                    return 0;
                else
                    return 1;
            }
        });
        return authorsrecord;
    }
    public ArrayList<RecordClass> sortbyrelevance(String titletag,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
        ArrayList<RecordClass> authorsrecord;
        authorsrecord=entityResolver.resolveentityforquery1part2(titletag);
        HashMap<RecordClass,Integer> soring = new HashMap<>();
        for(RecordClass recordClass:authorsrecord)
        {
            String[] recordtag = recordClass.publications.split(" ");
            int count = 0;
            for(int i=0;i<recordtag.length;i++)
            {
                if(recordtag[i].equals(titletag))
                {
                    count++;
                }
            }
            soring.put(recordClass,count);
        }
        List<Map.Entry<RecordClass,Integer>> list = new LinkedList<>(soring.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<RecordClass, Integer>>() {
            @Override
            public int compare(Map.Entry<RecordClass, Integer> o1, Map.Entry<RecordClass, Integer> o2) {
                if(o1.getValue()>o2.getValue())
                    return -1;
                else if(o1.getValue()<o2.getValue())
                    return 1;
                else return 0;
            }
        });
        ArrayList<RecordClass> finalrecord = new ArrayList<>();
        Map<RecordClass,Integer> recordClassIntegerMap = new LinkedHashMap<>();
        for (Map.Entry<RecordClass,Integer> entry:list)
        {
            finalrecord.add(entry.getKey());
        }
        
        return finalrecord;
    }

    public ArrayList<RecordClass> between2year(Integer date1, Integer date2, String titletag,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
        ArrayList<RecordClass> authorsrecord = new ArrayList<>();
        ArrayList<RecordClass> between2yearforauthor = new ArrayList<>();
        authorsrecord=entityResolver.resolveentityforquery1part2(titletag);
        for(RecordClass recordClass:authorsrecord)
        {
            if(recordClass.year>=date1&&recordClass.year<=date2)
            {
                between2yearforauthor.add(recordClass);
            }
        }
        return between2yearforauthor;
    }

    public ArrayList<RecordClass> sincesomegivernyear(Integer date1, String titletag,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
        ArrayList<RecordClass> authorsrecord = new ArrayList<>();
        ArrayList<RecordClass> sincesomegivenyear = new ArrayList<>();
        authorsrecord=entityResolver.resolveentityforquery1part2(titletag);
        for(RecordClass recordClass:authorsrecord)
        {
            if(recordClass.year>=date1)
            {
                sincesomegivenyear.add(recordClass);
            }
        }
        return sincesomegivenyear;
    }

    public ArrayList<RecordClass> onlypublications(String titletag,ArrayList<ArrayList<String>> www) throws FileNotFoundException, XMLStreamException {
        return entityResolver.resolveentityforquery1part2(titletag);
    }
}