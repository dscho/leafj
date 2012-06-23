//Class of user-defined sample description for a set of leafs
//includes methods for reading and writing defaults as well as getting user input
//Julin Maloof
//Nov 3,2011

//April 6, 2011
//Working to make user customizable input


import ij.*;
import ij.gui.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import java.awt.Button;

//check for configuration file
//if no configuration file, present a generic table
//if there is a configuration file, load it.
//ask if these defaults should be modified
//show editable table to modify
//save table

//phase 1: create and display a generic table
//phase 2: convert that table into the current dialog display
//I think this should be its own class.  The current class is for the actual sample descripton
//the other class can be for the table

//this thing should be rewritten with some kind of hash or equivalent.

public class sampleDescription {
	private GenericDialog gd = new GenericDialog("Sample Description");
	public Boolean saveRois;
	private OptionsTable ot;

	sampleDescription() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() { //note could also be invoke and wait?
				public void run () {
					//read defaults if they exist
					ot = new OptionsTable(null);
				}
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDescription(String filename) {
//		defaults[7] = filename;
		getInput();
	}
				
	public void getInput() {
		//allow user to define some inputs
		
		//set up and display dialog box
		gd.addMessage("Enter terms into relevant fields.  Leave non-applicable fields blank");
		
		for (int i = 0; i < ot.getNumberOfFields();i++){
			if (ot.getFieldValues(i).length==0) {
				gd.addStringField(ot.getFieldName(i),"",20);
			} else {
				gd.addChoice(ot.getFieldName(i),ot.getFieldValues(i), "");
			}
		}
			
//		gd.addCheckbox("Save Set, Dissected by, and Measured by, as defaults?",true);
//		gd.addCheckbox("Save Rois to file?",true);
		gd.setCancelLabel("Edit these options");
		gd.setOKLabel("Continue");
		gd.showDialog();
		
		if(gd.wasCanceled()) {
			gd.dispose();
			ot.editTable();
			gd = new GenericDialog("Sample Description");
			getInput();
		}
		
		saveRois = gd.getNextBoolean();
					
	}
	
	public String getFieldNames() {
		//returns field names as a tab-delimited String
		String s = new String();
//		for(String f : fieldNames) {
//			s = s + f + "\t";
//		}
		return s.trim();
	}
	
	public String getDescription() {
		//returns sample description as a tab-delimited String
		String s = new String();
//		for(String d : description) {
//			s = s + d + "\t";
//		}
		return s.trim();
	}
	
	public String getGtRep() {
		//returns gt + rep
//		return(description[2]+"_"+description[3]);
		return "";
	}
	
	public String getGtTrtRep() {
		//returns gt + trt + rep
//		return(description[2]+"_"+description[1]+"_"+description[3]);
		return "";
	}
		
	
}
