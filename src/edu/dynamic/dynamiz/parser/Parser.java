package edu.dynamic.dynamiz.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dynamic.dynamiz.logic.Command;
import edu.dynamic.dynamiz.logic.CommandType;

/**
 * This is the boss right here
 * 
 * @author nhan
 *
 */
public class Parser {
	private static final char OPTION_SIGNAL_CHARACTER = '-';
	
	private CommandLine cmdLine;
	
	public Parser(String inputCmd) {
		if (inputCmd.isEmpty()) {
			throw new IllegalArgumentException("Null command is given.");
		}
		
		cmdLine = parse(inputCmd);
	}
	
	public CommandLine parse(String inputCmd) {
		if (inputCmd.isEmpty()) {
			throw new IllegalArgumentException("Null input command");
		}
		
		String commandWord = Util.getFirstWord(inputCmd);
		CommandType cmdType = CommandType.fromString(commandWord);
		
		inputCmd = Util.stripFirstWord(inputCmd);
		Options options = new Options();
		
		String allAliases = "";
		for (String alias: OptionType.getAllAliases()) {
			String toBeAppended = null;
			
			if (alias.charAt(0) == OPTION_SIGNAL_CHARACTER) {
				String wordRegex = "[\\w" + OPTION_SIGNAL_CHARACTER + "]";
				toBeAppended = String.format("(?<!%1$s)(?=%1$s)%2$s\\b|", wordRegex, alias);  
			} else {
				toBeAppended = "\\b" + alias + "\\b" + "|";
			}
			allAliases += toBeAppended;
		}
		
		allAliases = allAliases.substring(0, allAliases.length() - 1);
		
		String optRegex = "(?<=(" + allAliases + "))" + // Lookahead for option keyword
						  "(.*?)" + // Arguments sandwiched between 2 keywords or 1 keywords and end of line
						  "(?=(" + allAliases + "|$))"; // Another keyword or end of line
		System.out.println(optRegex);
		Pattern optPattern = Pattern.compile(optRegex, Pattern.CASE_INSENSITIVE);
		
		Matcher optMatcher = optPattern.matcher(inputCmd);
		String param = optPattern.split(inputCmd)[0];
		
		while(optMatcher.find()) {
			String opt = optMatcher.group(1);
			String[] values = optMatcher.group(2).split("\\s+");
			
			List<String> newValues = Util.removeEmptyStringsInArray(values);
			
			Option option = new Option(opt, newValues);
			options.add(option);
		}
		
		CommandLine cmdLine = new CommandLine(cmdType, options, param);
		
		return cmdLine;
	}
	
	public CommandLine getCommandLine() {
		return this.cmdLine;
	}
	
	public static void main(String[] args) {
		String cmd = "add Meeting CS2103 from 8h30 8h45 -s 8h42 to 9h45";
		Parser parser = new Parser(cmd);
		CommandLine cmdLine = parser.getCommandLine();
		
		System.out.println(cmdLine);
		
		
	}
}