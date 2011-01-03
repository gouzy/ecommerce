package fr.alma.ecommerce.frontal;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Client {
	
	private static FrontalRemote frontal;

	/**
	 * @param args
	 * @throws NamingException 
	 */
	public static void main(String[] args) throws NamingException {
		
		Context context = new InitialContext();
		frontal = (FrontalRemote) context.lookup("FrontalServer/remote");
		
		frontal.runConsole();
	}

}
