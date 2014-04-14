/**
 * 
 */
package ECLA;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.*;
/**
 * @This class is doing the file analysis
 * @author:xzh4611
 * @version:v1.0
 * @date:12/04/2014
 */
public class AnalysisController extends RulesController {
	/**
	 * Attributes
	 */
	File borrowfile,instructionfile;//file name
	LinkedHashMap<Integer,HashMap<String,List<String>>> sectionMap = new LinkedHashMap<Integer,HashMap<String,List<String>>>(); 
	
	List<String> borrowfilelist = new ArrayList<String>();

	LinkedList<HashMap<String,List<String>>> linklist = new LinkedList<HashMap<String,List<String>>>();
	/**
	 * Methods
	 */
	//Get borrowfile and instructionfile from class ECL
	public void SetInitial(String borrowfile, String instructionfile){
		this.borrowfile = new File(borrowfile);
		this.instructionfile = new File(instructionfile);
	}
	
	
	//Analysis interface
	public void AnalysisInterface(){
		//reads content from borrowfile 
		this.ReadBorrowfileToList();
		//analysis borrowfilelist
		this.Analysisborrowfilelist();
		
		
		exit(0);
	}
	

	// read borrowfile to list
	 private void ReadBorrowfileToList(){ 
		 Scanner scanner;	
		 try { 
			 scanner = new Scanner(this.borrowfile);	
			  
			 while(scanner.hasNextLine()){
				 borrowfilelist.add(scanner.nextLine()); 
			 }
		
			 scanner.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exit(0);
			}
			
	 }
	 
	 
	 //analysis borrowfile List
	 private void Analysisborrowfilelist(){
		 int count=0;
		 for(int index=0;index<this.borrowfilelist.size();index++){
			 String content = this.borrowfilelist.get(index);
			 	if(content.contains("name")){
			 		count++;
			 		linklist.add(this.SingleLineProcess(index, "name"));
			 	}
			 	else if(content.contains("birthday")){
			 		linklist.add(this.SingleLineProcess(index, "birthday"));
			 	}
			 	else if(content.contains("phone")){
			 		linklist.add(this.SingleLineProcess(index, "phone"));
			 	}
			 	else if(content.contains("email")){
			 		linklist.add(this.SingleLineProcess(index, "email"));
			 	}
			 	else if(content.contains("address")){
				 //out.println(this.borrowfilelist.get(index));
			 		linklist.add(this.MultipleLineProcess(index,"address"));
			 		index+=this.MultipleLineProcess(index,"address").size()-1;
				}
			 	else if(content.contains("booklist")){
			 		linklist.add(this.MultipleLineProcess(index,"booklist"));
			 		index+=this.MultipleLineProcess(index,"booklist").size()-1;
			 	}
			
		
		 }
		 
		 
		 //this.SetupSection();
		  
	 }
	 
	 
	 
	 //Set up section 
	 private void SetupSection(){
		for( int i=0; i<linklist.size(); i++ ) {
			sectionMap.put(i,linklist.get(i));
		}
	 }
	 
	 
	 
	 
	 //Single line process
	 private LinkedHashMap<String,List<String>> SingleLineProcess(int index,String string){
		 List<String> list = new ArrayList<String>();
		 LinkedHashMap<String,List<String>> fieldMap = new  LinkedHashMap<String,List<String>>();

		 Scanner singleLine = new Scanner (this.borrowfilelist.get(index));
		 singleLine.useDelimiter(string+"\t*");
		 while(singleLine.hasNext()){
			 String getstring = singleLine.next();
			 if(!getstring.equals(string)){
				 list.add(getstring);
			 }
		 }
		 singleLine.close();
		 
		 fieldMap.put(string, list);
		 
		 return fieldMap;
		 
		 
	 }
	 private LinkedHashMap<String,List<String>> MultipleLineProcess(int index,String string){
		 	List<String> list = new ArrayList<String>();
			LinkedHashMap<String,List<String>> fieldMap = new  LinkedHashMap<String,List<String>>();

		 	if(this.borrowfilelist.get(index).contains(string)){
		 		Scanner scanner = new Scanner(this.borrowfilelist.get(index));
		 		scanner.useDelimiter(string+"\t*");
		 		while(scanner.hasNext()){
		 			String getstring = scanner.next();
		 			if(!getstring.equals(string)){
		 				list.add(getstring);
		 			}
		 		}
	 			scanner.close();
		 	}
		
		 for(int tempindex=index;tempindex<this.ReturnSizeOfMultopleLine(index);tempindex++){
			 if(!this.MultipleLineConditionCheck(this.borrowfilelist.get(tempindex))){
				 list.add(this.FilterTab(this.borrowfilelist.get(tempindex)));
			 }
		
		 }
		 
		 fieldMap.put(string, list);

		 
		 return fieldMap;
		 
	 }
	 
	 
	 
	
	 
	 
	 //return available length of multiple line
	 private int ReturnSizeOfMultopleLine(int index){
		 int tempindex;
		 for(tempindex=index+1;tempindex<this.borrowfilelist.size();tempindex++){
			 if(this.MultipleLineConditionCheck(this.borrowfilelist.get(tempindex)) || this.borrowfilelist.get(tempindex).equals("")){
				break; 
			 }
		 }
		 return tempindex;
	 }
	 
	
	 
	 
	 
	
}
