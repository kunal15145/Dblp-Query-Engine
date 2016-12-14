package com.company;

import javax.swing.*;
import javax.swing.border.*;
import javax.xml.stream.XMLStreamException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI {

    private static Dblpparser dblpparser;
    private static ArrayList<ArrayList<String>> wwwrecords;
    private static HashMap<String,Integer> noofp;
	private JFrame frame=new JFrame("DBLP");
	JTextArea displayBox=new JTextArea();
	JScrollPane js=new JScrollPane(displayBox);
	
	JPanel tablePanel,queryPanel=new JPanel(),leftPanel,queryTopPanel,queryMiddlePanel,displayPanel;
	Border border=BorderFactory.createLineBorder(Color.BLACK);
	ImageIcon search=new ImageIcon("search.png","search icon");
	ImageIcon reset=new ImageIcon("reset.png","search icon");
	JButton searchButton=new JButton("Search",search);		
	JButton resetButton=new JButton("Reset",reset);	
	MyFocusListener focusListener=new MyFocusListener();
	JTable query1Table,query2Table;
	private String selectedQuery="",searchBy="",nameTag="",selectedOption="",year1="0000",year2="0000",noOfPublications="";
	private JTextField field1,field2,field3a,field3b,fieldQuery2,authorField,yearField;
	public String getK(){	return fieldQuery2.getText();	}
	public JTextArea getDisplay(){	return displayBox;	}
	public JPanel getTablePanel(){	return tablePanel;	}
	public String  getselectedQuery(){	return selectedQuery;	}

	public void mainScreen(){
		Font font=new Font("Calibri",Font.PLAIN,12);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.black, 2));
		frame.setSize(1200, 700);
		JLabel heading =new JLabel("DBLP QUERY ENGINE",SwingConstants.CENTER);
		heading.setFont(new Font("Lucida Sans",Font.PLAIN, 60));
		heading.setBorder(border);
		frame.add(heading,BorderLayout.NORTH);
		
		tablePanel=new JPanel();
		tablePanel.setBackground(Color.white);
		tablePanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		//delete
		//tablePanel.setBackground(Color.LIGHT_GRAY);
		//delete
		js.setPreferredSize(new Dimension(300,100));
		js.setBorder(border);
		//tablePanel.add(js);
	
	
		
		queryPanel.setPreferredSize(new Dimension(320,400));		queryPanel.setBackground(Color.white);
		leftPanel=new JPanel();		leftPanel.setBackground(Color.white);
		leftPanel.setPreferredSize(new Dimension(350, 700));
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		queryTopPanel=new JPanel();
		queryMiddlePanel=new JPanel();	queryMiddlePanel.setBorder(BorderFactory.createLineBorder(Color.orange,3));	
		displayPanel=new JPanel();	displayPanel.add(js);
		
		String[] queries={"-QUERIES-","Query 1","Query 2","Query 3"};
		JComboBox<String> queryBox=new JComboBox<>(new MyComboBox(queries));
		queryBox.setFont(font);
		queryBox.setBackground(Color.white);
		queryBox.setPreferredSize(new Dimension(80,25));
		queryBox.setSelectedIndex(0);
		queryBox.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		queryBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				selectedQuery=(String)queryBox.getSelectedItem();
				queryMiddlePanel.removeAll();	queryMiddlePanel.repaint(); 	
				makeQueryMiddlePanel(queryBox);
			}
		});
		//=====================search and reset
		searchButton.setBackground(Color.white);
		resetButton.setBackground(Color.white);
		searchButton.addActionListener(new searchActionListener(this));
		
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				queryMiddlePanel.removeAll();
				queryMiddlePanel.repaint();
				tablePanel.removeAll();
				tablePanel.repaint();
				//selectedQuery="";
				searchBy="";nameTag="";selectedOption="";year1="0000";year2="0000";noOfPublications="";
				makeQueryMiddlePanel(queryBox);
				
			}
		});
		
		queryTopPanel.add(queryBox);	queryTopPanel.add(Box.createRigidArea(new Dimension(50,25)));	queryTopPanel.setBackground(Color.white);
		queryPanel.add(queryTopPanel);
		displayPanel.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
		leftPanel.add(queryPanel);
		leftPanel.add(displayPanel);
		frame.add(leftPanel,BorderLayout.WEST);
		frame.add(tablePanel,BorderLayout.CENTER);	
		frame.setVisible(true);
	}
	public void makeQueryMiddlePanel(JComboBox<String> queryBox)
	{
		String selectedQuery=(String)queryBox.getSelectedItem(); 
		displayBox.append("\nUser selected the option:  "+selectedQuery);
		EventQueue.invokeLater(new Runnable(){
	        public void run()
	        {	queryPanel.revalidate();  }
	    });
		
		if(selectedQuery.equals("Query 1"))
			query1Panel();
		else if(selectedQuery.equals("Query 2"))
			query2Panel();
		else if(selectedQuery.equals("Query 3"))
			query3Panel();
	}
	
	//data for queries 
	//String selectedQuery="",searchBy="",nameTag="",selectedOption="",year1="0000",year2="0000";

	public void query1Panel(){
		
		Font font=new Font("Times New Roman",Font.BOLD,14);
		JPanel topPanel=new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));			//topPanel.setBackground(Color.orange);
		topPanel.setPreferredSize(new Dimension(300,250));
		//topPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		topPanel.add(Box.createRigidArea(new Dimension(350,20)));
		String[] values={	"-SEARCH BY-","Name","Title Tag"	};
		JComboBox<String> cb=new JComboBox<>(new MyComboBox(values));
		cb.setFont(font);
		cb.setMaximumSize(new Dimension(120,25));
		cb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{	searchBy=(String)cb.getSelectedItem();	
			}
		});
		JPanel first=new JPanel();
		first.setMaximumSize(new Dimension(300,100));		
		JLabel label1=new JLabel("    Name / Title Tags : ");			label1.setFont(font);
		field1=new JTextField();
		
		
		label1.setPreferredSize(new Dimension(145,25));
		field1.setPreferredSize(new Dimension(145,25));
		
		JLabel label2=new JLabel("   Since Year : ");			label2.setFont(font);
		 field2=new JTextField("   YYYY   ");	field2.addFocusListener(focusListener);
		label2.setPreferredSize(new Dimension(145,25));
		field2.setPreferredSize(new Dimension(60,25));
		
		JLabel label3=new JLabel("       Custom Range : ");		label3.setFont(font);
		label3.setPreferredSize(new Dimension(145,25));
		 field3a=new JTextField("   YYYY   ");				JLabel label3dash=new JLabel(" --");		field3b=new JTextField("   YYYY   ");
		field3a.setPreferredSize(new Dimension(60,25));		field3b.setPreferredSize(new Dimension(60,25));
		field3a.addFocusListener(focusListener);                   field3b.addFocusListener(focusListener);	
		first.add(label1); first.add(field1);		
		first.add(label2); first.add(field2);		
		first.add(label3); 		first.add(field3a);		first.add(label3dash); 		first.add(field3b);
	
		JRadioButton sort1=new JRadioButton("Sort By Year        ");		sort1.setFont(font);
		sort1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent s1){	selectedOption="3";	}
		});
		JRadioButton sort2=new JRadioButton("Sort By Relevance");		sort2.setFont(font);
		sort2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent s2){	selectedOption="4";	}
		});
		ButtonGroup sort=new ButtonGroup();
		
		sort.add(sort1);
		sort.add(sort2);
		
		sort1.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		sort2.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		
		topPanel.add(cb);
		topPanel.add(first);
		topPanel.add(sort1);	topPanel.add(sort2);
		queryMiddlePanel.add(topPanel);
		queryPanel.add(queryMiddlePanel);
		queryPanel.add(searchButton);		queryPanel.add(resetButton);
		
	}
	public void query2Panel(){
		JLabel label=new JLabel("  No. of Publications: ");	label.setFont(new Font("Times New Roman",Font.BOLD,14));		
		label.setPreferredSize(new Dimension(145,25));
		fieldQuery2=new JTextField(5);	
		queryMiddlePanel.add(label);
		queryMiddlePanel.add(fieldQuery2);
		queryPanel.add(queryMiddlePanel);
		queryPanel.add(searchButton);		queryPanel.add(resetButton);
	}
	public void query3Panel(){
		JPanel tempPanel=new JPanel();	tempPanel.setPreferredSize(new Dimension(300,100));
		//queryMiddlePanel.setPreferredSize(new Dimension(300,200));
		JLabel authorLabel=new JLabel("Author Name:   "); authorLabel.setFont(new Font("Times New Roman",Font.BOLD,14));
		authorLabel.setPreferredSize(new Dimension(100,25));
		authorField=new JTextField(9);
		JLabel yearLabel=new JLabel("  Prediction Year:");	yearLabel.setFont(new Font("Times New Roman",Font.BOLD,14));
		//yearLabel.setBackground(Color.red); yearLabel.setOpaque(true);
		yearLabel.setPreferredSize(new Dimension(150,25));
		yearField=new JTextField(5);
		tempPanel.add(authorLabel);
		tempPanel.add(authorField);
		tempPanel.add(yearLabel);	
		tempPanel.add(yearField);
		queryMiddlePanel.add(tempPanel);
		queryPanel.add(queryMiddlePanel);
		queryPanel.add(searchButton);	queryPanel.add(resetButton);
	}
	public String[]  getFieldData()
	{		String[] errorString=new String[2];
			errorString[0]=null;
		
		if(selectedQuery.equals("Query 1"))
		{	nameTag=field1.getText();
			
			if(nameTag.isEmpty()){
				errorString[1]="NAME/TITLE cannot be BLANK!";
				return errorString;
			}
			if(!searchBy.equals("Name") && !searchBy.equals("Title Tag")){
				errorString[1]="Please choose one from NAME / TITLE TAG ";
				return errorString ;
			}
			else if(selectedOption.isEmpty() || selectedOption.equals("1") || selectedOption.equals("2") )		//selectedOption.equals("1") || selectedOption.equals("2" ) these are done so as to get error message in searchActionListener
			{	
					if(!	field2.getText().equals("   YYYY   "))
					{	
					selectedOption="1";
					year1=field2.getText();
					 if(!year1.matches("[0-9]+") || year1.length()!=4){
						errorString[1]="Year1 Should be a 4-Digit Integer";	
						return errorString;
					}
					}
					else if(!field3a.getText().equals("   YYYY   ")		||  !	field3b.getText().equals("   YYYY   "))
							{		selectedOption="2"; 	year1=field3a.getText(); 		year2=field3b.getText();			
							
							 if(!year1.matches("[0-9]+") || year1.length()!=4 || !year2.matches("[0-9]+") || year2.length()!=4 || Integer.parseInt(year1)<Integer.parseInt(year2))
							{
								errorString[1]="Years Should be  4-Digit Integers and Year1 < Year2";	return errorString;
							}
						}
			else 	selectedOption="0";
			}
		}
		
		else if(selectedQuery.equals("Query 2"))
		{
			if(fieldQuery2.getText().isEmpty()) 
				{	errorString[1]="No. of Publications can't be EMPTY!";	return errorString;		}
			else if(!fieldQuery2.getText().matches(("[0-9]+")))	//if (text.matches("[0-9]+") && text.length() > 2) 
				{	errorString[1]="No. of Publications contain NON-INTEGER DATA";	return errorString;			}
			else noOfPublications=fieldQuery2.getText();
		}	
		else if(selectedQuery.equals("Query 3"))
		{	nameTag=authorField.getText();	year1=yearField.getText();
			if(authorField.getText().isEmpty())
			{	errorString[1]="Author Name can't be EMPTY!!";		return errorString;		}
			else if (	!yearField.getText().matches("[0-9]+") || yearField.getText().length()!=4)
			{	errorString[1]="Year Should be a 4-Digit Integer ";	return errorString;			}
		}
		String[]	queryData={selectedQuery,searchBy,nameTag,selectedOption,year1,year2,noOfPublications};
		return queryData;
	}
	
	public static void main(String a[]) throws FileNotFoundException, XMLStreamException {
	    dblpparser = new Dblpparser(new File("dblp.xml"));
        wwwrecords=dblpparser.resolveenititywithauthor();
        noofp = dblpparser.findauthorswithminkpublications(1);
		GUI gui=new GUI();
		gui.mainScreen();
	}
	public ArrayList<ArrayList<String>> getwwwrecord(){return wwwrecords;}
	public HashMap<String,Integer> getNoofp(){return noofp;}
}