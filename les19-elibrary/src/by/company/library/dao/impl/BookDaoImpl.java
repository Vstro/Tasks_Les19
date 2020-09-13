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

	@Override
	public void addBook(Book book) throws DAOException {
		FileWriter out;
		
		try {
			out = new FileWriter("books.txt", true); // При создании пишет в конец файла, если второй аргумент == true
		} catch (IOException e) {
			throw new DAOException(e);
		}
		
		BufferedWriter bw = new BufferedWriter(out);
		PrintWriter pw = new PrintWriter(bw);
		
		pw.println(book.getTitle() + " " + book.getPrice() + " " + book.getAccess());
		pw.close();
	}

	@Override
	public List<Book> showLibrary(Role access) throws DAOException {
		List<Book> books = new ArrayList<Book>();
		FileReader reader;
		
		try {
			reader = new FileReader("books.txt");
		} catch (FileNotFoundException e) {
			throw new DAOException("FileNotFound", e);
		}
		
		BufferedReader br = new BufferedReader(reader);
				
		// Читаем первую строку
		String line;
		try {
			line = br.readLine();
		} catch (IOException e) {
			// Закрываем поток перед выходом из метода
			try {
				br.close();
			} catch (IOException e1) {
				throw new DAOException("TwiceIOError", e);
			}
			
			throw new DAOException("IOError", e);
		}
		
		String[] params;
		if (access.equals(Role.ADULT)) {
			while(line != null) {
				// Добавляем книгу
				params = line.split("\\s+");
				books.add(new Book(params[0], Integer.parseInt(params[1])));
				
				// Читаем следующую запись
				try {
					line = br.readLine();
				} catch (IOException e) {
					// Закрываем поток перед выходом из метода
					try {
						br.close();
					} catch (IOException e1) {
						throw new DAOException("TwiceIOError", e);
					}
					
					throw new DAOException("IOError", e);
				}
			}			
		} else {
			String bookAccess;
			while(line != null) {
				// Проверяем доступна ли книга для JUNIOR
				params = line.split("\\s+");
				bookAccess = params[2];
				if (bookAccess.equals(access.toString())) {
					// Добавляем книгу);
					books.add(new Book(params[0], Integer.parseInt(params[1])));
				}
				
				// Читаем следующую запись
				try {
					line = br.readLine();
				} catch (IOException e) {
					// Закрываем поток перед выходом из метода
					try {
						br.close();
					} catch (IOException e1) {
						throw new DAOException("TwiceIOError", e);
					}
					
					throw new DAOException("IOError", e);
				}
			}
		}
		
		try {
			br.close();
		} catch (IOException e) {
			throw new DAOException("IOClosingError", e);
		}
		
		return books;
	}

}
