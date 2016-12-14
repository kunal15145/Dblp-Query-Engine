package com.company;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.management.Query;
import javax.swing.JLabel;


public class BackEndIntegration {
	
	 String selectedQuery,searchBy,nameTag,selectedOption,year1,year2,noOfPublications;
	 ArrayList<RecordClass> list;
	 String[] queryData;
	GUI gui;
	 
	public BackEndIntegration(GUI gui){		this.gui=gui;	}
	
	 public  ArrayList<RecordClass> integrate(String[] data){
		queryData=data;
		
		if(queryData==null)
				{	System.out.print("Return since querydata null");
					return null;
				}
		else	
				{	
					 selectedQuery=queryData[0];
					 searchBy=queryData[1];
					 nameTag=queryData[2];
					 selectedOption=queryData[3];
					 year1=queryData[4];
					 year2=queryData[5];
					 noOfPublications=queryData[6];

					System.out.println("\n\nSelected query:"+queryData[0]);
					System.out.println("search by:"+queryData[1]);
					System.out.println("name/title tag:"+queryData[2]);
					System.out.println("option :"+queryData[3]);
					System.out.println("year1 :"+queryData[4]);
					System.out.println("year2 :"+queryData[5]);
					System.out.println("no of publications "+queryData[6]);
				}
		
		try{
			if(selectedQuery.equals("Query 1"))
			{
					Query1Criteria queryVar=query1Factory(searchBy);
						if(selectedOption.equals("0"))
						{	
							System.out.println(nameTag);
							if(queryVar instanceof Query1Author)
								list=((Query1Author) queryVar).onlyauthors(nameTag,gui.getwwwrecord());
							else 
								list=((Query1Publ)queryVar).onlypublications(nameTag,gui.getwwwrecord());
								
						}
						
						else if(selectedOption.equals("1"))
							list=queryVar.sincesomegivernyear(Integer.parseInt(year1), nameTag,gui.getwwwrecord());
					
						
						else if(selectedOption.equals("2"))
							list=queryVar.between2year(Integer.parseInt(year1), Integer.parseInt(year2) ,nameTag,gui.getwwwrecord());
				
						
						else if(selectedOption.equals("3"))
							list=queryVar.sortbydatereverse(nameTag,gui.getwwwrecord());
						
						else
							list=queryVar.sortbyrelevance(nameTag,gui.getwwwrecord());
			}
			
			else if(selectedQuery.equals("Query 2"))
			{	list=new ArrayList<RecordClass>();
				Query2Mink query2=new Query2Mink(new File("dblp.xml"));
				ArrayList<String> query2list= query2.getauthorwithkpub(Integer.parseInt(noOfPublications),gui.getNoofp(),gui.getwwwrecord());
				for (String str:query2list)
				{
					ArrayList<String> temp=new ArrayList<>();
					temp.add(str);
					list.add(new RecordClass(temp,null,null,null,0,null,null,null));
				}
			}
			
			else if(selectedQuery.equals("Query 3"))
			{		
					gui.getTablePanel().removeAll();
					gui.getTablePanel().repaint();
					//Query3 q3=new Query3(new File("dblp.xml"));
					Query3 query3 = new Query3(new File("dblp.xml"));
                    query3.predict(nameTag,Integer.parseInt(year1),gui.getwwwrecord());
					double predictedResult=query3.get_prediction();

                HashMap<Integer,Double> p = new HashMap<>();
                    Query1Author query1Authorq = new Query1Author(new File("dblp.xml"));
                ArrayList<RecordClass> result = query1Authorq.onlyauthors(nameTag,gui.getwwwrecord());
                for(RecordClass recordClass:result){
                    double d = 1;
                    if(!p.containsKey(recordClass.year)&&recordClass.year==Integer.parseInt(year1))
                        p.put(recordClass.year,d);
                    else if(recordClass.year==Integer.parseInt(year1)){
                        Double temp=p.get(recordClass.year);
                        temp = temp + 1;
                        p.put(recordClass.year,temp);
                    }
                }

					JLabel valueLabel=new JLabel("Predicted Value:   "+predictedResult+"        "+" "+"Actual Result:   "+p.get(Integer.parseInt(year1)));
					valueLabel.setFont(new Font("Calibri",Font.BOLD,25));
					
					gui.getTablePanel().add(valueLabel);
					return null; //change this
			}
		}	
		catch(Exception e){	e.printStackTrace(); 	}
		return list;
	 }
	
	 public Query1Criteria query1Factory(String searchBy) 	
	 {
		 System.out.println("HI");
		 try{
		 if(searchBy.equals("Name"))
			 return new Query1Author(new File("dblp.xml"));
		 else if(searchBy.equals("Title Tag"))
			 return new Query1Publ(new File("dblp.xml"));
		 
		 }
		 catch(Exception e){	e.printStackTrace();}
		 return null;
	 }
}
