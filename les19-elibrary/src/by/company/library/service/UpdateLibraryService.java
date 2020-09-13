package by.company.library.service;

import java.util.List;

import by.company.library.bean.Book;
import by.company.library.bean.Role;
import by.company.library.service.exception.ServiceException;

public interface UpdateLibraryService {
	void addNewBook(String title, int price, Role access) throws ServiceException;
	List<Book> showLibrary(Role access) throws ServiceException;
}
