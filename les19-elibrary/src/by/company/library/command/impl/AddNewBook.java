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
		
		try {
			if (params.length > 2) {
				title = params[2];
			}
			if (params.length > 3) {			
				price = Integer.parseInt(params[3]);			
			}
			if (params.length > 4) {			
				access = Role.valueOf(params[4]);			
			}		
		
			ServiceFactory.getInstance().getUpdateLibraryService().addNewBook(title, price, access);
		
		} catch (NumberFormatException e) {
			throw new CommandException("Incorrect book price!", e);
		} catch (IllegalArgumentException e) {
			throw new CommandException("UnrecognizableRole", e);		
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		
		return params[0] + " New book was succesfully added!";
	}

}
