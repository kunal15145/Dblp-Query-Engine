package com.company;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yagami on 19-11-2016.
 */
public class Query2Mink {
    EntityResolver entityResolver;
    private ArrayList<String> authorwwithkpubl = new ArrayList<>();
    Query2Mink(File file) throws FileNotFoundException, XMLStreamException {
        entityResolver = new EntityResolver(file);
    }
    public ArrayList<String> getauthorwithkpub(Integer k, HashMap<String,Integer> map,ArrayList<ArrayList<String>> wwwrecords) throws FileNotFoundException, XMLStreamException {
        System.out.println("Hi");
        authorwwithkpubl=entityResolver.resolveentityforquery2(k,map,wwwrecords);
        return authorwwithkpubl;
    }
}
