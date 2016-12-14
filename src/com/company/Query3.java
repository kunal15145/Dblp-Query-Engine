package com.company;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Query3{

    // array list of all objects containing data having same title
    //private publication a; //objects of publication which will store data
    //private List<String> collection = new ArrayList<String>(); // just to check tags in a file.
    File myfile;
    Map<Integer,Double> m = new TreeMap<Integer,Double>();
    Map<Double,Double> m2 = new TreeMap<Double,Double>();
    List<Double> l = new ArrayList<Double>();
    List<Double> l2 = new ArrayList<Double>();
    long result1;
    List<Double> l3 = new ArrayList<Double>();
    Query3(File file)
    {
        this.myfile=file;
    }
    public void predict(String author,Integer year,ArrayList<ArrayList<String>> wwwrecords) throws FileNotFoundException, XMLStreamException {
        Query1Author query1Author = new Query1Author(myfile);
        ArrayList<RecordClass> result = query1Author.onlyauthors(author,wwwrecords);
        for(RecordClass recordClass:result){
            double d = 1;
            if(!m.containsKey(recordClass.year)&&recordClass.year<year)
                m.put(recordClass.year,d);
            else if(recordClass.year<year){
                Double temp=m.get(recordClass.year);
                temp = temp + 1;
                m.put(recordClass.year,temp);
            }
        }
        display(m);
		/*query1 q = new query1();
		q.search(author);
		p = q.get_list();
		for(publication pub : p)
		{
			double value = 0;
			if(pub.get_y()<year)
			{

				if(m.get(pub.get_y())==null)
					value=1;
				else{
					value = m.get(pub.get_y());
					value++;
				}
				m.put(pub.get_y(), value);
			}


		}*/
    }

    private void display(Map<Integer, Double> m) {
        for(Map.Entry<Integer,Double> entry:m.entrySet())
        {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }

    public double get_prediction()
    {
        double newValue;
        double alpha;
        double min;
        for(Map.Entry e :m.entrySet() )
        {
            l.add((Double)e.getValue());
        }
        for(alpha = 0.1 ;alpha <0.9 ; alpha = alpha+0.01)
        {
            double msd=0,c=0;
            //System.out.println(alpha);
            for(int i=0;i<l.size();i++)
            {
                if(i==0)
                    newValue = l.get(i);
                else
                    newValue = alpha* l.get(i) + (1-alpha)*l2.get(i-1);
                l2.add(newValue);

            }
            //System.out.println(l2);
            min = Double.MAX_VALUE;
            for(int i=0;i<l.size()-1;i++)
            {
                msd = msd + ((l2.get(i)- l.get(i+1))*((l2.get(i)- l.get(i+1))));
                //System.out.println((l2.get(i) + " - " + l.get(i+1)));
                c++;
            }
            //System.out.println("msd= " + msd + "c= " + c);
            msd = msd/c;
            //System.out.println("msd= " + msd + " value = " + l2.get(l2.size()-1));
            if(!l2.isEmpty())
                m2.put(msd,l2.get(l2.size()-1));
            l2.clear();
        }
        for(Map.Entry e :m2.entrySet() )
        {
            l3.add((Double)e.getValue());
        }
        return l3.get(0);

    }
    /*public static void main(String args[]) throws FileNotFoundException, XMLStreamException {
		prediction p = new prediction();
		p.predict("Sanjeev Saxena",2000);
		p.get_prediction();
		for(Map.Entry e :p.m.entrySet() )
		{
			System.out.println(e.getKey() + " " + e.getValue());
		}
	}*/
}

