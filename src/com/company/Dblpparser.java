package com.company;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
public class Dblpparser {
    File file=null;
    private boolean bauthor = false;
    private boolean byear = false;
    private boolean btitle = false;
    private boolean bpages = false;
    private boolean brecords = false;
    private boolean bvolume = false;
    private boolean bjournal = false;
    private boolean burl = false;
    private ArrayList<String> authors;
    private String publications;
    private HashMap<Integer,String> publicationtodates;
    private String pages;
    private int year;
    private String volume;
    private String journal;
    private String url;
    private ArrayList<RecordClass> recordClasses = new ArrayList<>();
    private HashMap<String,Integer> noofpublications = new HashMap<>();
    ArrayList<String> author;
    ArrayList<ArrayList<String>> entityresolver;
    boolean bwww = false;
    ArrayList<RecordClass> temprecords = new ArrayList<>();
    boolean found = false;

    Dblpparser(File inputfile) throws FileNotFoundException, XMLStreamException {
        this.file = inputfile;
    }
    public ArrayList<RecordClass> parserwithauthor(String matchingauthor) throws FileNotFoundException, XMLStreamException {
        ArrayList<RecordClass> temprecord1 = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty("javax.xml.stream.isCoalescing",true);
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement element = (StartElement) event;
                if (element.getName().toString().equalsIgnoreCase("article") || element.getName().toString().equalsIgnoreCase("inproceedings") || element.getName().toString().equalsIgnoreCase("proceedings") || element.getName().toString().equalsIgnoreCase("book") || element.getName().toString().equalsIgnoreCase("incollection") || element.getName().toString().equalsIgnoreCase("phdthesis") || element.getName().toString().equalsIgnoreCase("masterthesis")) {
                    brecords = true;
                    authors = new ArrayList<>();
                    publicationtodates = new HashMap<>();
                }
                if (element.getName().toString().equalsIgnoreCase("author") && brecords == true) {
                    bauthor = true;
                }
                if (element.getName().toString().equalsIgnoreCase("title") && brecords == true) {
                    btitle = true;
                }
                if (element.getName().toString().equalsIgnoreCase("pages") && brecords == true) {
                    bpages = true;
                }
                if (element.getName().toString().equalsIgnoreCase("year") && brecords == true) {
                    byear = true;
                }
                if (element.getName().toString().equalsIgnoreCase("volume") && brecords == true) {
                    bvolume = true;
                }
                if ((element.getName().toString().equalsIgnoreCase("journal") || element.getName().toString().equalsIgnoreCase("booktitle")) && brecords == true) {
                    bjournal = true;
                }
                if (element.getName().toString().equalsIgnoreCase("url") && brecords == true) {
                    burl = true;
                }
            }
            if (event.isEndElement()) {
                EndElement endElement = (EndElement) event;
                if (endElement.getName().toString().equalsIgnoreCase("article") || endElement.getName().toString().equalsIgnoreCase("inproceedings") || endElement.getName().toString().equalsIgnoreCase("proceedings") || endElement.getName().toString().equalsIgnoreCase("book") || endElement.getName().toString().equalsIgnoreCase("incollection") || endElement.getName().toString().equalsIgnoreCase("phdthesis") || endElement.getName().toString().equalsIgnoreCase("masterthesis")) {
                    brecords = false;
                    for(String s:authors)
                    {
                        if(s.equals(matchingauthor))
                        {
                            temprecord1.add(new RecordClass(authors, publications, publicationtodates, pages, year, volume, journal, url));
                        }
                    }
                }
            }
            if (event.isCharacters()) {
                Characters characters = (Characters) event;
                if (bauthor) {
                    authors.add(characters.getData());
                    bauthor = false;
                }
                if (btitle) {
                    publications=characters.getData();
                    btitle = false;
                }
                if (bpages) {
                    pages = characters.getData();
                    bpages = false;
                }
                if (byear) {
                    year = Integer.parseInt(characters.getData());
                    byear = false;
                }
                if (bvolume) {
                    volume = characters.getData();
                    bvolume = false;
                }
                if (bjournal) {
                    journal = characters.getData();
                    bjournal = false;
                }
                if (burl) {
                    url = characters.getData();
                    burl = false;
                }
            }
        }
        return temprecord1;
    }
    public ArrayList<RecordClass> parserwithtitletag(String titletag) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty("javax.xml.stream.isCoalescing",true);

        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
        while (eventReader.hasNext())
        {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement element = (StartElement) event;
                if (element.getName().toString().equalsIgnoreCase("article") || element.getName().toString().equalsIgnoreCase("inproceedings") || element.getName().toString().equalsIgnoreCase("proceedings") || element.getName().toString().equalsIgnoreCase("book") || element.getName().toString().equalsIgnoreCase("incollection") || element.getName().toString().equalsIgnoreCase("phdthesis") || element.getName().toString().equalsIgnoreCase("masterthesis")) {
                    brecords = true;
                    authors = new ArrayList<>();
                    publicationtodates = new HashMap<>();
                }
                if (element.getName().toString().equalsIgnoreCase("author") && brecords == true) {
                    bauthor = true;
                }
                if (element.getName().toString().equalsIgnoreCase("title") && brecords == true) {
                    btitle = true;
                }
                if (element.getName().toString().equalsIgnoreCase("pages") && brecords == true) {
                    bpages = true;
                }
                if (element.getName().toString().equalsIgnoreCase("year") && brecords == true) {
                    byear = true;
                }
                if (element.getName().toString().equalsIgnoreCase("volume") && brecords == true) {
                    bvolume = true;
                }
                if ((element.getName().toString().equalsIgnoreCase("journal") || element.getName().toString().equalsIgnoreCase("booktitle")) && brecords == true) {
                    bjournal = true;
                }
                if (element.getName().toString().equalsIgnoreCase("url") && brecords == true) {
                    burl = true;
                }
            }
            if (event.isEndElement()) {
                EndElement endElement = (EndElement) event;
                if (endElement.getName().toString().equalsIgnoreCase("article") || endElement.getName().toString().equalsIgnoreCase("inproceedings") || endElement.getName().toString().equalsIgnoreCase("proceedings") || endElement.getName().toString().equalsIgnoreCase("book") || endElement.getName().toString().equalsIgnoreCase("incollection") || endElement.getName().toString().equalsIgnoreCase("phdthesis") || endElement.getName().toString().equalsIgnoreCase("masterthesis")) {
                    brecords = false;
                    if (publications.contains(titletag)) {
                        recordClasses.add(new RecordClass(authors, publications, publicationtodates, pages, year, volume, journal, url));
                    }
                }
            }
            if (event.isCharacters()) {
                Characters characters = (Characters) event;
                if (bauthor) {
                    authors.add(characters.getData());
                    bauthor = false;
                }
                if (btitle) {
                    publications=characters.getData();
                    btitle = false;
                }
                if (bpages) {
                    pages = characters.getData();
                    bpages = false;
                }
                if (byear) {
                    year = Integer.parseInt(characters.getData());
                    byear = false;
                }
                if (bvolume) {
                    volume = characters.getData();
                    bvolume = false;
                }
                if (bjournal) {
                    journal = characters.getData();
                    bjournal = false;
                }
                if (burl) {
                    url = characters.getData();
                    burl = false;
                }
            }
        }
        return recordClasses;
    }
    public HashMap<String, Integer> findauthorswithminkpublications(Integer k) throws FileNotFoundException, XMLStreamException {
        System.out.println("there");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty("javax.xml.stream.isCoalescing",true);

        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
        while (eventReader.hasNext())
        {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement element = (StartElement) event;
                if (element.getName().toString().equalsIgnoreCase("article") || element.getName().toString().equalsIgnoreCase("inproceedings") || element.getName().toString().equalsIgnoreCase("proceedings") || element.getName().toString().equalsIgnoreCase("book") || element.getName().toString().equalsIgnoreCase("incollection") || element.getName().toString().equalsIgnoreCase("phdthesis") || element.getName().toString().equalsIgnoreCase("masterthesis")) {
                    brecords = true;
                    author=new ArrayList<>();
                }
                if (element.getName().toString().equalsIgnoreCase("author") && brecords == true) {
                    bauthor = true;
                }
            }
            if (event.isEndElement()) {
                EndElement endElement = (EndElement) event;
                if (endElement.getName().toString().equalsIgnoreCase("article") || endElement.getName().toString().equalsIgnoreCase("inproceedings") || endElement.getName().toString().equalsIgnoreCase("proceedings") || endElement.getName().toString().equalsIgnoreCase("book") || endElement.getName().toString().equalsIgnoreCase("incollection") || endElement.getName().toString().equalsIgnoreCase("phdthesis") || endElement.getName().toString().equalsIgnoreCase("masterthesis")) {
                    brecords = false;
                    for(String s : author)
                    {
                        if(noofpublications.containsKey(s)==true)
                        {
                            noofpublications.put(s,noofpublications.get(s)+1);
                        }
                        else
                        {
                            noofpublications.put(s,1);
                        }
                    }
                }
            }
            if (event.isCharacters()) {
                Characters characters = (Characters) event;
                if (bauthor) {
                    author.add(characters.getData());
                    bauthor = false;
                }
            }
        }
        return noofpublications;
    }
    public ArrayList<ArrayList<String>> resolveenititywithauthor() throws FileNotFoundException, XMLStreamException {
        System.out.println("Er");
        entityresolver = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty("javax.xml.stream.isCoalescing",true);

        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
        while (eventReader.hasNext())
        {
            XMLEvent event = eventReader.nextEvent();
            if(event.isStartElement())
            {
                StartElement element = (StartElement)event;
                if(element.getName().toString().equalsIgnoreCase("www"))
                {
                    bwww=true;
                    brecords=true;
                    temp = new ArrayList<>();
                }
                if(element.getName().toString().equalsIgnoreCase("author")&&brecords==true)
                {
                    bauthor=true;
                }
            }
            if(event.isEndElement())
            {
                EndElement endelement = (EndElement)event;
                if(endelement.getName().toString().equalsIgnoreCase("www"))
                {
                    entityresolver.add(temp);
                    if(temp.contains(author))
                    {
                        found =  true;
                        for(String s:temp)
                        {
                            temprecords=this.parserwithauthor(s);
                            for(RecordClass recordClass:temprecords)
                            {
                                recordClasses.add(recordClass);
                            }
                        }
                    }
                    brecords=false;
                }
            }
            if(event.isCharacters())
            {
                Characters characters = (Characters) event;
                if(bauthor)
                {
                    temp.add(characters.getData());
                    bauthor=false;
                }
            }
        }
        return entityresolver;
    }
    public ArrayList<ArrayList<String>> getEntityresolver()
    {return entityresolver;}
}