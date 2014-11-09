package edu.dynamic.dynamiz.UI;

//A0119397R

/**
 * For V0.3 we tried to switch to using JavaFX for GUI
 * This TagFormat class is reserved for adding html div tag to string content 
 * Hence facilitates GUI to display certain string according to its div class tag
 */
class TagFormat{
	private static boolean addHTMLTAG = false;
	public static final String SUCCESS = "sucess";
	public static final String HELP = "help";
	public static final String ERORR = "error";
	public static final String PROMPT = "prompt";

	public static final String PRIORITY = "PRIORITY_HEADER";
	public static final String TASK_ID = "ID";
	public static final String TASK_DESCRIPTION = "DESC";
	public static final String TASK_STATUS = "STATUS";
	public static final String START_TIME = "Start";
	public static final String END_TIME = "End";
	public static final String TIME = "time";

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


	private static final String FORMAT_HTML_TAG = "<div class = %s >%s</div>";

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

