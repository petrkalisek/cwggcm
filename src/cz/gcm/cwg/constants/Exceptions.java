package cz.gcm.cwg.constants;



public class Exceptions {
	
	public static final int API_ERROR_101 = 101;	
	public static final int API_ERROR_102 = 102; 	//Přístup byl odepřen (z důvodu nedostatečných oprávnění).
	public static final int API_ERROR_103 = 103;	//Uživatel není přihlášen (session vypršela).
	
	public static final int API_ERROR_200 = 200;	//Chybné vstupní parametry
	public static final int API_ERROR_201 = 201; 	//Chybějící přihlašovací parametry.
	public static final int API_ERROR_202 = 202;	//Chybějící identifikace CWG (cwgno nebo název+verze).
	public static final int API_ERROR_203 = 203;	//Chybný formát.
	public static final int API_ERROR_204 = 204;	//Nebyl přiložen soubor.
	public static final int API_ERROR_205 = 205;	//Zadané heslo je příliš krátké.
	
}
