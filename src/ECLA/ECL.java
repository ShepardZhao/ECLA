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
	private AnalysisECLB anaysisBorrow;
	private AnalysisECLI anaysisInstruction;
	private ExecutiveECL executiveLogical;
	
	//constructor
	ECL(String borrowfile, String instructionfile, String outputfile, String reportfile){
		this.borrowfile = borrowfile;
		this.instructionfile = instructionfile;
		this.outputfile = outputfile;
		this.reportfile = reportfile;
		this.anaysisBorrow = new AnalysisECLB();
		this.anaysisInstruction = new AnalysisECLI();
		this.executiveLogical = new ExecutiveECL();
	}
	
	//Get AnalysisECLI
	public AnalysisECLI GetAnalysisInstruction(){
		return this.anaysisInstruction;
	}
	
	//Get AnalysisECLB
	public AnalysisECLB GetAnalysisBorrow(){
		return this.anaysisBorrow;
	}
	
	//Get ExecutiveECL
	public ExecutiveECL GetExecutiveECL(){
		return this.executiveLogical;
	}
	
	//Get borrow file
	public String GetBorrowfile(){return this.borrowfile;}
	
	//Get instruction file
	public String GetInstructionfile(){return this.instructionfile;}
	
	//Get output file
	public String Getoutputfile(){return this.outputfile;}
	
	//Get report file
	public String Getreportfile(){return this.reportfile;}
	
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
	/**
	 * Program interface
	 */
		
		/**
		 * Condition check
		 */
		if(ArgsCheck(args)){
			// Object that reads the arguments from command line
			// args[0] -> borrowfile 
			// args[1] -> instructionfile
			// args[2] -> outputfile
			// args[3] -> reportfile
			ECL ECLSystem = new ECL(args[0],args[1],args[2],args[3]);
			
			/**
			 * Pass file to analysis
			 */
			//Pass the borrowfile to class AnalysisECLB 	
			ECLSystem.GetAnalysisBorrow().SetInitial(ECLSystem.GetBorrowfile());
			
			//borrow analysis interface
			ECLSystem.GetAnalysisBorrow().ReadBorrowfile();
			
			//Pass the instruction file to class 
			ECLSystem.GetAnalysisInstruction().SetInitial(ECLSystem.GetInstructionfile());
			
			//instruction analysis interface
			
			ECLSystem.GetAnalysisInstruction().ReadInstructionFile();
			
			/**
			 * end
			 */
			
			
			/**
			 * Fetching the BorrowHashMap and InstructionHashMap are to process.
			 * and also passed the output file and report file
			 */
			
			ECLSystem.GetExecutiveECL().SetExecutiveInital(ECLSystem.GetAnalysisBorrow().GetsectionList(), ECLSystem.GetAnalysisInstruction().GetInstructionList(),ECLSystem.Getoutputfile(),ECLSystem.Getreportfile());
			ECLSystem.GetExecutiveECL().Executing();	
			
			/**
			 * end
			 */
			
			
		}else{
			out.println("You have to input right arguments!");
			exit(0);
		}
		
		/**
		 * end
		 */
		
		
	/**
	 * end
	 */
	}

}
