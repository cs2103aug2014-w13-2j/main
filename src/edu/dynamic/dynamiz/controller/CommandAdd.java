package edu.dynamic.dynamiz.controller;


import edu.dynamic.dynamiz.parser.Option;
import edu.dynamic.dynamiz.parser.OptionType;
import edu.dynamic.dynamiz.parser.Options;

public class CommandAdd implements Command {
	private CommandType commandType;
	private String param;
	private Options options;
	
	public CommandAdd(CommandType commandType, Options options, String param) {
		if (isValidParam(param)) {
			this.commandType = commandType;
			this.param = param;
			this.options = extractOptions(options);
		} else {
			throw new IllegalArgumentException("Invalid param");
		}
	}
	
	
	public Options extractOptions(Options options) {
		Options opts = new Options();
		
		for (OptionType optType: commandType.getApplicableOptions()) {
			Option opt = options.getOptions(optType).get(0); // Assume that there is one Option
			opts.add(opt);
		}
		return opts;
	}
	
	private boolean isValidParam(String param) {
		return (param != null) && (!param.isEmpty());
	}
	@Override
	public void execute() {
		
	}
	
	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return null;
	}

}
