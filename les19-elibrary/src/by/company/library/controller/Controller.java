package by.company.library.controller;

import by.company.library.command.Command;
import by.company.library.command.exception.CommandException;

public class Controller {
	private final CommandProvider provider = new CommandProvider();
	
	public String doAction(String request){
		String commandName = request.split("\\s+")[1];
		String response = request.split("\\s+")[0];
				
		try {
			Command command = provider.getCommand(commandName);
			response = command.execute(request);
		} catch (CommandException e) {
			System.out.println(e.getMessage());
			response += " Error occured, please try again after a while";
		}
		
		return response;
	}
}
