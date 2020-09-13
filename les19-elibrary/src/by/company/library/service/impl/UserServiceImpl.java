package by.company.library.service.impl;

import by.company.library.bean.Role;
import by.company.library.bean.User;
import by.company.library.dao.DAOFactory;
import by.company.library.dao.UserDao;
import by.company.library.dao.exception.DAOException;
import by.company.library.service.UserService;
import by.company.library.service.exception.ServiceException;

public class UserServiceImpl implements UserService{

	@Override
	public User logination(String login, String password) throws ServiceException {
		User loggedUser;
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDao userDAO = daoFactory.getUserDAO();
		
		try {
			loggedUser = userDAO.logination(login, password);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return loggedUser;
	}

	@Override
	public User registerUser(String login, String password) throws ServiceException {
		User signedUser;
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDao userDAO = daoFactory.getUserDAO();
		
		try {
			signedUser = userDAO.registerUser(login, password);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return signedUser;
	}

	@Override
	public boolean changeUserRole(String login, Role newRole) throws ServiceException {
		boolean success;
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDao userDAO = daoFactory.getUserDAO();
		
		try {
			success = userDAO.changeUserRole(login, newRole);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return success;
	}

}
