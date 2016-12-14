package com.company;
import javax.swing.*;

public class MyComboBox extends DefaultComboBoxModel<String>  {
	static final long serialVersionUID=1L;
	public MyComboBox(){ }
	public MyComboBox(String [] array)
	{	super(array);	}
	
	@Override 	public void setSelectedItem(Object o)
	{
		if(o.toString().startsWith("-"))
			return;
		else
			super.setSelectedItem(o);
	}
}
