/**
 * This file is reading and analyzing instruction file
 * @author Xun Zhao
 *
 */
package ECLA;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
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
				//convert all m-space or m-tab to one space
				String newcontent = this.FilterSpaceTabToOneSpace(content);

			if(!newcontent.isEmpty()){
				if (newcontent.contains("add") && newcontent.contains("name") && newcontent.contains("birthday")){
					if(!this.RturnTempLinkedMap("add",newcontent).isEmpty()){
					Instructionlist.add(this.RturnTempLinkedMap("add",newcontent));	
					}
				}
				else if(newcontent.contains("delete") && newcontent.contains("name") && newcontent.contains("birthday")){
					if(!this.RturnTempLinkedMap("delete",newcontent).isEmpty()){
					Instructionlist.add(this.RturnTempLinkedMap("delete",newcontent));
					}		
				}
				else if(newcontent.contains("sort")){
					Instructionlist.add(this.RturnTempLinkedMapSort(newcontent));
				}
				else if(newcontent.contains("query")){
					if(!this.RturnTempLinkedMapQuery(newcontent).isEmpty()){
					Instructionlist.add(this.RturnTempLinkedMapQuery(newcontent));
					}
				}
				else if(newcontent.contains("save")){
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
		if(!this.FieldLinkedHashMap(type,value).isEmpty()){
			temp.put(type, this.FieldLinkedHashMap(type,value));
		}
		
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
		else if(this.ComplexQueryforFormatTwo(getstring)){
			queryLinkedHashMap.put("query_formatOne", this.FieldLinkedHashMap("query", getstring));
			
		}
		
		
		
		return queryLinkedHashMap;

		
	}
	
	
	//check the complex condition
	private boolean ComplexQueryforFormatTwo(String getstring){
		boolean condition =true;
		Scanner setscanner = new Scanner(getstring);
		setscanner.useDelimiter("; ");
		while(setscanner.hasNext()){
			String value = setscanner.next();
			if(!this.KeywordCheck(value)){
				condition = false;
			}
		}
		setscanner.close();
		return condition;
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
		queryscanner.useDelimiter(";");
		while(queryscanner.hasNext()){
			String getstring = queryscanner.next().replaceAll("^\\s+|^\\t", "");
			if(getstring.contains("name")){
				name.add(getstring.replaceAll("^name\\s+|\\t+", ""));
			}
			else if(getstring.contains("birthday")){
				birthday.add(getstring.replaceAll("^birthday\\s+|\\t+", ""));
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
		String newstring = content.replaceAll(type+"\\s", "");
		Scanner ioscanner = new Scanner(newstring);
		ioscanner.useDelimiter(";");
		while(ioscanner.hasNext()){
			String primStirng = ioscanner.next();
			String  getstring= this.FilterSpaceTabToOneSpaceInBeginning(primStirng);
			
			if(getstring.contains("birthday")){//check birthday 
				String birthdayvalue = this.FilterKeywordAndRestSpaceOrTab("birthday",getstring);

				if(this.BirthdayFieldCheck(birthdayvalue)){
					linkedHashMap.put("birthday", this.SingleLineProcess(birthdayvalue));
				}
			}
			else if(getstring.contains("name")){//check name
				String namevalue = this.FilterKeywordAndRestSpaceOrTab("name",getstring);
				if(this.NameFieldCheck(namevalue)){
					linkedHashMap.put("name",this.ReturnNameList(namevalue));
				}	
			}
			else if(getstring.contains("email")){//check email
				String emailvalue = this.FilterKeywordAndRestSpaceOrTab("email",getstring);
				if(this.MailFieldCheck(emailvalue)){
					linkedHashMap.put("email", this.SingleLineProcess( emailvalue));
				}	
			}
			else if(getstring.contains("phone")){//check phone
				String phonevalue = this.FilterKeywordAndRestSpaceOrTab("phone",getstring);
				if(this.PhoneFieldCheck(phonevalue)){
					linkedHashMap.put("phone", this.PhoneSingleLineProcess(phonevalue));
				}	
			}
			else if(getstring.contains("address")){//check address
				String phonevalue = this.FilterKeywordAndRestSpaceOrTab("address",getstring);
					linkedHashMap.put("address", this.SingleLineProcess(phonevalue));
			}
			else if(getstring.contains("booklist")){//check booklist
				linkedHashMap.put("booklist", this.DeepBooklistCheck(getstring));			
			}			
			
		}
		ioscanner.close();
		
		//condition for delete field check
		if(type.equals("delete") || type.equals("add")){
			if(linkedHashMap.containsKey("name") && linkedHashMap.containsKey("birthday")){
				return linkedHashMap;

			}
			else{
				linkedHashMap.clear();
			}
		}
		else{
			return linkedHashMap;
		}
		
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
