/**
 * 
 */
package ECLA;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.*;
/**
 * @This class is doing the file analysis
 * @author:xzh4611
 * @version:v1.0
 * @date:12/04/2014
 */
public class AnalysisECLB extends AnalysisController {
	/**
	 * Attributes
	 */
	private List<LinkedHashMap<String,List<String>>> borrowsectionList = new ArrayList<LinkedHashMap<String,List<String>>>();
	
	/**
	 * Methods
	 */


	/**
	 * Read BorrowFile section
	 */

	// read borrowfile to list
	 public void ReadBorrowfile(){ 
		 LinkedHashMap<String,List<String>> linkedHashMap = new LinkedHashMap<String,List<String>>(); 
		 List<LinkedHashMap<String,List<String>>> tempList = new ArrayList<LinkedHashMap<String,List<String>>>();
		 List<String> multilineAddress = new ArrayList<String>();
		 List<String> multilineborrowlist = new ArrayList<String>();
		 try { 
			 Scanner scanner;	
			 scanner = new Scanner(this.GetFileWithName());
			 int count =0;
			 boolean namecondition=true;
			 boolean birthdaycondition=true; 
			 while(scanner.hasNextLine()){
				 String content = scanner.nextLine();
				 //once there is not empty line
				 if(!content.isEmpty()){ 	
					 //if current line contained name field
					if(content.contains("name")){
						
						if(this.SingleLineConditionCheck("name", content)){
							namecondition =false;
						}
						else{
							namecondition=true;
							linkedHashMap.put("name",this.SingleLineProcess("name",content));
							//out.println(content);
						}
					
					}
					//if current line contained birthday field && name field has corrected format
					else if(content.contains("birthday") && namecondition){
						if(this.SingleLineConditionCheck("birthday", content)){
							
							birthdaycondition = false;
						}
						else{
							birthdaycondition =true;
							linkedHashMap.put("birthday",this.SingleLineProcess("birthday",content));

						}
					}
					//if current line contained phone field && name and birthday fields have corrected format
					else if(content.contains("phone") && namecondition  && birthdaycondition){
						if(!this.SingleLineConditionCheck("phone", content)){
							linkedHashMap.put("phone",this.PhoneSingleLineProcess("phone",content));
						}
					}
					//if current line contained email field && name and birthday fields have corrected format
					else if(content.contains("email") && namecondition && birthdaycondition){
						if(!this.SingleLineConditionCheck("email", content)){
							linkedHashMap.put("email",this.SingleLineProcess("email",content));
							}
					}
					//MultiLine touched
					//if current line contained ", " || contained string like booklist && name and birthday fields have corrected format
					else if((content.contains(", ") || content.contains("booklist")) && namecondition && birthdaycondition){
						linkedHashMap.put("booklist",MultipleLineProcess("booklist",content,multilineborrowlist));
						
						
					}
					//if current line contained string \*t string only && contained address && name && birthday have corrected format
					else if((this.MultipleAddressLineCheck(content) || content.contains("address")) && namecondition && birthdaycondition){
						linkedHashMap.put("address",MultipleLineProcess("address",content,multilineAddress));
					}
					//MultiLine end
				 }
				 
				 else{
					 if(linkedHashMap.containsKey("name") && linkedHashMap.containsKey("birthday")){
					 tempList.add(linkedHashMap);
	
					 borrowsectionList.addAll(tempList);
					 
					 tempList = new ArrayList<LinkedHashMap<String,List<String>>>();

					 linkedHashMap = new LinkedHashMap<String,List<String>>();
					 
					 multilineborrowlist = new ArrayList<String>();
					 
					 multilineAddress = new ArrayList<String>();
					 }
					 
					 }
			 }
			 scanner.close();
			 //this.Analysisborrowfilelist(TempsectionMap);
			 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exit(0);
			}
			
	 }
	 
	 

	 //condition check
	 private boolean SingleLineConditionCheck(String type,String string){
		 Scanner scanner = new Scanner(string);
		 boolean condition = false;
		 scanner.useDelimiter(type+"\t*");
		 while(scanner.hasNext()){
			 String getcontent = scanner.next();
			 if(type.equals("name")){
				 if(this.NameFieldCheck(getcontent)){
					 condition =true;
				 }
				 else{
					 condition =false;
			 	}
			 }
			 else if(type.equals("birthday")){
				 if(this.BirthdayFieldCheck(getcontent)){
					 condition =true;
				 }
				 else{
					 condition =false;
				 	}

			 }
			 else if(type.equals("phone")){
				 if(this.PhoneFieldCheck(getcontent)){
					 condition =true;
				 }
				 else{
					 condition =false;
				 	}
			 }
			 else if(type.equals("email")){
				 if(this.MailFieldCheck(getcontent)){
					 condition =true;
				 }
				 else{
					 condition =false;
				 	}
			 }
			 
		 }
		 scanner.close();
		 
		 return condition;
	 }

 
	 /**
	  * end
	  */
	 
	 
	 /**
	  * Return SectionMap
	  */
	 public List<LinkedHashMap<String,List<String>>>  GetsectionList(){
			return this.borrowsectionList;
		}
	 /**
	  * end
	  */
	 
	 
	 
	
}
