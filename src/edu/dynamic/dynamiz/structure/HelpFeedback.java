package edu.dynamic.dynamiz.structure;

/**
 * Defines the feedback type for help command.
 * @author zixian
 */
public class HelpFeedback extends Feedback {
    public static final String CLASSNAME = "HelpFeedback";
    
    //Main data member
    private String helpContents;
    
    //Constructor
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
    public String getClassName(){
	return CLASSNAME;
    }
}
