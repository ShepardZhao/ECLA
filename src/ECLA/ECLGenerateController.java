/**
 * This is an abstract class
 * This is the Generator class that is super class of ECLOutput and class of ECLReport  
 * @author zhaoxun321
 */
package ECLA;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
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
	
	
	
	/**
	 * Generate query section key
	 * @param object
	 * @return
	 */
	protected String GenerateQueryHeaderKey(LinkedHashMap<String, List<String>> object){
		String wholeString = "";
		for(Entry<String, List<String>> entry : object.entrySet()){
		    List<String> value = entry.getValue();
		    wholeString+="; "+entry.getKey()+" "+ value;
		}
		
		String generateString = "--- query"+ wholeString + " ---";
		String finaltitle = generateString.replaceAll("--- query; ","--- query ").replaceAll("\\[|\\]", "").replace(", ", "; ");
		return finaltitle;
	}	
	


	/**
	 * end
	 */
	
	
	
	/**
	 * Generate footer	
	 * @return
	 */
	protected String GenerateQueryFooter(){
		
		return "--- End of query ---";
	}	
		
	/**
	 * end	
	 */
	
	
	
}
