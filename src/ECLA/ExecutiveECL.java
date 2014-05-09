/**
 * This is a executive class that will generate the output file and report file
 * The method is looping collections between borrowlist(borrower record from the class AnalysisECLB) and instructionlist (instruction record from class AnalysisECLI)
 * @author xun zhao
 * 
 */
package ECLA;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;
public class ExecutiveECL extends ECLController {
	/**
	 * Attributes
	 */
	private List<LinkedHashMap<String,List<String>>> borrowlist; //collection about the record of borrower
	private List<LinkedHashMap<String,LinkedHashMap<String,List<String>>>> instructionlist; // collection about the record of instruction
	private List<LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>>> query = new ArrayList<LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>>>(); // generated the query collection it will be used to generate the report through the class EClOutput
	private ECLReport eclr; //Composition class which will be used to generate the report file
	private ECLOutput eclo;//Composition class which will be used to generate the output file while all records been read up
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
		this.eclo = new ECLOutput(outputfile);
		this.eclr = new ECLReport(reportfile);
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
		
		//once the instruction record has been read up
		//prepare to generate the output file
		this.eclo.GenerateOutputFile(this.borrowlist);
	}
	
	//Loop ArrayList of Instruction
	private void LoopInstructionList(LinkedHashMap<String,LinkedHashMap<String,List<String>>> item){
		for (Map.Entry<String, LinkedHashMap<String,List<String>>> entry : item.entrySet()) {
		    String key = entry.getKey();
		    LinkedHashMap<String, List<String>> value = entry.getValue();
		    if(key.equals("sort")){
		    	//doing the sort function
		    	this.SortProcess(value);
		    }
		    else if(key.equals("add")){
		    	//doing add or update function
		    	this.AddOrUpdateProcess(value);
		    }
		    else if(key.equals("delete")){
		    	//doing delete function
		    	this.DeleteProcess(value);
		    }
		    else if(key.equals("query_formatOne")){
		    	//put these query into a list, if these query values contain one or more results		    	
		    	this.query.add((this.Query_formatOneProcess(this.eclr.GenerateQueryHeaderKey(value),value)));
		    }
		    else if(key.equals("query_formatTwo")){
		    	//doing the query format two function 
		    	this.query.add((this.query_formatTwoProcess(this.eclr.GenerateQueryHeaderKey(value),value)));

		    }
		    else if(key.equals("save")){
		    	//doing the save function
		    	this.eclr.WriteToReportFile(query);
		    	query.clear();
		    }
		}
	}
	
	
	/**
	 * end
	 */
	

	
	/**
	 * sort processes
	 * @param getSort
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
	 * @param getvalue
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
	 * @param getvalue
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
	 * @param getkye
	 * @param getvalue
	 * @return
	 */
	private LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>> Query_formatOneProcess(String getkye, LinkedHashMap<String, List<String>> getvalue){
		LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>> tempquery = new LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>>();

		try{
			tempquery.put(getkye, this.SaveQueryFormatOne(getvalue));
		}
		catch(Exception e){
			e.getMessage();
		}

	return tempquery;
	}
	/**
	 * end
	 */
	
	
	
	/**
	 * query_formatTwo processes
	 * @param key
	 * @param getvalue
	 * @return
	 */
	
	private LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>> query_formatTwoProcess(String key,LinkedHashMap<String, List<String>> getvalue){
		LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>> tempquery_format_two = new LinkedHashMap<String,List<LinkedHashMap<String,List<String>>>>();
		try{
			tempquery_format_two.put(key,this.SaveQueryFormatTwo(getvalue));
			}
			catch(Exception e){
				e.getMessage();
			}
		
		
		return tempquery_format_two;
	}
	
	
	/**
	 * end
	 */
	
	
	
	/**
	 * return the name and birthday to list
	 * @param getvalue
	 * @return
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
	
	
	/**
	 * end
	 */
	
	
	
	
	/******************************************** Algorithm **********************************************/
	
		
		/**
		 *  Sort Algorithm without Java its API
		 * @param sort_type
		 */
		private void sort(String sort_type){
			//sort existed items
			List<LinkedHashMap<String,List<String>>> temp = new ArrayList<LinkedHashMap<String,List<String>>>();
			if(sort_type.equals("birthday")){
				temp.addAll(this.DateStringSort(sort_type,this.ReturnExistField(sort_type)));
			}
			else{
				temp.addAll(this.StringSort(sort_type,this.ReturnExistField(sort_type)));					
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
		
		
		//return exist field 
		private List<LinkedHashMap<String,List<String>>> ReturnExistField(String key){
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
			Iterator<LinkedHashMap<String, List<String>>> i = this.borrowlist.iterator();
			while (i.hasNext()) {
				LinkedHashMap<String, List<String>> o = i.next();
			   if(this.DeeperLoop(o,tempList)){
					//if matched then remove the index from current list
					i.remove();
					
				}
			}
			
			//get rid of null element from list
			List<LinkedHashMap<String,List<String>>> newlist = new ArrayList<LinkedHashMap<String,List<String>>>();
			Iterator<LinkedHashMap<String, List<String>>> x = this.borrowlist.iterator();

			while (x.hasNext()) {
				LinkedHashMap<String, List<String>> o = x.next();
			   if(!o.isEmpty()){
					//if matched then remove the index from current list
				   newlist.add(o);
				}
			}
			//clearing the old 
			this.borrowlist.clear();
			//re-add all items from newlist to borrowlist
			this.borrowlist.addAll(newlist);		
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
		private List<LinkedHashMap<String,List<String>>> SaveQueryFormatOne(LinkedHashMap<String, List<String>> value){
			List<LinkedHashMap<String,List<String>>> list = new ArrayList<LinkedHashMap<String,List<String>>>();
			for(int index=0;index<this.borrowlist.size();index++){
						if(this.QueryCompareFormatOne(value,this.borrowlist.get(index))){
							list.add(this.borrowlist.get(index));
						}		
				}
			
			return list;	
			}
			
		//in query process, go through comparing the key and value to make sure current parameter is valid
		private boolean QueryCompareFormatOne(LinkedHashMap<String, List<String>> queryvalue, LinkedHashMap<String,List<String>>  borrowlistvalue){
			boolean condition = false;
			for (Entry<String,List<String>> entry:queryvalue.entrySet()){
				String getkey=entry.getKey();
				List<String> getvalue =entry.getValue();
				if(borrowlistvalue.containsKey(getkey) && borrowlistvalue.containsValue(getvalue)){
					condition =true;
				}
				else{
					condition = false;
					break;
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
		
		private List<LinkedHashMap<String, List<String>>> SaveQueryFormatTwo(LinkedHashMap<String, List<String>> value){
			List<LinkedHashMap<String, List<String>>> templinkedhashmap = new ArrayList<LinkedHashMap<String, List<String>>>(); 
			for(int index=0;index<this.borrowlist.size();index++){
				if(this.QueryCompareFormatTwo(value,this.borrowlist.get(index))){
					templinkedhashmap.add(this.ReturnNewRecordWithinBooklist(value.get("date"), this.borrowlist.get(index)));
				}		
			}
	
			return templinkedhashmap;
		}
		
		
		//confirm that current record is matched to the condition of query format 2 
		private boolean QueryCompareFormatTwo(LinkedHashMap<String, List<String>> queryvalue, LinkedHashMap<String,List<String>>  borrowlistvalue){
			if( (borrowlistvalue.containsKey("name") && borrowlistvalue.get("name").equals((queryvalue.get("name")))) &&
				(borrowlistvalue.containsKey("birthday") &&  borrowlistvalue.get("birthday").equals((queryvalue.get("birthday"))))
				&& borrowlistvalue.containsKey("booklist")){	
					return true; 
					}
			return false;
		}	
		
		//return new record that matched borrow date; ie. 02-03-2003 to 10-03-2003
		private LinkedHashMap<String,List<String>> ReturnNewRecordWithinBooklist(List<String> bookdate,  LinkedHashMap<String,List<String>> borrowlistvalue){
			List<String> newbooklist = new ArrayList<String>();	
			LinkedHashMap<String,List<String>> templinkedHashMap = new LinkedHashMap<String,List<String>>();
			templinkedHashMap.putAll(borrowlistvalue);
			
			String begindate = bookdate.get(0);
			String endsdate = bookdate.get(1);
			for(int index=0;index<borrowlistvalue.get("booklist").size();index++){
				Scanner booklistscanner = new Scanner(borrowlistvalue.get("booklist").get(index));
				booklistscanner.useDelimiter(", ");
				while(booklistscanner.hasNext()){
					String getvalue = booklistscanner.next();
					if(this.BooklistDateCheck(getvalue)){
						if(ValidateDateScope(getvalue,begindate,endsdate)){
							newbooklist.add(borrowlistvalue.get("booklist").get(index));
						}
					}
					
				}
				booklistscanner.close();
				
			}
			
			if(!newbooklist.isEmpty()){
				templinkedHashMap.remove("booklist");
				templinkedHashMap.put("booklist",newbooklist);
			}
			
			
			return templinkedHashMap;
			
		}
		
		
		//check date scope
		private boolean ValidateDateScope(String booklistdate, String begindate, String endsdate){
			SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
			boolean condition =false;
			
				try {
					if(dateformat.parse(booklistdate).before(dateformat.parse(endsdate)) && dateformat.parse(booklistdate).after(dateformat.parse(begindate))){
						condition = true;
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			return condition;
			
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
		
	

		
		
	
	
	
}
