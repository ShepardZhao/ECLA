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
public class ECLController {
	private final String number_punct_Pattern = "[\\p{Digit}|\\p{Punct}]";
	private final String birthday_Pattern = "^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((18|19|20|21)\\d\\d)";
	private final String email_Pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private final String phone_Pattern = "[\\d+]";
	private final String isbn_Pattern = "^[0-9]{13}$";
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
	    Matcher m = pattern.matcher(this.FilterTab(namevalue));
	    boolean find = false;
	    while(m.find()){
	    	//if found
	    	find = true;
	    }
	    
	    //if found then return true
	    if(find==true){
	    	return true;
	    }
	    // if not found then reutrn false
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
		//if current name contains number or punctuation then reutrn false
		if(this.PatternCheck(this.number_punct_Pattern,namevalue)){
			return false;
		}
		else{
			return true;
		}
			
	}
	 	
	
	/**
	 * end
	 */
	
	
	/**
	 * Birthday field check 
	 */
	protected boolean BirthdayFieldCheck(String birthdayString){
		
		if(this.PatternCheck(this.birthday_Pattern,birthdayString)){
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
		if(this.PatternCheck(this.email_Pattern, MailString)){
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
		if(this.PatternCheck(this.phone_Pattern, PhoneInteger)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * book list single field process
	 */
	
	protected boolean BooklistISBNCheck(String getisbn){
		if(this.PatternCheck(this.isbn_Pattern, getisbn)){
			return true;
		}
		else{
			return false;
		}
	}
	
	protected boolean BooklistDateCheck(String getstring){
		//in this case the birthday date has same format just like the borrowed date
		if(this.PatternCheck(this.birthday_Pattern, getstring)){
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
	 * Multiple line condition check
	 */
	protected boolean MultipleLineConditionCheck(String getstring){
		String[] keywords ={"name","address","birthday","email","phone","booklist",", "};
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
	  * filter space and tab
	  * @param string
	  * @return
	  */
	 protected String FilterTab(String string){
		        String dest = "";
		        if (string!=null) {
		            Pattern p = Pattern.compile("^ |\t");
		            Matcher m = p.matcher(string);
		            dest = m.replaceAll("");
		        }
		        return dest;
		    }
	 
	 
	 
	 /**
	  * end
	  */
	 
	
	 /**
	  * Multiple line  check
	  */
	 //multiple line check
	 protected boolean MultipleLineCheck(String string){
		 
		 boolean condition =false;
		 Scanner addressScanner =new Scanner(string);
		 addressScanner.useDelimiter(", ");
		 while(addressScanner.hasNext()){
			 String  getnewString = this.FilterTab(addressScanner.next());
			 
			 if(this.BooklistISBNCheck(getnewString) || this.BooklistDateCheck(getnewString)){
				 condition =true;
			 }
			
		 }
			 
		 addressScanner.close();
		
		 return condition;
		 
	 }
	 
	 
	 
	 //phone process
	protected List<String> PhoneSingleLineProcess(String keyname, String string){
		 List<String> list = new ArrayList<String>();
		 Scanner singleLine = new Scanner (string);
		 singleLine.useDelimiter(keyname);
		 while(singleLine.hasNext()){
			 String getstring = singleLine.next();
			 if(!getstring.equals(string)){
				 
				 list.add(this.phoneString(getstring));
			 }
		 }
		 singleLine.close();

	
		 
		 return list;
	 }
	 
	//return the phone string that contains space
	private String phoneString(String phonestring){
		
		String string = this.FilterTab(phonestring);
		String non_zero = string.replaceAll("^0*","");
		
		
		return non_zero;
		
	}
	 
	 
	 //Single line process
	 protected List<String> SingleLineProcess(String keyname,String string){
		 List<String> list = new ArrayList<String>();
		 Scanner singleLine = new Scanner (string);
		 singleLine.useDelimiter(keyname);
		 while(singleLine.hasNext()){
			 String getstring = singleLine.next();
				 
				 list.add(this.FilterTab(getstring).replaceAll("\\s+", " "));
			 
		 }
		 singleLine.close();

		 return list;


	 }
	 
	 
	 //MultiList address process
	 protected List<String> MultipleAddressProcess(String type, String string,List<String> multiline){
	 		 Scanner scanner = new Scanner(string);
	 		 
			 	if(string.contains(type)){

			 		scanner.useDelimiter(type);
			 		while(scanner.hasNext()){
			 			String getstring = scanner.next();
			 
			 				multiline.add(this.FilterTab(getstring).replaceAll("^\\s+|\\s+|\\t+", " "));
			 			
			 		}
		 			scanner.close();
			 	}else{
	 				multiline.add(this.FilterTab(string).replaceAll("\\s+", " "));
			 	}
	 	

		 return multiline;

	 }
	 
	 
	 // MultiList booklist process
	 protected List<String> MultipleBooklistProcess(String type, String string,List<String> multiline){
 		 Scanner scanner = new Scanner(string);

		 	if(string.contains(type)){
		 		scanner.useDelimiter(type);
		 		while(scanner.hasNext()){
		 			String getstring = scanner.next();
		 				multiline.add(this.FilterTab(getstring));
		 			
		 		}
	 			scanner.close();
		 	}else{
 				multiline.add(this.FilterTab(string));
		 	}
 	

	 return multiline;

 }
	 
	 /**
	  * end
	  */
	 

	 /**
	  * Re-ranged borrowlist
	  */
	 protected LinkedHashMap<String,List<String>>  ReRangedBorrowlist(LinkedHashMap<String,List<String>> linkedHashMap){
		 LinkedHashMap<String,List<String>> templinkedlist = new LinkedHashMap<String,List<String>> ();
			 if(linkedHashMap.containsKey("name")){
				 templinkedlist.put("name",linkedHashMap.get("name"));
			 }
			 if(linkedHashMap.containsKey("birthday")){
				 templinkedlist.put("birthday",linkedHashMap.get("birthday"));
			 }
			 if(linkedHashMap.containsKey("phone")){
				 templinkedlist.put("phone",linkedHashMap.get("phone"));
			 }
			 if(linkedHashMap.containsKey("email")){
				 templinkedlist.put("email",linkedHashMap.get("email"));
			 }
			 if(linkedHashMap.containsKey("address")){
				 templinkedlist.put("address",linkedHashMap.get("address"));
			 }
			 if(linkedHashMap.containsKey("booklist")){
				 templinkedlist.put("booklist",linkedHashMap.get("booklist"));
			 }
			
		return templinkedlist; 
	 }
		 
	 /**
	  * end
	  */
	
	 /**
	  * convert address from list to string
	  */
	 protected String ConvertListToString(List<String> list){
		 String listString = "";

		 for (String s : list)
		 {
		     listString += s;
		 }
		 
		 return listString;
		 
	 }
	 
	 
	 
	 /**
	  * end
	  */
	 

	
	
}
