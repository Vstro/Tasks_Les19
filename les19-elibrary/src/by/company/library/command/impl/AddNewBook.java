package by.company.library.command.impl;

import by.company.library.bean.Role;
import by.company.library.command.Command;
import by.company.library.command.exception.CommandException;
import by.company.library.service.ServiceFactory;
import by.company.library.service.exception.ServiceException;

public class AddNewBook implements Command{

	@Override
	public String execute(String request) throws CommandException {
		String[] params = request.split("\\s+");
		String title = "Unknown";
		int price = 0;
		Role access = Role.JUNIOR;
		
		if (params.length > 2) {
			title = params[2];
		}
		if (params.length > 3) {
			try {
				price = Integer.parseInt(params[3]);
			} catch (NumberFormatException e) {
				throw new CommandException("Incorrect book price!", e);
			}
		}
		if (params.length > 4) {
			access = Role.valueOf(params[4]);
		}
		
		try {
			ServiceFactory.getInstance().getUpdateLibraryService().addNewBook(title, price, access);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		
		return params[0] + " New book was succesfully added!";
	}

}
