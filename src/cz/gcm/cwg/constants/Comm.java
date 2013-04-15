package cz.gcm.cwg.constants;

public class Comm {
	
	public static final String API_BASE_URL = "http://test.cwg.gcm.cz/api/";
	public static final String API_URL_LOGIN = API_BASE_URL+"login";
	public static final String API_URL_LOGININFO = API_BASE_URL+"login-info";
	//public static final String API_LOGIN_CWGINFO = "http://192.168.1.203/gc/cwgApi/index.php";
	public static final String API_URL_CWGINFO = API_BASE_URL+"cwg/info";
	public static final String API_URL_MY_COLLECTION = API_BASE_URL+"collection/export";
	
	public static final String API_SESSION_NAME = "Session";
	public static final String API_USERNAME_NAME = "username";
	public static final String API_PASSWORD_NAME = "password";
	public static final String API_ERROR_NAME = "Error";
	public static final String API_SUCCESS_NAME = "Success";
	
	public static final String API_ERROR_LOGIN = "login";
	public static final String API_ERROR_LOGIN_NEEDED = "You must specify username and password.";
	public static final String API_ERROR_INVALID_LOGIN = "Invalid username or password.";
	public static final String API_ERROR_MUST_LOG_IN = "You must first log in.";
	
}
