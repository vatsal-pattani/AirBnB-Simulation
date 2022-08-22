import java.io.IOException;
import java.util.*;


public class User {
	String name;
	int id;
	float balance;
	public ArrayList<Property> current_booking = new ArrayList<>();
	static {
		// Initialize current_booking
	}
	public User() {
		
	}
	
	public User(String name, int id, float balance) { 
		this.name = name;
		this.id = id;
		this.balance = balance;
	}
	
	public User(Object[] arr, ArrayList<Property> prop_arr1){
		this.name= (String)arr[0];
		this.id= (int)arr[1];
		this.balance= (float)arr[2];
		for(int i=3;i<arr.length;i++) {
			for(Property p:prop_arr1) {
				if(p.name.equals((String)arr[i]))
					current_booking.add(p);
			}
		}
	}
	
	public static ArrayList<Property> search(int date, int nights, ArrayList<Property> prop_arr1) {
		
		ArrayList<Property> searched = new ArrayList<>();
//		int count=0;
		for(Property p:prop_arr1) {
			int count=0;
			System.out.println(p.name);
			for(int i=date-1; i<(date+nights-1);i++) {
				if(p.calendar[i] == 0)
					count++;
			}
			if(count==nights)
				searched.add(p);
		}
		return searched;
	}
	
	public float show_balance() {
		return this.balance;
	}
	
	public Property book_prop(Property p,int date, int nights, List<Manager> m_list) {
		
		if(p.price*nights<this.balance) {
			for(int i=date-1; i<(date+nights-1);i++) {
				p.calendar[i] = this.id;
			}
			
			current_booking.add(p);
			this.balance -= p.price*nights;
			System.out.println("Property booked for successfully!");
			for(Manager m : m_list) {
				if(m.name.equals(p.manager))
					m.balance += p.price*nights;
			}
			return p;
		}
		else {
			System.out.println("Insufficient Balance!");
			return p;
		}
	}
		
	
	public int cancel_prop(int n1, ArrayList<Property> prop_arr, int id) {
		
		Property selected = current_booking.get(n1);  //Assume some property is selected.
		prop_arr.remove(selected);
		int nights=0;
		for(int i=0; i<selected.calendar.length; i++) {
			if(selected.calendar[i]==id) {
				nights++;
				selected.calendar[i] = 0;
			}
		}
		prop_arr.add(selected);
		current_booking.remove(selected); 
		balance = balance + 100*nights;
		
		System.out.println("Booking cancelled successfully!");
		return nights;
	}
	
	public void display() {
		System.out.println("Your name: "+name);
		System.out.println("Your id: "+id);
		System.out.println("Your balance: "+balance);
		
		System.out.println("Details of your booked properties:");
		for(Property p: current_booking) {
			p.display();
			System.out.print("\n");
		}
	}
	
	Object[] export() {
		int l= current_booking.size();
		Object[] list= new Object[l+3];
		list[0]= this.name;
		list[1]= this.id;
		list[2]= this.balance;
		for(int i=3;i<l+3;i++) {
			list[i] = current_booking.get(i-3).name;
		}
		
		return list;
	}
	
	public Property add_property(String s, String s1, float f) {
		return null;
	}

}
