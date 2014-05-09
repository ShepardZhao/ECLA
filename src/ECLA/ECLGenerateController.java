/**
 * This is an abstract class
 * This is the Generator class that is super class of ECLOutput and class of ECLReport  
 * @author zhaoxun321
 */
package ECLA;
import java.io.PrintWriter;

import java.util.*;
public abstract class ECLGenerateController {
	/**
	 * write to single line
	 */
	protected void WriteToSingleLine(String keyname,List<String> getvaluelist, PrintWriter writer){
		if(keyname.equals("birthday")){
			for(String string: getvaluelist){
				writer.println("\t\t\t"+string);
			}
		}
		else{
			for(String string: getvaluelist){
				writer.println("\t\t\t\t"+string);
			}
		}
		
		
	}
	
	/**
	 * end
	 */
	
	
	
	/**
	 * write to multiple line
	 */
	protected void WriteToMultiple(String getkey,List<String> getvaluelist,PrintWriter writer){
		if(getkey.equals("address")){
			writer.print("\t\t\t\t");
			for(String item:getvaluelist){
				writer.print(item);
			}
			writer.println();
		}
		else if(getkey.equals("booklist")){
			for(int index=0;index<getvaluelist.size();index++){
				if(index==0){
					writer.println("\t\t\t"+getvaluelist.get(index));
				}
				else{
					writer.println("\t\t\t\t\t"+getvaluelist.get(index));
				}
			}
			
			
		}
	}
	
	/**
	 * end
	 */
	
	
	
	
	
	
	
}
