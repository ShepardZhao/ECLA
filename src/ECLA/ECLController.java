/**
 * This is the System controller. It will do something logical algorithms
 * This is an abstract class
 * This class is the super of Classes of AnalysisECLB, AnalysisECLI and ExecutiveECL
 * @author Xun Zhao
 */
package ECLA;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;


public abstract class ECLController {
	private final String[] keywords ={"name","address","birthday","email","phone","booklist",", "};
	private final String number_punct_Pattern = "[\\p{Digit}|\\p{Punct}]";
	private final String date_Pattern = "^(0?[1-9]|[12][0-9]|3[01])[- /.](0?[1-9]|1[012])[- /.](19|20)\\d\\d$";
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
		String getstring = this.CovertDateToExactly(this.FilterSpaceTabToEmpty(birthdayString));
		if(this.PatternCheck(this.date_Pattern,getstring)){
			return true;
		}
		else{
			return false;
		}	
		
		
	}
	
	//convert date form 3-09-1999 to 03-09-1999
	protected String CovertDateToExactly(String date){
            if (date.charAt(1) == '-') date = "0" + date;
            if (date.charAt(4) == '-') date = date.substring(0, 3) + "0" + date.substring(3);
            return date;


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
		//in this case the borrowed date has same format just like the birthday date
            //if the date is the format d-mm-yyyy or dd-m-yyyy then we think that is right
            if(this.PatternCheck(this.date_Pattern, getstring)){
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
	protected boolean KeywordCheck(String getstring){
		boolean condition = false;
		for(String index : this.keywords){
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
		 Scanner Scanner =new Scanner(string);
         Scanner.useDelimiter(", ");
		 while(Scanner.hasNext()){
			 String  getnewString = this.FilterTab(Scanner.next());

			 if(this.BooklistISBNCheck(getnewString) || this.BooklistDateCheck(getnewString)){
				 condition =true;
			 }

			
		 }

         Scanner.close();
         return condition;
		 
	 }
	 
	 
	 
	 //phone process
	protected List<String> PhoneSingleLineProcess(String string){
		 List<String> list = new ArrayList<String>();
		 Scanner singleLine = new Scanner (string);
		 while(singleLine.hasNext()){
			 String getstring = singleLine.next();
				 list.add(this.phoneString(getstring));
			 
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
	 protected List<String> SingleLineProcess(String string){
		 List<String> list = new ArrayList<String>();
		 Scanner singleLine = new Scanner (string);
		 while(singleLine.hasNext()){
			 String getstring = singleLine.next();
				 
				 list.add(getstring);
			 
		 }
		 singleLine.close();

		 List<String> newlist = new ArrayList<String>();

		 for(String item : list){
			 if(!item.isEmpty()){
				 newlist.add(item);
			 }
		 }
		 		 
		 return newlist;
	 }
	 
	 
	 //MultiList address process
	 protected List<String> MultipleAddressProcess(String type, String string,List<String> multiline){
	 		 Scanner scanner = new Scanner(string);
	 		 
			 	if(string.contains(type)){

			 		scanner.useDelimiter(type);
			 		while(scanner.hasNext()){
			 			String getstring = scanner.next();
			 			String tempaddress = this.FilterSpaceTabToOneSpace(getstring);
			 			String address =  this.FilterSpaceTabToOneSpaceInBeginning(tempaddress);
			 			String finalladdress= address.replaceAll(",\\s+|,\\t+",", ");
			 			
			 			if(!finalladdress.isEmpty()){
				 			multiline.add(finalladdress);

			 			}	
			 			
			 		}
		 			scanner.close();
			 	}else{
			 		
		 			String temp = this.FilterSpaceTabToOneSpace(string);
		 			String tempaddress = this.FilterSpaceTabToOneSpaceInBeginning(temp);
		 			String finaltemp = tempaddress.replaceAll(",\\s+|,\\t+", ", ");

	 				multiline.add(finaltemp);
		 			
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

		 				multiline.add(this.ConvertBoolListDate(this.FilterTab(getstring)));
		 			
		 		}
	 			scanner.close();
		 	}else{

 				multiline.add(this.ConvertBoolListDate(this.FilterTab(string)));
		 	}
 	

	 return multiline;

 }


	 /**
	  * end
	  */


    /**
     * replace date such as from d-m-yyyy to dd-mm-yyyy
     */

    private String ConvertBoolListDate(String string){
       Scanner scanner = new Scanner(string);
        String date = null;
        scanner.useDelimiter(", ");
        while (scanner.hasNext()){
            String getStirng = scanner.next();
            if(this.BooklistDateCheck(getStirng)){
                date = getStirng;
            }

        }
    if(date!=null) {
        return string.replaceAll(date, this.CovertDateToExactly(date));
    }
    else{
        return string;
    }

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
	 
	 
	 /**
	  * name re-build
	  */
	 
	 protected List<String> ReturnNameList(String namelist){
		 List<String> newlist = new ArrayList<String>();
		 newlist.add(this.FilterSpaceTabToOneSpaceInBeginning(namelist));
		 return newlist;
		 
		 
	 }	
	 
	 
	 /**
	  * end
	  */
	 
	 /**
	  * Filter \t+ or \s+ in anyplace
	  */
	 
	 protected String FilterSpaceTabToEmpty(String getstring){
		 return getstring.replaceAll("\\t+|\\s+", "");
	 }
	 
	 /**
	  * end
	  */
	 
	 /**
	  * Filter \t+ or \s+ in beginning
	  */
	 
	 protected String FilterSpaceTabToOneSpaceInBeginning(String getstring){
		 return getstring.replaceAll("^\\t+|^\\s+", "");
	 }
	 
	 
	 /**
	  * end
	  */
	 
	 /**
	  * Filter \t+ or \s+ 
	  * making all m-space or m-tab to be one space
	  */
	 protected String FilterSpaceTabToOneSpace(String getstring){
		 return getstring.replaceAll("\\t+|\\s+", " ");
	 }
	 
	 /**
	  * end
	  */
	 
	 /**
	  * Filter keyword and rest tabs or spaces off
	  */
	 protected String FilterKeywordAndRestSpaceOrTab(String keyword, String string){
		 return string.replaceAll("^"+keyword+"\\t+|"+keyword+"\\s+", "");
	 }
	 
	
	
}
