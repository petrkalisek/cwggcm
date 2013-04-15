package cz.gcm.cwg.exceptions.login;

import cz.gcm.cwg.exceptions.LoginException;

public class ToManyLoginRequest extends LoginException{
	public ToManyLoginRequest(String message) {
		super(message);
	}
}
