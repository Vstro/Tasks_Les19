package by.company.library.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import by.company.library.bean.Role;
import by.company.library.bean.User;
import by.company.library.dao.UserDao;
import by.company.library.dao.exception.DAOException;

public class UserDaoImpl implements UserDao{
	
	private final static string userFileName = "users.txt";
	
	@Override
	public User registerUser(String login, String password) throws DAOException {
		// Сначала проверяем нет ли уже пользователя с таким логином
		FileReader reader;
		
		try {
			reader = new FileReader(userFileName);
		} catch (FileNotFoundException e) {
			throw new DAOException("FileNotFound", e);
		}
		
		BufferedReader br = new BufferedReader(reader);
		
		boolean found = false;
		String line;
		
		// Читаем первую строку
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
		
		String userLogin = null;
		while(line != null) {
			userLogin = line.split("\\s+")[0];
			if (userLogin.equals(login)) {
				found = true;
				break;
			}
			
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
		
		try {
			br.close();
		} catch (IOException e) {
			throw new DAOException("IOClosingError", e);
		}
		
		if (found) {
			throw new DAOException("SuchUserAlreadyExists");
		}

		// Добавляем нового пользователя в базу
		FileWriter out;
		
		try {
			out = new FileWriter(userFileName, true);
		} catch (IOException e) {
			throw new DAOException("FileNotFound", e);
		}
		
		BufferedWriter bw = new BufferedWriter(out);
		PrintWriter pw = new PrintWriter(bw);
		
		pw.println(login + " " + password + " " + "JUNIOR");
		pw.close();
		
		return new User(login, password);
	}

	@Override
	public User logination(String login, String password) throws DAOException {
		FileReader reader;
		
		try {
			reader = new FileReader(userFileName);
		} catch (FileNotFoundException e) {
			throw new DAOException("FileNotFound", e);
		}
		
		BufferedReader br = new BufferedReader(reader);
		
		boolean found = false;
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
		String[] userParams = null;
		while(line != null) {
			userParams = line.split("\\s+");
			if (userParams[0].equals(login) && userParams[1].equals(password)) {
				found = true;
				break;
			}
			
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
		
		try {
			br.close();
		} catch (IOException e) {
			throw new DAOException("IOClosingError", e);
		}
		
		if (found && userParams != null) {
			return new User(login, password, Role.valueOf(userParams[2]));
		} else {
			throw new DAOException("UserNotFound");
		}
	}

	@Override
	public boolean changeUserRole(String login, Role newRole) throws DAOException {
		RandomAccessFile raf;
		
		try {
			raf = new RandomAccessFile(userFileName, "rw");
		} catch (FileNotFoundException e) {
			throw new DAOException("FileNotFound", e);
		}
		
		String line;		
		try {
			line = raf.readLine();
		} catch (IOException e) {
			// Закрываем поток перед выходом из метода
			try {
				raf.close();
			} catch (IOException e1) {
				throw new DAOException("TwiceIOError", e);
			}
			
			throw new DAOException("IOError", e);
		}
		String[] userParams = null;
		while(line != null) {
			userParams = line.split("\\s+");
			
			if (userParams[0].equals(login)) {							
				// Считываем текущую роль целевого пользователя
				Role currentRole;		
				try {
					currentRole = Role.valueOf(userParams[2]);
				} catch (IllegalArgumentException e) {
					// Закрываем поток перед выходом из метода
					try {
						raf.close();
					} catch (IOException e1) {
						throw new DAOException("UnrecognizableRoleAndIOError", new IOException(e));
					}
					
					throw new DAOException("UnrecognizableRole", e);
				}
				
				if (!currentRole.equals(newRole)) {
					try {
						// Возвращаем позицию чтения/записи назад на длину самой длинной (в строковом представлении) роли + \r\n
						raf.seek(raf.getFilePointer() - Role.maxLength() - 2);
						// Затираем старую запись пробелами (на случай, если новая будет короче)
						for (int i = 0; i < Role.maxLength(); i++) {
							raf.writeByte(' ');
						}
						// Снова возвращаемся и пишем новую запись
						raf.seek(raf.getFilePointer() - Role.maxLength());
						raf.writeBytes(newRole.toString());
					} catch (IOException e) {
						// Закрываем поток перед выходом из метода
						try {
							raf.close();
						} catch (IOException e1) {
							throw new DAOException("TwiceIOError", e);
						}
						
						throw new DAOException("IOError", e);
					}
				}
				
				// Закрываем поток перед выходом из метода
				try {
					raf.close();
				} catch (IOException e) {
					throw new DAOException("IOError", e);
				}
				return true;
			}
			
			try {
				line = raf.readLine();
			} catch (IOException e) {
				// Закрываем поток перед выходом из метода
				try {
					raf.close();
				} catch (IOException e1) {
					throw new DAOException("TwiceIOError", e);
				}
				
				throw new DAOException("IOError", e);
			}
		}
		
		try {
			raf.close();
		} catch (IOException e) {
			throw new DAOException("IOClosingError", e);
		}
		
		return false;
	}
}
