import java.util.*;

public class Manager extends User{
	ArrayList<Property> owned = new ArrayList<>();
	
	static {
		//Initialize owned.
		ArrayList<Property> owned; 
	}
	
	public Manager(String name, int id, float balance) { 
		super(name,id,balance);
	}
	
	public Manager(Object[] arr, Map<String, Integer> map, ArrayList<Property> prop_arr1){
		String name_temp =(String) arr[0];
		int no_of_owned_prop = map.get(name_temp);
		int x = arr.length - no_of_owned_prop - 3;
		this.name = (String)arr[0];
		this.id= (int)arr[1];
		this.balance= (float)arr[2];
		for(int i=3;i<x-1+4;i++) {
			for(Property p:prop_arr1) {
				if(p.name.equals((String)arr[i]))
					current_booking.add(p);
			}
			
		}
		
		for(int i= x+4; i<arr.length; i++){
			for(Property p:prop_arr1) {
				if(p.name.equals((String)arr[i]))
					owned.add(p);
			}
		}
	}
	
	public Property add_property(String p_name, String p_address, float p_price) {
		
		Property p = new Property(p_name,p_address,p_price,this.name);
		owned.add(p); // We have to save this in database.
		return p;
		//We also have to add this property to database of all properties.
	}
	
	@Override
	Object[] export() {
//		Object[] list = new Object[super.export().length + owned.size()]; 
		int l= current_booking.size();
		Object[] list= new Object[l+3+owned.size()];
		list[0]= this.name;
		list[1]= this.id;
		list[2]= this.balance;
		for(int i=3;i<l+3;i++) {
			list[i] = current_booking.get(i-3).name;
		}
		for(int i=l+3;i<l+3+owned.size();i++) {
			list[i] = owned.get(i-l-3).name;
		}
		
		return list;
	}

}
