package by.company.library.dao;

import java.util.List;

import by.company.library.bean.Book;
import by.company.library.bean.Role;
import by.company.library.dao.exception.DAOException;

public interface BookDao {	
	public void add(Book book) throws DAOException;
	public List<Book> allLibrary(Role access) throws DAOException;
}
