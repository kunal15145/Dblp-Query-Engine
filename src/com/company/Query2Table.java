package com.company;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class Query2Table extends AbstractTableModel {
	static final long serialVersionUID=1L;
	
	private String[] colNames={"S.No.","AUTHORS"};
	private Object[][] data;
	
	public Query2Table(Object[][] data)
	{this.data=data;}
	 
	public int getColumnCount()	
	{
		return colNames.length;
	}
	
	public int getRowCount()
	{
		return data.length;
	}
	
	public String getColumnName(int col)
	{
		return colNames[col];
	}
	
	public Object getValueAt(int row,int col)
	{
		return data[row][col];
	}
	
	public static void  cellResizer(JTable table){
		
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(679);	
		}
}
	
