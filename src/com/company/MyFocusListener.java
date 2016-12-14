package com.company;
import java.awt.event.*;
import javax.swing.*;

public class MyFocusListener implements FocusListener {
	JTextField temp;
	public void focusLost(FocusEvent e)
	{
		temp=(JTextField)e.getSource();
		if(temp.getText().isEmpty())
			temp.setText("   YYYY   ");
	}
	
	public void focusGained(FocusEvent e)
	{
		temp=(JTextField)e.getSource();
		if(temp.getText().equals((String)"   YYYY   "))
		temp.setText("");	
	}
}
