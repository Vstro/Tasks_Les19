package by.company.library.service.impl;

import java.util.List;

import by.company.library.bean.Book;
import by.company.library.bean.Role;
import by.company.library.dao.DAOFactory;
import by.company.library.dao.exception.DAOException;
import by.company.library.service.UpdateLibraryService;
import by.company.library.service.exception.ServiceException;

public class UpdateLibraryServiceImpl implements UpdateLibraryService {

	@Override
	public void addNewBook(String title, int price, Role access) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		
		try {
			daoFactory.getBookDAO().addBook(new Book(title, price, access));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}	
	}

	@Override
	public List<Book> showLibrary(Role access) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		List<Book> books;
		
		try {
			books = daoFactory.getBookDAO().showLibrary(access);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return books;
	}

}
