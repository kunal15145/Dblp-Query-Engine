package com.company;

import javax.swing.table.AbstractTableModel;
import javax.swing.JTable;
import javax.swing.table.*;
import java.awt.*;

public class Query1Table extends AbstractTableModel {
	static final long serialVersionUID=1L;
	
	private String[] colNames={"S.No.","AUTHORS","TITLE","PAGES","YEAR","VOLUME","BOOKTITLE / JOURNAL","URL"};
	private Object[][] data;
	
	public Query1Table(Object[][] data)
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
		 
		for (int column = 0; column < table.getColumnCount(); column++)
		{
		    TableColumn tableColumn = table.getColumnModel().getColumn(column);
		    
		    int preferredWidth=tableColumn.getPreferredWidth();
		    int maxWidth = tableColumn.getMaxWidth();
		 
		    for (int row = 0; row < table.getRowCount(); row++)
		    {
		        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		        Component c = table.prepareRenderer(cellRenderer, row, column);

		        int width = c.getPreferredSize().width + table.getIntercellSpacing().width+40;	//40 is the extra  blank space in the cell so that table does not look crammed
		        preferredWidth = Math.max(preferredWidth, width);
		 
		        //  We've exceeded the maximum width, no need to check other rows
		        if (preferredWidth >= maxWidth)
		        {
		            preferredWidth = maxWidth;
		            break;
		        }
		    }
		    tableColumn.setPreferredWidth( preferredWidth );
		}
	}
	
}