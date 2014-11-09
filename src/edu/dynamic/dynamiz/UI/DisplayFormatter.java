package edu.dynamic.dynamiz.UI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.*;

import edu.dynamic.dynamiz.structure.*;

//@author A0119397R
/**
 * Acts as the information interpreter of Feedback items and formatter for UI
 * */

public class DisplayFormatter implements DisplayerInterface {
	
	static private final String NULL_STRING = "null object";
	/**
	 * @param 
	 * @return String
	 * Format Calendar object to readable string
	 */
	public String dateFormatter(Calendar c){
		String s = String.format("%1$tm,%1$te",c);
		return s;	
	}
	/**
	 * @param 
	 * @return 
	 * Format MyDate object to readable string
	 */
	public String dateFormatter(MyDate d){
		String s = String.format("%tm,%td,%ty", d);
		return s;
	}
	/**
	 * @param d
	 * @return
	 * Format MyDate object to readable string
	 */
	public String timeFormatter(MyDate d){
		String s = String.format("%tH:%tM", d);
		return s;
	}
	
	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayWelcomeMessage()
	 */
	public String displayWelcomeMessage(){
		return WELCOME_MESSAGE;
	}

	/**
	 * @return Title Line for task list 
	 */
	public String displayTitleLine() {
		String s=String.format("| %-3s| %-26s| %-9s| %-17s| %-17s| %-9s  |\n","ID", "Description","Priority","Start Time","End Time","Status");	
		return s;
	}

	
	private String displayDividingLine() {
		String s = new String("------------------------------------------------------------------------------------------------\n");
		return s;
	}
	private String displayParaLine() {
		String s = new String("----------------------------------------\n");
		return s;
	}
	
	/** 
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayStringList(ArrayList<String>)
	 */
	@Override
	public String displayStringList(ArrayList<String> arr) {	
		if(arr == null) return NULL_STRING;
		StringBuilder s = new StringBuilder();

		for(int i=0;i<arr.size();i++){
			if(!arr.get(i).isEmpty())
				s.append(arr.get(i).trim()).append("\n");
		}

		return s.toString();	
	}
	
	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayTaskItem(TaskItem)
	 */
	@Override
	public String displayTaskItem(TaskItem task) {
		assert task!=null;
		return task.toString();
	}

	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayTaskFile(TaskItem)
	 */
	@Override
	public String displayTaskFile(TaskItem task) {
		assert task!=null;
		return task.toFileString();
	}

	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayTaskFeedback(TaskItem)
	 */
	@Override
	public String displayTaskFeedback(TaskItem task) {
		assert task!=null;
		return task.getFeedbackString();
	}

	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayTaskList(ArrayList<TaskItem>)
	 */
	@Override
	public String displayTaskList(ArrayList<TaskItem> taskList) {
		assert taskList!=null;

		StringBuilder s = new StringBuilder();
		for(int i = 0; i< taskList.size(); i++){
			s.append(taskList.get(i).toString()).append("\n");
		}
		return s.toString();
	}

	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayTaskList(TaskItem[])
	 */
	@Override
	public String displayTaskList(TaskItem[] taskList) {
		assert taskList!=null;
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< taskList.length; i++){
			s.append(taskList[i].toString()).append("\n");
		}
		return s.toString();
	}

	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayEventFeedback(EventItem)
	 */
	@Override
	public String displayEventFeedback(EventItem event) {
		assert event!=null;
		return event.getFeedbackString();	
	}

	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayEventFile(EventItem)
	 */
	@Override
	public String displayEventFile(EventItem event) {
		assert event!=null;
		return event.toFileString();
	}
	
	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayEventItem(EventItem)
	 */
	@Override
	public String displayEventItem(EventItem event) {
		assert event!=null;
		return event.toString();
	}

	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayEventList(ArrayList<EventItem>)
	 */
	@Override
	public String displayEventList(ArrayList<EventItem> eventList) {
		assert eventList!=null;
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< eventList.size(); i++){
			s.append(eventList.get(i).toString()).append("\n");
		}
		return s.toString();
	}
	
	/**
	 * @see edu.dynamic.dynamiz.UI.DisplayerInterface#displayEventList(EventItem[])
	 */
	@Override
	public String displayEventList(EventItem[] eventList) {
		assert eventList!=null;
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< eventList.length; i++){
			s.append(eventList[i].toString()).append("\n");
		}
		return s.toString();
	}
	@Override
	public String displayToDoItem(ToDoItem todoItem) {
		assert todoItem !=null;
		return todoItem.toString();
	}
	@Override
	public String displayToDoFeedback(ToDoItem todoItem) {
		assert todoItem !=null;
		return todoItem.getFeedbackString();
	}
	@Override
	public String displayToDoFile(ToDoItem todoItem) {
		assert todoItem !=null;
		return todoItem.toFileString();
	}
	@Override
	public String displayToDoList(ArrayList<ToDoItem> todoList) {
		assert todoList !=null;
		StringBuilder s = new StringBuilder();
		for(int i = 0; i< todoList.size(); i++){
			s.append(todoList.get(i).toString()).append("\n");
		}
		return s.toString();
	}
	@Override
	public String displayToDoList(ToDoItem[] todoList) {
		assert todoList !=null;		
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
			tag = ENTER_COMMAND_STR;
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
		return tag;
	}
	public String displayPrompt(String promptMessage) {
		return promptMessage;
	}		
	
	/**
	 * Receive a @param Feedback Object 
	 * 
	 * @return ArrayList<StrIntPair>
	 */
	
	public ArrayList<StrIntPair> displayFeedback(Feedback commandFeedback) {
		ArrayList<StrIntPair> displayContentList = new ArrayList<StrIntPair>();
		assert commandFeedback!=null;	
		String s = new String(); 
		int t = getFeedbackTag(commandFeedback);
		switch(t){	
		/**
		 * Check which subclass this Feedback Object belongs to and format accordingly
		 */
		case HELP_FEEDBACK_TAG:
			HelpFeedback hf = (HelpFeedback)commandFeedback; 
			s = hf.getHelpContent();
			s+="\n";
			displayContentList.add(new StrIntPair(s));
			break;		
		case ERROR_FEEDBACK_TAG:
			ErrorFeedback ef = (ErrorFeedback)commandFeedback; 	
			s=ef.getMessage();
			s+="\n";
			displayContentList.add(new StrIntPair(s));
			break;			
		case SUCCESS_FEEDBACK_TAG:
			SuccessFeedback sf = (SuccessFeedback) commandFeedback;
			s  = sf.getCommandType()+" successfully!";
			s = s+"\n";	
			displayContentList.add(new StrIntPair(s));	
			getSucFeedbackContent(displayContentList,sf);		
			break;
		default:
			s = "Invalid Instruction\n";
			displayContentList.add(new StrIntPair(s));
		}
		return  displayContentList;	
	}

	private void getSucFeedbackContent(ArrayList<StrIntPair> displayContentList, SuccessFeedback sf){
		assert(displayContentList!=null);
		ToDoItem[] list = sf.getAffectedItems();
		if(list==null){
			displayContentList.add(new StrIntPair("The list is empty!\n"));
			return;
		}		
		/**
		 * Check which command this SuccessFeedback is, and format accordingly
		 */
		if(sf.getCommandType().equalsIgnoreCase(SHOW_COMMAND)||sf.getCommandType().equalsIgnoreCase(ADD_COMMAND)){
			assert(1==list.length);
			displayContentList.add(new StrIntPair(displayDividingLine()));
			formatTaskChunk(displayContentList,list[0]);
			return;
		}

		else if(sf.getCommandType().equalsIgnoreCase(UPDATE_COMMAND)){
			assert(2 == list.length);
			displayContentList.add(new StrIntPair(displayParaLine()));
			displayContentList.add(new StrIntPair("Item affected:\n"));
			formatTaskChunk(displayContentList,list[0]);
			displayContentList.add(new StrIntPair(displayParaLine()));
			displayContentList.add(new StrIntPair("Updated Item:\n"));
			formatTaskChunk(displayContentList,list[1]);		
		}

		else{ 
			displayContentList.add(new StrIntPair(displayDividingLine()));
			displayContentList.add(new StrIntPair(displayTitleLine()));
			displayContentList.add(new StrIntPair(displayDividingLine()));
			for (int i = 0;i <list.length;i++){
				formatTaskLine(displayContentList,list[i]);
			}
			displayContentList.add(new StrIntPair(displayDividingLine()));
		}
	}
	/**
	 * Format task list for list display
	 * @param contentList
	 * @param item
	 */
	private void formatTaskLine(ArrayList<StrIntPair> contentList,ToDoItem item){
		String strForID = "| %-2s | %-26s|";
		String strForPri = " %-9s";
		String strForTime = "| %-17s| %-17s|";
		String strForStat =	" %-9s  ";
		String strForEndLine = "|\n";
		assert item!=null;
		assert contentList!=null;
		int ID = item.getId();
		String des = item.getDescription();
		int pri = item.getPriority();
		String prioS = TagFormat.formatPri(pri);
		String starT = "";
		String endT = "";
		String stas = item.getStatus();
		int stasTag;
		if(stas.equalsIgnoreCase(STATU_PEND)) stasTag =STATU_PEND_TAG;
		else stasTag = STATU_COMPLETE_TAG;
		if(des.length()>=23){
			des = des.substring(0, 23);
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
			endT = t.getEndDateString();	

		}
		contentList.add(new StrIntPair(String.format(strForID, ID,des)));
		contentList.add(new StrIntPair(String.format(strForPri, prioS),pri));
		contentList.add(new StrIntPair(String.format(strForTime,starT,endT)));
		contentList.add(new StrIntPair(String.format(strForStat,stas),stasTag));
		contentList.add(new StrIntPair(strForEndLine));

	}

	/**Format task list for chunk display
	 * @param contentList
	 * @param item
	 */
	private void formatTaskChunk(ArrayList<StrIntPair> contentList,ToDoItem item){
		assert item!=null;
		assert contentList!=null;
		
		int ID = item.getId();
		String des = item.getDescription();
		int pri = item.getPriority();
		String prioS = TagFormat.formatPri(pri);
		String stas = item.getStatus();
		int stasTag;
		if(stas.equalsIgnoreCase(STATU_PEND)) stasTag =STATU_PEND_TAG;
		else stasTag = STATU_COMPLETE_TAG;
		contentList.add(new StrIntPair("ID: "+ID+"\n"+"Des: "+des+"\n"+"Priority: "));
		contentList.add(new StrIntPair(prioS+"\n",pri));
		if(item instanceof TaskItem){
			TaskItem t = (TaskItem)item;
			String ddl = t.getDeadlineString();
			contentList.add(new StrIntPair("Deadline: "+ddl+"\n"));
		}
		else if (item instanceof EventItem){
			EventItem t = (EventItem)item;
			String starT = t.getStartDateString();
			String endT = t.getEndDateString();
			contentList.add(new StrIntPair("Start Time: "+starT+"\n"));
			contentList.add(new StrIntPair("End Time:   "+endT+"\n"));
		}
		contentList.add(new StrIntPair("Status: "));
		contentList.add(new StrIntPair(stas+"\n",stasTag));
	}

	
	private int getFeedbackTag(Feedback f){
		String t =f.getClassName();
		if(t.equalsIgnoreCase("SuccessFeedback")) return SUCCESS_FEEDBACK_TAG;
		if(t.equalsIgnoreCase("ErrorFeedback")) return ERROR_FEEDBACK_TAG;	
		if(t.equalsIgnoreCase("HelpFeedback")) return HELP_FEEDBACK_TAG;
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

