/**
 * This file is generating the report file only
 * @author Xun zhao
 */
package ECLA;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.*;
public class ECLReport extends ECLGenerateController{
	/**
	 * Attribute
	 */
		private File report;

	/**
	 * end
	 */
	
	
	
	/**
	 * Constructor
	 */
		ECLReport(String report){
			this.report = new File(report);
		}
		
	/**
	 * end
	 */
	
	/**
	 * Write to report file
	 */
		public void WriteToReportFile(List<LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>>> object){
		if	(!object.isEmpty()){
			try {
					PrintWriter writer;
					writer = new PrintWriter(new BufferedWriter(new FileWriter(this.report,true)));
					int size = object.size();
				  for(int i=0;i<object.size();i++){	
					for (Entry<String, List<LinkedHashMap<String, List<String>>>> entryroot : object.get(i).entrySet()){
						writer.println(entryroot.getKey());

					  for (int index=0;index<entryroot.getValue().size();index++){
							writer.println();

						for(Entry<String, List<String>> entry : entryroot.getValue().get(index).entrySet()){
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
						
						}
					  }
					
						writer.println();
						writer.println(this.GenerateQueryFooter());
						if(i+1<size){
							writer.println("\n\n\n");
						}
					}
					writer.close();
					
								
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
