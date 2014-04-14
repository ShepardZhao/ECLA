package ECLA;

/**
 * The program is a Electronic Community Library (ECL) Management System

 * @author xzha4611
 * Starts from 12/04/2014
 */


import static java.lang.System.*;

import java.util.*;


public class ECL {

	//attributes 
	private String borrowfile,instructionfile,outputfile,reportfile;
	private AnalysisController anaysisController;
	private ECLG eclGenerator;
	
	//constructor
	ECL(String borrowfile, String instructionfile, String outputfile, String reportfile,AnalysisController anaysisController,ECLG eclGenerator){
		this.borrowfile = borrowfile;
		this.instructionfile = instructionfile;
		this.outputfile = outputfile;
		this.reportfile = reportfile;
		this.anaysisController = anaysisController;
		this.eclGenerator = eclGenerator;
	}
	
	//Get ECLG
	public ECLG GetEclGenerator(){
		return this.eclGenerator;
	}
	
	//Get AnaysisController
	public AnalysisController GetAnaysisController(){
		return this.anaysisController;
	}
	//Get borrow file
	public String GetBorrowfile(){return this.borrowfile;}
	
	//Get instruction file
	public String GetInstructionfile(){return this.instructionfile;}
	
	//Get output file
	public String outputfile(){return this.outputfile;}
	
	//Get report file
	public String reportfile(){return this.reportfile;}
	
	//Arguments detection
	static boolean ArgsCheck(String[] getargs){
		if (getargs.length<4){
			return false;
		}
		else{
			return true;
		}
		
		
		
	}
	
	//main function
	public static void main(String[] args) {
		
		if(ArgsCheck(args)){
			// Object that reads the arguments from command line
			// args[0] -> borrowfile 
			// args[1] -> instructionfile
			// args[2] -> outputfile
			// args[3] -> reportfile
			ECL ECLSystem = new ECL(args[0],args[1],args[2],args[3],new AnalysisController(),new ECLG());
			//pass the borrowfile to class AnalysisController 	
			ECLSystem.anaysisController.SetInitial(args[0], args[1]);
			//File analysis interface
			ECLSystem.anaysisController.AnalysisInterface();
			
			
			
			
		}else{
			out.println("You have to input right arguments!");
			exit(0);
		}
		
		
	}

}
