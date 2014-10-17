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
	static final boolean HTML_TAG = false;
	static final String UPDATE_COMMAND = "update";

	private static final String FORMAT_FEEDBACKSTRING = "ID: %1$s\n"+"Desc: %2$s\n"+"Priority: %3$d\n"+
			"Deadline: %4$s\n"+"Status: %5$s";
	
	private static final String FORMAT_FEEDBACKSTRING_HTML = 
									"ID: <div class:\"%s\">%1$s</div>\n"
									+"Desc:<div class:\"%s\">%2$s</div>\n"
									+"Priority: <div class:\"%s\">%3$d</div>\n"
									+"Deadline: <div class:\"%s\">%4$s</div>\n"
									+"Status: <div class:\"%s\">%5$s</div>";
	
	public String dateFormatter(Calendar c){
		String s = String.format("%1$tm,%1$te",c);
		return s;	
	}
	public String dateFormatter(Date d){
		
		String s = String.format("%tm,%td,%ty", d);
		return s;
	}
	public String timeFormatter(Date d){
		String s = String.format("%tH:%tM", d);
		return s;
	}
	

	public String displayWelcomeMessage(){
		return WELCOME_MESSAGE;
	}
	
	
	
	public String displayTitleLine() {
		String s=String.format("%-20s%15s   %12s  %8s  %8s   ", "Task","Status","Priority","Start Time","End Time");	
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
		 s = TagFormat.format(s, TagFormat.PROMPT); 
		return s;
	}
//	public String displayEnterCommandPrompt() {
//		String s = new String ("Please Enter Command:");
//		return s;
//	}


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
		 tag = TagFormat.format(tag, TagFormat.PROMPT);
		return tag;
	}

	public String displayPrompt(String promptMessage) {
		 promptMessage = TagFormat.format(promptMessage, TagFormat.PROMPT);
		return promptMessage;
	}

	
	public String displayFeedback(Feedback commandFeedback) {
		assert commandFeedback!=null;
		
		String s = new String(); 
		int t = getFeedbackTag(commandFeedback);
		switch(t){
		
		case HELP_FEEDBACK_TAG:
			HelpFeedback hf = (HelpFeedback)commandFeedback; 
			s = hf.getHelpContent();		
			 s = TagFormat.format(s, TagFormat.HELP);
			break;
			
		case ERROR_FEEDBACK_TAG:
			ErrorFeedback ef = (ErrorFeedback)commandFeedback; 
			s =ef.getCommandType()+" unsuccessful!"+"\n";
			 s = TagFormat.format(s, TagFormat.ERORR);		
			s+=" "+ef.getMessage();		
			break;
			
		case SUCCESS_FEEDBACK_TAG:
			
			SuccessFeedback sf = (SuccessFeedback) commandFeedback;
			
			String sMsg = sf.getCommandType()+" successfully!";
			 sMsg = TagFormat.format(sMsg, TagFormat.SUCCESS);
			sMsg = sMsg+"\n";	
			
			StringBuilder a = new StringBuilder();
			getFeedbackContent(a,sf);
			
			
			s = sMsg + a.toString();
			
			break;
		default:
			//s = commandFeedback.getCommandType();
			s = commandFeedback.getOriginalCommand();
			
		}
		return s;		
	}
	
	
	
	private void getFeedbackContent(StringBuilder a, SuccessFeedback sf){
		ToDoItem[] list = sf.getAffectedItems();
		assert list!=null;
		
		if(sf.getCommandType().equals(UPDATE_COMMAND)){
			assert(2==list.length);
			a.append("Item affected:").append("\n");
		
			a.append(formatTask(list[0])).append("\n");
			
			a.append("Updated:").append("\n");
			a.append(formatTask(list[1])).append("\n");
			
		}
		else{ 
			for( int i = 0 ; i< list.length; i++){
			a.append(formatTask(list[i]));
			a.append("\n");
		}			
		}
		
		
	}
	
	private String formatTask(ToDoItem item){
		final String FORMAT_FEEDBACKSTRING = "ID: %1$s\n"+"Desc: %2$s\n"+"Priority: %3$d\n"+
				"Deadline: %4$s\n"+"Status: %5$s";
		
		final String FORMAT_FEEDBACKSTRING_HTML = 
										"ID: <div class:\"%s\">%1$s</div>\n"
										+"Desc:<div class:\"%s\">%2$s</div>\n"
										+"Priority: <div class:\"%s\">%3$d</div>\n"
										+"Deadline: <div class:\"%s\">%4$s</div>\n"
										+"Status: <div class:\"%s\">%5$s</div>";
		assert item!=null;
		StringBuilder sb = new StringBuilder();
		String ID = item.getId();
		 ID = TagFormat.format(ID, TagFormat.TASK_ID);
		String des = item.getDescription();
		 des = TagFormat.format(des, TagFormat.TASK_DESCRIPTION);
		int pri = item.getPriority();
		String prioS = TagFormat.formatPri(pri);
		String stas = item.getStatus();
		stas = TagFormat.format(stas, TagFormat.TASK_STATUS);
		sb.append(ID).append("\n").
		append(des).append("\n").
		append(prioS).append("\n").
		append(stas).append("\n");
		if(item instanceof TaskItem){
			TaskItem t = (TaskItem)item;
			String ddl = t.getDeadlineString();
			ddl = TagFormat.format(ddl, TagFormat.END_TIME);
			sb.append(ddl).append("\n");
		}
		else if (item instanceof EventItem){
			EventItem t = (EventItem)item;
			String starT = t.getStartDateString();
			starT = TagFormat.format(starT, TagFormat.START_TIME);
			String endT = t.getEndDateString();
			endT =  TagFormat.format(endT, TagFormat.END_TIME);
			sb.append(starT).append("\n").
			append(endT).append("\n");
			
		}
		
		return sb.toString();
	}
	
	private int getFeedbackTag(Feedback f){
		String t =f.getClassName();
		
		if(t.equals("SuccessFeedback")) return SUCCESS_FEEDBACK_TAG;
		if(t.equals("ErrorFeedback")) return ERROR_FEEDBACK_TAG;	
		if(t.equals("HelpFeedback")) return HELP_FEEDBACK_TAG;
		
		return FEEDBACK_TAG;		
		
	}

	
	
	@Override
	public String displayHelpPage() {
		StringBuilder sb = new StringBuilder();
		String title = StringUtils.center("Help Page", 9);
		sb.append(title).append("\n");
		return sb.toString();
	}	
}

class TagFormat{
	//HTML Tag
	//PRIORITY_TAG
	public static final String SUCCESS = "";
	public static final String HELP = "";
	public static final String ERORR = "";
	public static final String PROMPT = "";
	
	public static final String PRIORITY = "";
	public static final String TASK_ID = "";
	public static final String TASK_DESCRIPTION = "";
	public static final String TASK_STATUS = "";
	public static final String START_TIME = "";
	public static final String END_TIME = "";
	public static final String TIME = "";
	
	public static final String PRIORITY_URGENT= "";
	public static final int TAG_URGENT= 5;
	
	public static final String PRIORITY_HIGH = "";
	public static final int TAG_HIGH= 4;
	
	public static final String PRIORITY_MEDIUN = "";
	public static final int TAG_MEDIUM= 3;
	
	public static final String PRIORITY_LOW = "";
	public static final int TAG_LOW= 2;
	
	public static final String PRIORITY_NONE = "";
	public static final int TAG_NONE= 1;
	
	
	
	private static final String FORMAT_HTML_TAG = "<div class:\"%s\" >%s</div>";
	
	public static String format(String content, String tag){
		if(!DisplayFormatter.HTML_TAG) return content;
		return String.format(FORMAT_HTML_TAG, tag, content);
	}
	
	public static String formatPri(int tag){
		if(!DisplayFormatter.HTML_TAG) return String.valueOf(tag);
		String tagS = new String();
		switch(tag){
		case TAG_URGENT:
			tagS = PRIORITY_URGENT;
			break;
			
		case TAG_HIGH:
			tagS = PRIORITY_HIGH;
			break;
			
		case TAG_MEDIUM:
			tagS = PRIORITY_MEDIUN;
			break;
			
		case TAG_LOW:
			tagS = PRIORITY_LOW;
			break;
			
		case TAG_NONE:
			tagS = PRIORITY_NONE;
			break;
		
		
		}
		return String.format(tagS, String.valueOf(tag));
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
