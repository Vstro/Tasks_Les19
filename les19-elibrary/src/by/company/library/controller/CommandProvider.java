package by.company.library.controller;

import java.util.HashMap;
import java.util.Map;

import by.company.library.command.Command;
import by.company.library.command.exception.CommandException;
import by.company.library.command.impl.AddNewBook;
import by.company.library.command.impl.ChangeUserRole;
import by.company.library.command.impl.Logination;
import by.company.library.command.impl.Registration;
import by.company.library.command.impl.ShowLibrary;

final class CommandProvider {
	final private Map<String, Command> commands = new HashMap<>();

	CommandProvider() {
		commands.put("LOGIN", new Logination());
		commands.put("SIGNUP", new Registration());
		commands.put("ADDBOOK", new AddNewBook());
		commands.put("CHANGEROLE", new ChangeUserRole());
		commands.put("SHOWLIB", new ShowLibrary());
	}

	Command getCommand(String commandName) throws CommandException {
		Command command = commands.get(commandName);
		if (command == null) {
			throw new CommandException("Incorrect command name!");
		}
		return command;
	}

}
