package edu.dynamic.dynamiz.structure;

//@author A0110781N
/**
 * Defines the feedback type for help command.
 * 
 * Constructor
 * HelpFeedback(String commandType, String command, String content)	//Creates a new instance of this feedback.
 * 
 * Public Methods
 * String getHelpContent()	//Gets the help page content.
 * String getClassName()	//Gets the String representation of this feedback's class.
 */
public class HelpFeedback extends Feedback {
    public static final String CLASSNAME = "HelpFeedback";
    
    //Main data member
    private String helpContents;
    
    /**
     * Creates a new instance of this feedback.
     * @param commandType the type of command this feedback is used for.
     * @param command The input command this feedback is used for.
     * @param content The help page content to be displayed.
     */
    public HelpFeedback(String commandType, String command, String content){
	super(commandType, command);
	setContent(content);
    }
    
    /**
     * Gets the content of the requested help page.
     * @return The content String of the requested help page.
     */
    public String getHelpContent(){
	return helpContents;
    }
    
    //Sets the help page content
    private void setContent(String content){
	this.helpContents = content;
    }
    
    @Override
    /**
     * Gets the String representation of this feedback's class.
     * @return The String representation of this feedback's class.
     */
    public String getClassName(){
	return CLASSNAME;
    }
}
