package by.company.library.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import by.company.library.bean.Book;
import by.company.library.bean.Role;
import by.company.library.dao.BookDao;
import by.company.library.dao.exception.DAOException;

public class BookDaoImpl implements BookDao {

	private static final String BOOK_FILE_NAME = "books.txt";
	
	@Override
	public void add(Book book) throws DAOException {
		FileWriter out;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		
		try {
			
			out = new FileWriter(BOOK_FILE_NAME, true);
			bw = new BufferedWriter(out);
			pw = new PrintWriter(bw);
			pw.println(book.getTitle() + " " + book.getPrice() + " " + book.getAccess());
			
		} catch (IOException e) {
			throw new DAOException("IOError", e);
		} finally {
			if (pw != null) {
				pw.close(); // Не кидает Exception, как ни странно
			}
		}	
	}

	@Override
	public List<Book> allLibrary(Role access) throws DAOException {
		List<Book> books = new ArrayList<Book>();
		FileReader reader = null;
		BufferedReader br = null;
		
		try {
			reader = new FileReader(BOOK_FILE_NAME);
			
			br = new BufferedReader(reader);
			
			// Читаем первую строку
			String line;
			line = br.readLine();
			
			String[] params;
			if (access.equals(Role.ADULT)) {
				while(line != null) {
					// Добавляем книгу
					params = line.split("\\s+");
					String title = params[0];
					int price = Integer.parseInt(params[1]);
					books.add(new Book(title, price));
					
					// Читаем следующую запись
					line = br.readLine();
				}			
			} else {
				while(line != null) {
					// Проверяем доступна ли книга для JUNIOR
					params = line.split("\\s+");
					String title = params[0];
					int price = Integer.parseInt(params[1]);
					String bookAccess = params[2];
					
					if (bookAccess.equals(Role.JUNIOR.toString())) {
						// Добавляем книгу;
						books.add(new Book(title, price));
					}
					
					// Читаем следующую запись
					line = br.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			throw new DAOException("FileNotFound", e);
		} catch (IOException e) {
			throw new DAOException("IOError", e);
		} catch (NumberFormatException e) {
			throw new DAOException("ParseIntError", e);
		} finally {		
			// Закрываем поток перед выходом из метода
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e1) {
				throw new DAOException("IOClosingError", e1);
			}
		}
		
		return books;
	}

}
