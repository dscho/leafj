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
//	private String[] fieldNames = {"Set",
//		"Treatment",
//		"Genotype",
//		"Rep.",
//		"FT_day",
//		"FT_month",
//		"FT_year",
//		"Image_file",
//		"Dissected_by",
//		"Measured_by"};
//	private String[] description = new String[fieldNames.length];
//	private int[] defaultsIndex = {0,8,9}; //names of the fields that we want to save as defaults
//	private String[] defaults = new String[fieldNames.length];
//	private String filepath = "leaf_measure_defaults.txt";
//	private File defaultFile = new File(filepath);
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
			
			
//		for (int i = 0; i < fieldNames.length; i++) {
//			if (fieldNames[i].equals("Set")) {
//				gd.addChoice(fieldNames[i],UserDefaults.set,defaults[i]);
//				continue;
//			}
//			if (fieldNames[i].equals("Treatment")) {
//				gd.addChoice(fieldNames[i],UserDefaults.treatment,defaults[i]);
//				continue;
//			}
//			if (fieldNames[i].equals("Genotype") ){
//				gd.addChoice(fieldNames[i],UserDefaults.genotype,"");
//				continue;
//			}
//			if (fieldNames[i].equals("Rep.")) {
//				gd.addChoice(fieldNames[i],UserDefaults.replicate,"");
//				continue;
//			}
//			if (fieldNames[i].equals("FT_day") ||
//				fieldNames[i].equals("FT_month") || 
//				fieldNames[i].equals("FT_year")) {
//					gd.addStringField(fieldNames[i],"",2);
//					continue;
//				}
//			if (fieldNames[i].equals("Image_file") ||
//				fieldNames[i].equals("Dissected_by") || 
//				fieldNames[i].equals("Measured_by")) {
//					gd.addStringField(fieldNames[i],defaults[i],20);
//					continue;
//				}
//		}
		gd.addCheckbox("Save Set, Dissected by, and Measured by, as defaults?",true);
		gd.addCheckbox("Save Rois to file?",true);
		gd.setCancelLabel("Edit these options");
		gd.setOKLabel("Continue");
		gd.showDialog();
		
		if(gd.wasCanceled()) {
			ot.editTable();
		}
		
//		//populate array with contents of dialog box
//		for (int i = 0; i < fieldNames.length; i++) {
//			//ugly!!
//			switch(i) {
//			case 0:
//			case 1:
//			case 2:
//			case 3: description[i] = gd.getNextChoice(); break;
//			case 4:
//			case 5:
//			case 6:
//			case 7:
//			case 8:
//			case 9: description[i] = gd.getNextString(); break;
//			}
//		}
//		
		//write defaults to text file if requested.
//		if (gd.getNextBoolean()) try { //true if save defaults box is checked
//			FileWriter fw = new FileWriter(defaultFile);
//			PrintWriter pw = new PrintWriter(fw);
//			for (int i : defaultsIndex) {
//				pw.println(description[i]);
//			}
//			fw.close();
//		} catch (IOException e) {
//			IJ.showMessage("IO error writing defaults");
//		}
		
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
