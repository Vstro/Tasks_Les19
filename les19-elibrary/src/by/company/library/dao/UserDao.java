package by.company.library.dao;

import by.company.library.bean.Role;
import by.company.library.bean.User;
import by.company.library.dao.exception.DAOException;

public interface UserDao {
	public User registerUser(String login, String password) throws DAOException;
	public User logination(String login, String password) throws DAOException;
	public boolean changeUserRole(String login, Role newRole) throws DAOException;
}
