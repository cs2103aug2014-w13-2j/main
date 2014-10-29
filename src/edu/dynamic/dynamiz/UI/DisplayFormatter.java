package edu.dynamic.dynamiz.UI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.awt.color.*;
import java.util.logging.*;

import edu.dynamic.dynamiz.structure.*;

/**
 * @author Hu Wenyan
 * Implement Display functions for tasks, events and todoItems
 */
public class DisplayFormatter implements DisplayerInterface {
//	private static final String WELCOME_MESSAGE= "Welcome to Dynamiz!";
//	
//	private static final int FEEDBACK_TAG = 1;
//	private static final int ERROR_FEEDBACK_TAG = 2;
//	private static final int SUCCESS_FEEDBACK_TAG = 3;
//	private static final int HELP_FEEDBACK_TAG = 4;
	
	static final String UPDATE_COMMAND = "update";

	
	public String dateFormatter(Calendar c){
		String s = String.format("%1$tm,%1$te",c);
		return s;	
	}
//	public String dateFormatter(MyDate d){
//		
//		String s = String.format("%tm,%td,%ty", d);
//		return s;
//	}
//	public String timeFormatter(MyDate d){
//		String s = String.format("%tH:%tM", d);
//		return s;
//	}
	

	public String displayWelcomeMessage(){
		return WELCOME_MESSAGE;
	}
	
	
	
	public String displayTitleLine() {
		String s=String.format("|  %-4s|       %-20s|%-10s|%-18s|%-18s|%-10s|\n","ID", "Description","Priority","Start Time","End Time","Status");	
		return s;
	}

	@Override
	public String displayDividingLine() {
		String s = new String("--------------------------------------------------------------------------------------------------------");
		return s;
	}

	
	@Override
	public String displayString(String str) {	
		return str;
	}

	@Override
	public String displayStringList(ArrayList<String> arr) {	
		if(arr == null) return new String("null");

		StringBuilder s = new StringBuilder();

		for(int i=0;i<arr.size();i++){
			if(!arr.get(i).isEmpty())
			s.append(arr.get(i).trim()).append("\n");
		}

		return s.toString();	
	}
	

	@Override
	public String displayTaskItem(TaskItem task) {
		assert task!=null;
		//if(task == null) return new String("null task");
		 return task.toString();
	}

	@Override
	public String displayTaskFile(TaskItem task) {
		assert task!=null;
		//if(task == null) return new String("null task");
		return task.toFileString();
	}
	
	@Override
	public String displayTaskFeedback(TaskItem task) {
		assert task!=null;
		//if(task == null) return new String("null task");
		return task.getFeedbackString();
	}

	

	@Override
	public String displayTaskList(ArrayList<TaskItem> taskList) {
		assert taskList!=null;
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< taskList.size(); i++){
			s.append(taskList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public String displayTaskList(TaskItem[] taskList) {
		assert taskList!=null;
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< taskList.length; i++){
			s.append(taskList[i].toString()).append("\n");
		}
		return s.toString();

	}

	
	@Override
	public String displayEventFeedback(EventItem event) {
		assert event!=null;
		 return event.getFeedbackString();
		
	}

	@Override
	public String displayEventFile(EventItem event) {
		assert event!=null;
		String s = new String();
		return event.toFileString();
	}
	@Override
	public String displayEventItem(EventItem event) {
		assert event!=null;
		return event.toString();
	}
	
	@Override
	public String displayEventList(ArrayList<EventItem> eventList) {
		assert eventList!=null;
		String str = new String();
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< eventList.size(); i++){
			s.append(eventList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public String displayEventList(EventItem[] eventList) {
		assert eventList!=null;
		String str = new String();
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< eventList.length; i++){
			s.append(eventList[i].toString()).append("\n");
		}
		return s.toString();
	}

	

	@Override
	public String displayToDoItem(ToDoItem todoItem) {
		assert todoItem !=null;
		String str = new String();
		return todoItem.toString();
	}

	@Override
	public String displayToDoFeedback(ToDoItem todoItem) {
		assert todoItem !=null;
		String str = new String();
		return todoItem.getFeedbackString();
	}

	@Override
	public String displayToDoFile(ToDoItem todoItem) {
		assert todoItem !=null;
		String str = new String(); 
		return todoItem.toFileString();
	}
	
	@Override
	public String displayToDoList(ArrayList<ToDoItem> todoList) {
		assert todoList !=null;
		String str = new String();
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< todoList.size(); i++){
			s.append(todoList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	@Override
	public String displayToDoList(ToDoItem[] todoList) {
		assert todoList !=null;
		String str = new String();		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< todoList.length; i++){
			s.append(todoList[i].toString()).append("\n");
		}
		return s.toString();
	}
	


	public String displayPrompt() {
		String s = new String ("Command: ");
		return s;
	}

	public String displayPrompt(int promptTag) {
		String tag = new String();
		switch(promptTag){
		case ENTER_COMMAND_PROMPT:
			tag = ENTER_COMMAND_STR;
			break;
		case ENTER_ITEM_PROMPT:
			tag = "Please Enter Task: ";
			break;
		case ENTER_TASK_INDEX_PROMPT:
			tag = ENTER_TASK_INDEX_STR;
			break;
			
		case ENTER_TIME_PROMPT:
			tag = ENTER_TIME_PERIOD_STR;
			break;
			
		case INVALID_COMMAND_PROMPT:
			tag = ENTER_VALID_COMMAND_STR;
			break;
		default:
			tag = ENTER_VALID_COMMAND_STR;
		}		
		//tag+="\n";		
		return tag;
	}

	public String displayPrompt(String promptMessage) {
		return promptMessage;
	}

	
	public ArrayList<StrIntPair> displayFeedback(Feedback commandFeedback) {
		ArrayList<StrIntPair> displayContentList = new ArrayList<StrIntPair>();
		assert commandFeedback!=null;	
		String s = new String(); 
		int t = getFeedbackTag(commandFeedback);
		switch(t){		
		case HELP_FEEDBACK_TAG:
			HelpFeedback hf = (HelpFeedback)commandFeedback; 
			s = hf.getHelpContent();		
			displayContentList.add(new StrIntPair(s));
			break;		
		case ERROR_FEEDBACK_TAG:
			ErrorFeedback ef = (ErrorFeedback)commandFeedback; 	
			s=ef.getMessage();
			displayContentList.add(new StrIntPair(s));
			break;			
		case SUCCESS_FEEDBACK_TAG:
			
			SuccessFeedback sf = (SuccessFeedback) commandFeedback;
			
			//String sMsg = TagFormat.format(sf.getCommandType()+" successfully!",TagFormat.SUCCESS);
			s  = sf.getCommandType()+" successfully!";
			s = s+"\n";	
			displayContentList.add(new StrIntPair(s));
			
			//displayContentList.add(new StrIntPair(displayTitleLine()));
			
			getFeedbackContent(displayContentList,sf);
			
			
			
			
			break;
		default:
			s = "Invalid Instruction\n";
			displayContentList.add(new StrIntPair(s));
			
		}
		//return "<html>"+s+"</html>";	
		return  displayContentList;	
	}
	
	
	
	private void getFeedbackContent(ArrayList<StrIntPair> displayContentList, SuccessFeedback sf){
		assert(displayContentList!=null);
		ToDoItem[] list = sf.getAffectedItems();
		if(list==null){
			displayContentList.add(new StrIntPair("The list is empty!\n"));
			return;
		}
		
		if(sf.getCommandType().equals(UPDATE_COMMAND)){
			assert(2==list.length);
			displayContentList.add(new StrIntPair("Item affected:\n"));
			formatTaskChunk(displayContentList,list[0]);
			
			displayContentList.add(new StrIntPair("Updated Item:\n"));
			formatTaskChunk(displayContentList,list[1]);
			
		}
		else{ 
			if(list.length < 5){
			for( int i = 0 ; i< list.length; i++){
			formatTaskChunk(displayContentList,list[i]);
			}
			}
			else{
				displayContentList.add(new StrIntPair(displayTitleLine()));
				
				for (int i = 0;i <list.length;i++){
					formatTaskLine(displayContentList,list[i]);
				}
			}
		}
		
		
	}
	private void formatTaskLine(ArrayList<StrIntPair> contentList,ToDoItem item){
		String strFor1 = "|  %-4s| %-26s|";
		String strForPri = "%-10s";
		String strForTimeSta="|%-18s|%-18s|%-10s|\n";
		assert item!=null;
		assert contentList!=null;
		String ID = item.getId();
		String des = item.getDescription();
		int pri = item.getPriority();
		String prioS = TagFormat.formatPri(pri);
		String starT = "";
		String endT = "";
		String stas = item.getStatus();
		
		if(des.length()>=17){
			des = des.substring(0, 17);
			des = des + "...";
		}
		if(item instanceof TaskItem){
			TaskItem t = (TaskItem)item;
			starT = "";
			endT = t.getDeadlineString();
		}
		else if (item instanceof EventItem){
			EventItem t = (EventItem)item;
			starT = t.getStartDateString();
			//starT = TagFormat.format(starT, TagFormat.START_TIME);
			endT = t.getEndDateString();	
			
		}
		contentList.add(new StrIntPair(String.format(strFor1, ID,des)));
		contentList.add(new StrIntPair(String.format(strForPri, prioS),pri));
		contentList.add(new StrIntPair(String.format(strForTimeSta,starT,endT,stas)));
	}
	
	
	private void formatTaskChunk(ArrayList<StrIntPair> contentList,ToDoItem item){
		assert item!=null;
		assert contentList!=null;
		final String FORMAT_FEEDBACKSTRING = "ID: %1$s\n"+"Desc: %2$s\n"+"Priority: %3$d\n"+
					"Start: %4$s\n"+"End: %5$s\n"+"Status: %6$s";

		String ID = item.getId();
		String des = item.getDescription();
		int pri = item.getPriority();
		String prioS = TagFormat.formatPri(pri);
		String stas = item.getStatus();
		//stas = TagFormat.format(stas, TagFormat.TASK_STATUS);
		contentList.add(new StrIntPair("ID: "+ID+"\n"+"Des: "+des+"\n"+"Priority: "));
//		contentList.add(new StrIntPair("Des: "+des+"\n"));
//		contentList.add(new StrIntPair("Priority: "));
		contentList.add(new StrIntPair(prioS+"\n",pri));
		contentList.add(new StrIntPair("Status: "+stas+"\n"));
		
		if(item instanceof TaskItem){
			TaskItem t = (TaskItem)item;
			String ddl = t.getDeadlineString();
			//ddl = TagFormat.format(ddl, TagFormat.END_TIME);
			contentList.add(new StrIntPair("Deadline: "+ddl+"\n"));
		}
		else if (item instanceof EventItem){
			EventItem t = (EventItem)item;
			String starT = t.getStartDateString();
			//starT = TagFormat.format(starT, TagFormat.START_TIME);
			String endT = t.getEndDateString();
			//endT =  TagFormat.format(endT, TagFormat.END_TIME);
			contentList.add(new StrIntPair("Start Time: "+starT+"\n"));
			contentList.add(new StrIntPair("End Time: "+endT+"\n"));
			
		}
		
	}
	
	private int getFeedbackTag(Feedback f){
		String t =f.getClassName();
		
		if(t.equals("SuccessFeedback")) return SUCCESS_FEEDBACK_TAG;
		if(t.equals("ErrorFeedback")) return ERROR_FEEDBACK_TAG;	
		if(t.equals("HelpFeedback")) return HELP_FEEDBACK_TAG;
		
		return FEEDBACK_TAG;		
		
	}

	
	
	@Override
	public ArrayList<StrIntPair> displayHelpPage() {
		ArrayList<StrIntPair> printContentList = new ArrayList<StrIntPair>();
		StringBuilder sb = new StringBuilder();
		String title = StringUtils.center("Help Page", 9);
		sb.append(title).append("\n");
		printContentList.add( new StrIntPair(sb.toString()));
		return printContentList;
	}	
}

class TagFormat{
	//HTML Tag
	//PRIORITY_TAG
	private static boolean addHTMLTAG = false;
	public static final String SUCCESS = "";
	public static final String HELP = "";
	public static final String ERORR = "";
	public static final String PROMPT = "";
	
	public static final String PRIORITY = "PRIORITY_HEADER";
	public static final String TASK_ID = "";
	public static final String TASK_DESCRIPTION = "";
	public static final String TASK_STATUS = "";
	public static final String START_TIME = "";
	public static final String END_TIME = "";
	public static final String TIME = "";
	
	public static final String PRIORITY_URGENT_TAG= "PRIORITY_URGERNT";
	public static final String PRIORITY_HIGH_TAG = "PRIORITY_HIGH";
	public static final String PRIORITY_MEDIUM_TAG = "PRIORITY_MEDIUM";
	public static final String PRIORITY_LOW_TAG = "PRIORITY_LOW";
	public static final String PRIORITY_NONE_TAG = "PRIORITY_NONE";
	
	public static final int PRIORITY_URGENT = 8;
	public static final int PRIORITY_HIGH = 4;
	public static final int PRIORITY_MEDIUM = 2;
	public static final int PRIORITY_LOW = 1;
	public static final int PRIORITY_NONE = 0;
	public static final int PRIORITY_UNCHANGED = -1;
	
	
	private static final String FORMAT_HTML_TAG = "<div clas = %s >%s</div>";
	
	public static String format(String content, String tag){		
		if(addHTMLTAG) return String.format(FORMAT_HTML_TAG, tag, content);
		return content;
	}
	
	public static String formatPri(int pri){
		String s = new String();
		String content = new String();
		switch(pri){
		case PRIORITY_NONE:
			s = PRIORITY_NONE_TAG;
			content = "None";
			
			break;
		case PRIORITY_LOW:
			s = PRIORITY_LOW_TAG;
			content = "Low";
			break;
			
		case PRIORITY_MEDIUM:
			s = PRIORITY_MEDIUM_TAG;
			content = "Medium";
			break;
		case PRIORITY_HIGH:
			s = PRIORITY_HIGH_TAG;
			content = "High";
			break;
		case PRIORITY_URGENT:
			s = PRIORITY_URGENT_TAG;
			content = "Urgent";
			break;
		
		default:
			s = PRIORITY_NONE_TAG;
			content = "None";
			break;
			
		}
		s = String.format(FORMAT_HTML_TAG, s,content);
		if(addHTMLTAG == true) return s;
		return content;
	}
	
}


class StringUtils {
    public static String center(String s, int size) {
        return center(s, size, " ");
    }

    public static String center(String s, int size, String pad) {
        if (pad == null)
            throw new NullPointerException("pad cannot be null");
        if (pad.length() <= 0)
            throw new IllegalArgumentException("pad cannot be empty");
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }
}
