package by.company.library.command.impl;

import java.util.List;

import by.company.library.bean.Book;
import by.company.library.bean.Role;
import by.company.library.command.Command;
import by.company.library.command.exception.CommandException;
import by.company.library.service.ServiceFactory;
import by.company.library.service.exception.ServiceException;

public class ShowLibrary implements Command {

	@Override
	public String execute(String request) throws CommandException {
		String[] params = request.split("\\s+");
		Role access = Role.JUNIOR;
		List<Book> books;
		
		try {
			access = Role.valueOf(params[0]);				
				
			books = ServiceFactory.getInstance().getUpdateLibraryService().showLibrary(access);
			
		} catch (IllegalArgumentException e) {
			throw new CommandException("UnrecognizableRole", e);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		
		StringBuilder response = new StringBuilder();
		for (Book book: books) {
			response.append(book.getTitle() + "(" + book.getPrice() + " BYN) \n");
		}
		
		return params[0] + " " + response;
	}

}
