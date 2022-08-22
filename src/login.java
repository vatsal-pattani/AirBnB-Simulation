import java.util.*;
import java.io.*;

public class login {
	String username;
	String password;
	Map<String, String> users = new HashMap<String, String>();
	Map<String, String> admins = new HashMap<String, String>();
	public login() {
		try {
			FileInputStream admin = new FileInputStream("admin.properties");
			FileInputStream user = new FileInputStream("users.properties");
			Properties user_prop= new Properties();
			Properties admin_prop= new Properties();
			
			user_prop.load(user);
			admin_prop.load(admin);
			for (String key : user_prop.stringPropertyNames()) {
				   users.put(key, user_prop.get(key).toString());
				}
			for (String key : admin_prop.stringPropertyNames()) {
				   admins.put(key, admin_prop.get(key).toString());
				}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		
	}
	
	

	
	
	void in(String username,String password){
		this.username= username;
		this.password= password;
		
		//map.put(username, password);
//		try {
//			for(Map.Entry<String, String> me: map.entrySet()) {
//				properties.put(me.getKey(), me.getValue());
//			}
//			properties.store(new FileOutputStream("users.properties"), null);
//			}
//			catch(Exception e) {
//				System.out.println(e);
//			}
	}
	void print() {
		for(Map.Entry m : admins.entrySet()){    
		    System.out.println(m.getKey()+" "+m.getValue());    
		   } 
		
	}
	
	boolean search_users() {
		Map<String, String> search = new HashMap<>();
        search.put(this.username, this.password);
        
        boolean matches = search.entrySet()
                .stream()
                .anyMatch(it -> users.entrySet().contains(it));

        return matches;
        
	}
	
	boolean search_admins() {
		Map<String, String> search = new HashMap<>();
        search.put(this.username, this.password);
        
        boolean matches = search.entrySet()
                .stream()
                .anyMatch(it -> admins.entrySet().contains(it));

        return matches;
        
	}
	
	int search() {
		// 0= dosent exist
		// 1== admin
		// 2= user
		
		
		if(this.search_admins()) {
			return 1;
		}
		if(this.search_users()) {
			return 2;
		}
		else {
			return 0;
		}
		
	}
	
	

}