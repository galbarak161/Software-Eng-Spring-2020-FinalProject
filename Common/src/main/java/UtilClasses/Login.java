package UtilClasses;

import java.io.Serializable;

public class Login implements Serializable{

	private static final long serialVersionUID = -7286024812867814714L;
	
	String userName;
	String password;
	
	public Login(String userName, String password) throws Exception {
		this.userName = userName;
		this.password = EncryptionTool.encrypt(password);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() throws Exception {
		return EncryptionTool.decrypt(password);
	}

}
