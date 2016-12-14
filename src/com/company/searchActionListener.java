package com.company;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

public class searchActionListener implements ActionListener{
	GUI gui;
	JTable query1table,query2table;
	JScrollPane js1;
	JScrollPane js2;
	int indexDone=0,previousIndex=0,recordCount;	
	JPanel lowerTablePanel;
	
	ArrayList<RecordClass> list;
	
public searchActionListener(GUI gui)
	{
		this.gui=gui;
	}
	public void actionPerformed(ActionEvent e)
	{	
		indexDone=0;
		previousIndex=0;
		recordCount=0;
		//validate if the data is in correct format
		//done!! :D
		EventQueue.invokeLater(new Runnable(){
		public void run()
		{	gui.getTablePanel().revalidate();	}
		});
		
		
		
	
		
		 
		 
			BackEndIntegration backEnd=new BackEndIntegration(gui);
			String[] errorString=gui.getFieldData();
			
			if(errorString[0]==null)
				{	JLabel error=new JLabel("ERROR ENCOUNTERED:  "+errorString[1]);
					error.setFont(new Font("Calibri",Font.ITALIC,20));
					error.setForeground(Color.red);
					//System.out.println();
					gui.getTablePanel().removeAll();
					gui.getTablePanel().repaint();
					gui.getTablePanel().add(error);
					return;
				}
			else list=backEnd.integrate(gui.getFieldData());
			
			
			
/* 	useless ;now this thing is happening in backend integration
 * 		else  if(selectedQuery.equals("Query 2"))
			{
				Query2Mink query2Var=new Query2Mink(new File("dblp.xml"));
				ArrayList<String> temp=query2Var.getauthorwithkpub(Integer.parseInt(noOfPublications));
				list=new ArrayList<>();
				for(String str:temp)
				{	t=
					list.add(new RecordClass());
				}
			}
	}*/
			
			
			if(list!=null)
			System.out.println("reached end list size:"+list.size() );
		/*		dispalying the list 
			for(RecordClass item:list){
				//new Integer(indexDone+1);
				System.out.println("authors"+item.authors);
				System.out.println("Publications"+item.publications);
				System.out.println("Pages: "+item.pages);
		 		System.out.println("year"+new Integer(item.year));
				System.out.println("Volume:"+item.volume+"\n Journals"+item.journal+"\n URL"+item.url);
			}
			
		*/	
		/*	delete this block									//arrayList test code	
		ArrayList<RecordClass> list=new ArrayList<>();
		for(int i=0;i<45;i++)
		{	ArrayList<String> temp=new ArrayList<>();	
			temp.add("Author"+(i+1));
			RecordClass tempRecord=new RecordClass(temp,null,null,null,0,null,null,null);
			list.add(tempRecord);
		}
		//arraylist test code		
		//generate data list if no results found ,then instead of table make a jlabel saying so
		//ArrayList<Data>  list=null;
	delete above block	
	*/
		if(list==null && gui.getselectedQuery().equals("Query 3"))
		return;
	
			
		gui.getTablePanel().removeAll();
		gui.getTablePanel().repaint();
		gui.getTablePanel().revalidate();
		
		
		if( list==null || list.size()==0)
		{	
			JLabel error=new JLabel("NO RESULT FOUND!!");
					error.setFont(new Font("Calibri",Font.BOLD,35));
			gui.getTablePanel().add(error);
			return;
		}
		
		else
		{
		lowerTablePanel=new JPanel();	
		lowerTablePanel.setBackground(Color.white);
		JLabel count=new JLabel("TOTAL RECORDS:  "+list.size());
		count.setFont(new Font("Calibri",Font.BOLD,20));
		count.setPreferredSize(new Dimension(450,35));
		lowerTablePanel.add(count);
		ImageIcon next=new ImageIcon("next.png","next icon");
		ImageIcon end=new ImageIcon("end.png","end icon");
		ImageIcon previous=new ImageIcon("next2.png","previous icon");
		
		JPanel tableButtonPanel=new JPanel();
		tableButtonPanel.setBackground(Color.white);
		JButton nextButton=new JButton("NEXT        ",next);	nextButton.setFont(new Font("Calibri",Font.BOLD,20));
		JButton endButton=new JButton("END         ",end);	endButton.setFont(new Font("Calibri",Font.BOLD,20));
		JButton previousButton=new JButton("PREVIOUS",previous);	previousButton.setFont(new Font("Calibri",Font.BOLD,20));
	
		nextButton.setBackground(Color.white);
		endButton.setBackground(Color.white);
		previousButton.setBackground(Color.white);
		
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{	if(indexDone<0)
					indexDone=0;
				if(indexDone==list.size())
				{
				tableButtonPanel.removeAll();
				tableButtonPanel.add(previousButton);
				tableButtonPanel.add(endButton);
				lowerTablePanel.add(tableButtonPanel);
				gui.getTablePanel().revalidate();
					return;
				}
			else{
				tableButtonPanel.removeAll();
				tableButtonPanel.add(previousButton);
				tableButtonPanel.add(nextButton);
				lowerTablePanel.add(tableButtonPanel);
				}
				gui.getTablePanel().removeAll();
				gui.getTablePanel().repaint();
				recordCount=listToTable(list);	
			}
		});
		
		previousButton.addActionListener(new ActionListener(){
		
			public void actionPerformed(ActionEvent e)
			{	
				System.out.println("VAlue of indexDone:"+indexDone);
				indexDone=indexDone-recordCount-20;
				System.out.println("Changed value indexDone:"+indexDone);
				
				if(indexDone<0)
				{	System.out.println("index<0");
					tableButtonPanel.removeAll();
					tableButtonPanel.add(endButton);
					tableButtonPanel.add(nextButton);
					lowerTablePanel.add(tableButtonPanel);
					gui.getTablePanel().revalidate();
					return;
				}
				else {
					tableButtonPanel.removeAll();
					tableButtonPanel.add(previousButton);
					tableButtonPanel.add(nextButton);
					lowerTablePanel.add(tableButtonPanel);
				}
				gui.getTablePanel().removeAll();
				gui.getTablePanel().repaint();
				
				recordCount=listToTable(list);
			}
		});
	
		if(list.size()<=20){
				tableButtonPanel.removeAll();
				tableButtonPanel.add(endButton);
				lowerTablePanel.add(tableButtonPanel);
		}
		else if(indexDone<20)
		{	//System.out.println("indexDone<20");
			tableButtonPanel.removeAll();
			tableButtonPanel.add(endButton);
			tableButtonPanel.add(nextButton);
			lowerTablePanel.add(tableButtonPanel);
		}/*
		else if(indexDone>=list.size()-20)
		{	lowerTablePanel.remove(tableButtonPanel);
			tableButtonPanel.add(previousButton);
			tableButtonPanel.add(endButton);
			lowerTablePanel.add(tableButtonPanel);
		}*/
		
		else{
			tableButtonPanel.removeAll();
			tableButtonPanel.add(previousButton);
			tableButtonPanel.add(nextButton);
			lowerTablePanel.add(tableButtonPanel);
		}
		gui.getTablePanel().repaint();
		gui.getTablePanel().revalidate();
		recordCount=listToTable(list);
		
		}
}
	
	public int listToTable(List<RecordClass> list ){
			int tableRows=0;
		if( gui.getselectedQuery().equals("Query 1")   )
		{
				Object [][] data=new Object [20][8];
			
				while(indexDone<list.size() && tableRows<20)
				{		int index=indexDone%20;
						RecordClass item=list.get(indexDone);
						data[index][0]=new Integer(indexDone+1);
						data[index][1]=item.authors;
						data[index][2]=(String)item.publications;
						data[index][3]=(String)item.pages;
				 		data[index][4]=new Integer(item.year);
						data[index][5]=(String)item.volume;
						data[index][6]=(String)item.journal;
						data[index][7]=(String)item.url;
						tableRows++;
						indexDone++;
				}
				
						query1table=new JTable(new Query1Table(data) );
						//query1table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black,2));
						query1table.getTableHeader().setBackground(Color.pink);
						query1table.getTableHeader().setFont(new Font("Sans Serif",Font.BOLD,14));
						Query1Table.cellResizer(query1table);
						js1=new JScrollPane(query1table);
						query1table.setFillsViewportHeight(true);
						js1.setPreferredSize(new Dimension(800,370));
						gui.getTablePanel().add(js1);
						gui.getTablePanel().add(lowerTablePanel);
						gui.getDisplay().append("\nsearch ended");
						gui.getTablePanel().revalidate();
		}	
		else if( gui.getselectedQuery().equals("Query 2"))
		{
			Object [][] data=new Object [20][2];
			while(indexDone<list.size() && tableRows<20)
			{		int index=indexDone%20;
					RecordClass item=list.get(indexDone);
					
					data[index][0]=new Integer(indexDone+1);
					data[index][1]=item.authors;
					tableRows++;
					indexDone++;
			}
			
					query2table=new JTable(new Query2Table(data) );
					//query1table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black,2));
					query2table.getTableHeader().setBackground(Color.yellow);
					query2table.getTableHeader().setFont(new Font("Sans Serif",Font.BOLD,14));
					Query2Table.cellResizer(query2table);
					js2=new JScrollPane(query2table);
					query2table.setFillsViewportHeight(true);
					js2.setPreferredSize(new Dimension(800,370));
					gui.getTablePanel().add(js2);

					gui.getTablePanel().add(lowerTablePanel);
					gui.getDisplay().append("\nsearch ended");
					gui.getTablePanel().revalidate();
		}
		return tableRows;
	}

		
	
}
	

