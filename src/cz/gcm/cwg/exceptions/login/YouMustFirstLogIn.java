package cz.gcm.cwg.exceptions.login;

import cz.gcm.cwg.exceptions.LoginException;

public class YouMustFirstLogIn extends LoginException{
	public YouMustFirstLogIn(String message) {
		super(message);
	}
}
