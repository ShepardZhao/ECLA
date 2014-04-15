/**
 * 
 */
package ECLA;

import java.io.File;
import java.util.*;
import java.util.regex.*;

/**
 * This is the System controller, that will do something logical algorithms
 * 
 * @author xzha4611
 *
 */
public class AnalysisController {
	private final String number_punct_Pattern = "[\\p{Digit}|\\p{Punct}]";
	private final String birthday_Pattern = "^\\d{2}-\\d{2}-\\d{4}$";
	private final String email_Pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private final String phone_Pattern = "[\\d+]";
	private File filename;
	
	
	/**
	 * Set Initial file path
	 */
	protected void SetInitial(String filename){
		this.filename = new File(filename);
	}
	/**
	 * end
	 */
	
	
	/**
	 * Get file 
	 */
	protected File GetFileWithName(){
		return this.filename;
	}
	
	/**
	 * end
	 */
	
	
	
	
	
	/**
	 * pattern check
	 */
	
	private boolean PatternCheck(String patternString, String namevalue){
	    Pattern pattern = Pattern.compile(patternString);
	    Matcher m = pattern.matcher(namevalue);
	    boolean find = false;
	    while(m.find()){
	    	find = true;
	    }
	    
	    if(find==true){
	    	return true;
	    }
	    else{
	    return false;
	    }
	}
	
	/**
	 * end
	 */
	

	/**
	 * Name field check
	 */
	//Name field checked only
	protected boolean NameFieldCheck(String namevalue){
		
		if(namevalue.isEmpty() || this.PatternCheck(this.number_punct_Pattern,namevalue)){
			return true;
		}
		else{
			return false;
		}
			
	}
	 	
	
	/**
	 * end
	 */
	
	
	/**
	 * Birthday field check 
	 */
	protected boolean BirthdayFieldCheck(String birthdayString){
		
		if(birthdayString.isEmpty() || !this.PatternCheck(this.birthday_Pattern,birthdayString)){
			return true;
		}
		else{
			return false;
		}	
	}
	
	/**
	 * end
	 */
	
	
	/**
	 * Email field check
	 */
	protected boolean MailFieldCheck(String MailString){
		if(MailString.isEmpty() || !this.PatternCheck(this.email_Pattern, MailString)){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * end
	 */
	
	
	/**
	 * Phone field check
	 */
	protected boolean PhoneFieldCheck(String PhoneInteger){
		if(PhoneInteger.isEmpty() || !this.PatternCheck(this.phone_Pattern, PhoneInteger)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 * Multiple line condition check
	 */
	protected boolean MultipleLineConditionCheck(String getstring){
		String[] keywords ={"name","address","birthday","email","phone","booklist"};
		boolean condition = false;
		for(String index : keywords){
			if(getstring.contains(index)){
				condition = true;
			}
		}
		return condition;
		
		
	}
	
	
	/**
	 * end
	 */
	
	
	
	 /**
	  * filter /t
	  * @param string
	  * @return
	  */
	 protected String FilterTab(String string){
		        String dest = "";
		        if (string!=null) {
		            Pattern p = Pattern.compile("\t");
		            Matcher m = p.matcher(string);
		            dest = m.replaceAll("");
		        }
		        return dest;
		    }
	 
	 /**
	  * end
	  */
	 
	
	 /**
	  * Line processes
	  */
	 //multiple line check
	 protected boolean MultipleAddressLineCheck(String string){
		 String getstring =this.FilterTab(string);
		 
		 Pattern p = Pattern.compile("^[A-Za-z]+$");
		 Matcher m = p.matcher(getstring);
		 boolean condition =false;
		 while(m.find()){
			 condition = true;
		 }
		 return condition;
		 
	 }
	 
	 
	 
	 
	 //Single line process
	 protected List<String> SingleLineProcess(String keyname,String string){
		 List<String> list = new ArrayList<String>();

		 Scanner singleLine = new Scanner (string);
		 singleLine.useDelimiter(keyname);
		 while(singleLine.hasNext()){
			 String getstring = singleLine.next();
			 if(!getstring.equals(string)){
				 list.add(getstring);
			 }
		 }
		 singleLine.close();

		 return list;


	 }
	 
	 
	 //MultiList process
	 protected List<String> MultipleLineProcess(String type, String string,List<String> multiline){
	 		Scanner scanner = new Scanner(string);

		 	if(string.contains(type)){
		 		scanner.useDelimiter(type);
		 		while(scanner.hasNext()){
		 			String getstring = scanner.next();
		 			if(!getstring.equals(string)){
		 				multiline.add(getstring);
		 			}
		 		}
	 			scanner.close();
		 	}else{
 				multiline.add(string);

		 	}
		 return multiline;

	 }
	 /**
	  * end
	  */
	 

	 
	 
	 
	 
	 
	
	
}
