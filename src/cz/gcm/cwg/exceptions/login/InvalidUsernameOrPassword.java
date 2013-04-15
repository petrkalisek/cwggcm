package cz.gcm.cwg.exceptions.login;

import cz.gcm.cwg.exceptions.LoginException;

public class InvalidUsernameOrPassword extends LoginException {
	
	public InvalidUsernameOrPassword(String message){
		super(message);
	}
}
