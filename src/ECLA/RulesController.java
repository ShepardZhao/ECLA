/**
 * 
 */
package ECLA;

import java.util.*;
import java.util.regex.*;

/**
 * This is the executive controller, that will do something logical algorithm
 * 
 * @author xzha4611
 *
 */
public class RulesController {
	private final String number_punct_Pattern = "[\\p{Alnum}\\p{Punct}]*";
	private final String birthday_Pattern = "^\\d{2}-\\d{2}-\\d{4}$";
	private final String email_Pattern = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})";
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
		if(MailString.isEmpty() || this.PatternCheck(this.email_Pattern, MailString)){
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
	
	
	 //filter /t
	 protected String FilterTab(String string){
		        String dest = "";
		        if (string!=null) {
		            Pattern p = Pattern.compile("\t");
		            Matcher m = p.matcher(string);
		            dest = m.replaceAll("");
		        }
		        return dest;
		    }
	 
	 
	 
	
	
	
}
