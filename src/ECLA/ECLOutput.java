/**
 * 
 */
package ECLA;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import static java.lang.System.*;

/**
 * @author zhaoxun321
 *
 */
public class ECLOutput extends ECLGenerateController{
	/**
	 * Attributes
	 */
	private File outputfile;
	/**
	 * end
	 */
	
	
	/**
	 * Constructor
	 */
	
	
	ECLOutput(String outputfile){
		this.outputfile = new File(outputfile);
	}
	
	/**
	 * end
	 */

	/**
	 * Generate output file
	 */
	public void  GenerateOutputFile(List<LinkedHashMap<String,List<String>>> modifiedborrowlist){
		try {
			PrintWriter writer = new PrintWriter(this.outputfile,"UTF-8");
			for (int index=0;index<modifiedborrowlist.size();index++){
				for(Entry<String, List<String>> entry : modifiedborrowlist.get(index).entrySet()){
					String getkey = entry.getKey();
					List<String> getlist = entry.getValue();
				
					if(getkey.equals("name")){
						writer.print(getkey);
						this.WriteToSingleLine(getkey,getlist,writer);
					}
					else if(getkey.equals("birthday")){
						writer.print(getkey);
						this.WriteToSingleLine(getkey,getlist,writer);

					}
					else if(getkey.equals("phone")){
						writer.print(getkey);
						this.WriteToSingleLine(getkey,getlist,writer);
					}
					else if(getkey.equals("email")){
						writer.print(getkey);
						this.WriteToSingleLine(getkey,getlist,writer);
					}
					else if(getkey.equals("address")){
						writer.print(getkey);
						this.WriteToMultiple(getkey,getlist,writer);
					}
					else if(getkey.equals("booklist")){
						writer.print(getkey);
						this.WriteToMultiple(getkey,getlist,writer);
					}
				
				
				}
				writer.println();
				
			}
			writer.close();
			
			
			
			
		} catch (FileNotFoundException e) {
			out.println(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * end
	 */
	
	
	
}
