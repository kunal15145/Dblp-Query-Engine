package com.company;




import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yagami on 17-11-2016.
 */

public class EntityResolver {

    private Dblpparser dblpparser;
    private File file;
    private ArrayList<RecordClass> authorsandpublafterentityresolved=new ArrayList<>();
    private ArrayList<RecordClass> publicationaftermatchingtitlettag;
    private HashMap<String, Integer> authorwithminkpubl = new HashMap<>();
    private ArrayList<ArrayList<String>> wwwrecords = new ArrayList<>();
    private ArrayList<String> query2resolved = new ArrayList<>();
    private HashMap<String,Integer> temprecords = new HashMap<>();
    private ArrayList<RecordClass> temp = new ArrayList<>();
    private ArrayList<ArrayList<String>> temp1 = new ArrayList<>();

    EntityResolver(File File) throws FileNotFoundException, XMLStreamException {
        file = File;
        dblpparser=new Dblpparser(file);
    }

    public ArrayList<RecordClass> resolveentityforquery1(String author,ArrayList<ArrayList<String>> wwwrecords) throws FileNotFoundException, XMLStreamException {
        for(ArrayList<String> record : wwwrecords)
        {
            for (String s:record)
            {
                if(record.contains(author))
                {
                    temp=dblpparser.parserwithauthor(s);
                    authorsandpublafterentityresolved.addAll(temp);
                }
            }
        }
        return authorsandpublafterentityresolved;
    }

    public ArrayList<RecordClass> resolveentityforquery1part2(String titletag) throws FileNotFoundException, XMLStreamException {
        publicationaftermatchingtitlettag=dblpparser.parserwithtitletag(titletag);
        return publicationaftermatchingtitlettag;
    }

    public ArrayList<String> resolveentityforquery2(Integer k,HashMap<String,Integer> authorwithminkpubl,ArrayList<ArrayList<String>> wwwrecords) throws FileNotFoundException, XMLStreamException {
        for(ArrayList<String> str:wwwrecords)
        {
            int count=0;
            for(String s:str)
            {
                if(authorwithminkpubl.containsKey(s))
                {
                    count=count+authorwithminkpubl.get(s);
                    temprecords.put(s,count);
                }
            }
        }
        for(HashMap.Entry<String,Integer>entry:temprecords.entrySet())
        {
            if(entry.getValue()>k)
            {
                query2resolved.add(entry.getKey());
            }
        }
        return query2resolved;
    }

    public ArrayList<RecordClass> getAuthorsandpublafterentityresolved()
    {return authorsandpublafterentityresolved;}

    public ArrayList<RecordClass> getPublicationaftermatchingtitlettag()
    {return publicationaftermatchingtitlettag;}

    public ArrayList<String> getQuery2resolved()
    {return query2resolved;}

}