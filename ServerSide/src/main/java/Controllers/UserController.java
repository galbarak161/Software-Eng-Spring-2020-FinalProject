package Controllers;

import java.util.List;

import CloneEntities.CloneUser;
import Hibernate.HibernateMain;
import Hibernate.Entities.User;
import UtilClasses.Login;

public class UserController {

	private ServerOperations serverHandler;

	public UserController() {
		serverHandler = ServerOperations.getInstance();
	}

	/**
	 * handleLogInRequest(Login data)
	 * 
	 * function check users's username and passwords and keeps user from login while
	 * account is till logged from another place, and updates log status in DB.
	 * 
	 * @param data contains username and password entered
	 * @return if the information is correct and he is not logged in the system
	 *         returns new CloneUsert
	 * @throws Exception
	 */
	public CloneUser handleLogInRequest(Login data) throws Exception {
		List<User> userList = HibernateMain.getDataFromDB(User.class);
		for (User user : userList) {
			if ((user.getUserName().equals(data.getUserName())) && (user.getPassword().equals(data.getPassword()))
					&& (user.isLoggedIn() == false)) {
				user.setLoggedIn(true);
				if (HibernateMain.UpdateDataInDB(user) == 1)
					return user.createClone();
			}
		}
		return null;
	}

	/**
	 * handleLogOutRequest(int userId)
	 * 
	 * function checkis user is log in and if he is the function update login status
	 * in DB
	 * 
	 * @param userId the users id , who want to log out
	 * @return -1 if user is not found to be logged in , otherwise it found the user
	 *         and updates status.
	 * @throws Exception
	 */
	public int handleLogOutRequest(int userId) throws Exception {
		List<User> userList = HibernateMain.getDataFromDB(User.class);
		for (User user : userList) {
			if (user.getId() == userId) {
				user.setLoggedIn(false);
				return HibernateMain.UpdateDataInDB(user);
			}
		}
		return -1;
	}
}
