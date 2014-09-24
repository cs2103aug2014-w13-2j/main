package edu.dynamic.dynamiz.structure;

/**
 * @author Hu Wenyan
 * An exception class built for Dynamiz
 */
public class DynamizException extends Exception {
		
		/**
		 * Used when an operation cannot be completed.
		 * <br>Will request the program to continue execution.
		 */
		public static final int LEVEL_RETRY = 0;
		public static final String LEVEL_RETRY_MESSAGE = "Retry operaton";
		
		/**
		 * Used when an initialization problem occurs.
		 * <br>Will request the program to restart from the beginning and re-initialize.
		 */	
		public static final int LEVEL_RESTART = 1;
		public static final String LEVEL_RESTART_MESSAGE = "Restart program";
		
		/**
		 * Used when a fatal problem or unknown exception occurs.
		 * <br>Will request the system to exit with error code <code>1</code>.
		 */		
		public static final int LEVEL_TERMINATE = 2;
		public static final String LEVEL_TERMINATE_MESSAGE = "Terminate program";
		
		public static final String LEVEL_UNKNOWN_MESSAGE = "Unknown level provided";
		
		
		public final int level;
		
		/**
		 * Default constructor of {@link FatalException}.
		 * @param level Fatality level (int) of the exception. Refer to {@link FatalException}.
		 * @param message A string message telling details about the exception. 
		 * Will be printed out to inform user.
		 * @see #LEVEL_RETRY
		 * @see #LEVEL_RESTART
		 * @see #LEVEL_TERMINATE
		 */
		public DynamizException(int level, String message){	
			super(message);
			this.level = level;
		}
		
		@Override
		public String getMessage(){
			String s = super.getMessage();
			return s;
		}
		
		private String getLevelMessage(int level){
			switch(level){
			case LEVEL_RETRY:
				return LEVEL_RETRY_MESSAGE;
			case LEVEL_RESTART :
				return LEVEL_RESTART_MESSAGE;
			case LEVEL_TERMINATE :
				return LEVEL_TERMINATE_MESSAGE;
			default:
				return LEVEL_UNKNOWN_MESSAGE;
			}
		}
}
