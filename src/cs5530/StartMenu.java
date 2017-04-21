package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StartMenu {

	public static void displayMenu()
	{
		 System.out.println("------Welcome to Project 1, Phase 2------");
    	 System.out.println("1. login to an existing account");
    	 System.out.println("2. Create a new account");
    	 System.out.println("3. Exit");
    	 System.out.println("Please enter your choice:");
	}
	
	public static void main(String[] args) {
		Connector con=null;
		String choice;
        String sql=null;
        int c=0;
         try
		 {
			//remember to replace the password
			 	 con= new Connector();
	             System.out.println ("Database connection established");
	         
	             BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	             
	             while(true)
	             {
	            	 displayMenu();
	            	 while ((choice = in.readLine()) == null && choice.length() == 0);
	            	 try{
	            		 c = Integer.parseInt(choice);
	            	 }catch (Exception e)
	            	 {        		 
	            		 continue;
	            	 }
	            	 if (c<1 | c>3)
	            		 continue;
	            	 if (c==1)
	            	 {
	            	        String login;
	            	        String password;
	            	        
	            		 System.out.println("please enter your login:");
	            		 while ((login = in.readLine()) == null && login.length() == 0);
	            		 if(login.equals(""))
	            			 login = null;
	            		 System.out.println("please enter your password:");
	            		 while ((password = in.readLine()) == null && password.length() == 0);
	            		 if(password.equals(""))
	            			 password = null;
	            		 User currentUser = User.validateUser(login, password);
	            		 if(currentUser != null)
	            		 {
	            			 System.out.println("Welcome " + currentUser.firstName + " " 
	            					 + currentUser.lastName);
	            			 MainUserMenu.displayMenu(currentUser);
	            		 }
	            		 else
	            		 {
	            			 System.out.println("Username or password is invalid");
	            			 continue;
	            		 }
	            	 }
	            	 else if (c==2)
	            	 {	 
						String login;
						String password;
						String fname;
						String lname;
						String addr;
						String phone;
	            		 
	            		System.out.println("please enter your new account information");
	            		 
	            		System.out.println("Login:");
	            		while ((login = in.readLine()) == null && login.length() == 0);
	            		System.out.println("Account Password:");
	            		while ((password = in.readLine()) == null && password.length() == 0);
	            		System.out.println("First Name:");
	            		while ((fname = in.readLine()) == null && fname.length() == 0);
	            		System.out.println("Last Name:");
	            		while ((lname = in.readLine()) == null && lname.length() == 0);
	            		System.out.println("Address:");
	            		while ((addr = in.readLine()) == null && addr.length() == 0);
	            		System.out.println("Phone Number:");
	            		while ((phone = in.readLine()) == null && phone.length() == 0);
	            		 
	            		//User newUser = User.createUser(login, password, fname, lname, addr, phone);
	            		 
	            		/*if(newUser != null)
	            		{
	            			System.out.println("Welcome " + newUser.firstName + " " + newUser.lastName);
	            			MainUserMenu.displayMenu(newUser);
	            		}
	            		else
	            		{
	            			System.out.println("User creation failed, possibly due to your login already being in use");
	            		}*/
	            		 
	            	 }
	            	 else
	            	 {   
	            		 System.out.println("EoM");
	            		 Connector.stmt.close(); 
	        
	            		 break;
	            	 }
	             }
		 }
         catch (Exception e)
         {
        	 e.printStackTrace();
        	 System.err.println ("Either connection error or query execution error!");
         }
         finally
         {
        	 if (con != null)
        	 {
        		 try
        		 {
        			 con.closeConnection();
        			 System.out.println ("Database connection terminated");
        		 }
        	 
        		 catch (Exception e) { /* ignore close errors */ }
        	 }	 
         }

	}

}
