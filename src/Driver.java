import java.util.*;
import java.io.*;


public class Driver {

	int n;
	static User logged_in = new User("Vatsal",90,2000);
	//Initialization of ArrayLists
	static ArrayList<Property> prop_arr = new ArrayList<>();
	static List<User> user_list = new ArrayList<>();
	static List<Manager> manager_list = new ArrayList<>();
	static final Scanner sc1 = new Scanner(System.in);
	// --> Property array
	// --> User array
	// --> Manager array
	
	public static void Menu(int n) throws FileNotFoundException, IOException {
		
		System.out.println("1. Search(and Book)");
		System.out.println("2. Cancel Booking");
		System.out.println("3. Show Balance");
		
		if(n==1)
			System.out.println("4. Add Property");
		System.out.println("0. Save and Exit");
		
		System.out.println("Enter an integer corresponding to any option");
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			int x=0;
			x = sc1.nextInt();
			
			switch(x) {
			case 0:
				save();//Save everything
				System.out.println("Exiting...See you again!");
				System.exit(0);
				break;
			case 1:
				//Search
				while(true) {
					if(search(n)==true)
						break;
				}
				Menu(n);
				break;
			case 2:
				//Cancel
				Cancel();
				Menu(n);
				break;
			case 3:
				System.out.println("Your Current Balance:" +logged_in.show_balance());
				Menu(n);
				//Show balance
				break;
			case 4:
				if(n==1) {
					
					try {
						sc1.nextLine();
						System.out.println("Enter Name:");
						String p_name = sc1.nextLine();
						System.out.println("Enter Address:");
						String p_address = sc1.nextLine();
						System.out.println("Enter Price per night:");
						float p_price = sc1.nextFloat();
						Property p1 = logged_in.add_property(p_name, p_address, p_price);
						prop_arr.add(p1); //add property
						System.out.println("Property added successfully!");
					}
					catch(Exception e) {
						System.out.println("Enter valid input");
						System.out.println("Reverting to main menu");
						Menu(n);
					}
				}
					
				Menu(n);
				break;
			default:
				System.out.println("Enter valid Input");
				continue;
			}		
			break;
		}	
	}
	
	public static boolean search(int n) throws FileNotFoundException, IOException {
		//If User wants to search --##########################

		System.out.println("Enter date(first day) and Number of nights:");
		
		//Just put this into a while loop.

		int date=0;
		int nights=0;
		while(true) {
			try {
				System.out.println("Enter date:");
				date = sc1.nextInt();
				System.out.println("Enter nights:");
				nights = sc1.nextInt();
				
				try {
					if(date>31)
						throw new IOException("date should be from 1 to 31");
					if(nights>32-date)
						throw new IOException("Nights should be from "+date+" to 31");
					break;
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
					continue;
				}
			}
			catch(Exception e) {
				System.out.println("Enter integer input(format is DD NumOfNights)\n");
			}
		}
		ArrayList<Property> searched; 
		searched = User.search(date, nights, prop_arr);
		int count=1;
		if(searched.size()==0)
			System.out.println("No properties available");
		else
			System.out.println("Available properties are:");
		for(Property p: searched) {
			System.out.println(count+". " +"Name:" + p.name +"\n   Address:" + p.address + "\n   Price per night:" + p.price +"\n");
			count++;
		}
		
		System.out.println("1. Search again");
		System.out.println("2. Book from the list above");
		System.out.println("0. Return to Main Menu");
		int a;
		while(true) {
			try {
				System.out.println("Enter appropriate Integer:");
				a = sc1.nextInt(); //Will go in infinite loop if character is entered.
				if(a==1 || a==2 || a==0)
					break;
			}
			catch(Exception e) {
				
			}
		}
		if(a==0)
			Menu(n);
		if(a==1)
			return false; // search again
		if(a==2) {
			Booking(date, nights, searched);
			return true; //go to book
		}
		
		return true; //Terminate
		
		//#####################################(Search part ends here)
	}
	
	public static void Booking(int date, int nights, ArrayList<Property> searched) {
		//Booking -- ###########################
		System.out.println("Type index of property you want to book:");
		int a;
		while(true) {
			try {
				a = sc1.nextInt(); //Will go in infinite loop if character is entered.
				break;
			}
			catch(Exception e) {
				System.out.println("Enter appropriate Integer:");
			}
		}
		Property selected = searched.get(a-1);  //Assume some property is selected.
		prop_arr.remove(selected);
		
		prop_arr.add(logged_in.book_prop(selected, date, nights, manager_list));
		
		//selected.manager.add_funds();
		
		//################################# -- Booking Ends Here
	}
	public static void Cancel() {
		int count=1;
		if(logged_in.current_booking.size()==0)
			System.out.println("You have no Bookings!");
		else {
			System.out.println("Your Current bookings are:");
			for(Property p: logged_in.current_booking) {
				System.out.println(count+". " +"Name:" + p.name +"\n   Address:" + p.address + "\n   Price per night:" + p.price +"\n");
				count++;
			}
			
			System.out.println("Enter index of property you want to cancel");
			int n1;
			while(true) {
				try {
					System.out.println("Enter appropriate Integer:");
					n1 = sc1.nextInt(); //Will go in infinite loop if character is entered.
					break;
				}
				catch(Exception e) {
				}
			}
			String manager1 = logged_in.current_booking.get(n1-1).manager;
			
			int nights = logged_in.cancel_prop(n1-1, prop_arr, logged_in.id);
		
			for(Manager m:manager_list) {
				if(m.name.equals(manager1))
					m.balance = m.balance-100*nights;
			}
			
		}
		
	}
	
	public static Object[] login_function() {
		Object[] out = new Object[2];
		login l= new login();
		String username;
		System.out.println("Enter username:");
		username= sc1.nextLine();
		System.out.println("Enter password:");
		
		l.in(username, sc1.nextLine());
		//sc1.nextLine();
		
		if(l.search()== 0) {
			System.out.println("No such user exists");
			System.out.println("please try again");
			login_function();
		}
		if(l.search()== 1) {
			//Manager
			System.out.println("Welcome to AirBnB!");
			out[0]= username;
			out[1]= 1;
			return out;
		}
		else {
			// customer
			System.out.println("Welcome to AirBnB!");
			out[0]= username;
			out[1]= 2;
			return out;
		}
	}
	
	public static void save() throws FileNotFoundException, IOException{
		ObjectOutputStream o1 = new ObjectOutputStream(new FileOutputStream("User.txt"));
		o1.writeObject(user_list.size());
		for(User i:user_list) {
			o1.writeObject(i.export());
		}
		o1.close();
		
		ObjectOutputStream o2 = new ObjectOutputStream(new FileOutputStream("Manager.txt"));
		o2.writeObject(manager_list.size());
		for(Manager i:manager_list) {
			o2.writeObject(i.export());
		}
		
		o2.close();
		
		ObjectOutputStream o3 = new ObjectOutputStream(new FileOutputStream("Property.txt"));
		o3.writeObject(prop_arr.size());
		for(Property i:prop_arr) {
			o3.writeObject(i.export());
		}
		o3.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		ObjectInputStream i3 = new ObjectInputStream(new FileInputStream("Property.txt"));

		int n1= (int)i3.readObject();
		for(int i=0; i<n1; i++){
			prop_arr.add(new Property((Object[])i3.readObject()));
		}
		i3.close();
		
		ObjectInputStream i1 = new ObjectInputStream(new FileInputStream("User.txt"));
		n1 = (int)i1.readObject();
		for(int i=0; i<n1;i++) {
			
			user_list.add(new User((Object[])i1.readObject(),prop_arr));
		}
		i1.close();

		//code for finding how many propertys a specific manager owns

		Map<String, Integer> manager_map= new HashMap<>();

		for(Property p:prop_arr){
			if(manager_map.containsKey(p.manager)){
				manager_map.put(p.manager, manager_map.get(p.manager) +1);
			}
			else{
				manager_map.put(p.manager, 1);
			}
		}


		ObjectInputStream i2 = new ObjectInputStream(new FileInputStream("Manager.txt"));
		n1= (int)i2.readObject();
		for(int i=0; i<n1; i++){
			manager_list.add(new Manager((Object[])i2.readObject(), manager_map, prop_arr));
		}


		i2.close();
		
		//LOGIN---starts here---
		Object[] login_response;
		
		login_response = login_function();
		
		if((int)login_response[1]== 1) {
			for(Manager m:manager_list) {
				if(((String)login_response[0]).equals(m.name))
					logged_in = m;
			}
		}
		else {
			//Customer c1= new Customer(login_response[0], ); // constructor for customer
			for(User u:user_list) {
				if(((String)login_response[0]).equals(u.name))
					logged_in = u;
			}
		}
		//LOGIN---Ends here---
		
		//User logged_in; // = new Manager or new Customer.
		Menu((int)login_response[1]);
		
		
		//Display -- ################
		//If User wants to display his details.
		
		//logged_in.display();
		
		//##################### -- Display Ends here
	}
	
}
