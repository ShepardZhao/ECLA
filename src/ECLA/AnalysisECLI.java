/**
 * 
 */
package ECLA;

import java.io.FileNotFoundException;
import java.util.*;
/**
 * This file is reading and analyzing instruction file
 * @author zhaoxun321
 *
 */
public class AnalysisECLI extends ECLController{
	
	/**
	 * attributes
	 */
	private List<LinkedHashMap<String,LinkedHashMap<String,List<String>>>> Instructionlist = new ArrayList<LinkedHashMap<String,LinkedHashMap<String,List<String>>>>();
	
	/**
	 * end
	 */
	
	
	
	/**
	 * Methods
	 */
	
	
	/**
	 * Read instruction file
	 */
	public void ReadInstructionFile(){
		try {
			Scanner scanner = new Scanner(this.GetFileWithName());
			while(scanner.hasNextLine()){
				String content = scanner.nextLine();
			if(!content.isEmpty()){
				if (content.contains("add") && content.contains("name") && content.contains("birthday")){
					Instructionlist.add(this.RturnTempLinkedMap("add",content));	
				}
				else if(content.contains("delete") && content.contains("name") && content.contains("birthday")){
					Instructionlist.add(this.RturnTempLinkedMap("delete",content));
				}
				else if(content.contains("sort")){
					Instructionlist.add(this.RturnTempLinkedMapSort(content));
				}
				else if(content.contains("query")){
					
					Instructionlist.add(this.RturnTempLinkedMapQuery(content));
				}
				else if(content.contains("save")){
					Instructionlist.add(this.RturnTempLinkedMap("save"));

				}
				
			}
		}
			
			scanner.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * end
	 */
	
	/**
	 * Return tmp linkedmap
	 */
	private LinkedHashMap<String,LinkedHashMap<String,List<String>>> RturnTempLinkedMap(String type,String value){
		
		LinkedHashMap<String,LinkedHashMap<String,List<String>>> temp = new LinkedHashMap<String,LinkedHashMap<String,List<String>>>();
		
		temp.put(type, this.FieldLinkedHashMap(type,value));
		
		return temp;
	}
	
	
	/**
	 * end
	 */
	
	/**
	 * sort
	 */
	private LinkedHashMap<String,LinkedHashMap<String,List<String>>> RturnTempLinkedMapSort(String content){
		LinkedHashMap<String,LinkedHashMap<String,List<String>>> temp = new LinkedHashMap<String,LinkedHashMap<String,List<String>>>();
		LinkedHashMap<String,List<String>> linkedhashmap = new LinkedHashMap<String,List<String>>();
		List<String> list = new ArrayList<String>();
		if(content.contains("sort")){
			String newString = content.replaceAll("sort\\s", "");
			list.add(newString);
			linkedhashmap.put("sort", list);
			temp.put("sort", linkedhashmap);			
		}
	
	return temp;
	
	}
	
	
	
	/**
	 * end
	 */
	
	
	
	
	/**
	 * overloading
	 * Save only
	 */
	private LinkedHashMap<String,LinkedHashMap<String,List<String>>> RturnTempLinkedMap(String save){
		
		LinkedHashMap<String,LinkedHashMap<String,List<String>>> temp = new LinkedHashMap<String,LinkedHashMap<String,List<String>>>();
		LinkedHashMap<String,List<String>> linkedhashmap = new LinkedHashMap<String,List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("save");
		
		linkedhashmap.put("save", list);
		
		temp.put("save",linkedhashmap);
		
		return temp;
	}
	
	/**
	 * end
	 */
	
	/**
	 * Query only 
	 */
	private LinkedHashMap<String,LinkedHashMap<String,List<String>>> RturnTempLinkedMapQuery(String getstring){
		LinkedHashMap<String,LinkedHashMap<String,List<String>>> queryLinkedHashMap = new LinkedHashMap<String,LinkedHashMap<String,List<String>>>();
		//if current query is format two
		if(getstring.contains("name") && getstring.contains("birthday") && QueryForamtTwoCheck(getstring)){
			queryLinkedHashMap.put("query_formatTwo", this.ReturnQuery_format_twoString(getstring));
		}
		//if current query format is not validated by two then one would be
		else{
			queryLinkedHashMap.put("query_formatOne", this.FieldLinkedHashMap("query", getstring));

		}
		
		return queryLinkedHashMap;
	}
	
	
	private boolean QueryForamtTwoCheck(String getstring){
		Scanner splitdate = new Scanner(getstring);
		int count =0;
		splitdate.useDelimiter("; ");
		while(splitdate.hasNext()){
			String getdate = splitdate.next();
			if(this.BooklistDateCheck(getdate)){
				count++;
			}
		}	
		splitdate.close();
		if(count==2){
		return true;}
		else{return false;}
	
	}
	
	
	//this method will only return the LinkedHashMap value of the format two of query 
	private LinkedHashMap<String,List<String>> ReturnQuery_format_twoString(String string){
		LinkedHashMap<String,List<String>> format_two = new LinkedHashMap<String,List<String>>();
		List<String> name = new ArrayList<String>();
		List<String> birthday = new ArrayList<String>();
		List<String> date = new ArrayList<String>();

		
		//rid of "query"
		String newquerystring = string.replaceAll("^query\\s", "");
		Scanner queryscanner  = new Scanner(newquerystring);
		queryscanner.useDelimiter("; ");
		while(queryscanner.hasNext()){
			String getstring = queryscanner.next();
			if(getstring.contains("name")){
				name.add(getstring.replaceAll("^name\\s", ""));
			}
			else if(getstring.contains("birthday")){
				birthday.add(getstring.replaceAll("^birthday\\s", ""));
			}
			else{
				date.add(getstring);
			}
			
			
		}
		queryscanner.close();
		format_two.put("name", name);
		format_two.put("birthday", birthday);
		format_two.put("date", date);
		
	
		return format_two;
		
	}
	
	
	/**
	 * end
	 */
	
	
	/**
	 * instruction file check and get value
	 */
	private LinkedHashMap<String,List<String>> FieldLinkedHashMap(String type,String content){
		LinkedHashMap<String,List<String>> linkedHashMap = new LinkedHashMap<String,List<String>>();
		String newstring = content.replaceAll("^"+type+"\\s", "");
		
		Scanner ioscanner = new Scanner(newstring);
		ioscanner.useDelimiter("; ");
		while(ioscanner.hasNext()){
			String getstring = ioscanner.next();
			if(getstring.contains("birthday")){//check birthday 
				if(this.BirthdayFieldCheck(getstring.replaceAll("^birthday\\s",""))){
					linkedHashMap.put("birthday", this.SingleLineProcess("birthday", getstring));
				}
			}
			else if(getstring.contains("name")){//check name
				if(this.NameFieldCheck(getstring.replaceAll("^name\\s",""))){
					linkedHashMap.put("name",this.SingleLineProcess("name", getstring));

				}	
			}
			else if(getstring.contains("email")){//check email
				if(this.MailFieldCheck(getstring.replaceAll("^email\\s",""))){
					linkedHashMap.put("email", this.SingleLineProcess("email", getstring));
				}	
			}
			else if(getstring.contains("phone")){//check phone
				if(this.PhoneFieldCheck(getstring.replaceAll("^phone\\s",""))){
					linkedHashMap.put("phone", this.SingleLineProcess("phone", getstring));
				}	
			}
			else if(getstring.contains("address")){//check address
					linkedHashMap.put("address", this.SingleLineProcess("address", getstring));
			}
			else if(getstring.contains("booklist")){//check booklist
				linkedHashMap.put("booklist", this.DeepBooklistCheck(getstring));			
			}			
			
		}
		ioscanner.close();
		
		return linkedHashMap;
		
	}
	
	
	
	
	
	//deep booklist check
	private List<String> DeepBooklistCheck(String getbooklist){
		Scanner booklistscanner = new Scanner(getbooklist);
		List<String> list = new ArrayList<String>();
		booklistscanner.useDelimiter("^booklist\\s");
		while(booklistscanner.hasNext()){
			String getsubcontent = booklistscanner.next();
			//if current booklist string contains ', '
			if(getbooklist.contains(", ")){
				list.addAll(this.DeeperBookListCheck(getsubcontent));
			
			}
			
		}
		
		
		booklistscanner.close();
		return list;
	}
	
	
	//deeper book list check
	private List<String> DeeperBookListCheck(String modifiedbooklist){
		List<String> booklist = new ArrayList<String>();
		Scanner deepercheck = new Scanner(modifiedbooklist.replaceAll("^\\s+", ""));
		boolean isbn=false;
		boolean date=false;
		deepercheck.useDelimiter(", ");
		while(deepercheck.hasNext()){				
			String getcontent = deepercheck.next();
			if(this.BookFieldCheck(getcontent)){
				booklist.add(getcontent);
			}
		}
		
		
		deepercheck.close();
		
		return booklist;
		
	}
	
	/**
	 * end
	 */
	
	/**
	 * Instruction Booklistdate and ISBN check
	 */
	private boolean BookFieldCheck(String booklistString){
		boolean isbn = false;
		boolean bookdate = false;
		Scanner analysisbooklist = new Scanner(booklistString);
		while (analysisbooklist.hasNext()){
			String getstring = analysisbooklist.next();
			if(this.BooklistISBNCheck(getstring)){
				isbn = true;
			}
			else if(this.BooklistDateCheck(getstring)){
				bookdate = true;
			}
		}
		
		analysisbooklist.close();
		
		if(isbn && bookdate){
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
	 * Get Instruction list
	 */
	
	public List<LinkedHashMap<String,LinkedHashMap<String,List<String>>>> GetInstructionList(){
		return this.Instructionlist;
	}

	/**
	 * end
	 */
	
}
