/**
 * This is a executive class that will deal between borrow file and instruction file
 */
package ECLA;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
/**
 * This class is only containing the executing function 
 * @author zhaoxun321
 *
 */
public class ExecutiveECL extends ECLController {
	/**
	 * Attributes
	 */
	private List<LinkedHashMap<String,List<String>>> borrowlist;
	private List<LinkedHashMap<String,LinkedHashMap<String,List<String>>>> instructionlist;
	private LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>> query = new LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>>();
	private List<LinkedHashMap<String,List<String>>> tempquery = new ArrayList<LinkedHashMap<String,List<String>>>();
	private ECLReport eclr; // to generate the report
	private ECLOutput eclo;
	private String Outputfile;
	private String Reportfile;
	/**
	 * end
	 */

	
	/**
	 * 
	 * Initial method
	 * @param borrowlist
	 * @param instructionlist
	 */
	public void SetExecutiveInital(List<LinkedHashMap<String,List<String>>> borrowlist,  List<LinkedHashMap<String,LinkedHashMap<String,List<String>>>> instructionlist, String outputfile, String reportfile){
		this.borrowlist = borrowlist;
		this.instructionlist = instructionlist;
		this.Outputfile = outputfile;
		this.Reportfile = reportfile;
		this.eclr = new ECLReport(this.Reportfile);
		this.eclo = new ECLOutput(this.Outputfile);

	}

	/**
	 * end
	 */

	
	
	
	
	
	/**
	 * Executing
	 */
	public void Executing(){
		//loop ArrayList first
		for(LinkedHashMap<String,LinkedHashMap<String,List<String>>> item : instructionlist){
			this.LoopInstructionList(item);
		}
		
		
		//pass the modified borrowlist to class ECLOutput
		this.eclo.GenerateOutputFile(this.borrowlist);
	}
	
	//Loop ArrayList of Instruction
	private void LoopInstructionList(LinkedHashMap<String,LinkedHashMap<String,List<String>>> item){
		for (Map.Entry<String, LinkedHashMap<String,List<String>>> entry : item.entrySet()) {
		    String key = entry.getKey();
		    LinkedHashMap<String, List<String>> value = entry.getValue();
		    if(key.equals("sort")){
		    	this.SortProcess(value);
		    }
		    else if(key.equals("add")){
		    	this.AddOrUpdateProcess(value);
		    }
		    else if(key.equals("delete")){
		    	this.DeleteProcess(value);
		    }
		    else if(key.equals("query_formatOne")){
		    	//tempquery.addAll(this.Query_formatOneProcess(value));
		    	//this.query.put(this.Generatekey(value),tempquery);
		    	
		    }
		    else if(key.equals("query_formatTwo")){
		    	//this.query_formatTwoProcess(value);

		    }
		    else if(key.equals("save")){
		    	//this.eclr.WriteToReportFile(query);
		    	tempquery.clear();
		    	query.clear();
		    }
		}
	}
	
	
	/**
	 * end
	 */
	

	
	
	/**
	 * sort processes
	 */
	private void SortProcess(LinkedHashMap<String, List<String>> getSort){
		for(Entry<String, List<String>> entry : getSort.entrySet()){
		    List<String> value = entry.getValue();
		    //sort by name
		    	this.sort(value.get(0));
		}
		
	
		
		
	}
	
	
	
	/**
	 * end
	 */
	
	/**
	 * add or update processes
	 */
	private void AddOrUpdateProcess(LinkedHashMap<String, List<String>> getvalue){
		//update only
		if(this.AddorUpdateCheck(this.ReturnNameAndBirthday(getvalue))){
			this.UpdateRecordforBorrowlist(getvalue,this.ReturnNameAndBirthday(getvalue));
		}
		//add new record
		else{
			this.AddRecord(getvalue);
		}
	}
	
	/**
	 * end
	 */
	
	/**
	 * delete processes
	 */
	private void DeleteProcess(LinkedHashMap<String, List<String>> getvalue){
		//now, we pass the the templist to the borrowlist to check which section that contains these records then we are bale to delete this section
		this.DeleteRecordFromBorrowlist(this.ReturnNameAndBirthday(getvalue));
	}
	
	/**
	 * end
	 */
	
	
	/**
	 * query_formatOne processes
	 */
	private List<LinkedHashMap<String,List<String>>> Query_formatOneProcess(LinkedHashMap<String, List<String>> getvalue){
		List<LinkedHashMap<String,List<String>>> querylist = new ArrayList<LinkedHashMap<String,List<String>>>();

		try{
			querylist.add(this.SaveQueryFormatOne(getvalue));
		}
		catch(Exception e){
			e.getMessage();
		}
	
	return querylist;
	}
	
	
	
	
	/**
	 * end
	 */
	
	
	
	/**
	 * query_formatTwo processes
	 */
	
	private List<LinkedHashMap<String,List<String>>> query_formatTwoProcess(LinkedHashMap<String, List<String>> getvalue){
		List<LinkedHashMap<String,List<String>>> querylist = new ArrayList<LinkedHashMap<String,List<String>>>();
			
		try{
			querylist.add(this.SaveQueryFormatTwo(getvalue));
			}
			catch(Exception e){
				e.getMessage();
			}
		
		
		return querylist;
	}
	
	
	/**
	 * end
	 */
	
	
	
	
	private List<String> ReturnNameAndBirthday(LinkedHashMap<String, List<String>> getvalue){
		List<String> tempList = new ArrayList<String>();
		for(Entry<String, List<String>> entry : getvalue.entrySet()){
			String keyname = entry.getKey();
			List<String> value = entry.getValue();
			if(keyname.equals("name") || keyname.equals("birthday")){
			tempList.add(value.get(0));}
		}
		return tempList;
	} 
	
	
	/******************************************** Algorithm **********************************************/
	
		/**
		 * Sort Algorithm without Java its API
		 */
		
		private void sort(String sort_type){
			//sort existed items
			List<LinkedHashMap<String,List<String>>> temp = new ArrayList<LinkedHashMap<String,List<String>>>();
			if(sort_type.equals("birthday")){
				temp.addAll(this.DateStringSort(sort_type,this.ReturnExeistField(sort_type)));
			}
			else{
				temp.addAll(this.StringSort(sort_type,this.ReturnExeistField(sort_type)));					
			}
			
			//append none existed item to end of collection
			
			
			temp.addAll(this.ReturnNonField(sort_type));
			
			//clear the old collection
			this.borrowlist.clear();
			//append the new collection from temlist
			this.borrowlist.addAll(temp);
			
		}
	
		//check if current section has not contained key
		private List<LinkedHashMap<String,List<String>>> ReturnNonField(String key){
			List<LinkedHashMap<String,List<String>>> temp = new ArrayList<LinkedHashMap<String,List<String>>>();
			for(int index=0; index<this.borrowlist.size();index++){
				if(!this.borrowlist.get(index).containsKey(key)){
					temp.add(this.borrowlist.get(index));
				}
			}
			return temp;
			
		}
		
		private List<LinkedHashMap<String,List<String>>> ReturnExeistField(String key){
			List<LinkedHashMap<String,List<String>>> temp = new ArrayList<LinkedHashMap<String,List<String>>>();
			for(int index=0; index<this.borrowlist.size();index++){
				if(this.borrowlist.get(index).containsKey(key)){
					temp.add(this.borrowlist.get(index));
				}
			}
			return temp;
			
		}
		
		
		
		//date sort
		private List<LinkedHashMap<String,List<String>>> DateStringSort(String sortype, List<LinkedHashMap<String,List<String>>> exeistlist){
				SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
	        	LinkedHashMap<String,List<String>>  tmp = new LinkedHashMap<String,List<String>> ();

				boolean swap = true;	
				int j = 0;
			    while (swap) {
			        swap = false;
			        j++;
			        for (int i = 0; i < this.borrowlist.size() - j; i++) {
			            try {
							if (dateformat.parse(exeistlist.get(i).get(sortype).get(0)).compareTo(dateformat.parse(exeistlist.get(i + 1).get(sortype).get(0))) > 0) {
								tmp = new LinkedHashMap<String,List<String>> ();
				            	tmp = exeistlist.get(i);
								exeistlist.set(i, exeistlist.get(i + 1));
								exeistlist.set(i+1, tmp);
							    swap = true;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	

			        }
			    }
			   		//System.out.println(exeistlist);
			    return exeistlist;
		}
		
		//normal string sort
		private List<LinkedHashMap<String,List<String>>>  StringSort(String sorttype, List<LinkedHashMap<String,List<String>>> exeistlist){
        	LinkedHashMap<String,List<String>>  tmp = new LinkedHashMap<String,List<String>> ();

			boolean swap = true;	
				int j = 0;
			    while (swap) {
			        swap = false;
			        j++;
			        for (int i = 0; i < exeistlist.size() - j; i++) {
			            if (this.ConvertListToString(exeistlist.get(i).get(sorttype)).compareTo(this.ConvertListToString(exeistlist.get(i + 1).get(sorttype))) > 0) {
			            	tmp = new LinkedHashMap<String,List<String>> ();
			            	tmp = exeistlist.get(i);
			            	exeistlist.set(i, exeistlist.get(i + 1));
			            	exeistlist.set(i+1, tmp);
			                swap = true;
			            }
			        	

			        }

			    }
				
				return exeistlist;
			
			 
				
		}
	
	
		
		
		
		
		/**
		 * end
		 */
	
	
	
	
	
	
	
	
	
	
	
	
	
		/**
		 * Delete the Record From Borrowlist
		 */
		private void DeleteRecordFromBorrowlist(List<String> tempList){
		
			int index;
		
			
			for(index=0;index<this.borrowlist.size();index++){
					if(this.DeeperLoop(this.borrowlist.get(index),tempList)){
						//if matched then remove the index from current list
						this.borrowlist.remove(index);
						break;
					}
				}
			
		}
	
		
		/**
		 * end
		 */
		
		/**
		 * Add the record if possible
		 */
		
		//check whether it has condition to update or add a new record, if found that matched record then update otherwise add a new
		private boolean AddorUpdateCheck(List<String> tempList){
			boolean exeist =false;
			for(int index=0;index<this.borrowlist.size();index++){
				if(this.DeeperLoop(this.borrowlist.get(index),tempList)){
					exeist = true;
				}
			}
			
			return exeist; 
		}
		
		//add a record
		private void AddRecord(LinkedHashMap<String, List<String>> getvalue){
			
			List<LinkedHashMap<String,List<String>>> newlist = new ArrayList<LinkedHashMap<String,List<String>>>();
			 newlist.add(this.ReRangedBorrowlist(getvalue));
			 this.borrowlist.addAll(newlist);
			 
		} 
		
		/**
		 * end
		 */
		
	
		/**
		 * Update the record 
		 */
		private void UpdateRecordforBorrowlist(LinkedHashMap<String, List<String>> linkedHashMap,List<String> tempList){
			int index;
			for(index=0;index<this.borrowlist.size();index++){
				if(this.DeeperLoop(this.borrowlist.get(index),tempList)){
					break;
				}
			}
		
		//update value according to key
		for(Entry<String, List<String>> ientry : linkedHashMap.entrySet()){

			//?? how to merge the same ISBN needs to a temple
				this.borrowlist.get(index).put(ientry.getKey(), ientry.getValue());
			}		
				
		}
		
		/**
		 * end
		 */
		
		
		
		/**
		 * Query_format_one
		 */
		private LinkedHashMap<String,List<String>> SaveQueryFormatOne(LinkedHashMap<String, List<String>> value){
			LinkedHashMap<String,List<String>> newhashmap = new LinkedHashMap<String,List<String>>();
			
			for(int index=0;index<this.borrowlist.size();index++){
				for(Entry<String, List<String>> entry : this.borrowlist.get(index).entrySet()){
						String getkey = entry.getKey();
						List<String> getvalue = entry.getValue();
						if(this.QueryCompare(value,getkey,getvalue)){
							newhashmap.putAll(this.borrowlist.get(index));
						}
						
						
					}		
						
				}
			
			return newhashmap;	
			}
			
		
		private boolean QueryCompare(LinkedHashMap<String, List<String>> queryvalue, String borrowgetkey, List<String> borrowlistvalue ){
			boolean condition = false;
			for(Entry<String, List<String>> entry : queryvalue.entrySet()){
				
				if(entry.getKey().equals(borrowgetkey) && this.QueryCompaerValue(entry.getValue(), borrowlistvalue)){
					condition = true;
				}
			
			
			}
			return condition;
			
			
		}	
		
		//check the value	
		private boolean QueryCompaerValue(List<String> queryvalue, List<String> borrowlistvalue){
			boolean condition =false;
			for(int x=0;x<queryvalue.size();x++){
				for(int i=0;i<borrowlistvalue.size();i++){
					if(borrowlistvalue.get(i).contains(queryvalue.get(x))){
						condition = true;
					}
				}
			}
			return condition;
			
		}	
			
			
		
		
		
		/**
		 * end
		 */
		
		
		
		/**
		 * Query_format_two
		 */
		
		private LinkedHashMap<String, List<String>> SaveQueryFormatTwo(LinkedHashMap<String, List<String>> value){
			LinkedHashMap<String, List<String>> templinkedhashmap = new LinkedHashMap<String, List<String>>(); 
			
			return templinkedhashmap;
		}
		
		/**
		 * end
		 */
		
	
	/**
	 * end
	 */
		
		
		
		
		
		
		
		/**
		 * Deeper borrowlist loop
		 * @param secondlevelborrowlist
		 * @param tempList
		 * @return
		 */
		//Deeper loop 
		private boolean DeeperLoop(LinkedHashMap<String,List<String>> secondlevelborrowlist,List<String> tempList){
			boolean comparedname=false;
			boolean comparedbirthday=false;
				for(Entry<String, List<String>> entry : secondlevelborrowlist.entrySet()){
					String key = entry.getKey();

					List<String> value = entry.getValue();

					if(key.equals("name")){
						if(this.Compared(value.get(0),tempList )){
							comparedname = true;
						}
					}
					else if (key.equals("birthday")){
						if(this.Compared(value.get(0),tempList )){
							comparedbirthday = true;
						}
					}

				}				

			

			if(comparedname && comparedbirthday){
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
		 * Compare
		 * @param oldvalue
		 * @param newvalue
		 * @return
		 */

		//Compare 
		private boolean Compared(String oldvalue, List<String> newvalue){
			boolean compated = false;
			for(String item : newvalue){
				if(oldvalue.contains(item)){
					compated =true;
				}
			}
			return compated;
		}	
		/**
		 * end
		 */
		
	

		/**
		 * Generate query section key
		 * @param object
		 * @return
		 */
		private String Generatekey(LinkedHashMap<String, List<String>> object){
			String generateString = "--- query "+this.GenerateQueryString(object) + "---";
			return generateString;
		}	
		
		private String GenerateQueryString(LinkedHashMap<String, List<String>> object){
			ECLController temp = new ECLController();
			String wholeString = "";
			for(Entry<String, List<String>> entry : object.entrySet()){
			    List<String> value = entry.getValue();
			    wholeString=entry.getKey()+" "+temp.ConvertListToString(value);
			}
			
			return wholeString;
		}
	
	
	
	
	
	
}
