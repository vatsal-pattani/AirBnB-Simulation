
public class Property {
	public String name;
	public String address;
	public float price;
	public String manager;
	public int[] calendar=new int[31]; 
	
	Property(String name, String address, float price, String manager) {
		this.name= name;
		this.address = address;
		this.price= price;
		this.manager = manager; 
	}
	
	Property(Object[] arr){
		this.name= (String)arr[0];
		this.address= (String)arr[1];
		this.price= (float)arr[2];
		this.manager = (String)arr[3];
		for(int i=4;i<35;i++) {
			calendar[i-4]= (int)arr[i];
		}
	}
	
	boolean check_booked(int date) {
		if(calendar[date]== 1) {
			return true; // booked( not availaible)
		}
		else {
			return false; // avaliable
		}
	}
	
	Object[] export() {
		Object[] list= new Object[35];
		list[0]= name;
		list[1]= address;
		list[2]= price;
		list[3]= manager;
		for(int i=4;i<35;i++) {
			list[i]= calendar[i-4];
		}
		
		return list;
	}
	
	public void display() {
		System.out.println("Property name:" + name);
		System.out.println("Address: "+address);
	}
}
