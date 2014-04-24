package genomizerdesktop;

import com.google.gson.Gson;

public class Genomizer {

    public static void main(String args[]) {
	System.out.println("Starting Genomizerk");
	/* Your scrum master was here */
	// Lester

	System.out.println("adam testar");
	System.out.println("HUR GÅR DET DÅ ADAM /CAPS?");
	/* Gson exempel */
	Gson gson = new Gson();
	LoginCommand login = new LoginCommand("kalle", "123");
	String json = gson.toJson(login);
	LoginCommand login2 = gson.fromJson(json, LoginCommand.class);
	System.out.println(login2.username + " " + login2.password);
	/* Gson exempel */
	GUI gui = new GUI();
    }
}
