/**
 * This file is generating the report file only
 * @author Xun zhao
 */
package ECLA;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
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
						writer.println(this.GenerateFooter()+"\n\n\n\n");

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
	 * Generate footer	
	 * @return
	 */
	private String GenerateFooter(){
		
		return "--- End of query ---";
	}	
		
	/**
	 * end	
	 */
	
}
