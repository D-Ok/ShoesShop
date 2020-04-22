package shoesShop.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import shoesShop.Exceptions.ArgumentException;

public class Produser {
	private int n_company;
	private String name;
	private String city;
	private String region;
	private String street;
	private String building;
	private int office;
	private String main_phone;
	private String second_phone;
	private String third_phone;
	private String email;
	private String notes;
	private static DBConnector db = DBConnector.getInstance();
	
	private Produser() {};
	
	public Produser(int n_company, String name, String city, String region, String street, String building,
			int office, String main_phone, String second_phone, String third_phone, String email, String notes) throws ArgumentException {
		
			if(n_company>100000000) throw new ArgumentException("Number of company mast have length < 9");
			if(!(isUniqueNum(n_company))) throw new ArgumentException("Number of company mast be unique");
			if(!DBConnector.isValidName(name)) throw new ArgumentException("Incorrect name");
			if(!DBConnector.isValidName(city) || !DBConnector.isValidName(region)
					|| !DBConnector.isOnlyLetters(street) || !DBConnector.isValidBuilding(building)
					|| office>1000) throw new ArgumentException("Something wrong with address");
			if(notes.length()>100)  throw new ArgumentException("Notes is too long");
			if(!DBConnector.isValidEmail(email))  throw new ArgumentException("Email is incorrect");
			if(!DBConnector.isValidPhone(main_phone)) throw new ArgumentException("Not valid phone number");
			if(DBConnector.isNotEmpty(second_phone)) 
				if(!DBConnector.isValidPhone(second_phone)) throw new ArgumentException("Not valid phone secondary number");
			if(DBConnector.isNotEmpty(third_phone))
					if(!DBConnector.isValidPhone(third_phone)) throw new ArgumentException("Not valid phone secondary number");
			try {
		    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `produsers`"
		    			                                            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		    	ps.setInt(1, n_company);
		    	ps.setString(2, name);
		    	ps.setString(3, city);
		    	ps.setString(4, region);
		    	ps.setString(5, street);
		    	ps.setString(6, building);
		    	ps.setInt(7, office);
		    	ps.setString(8, main_phone);
		    	ps.setString(9, second_phone);
		    	ps.setString(10, third_phone);
		    	ps.setString(11, email);
		    	ps.setString(12, notes);
		    	ps.executeUpdate();
		    	ps.close();
	    	} catch(SQLIntegrityConstraintViolationException e) {
	    		System.out.println("User with this number exist.");
	    	} catch (SQLException e) {
				e.printStackTrace();
			}

			this.n_company = n_company;
			this.name = name;
			this.city = city;
			this.region = region;
			this.street = street;
			this.building = building;
			this.office = office;
			this.main_phone = main_phone;
			this.second_phone = second_phone;
			this.third_phone = third_phone;
			this.email = email;
			this.notes = notes;
			
	}
	
	protected static boolean isUniqueNum(int n) {
		String command = "SELECT * "
				+ "FROM  produsers "
				+ "WHERE n_company = '"+n+"'";
		
		return DBConnector.isUnique(command);
	}
	
	
	public String getcity() {
		return city;
	}
	public void setcity(String city) throws ArgumentException {
			if(DBConnector.isValidName(city)) {
	            String command = "UPDATE `produsers` "
	            		        + "SET city = '"+city+"' "
	            				+ "WHERE n_company = '"+n_company+"'";
				if(db.update(command)) this.city = city;
			} else throw new ArgumentException();
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) throws ArgumentException {
		if(DBConnector.isValidName(region)) {
            String command = "UPDATE `produsers` "
            		        + "SET region = '"+region+"' "
            				+ "WHERE n_company = '"+n_company+"'";
			if(db.update(command)) this.region = region;
		} else throw new ArgumentException();
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) throws ArgumentException {
		if(DBConnector.isValidName(street)) {
            String command = "UPDATE `produsers` "
            		        + "SET street = '"+street+"' "
            				+ "WHERE n_company = '"+n_company+"'";
			if(db.update(command)) this.street = street;
		} else throw new ArgumentException();
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) throws ArgumentException {
		if(DBConnector.isValidBuilding(building)) {
            String command = "UPDATE `produsers` "
            		        + "SET building = '"+building+"' "
            				+ "WHERE n_company = '"+n_company+"'";
			if(db.update(command)) this.building = building;
		} else throw new ArgumentException();
	}
	public int getOffice() {
		return office;
	}
	public void setOffice(int office) throws ArgumentException {
		if(office<1000) {
            String command = "UPDATE `produsers` "
            		        + "SET office = '"+office+"' "
            				+ "WHERE n_company = '"+n_company+"'";
			if(db.update(command)) this.office = office;
		} else throw new ArgumentException();
	}
	public String getMain_phone() {
		return main_phone;
	}
	
	public void setMain_phone(String main_phone) throws ArgumentException {
		if(DBConnector.isValidPhone(main_phone)) {
            String command = "UPDATE `produsers` "
            		        + "SET main_phone = '"+main_phone+"' "
            				+ "WHERE n_company = '"+n_company+"'";
			if(db.update(command)) this.main_phone = main_phone;
		} else throw new ArgumentException();
	}
	
	public String getSecond_phone() {
		return second_phone;
	}
	public void setSecond_phone(String second_phone) throws ArgumentException {
		if(!DBConnector.isNotEmpty(second_phone) || DBConnector.isValidPhone(second_phone)) {
            String command = "UPDATE `produsers` "
            		        + "SET second_phone = '"+second_phone+"' "
            				+ "WHERE n_company = '"+n_company+"'";
			if(db.update(command)) this.second_phone = second_phone;
		} else throw new ArgumentException();
	}
	public String getThird_phone() {
		return third_phone;
	}
	public void setThird_phone(String third_phone) throws ArgumentException {
		if(!DBConnector.isNotEmpty(third_phone) || DBConnector.isValidPhone(third_phone)) {
            String command = "UPDATE `produsers` "
            		        + "SET third_phone = '"+third_phone+"' "
            				+ "WHERE n_company = '"+n_company+"'";
			if(db.update(command)) this.third_phone = third_phone;
		} else throw new ArgumentException();
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws ArgumentException {
		if(DBConnector.isValidEmail(email)) {
            String command = "UPDATE `produsers` "
            		        + "SET email = '"+email+"' "
            				+ "WHERE n_company = '"+n_company+"'";
			if(db.update(command)) this.email = email;
		} else throw new ArgumentException();
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) throws ArgumentException {
		if(notes.length()<101) {
            String command = "UPDATE `produsers` "
            		        + "SET notes = '"+notes+"' "
            				+ "WHERE n_company = '"+n_company+"'";
			if(db.update(command)) this.notes = notes;
		} else throw new ArgumentException();
	}
	public int getN_company() {
		return n_company;
	}
	public String getName() {
		return name;
	}
	
	
	public static LinkedList<Produser> getAll() { 
		
		LinkedList<Produser> result = new LinkedList<Produser>();
		String command = "SELECT * FROM produsers";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	Produser s;
	    	while(rs.next()) {
	    		s = new Produser();
	    		s.n_company = rs.getInt("n_company");
				s.name = rs.getString("name");
				s.city = rs.getString("city");
				s.region = rs.getString("region");
				s.street = rs.getString("street");
				s.building = rs.getString("building");
				s.office = rs.getInt("name");;
				s.main_phone = rs.getString("main_phone");
				s.second_phone = rs.getString("second_phone");
				s.third_phone = rs.getString("third_phone");
				s.email = rs.getString("email");
				s.notes = rs.getString("notes");
	    	    result.add(s);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
     private static Produser getOne(String command) { 
    	Produser s = null;
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		s = new Produser();
	    		s.n_company = rs.getInt("n_company");
				s.name = rs.getString("name");
				s.city = rs.getString("city");
				s.region = rs.getString("region");
				s.street = rs.getString("street");
				s.building = rs.getString("building");
				s.office = rs.getInt("office");;
				s.main_phone = rs.getString("main_phone");
				s.second_phone = rs.getString("second_phone");
				s.third_phone = rs.getString("third_phone");
				s.email = rs.getString("email");
				s.notes = rs.getString("notes");
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
     
     public static Produser getOne(int n) { 
 		String command = "SELECT * FROM `produsers`"
 				+ " WHERE n_company = '"+n+"'";
 	
 		return getOne(command);
 	}
     
     public static Produser getProdOfModel(String n_model) {
    	 String command = "SELECT * "
    	 		+ "FROM `produsers` INNER JOIN `consignment_notes` ON produsers.n_company = consignment_notes.n_company"
    	 		+ " INNER JOIN `consignment_note_rows` ON consignment_notes.n_cons = consignment_note_rows.n_cons"
    	 		+ " INNER JOIN `products` ON products.id = consignment_note_rows.id_product"
    	 		+ " INNER JOIN `product_models` ON product_models.n_model = products.n_model"
  				+ " WHERE product_models.n_model = '"+n_model+"'";
  		return getOne(command);
     }
     
     public static LinkedList<ProductModel> getAllModel(int n_company) {
    	 String command = "SELECT DISTINCT * "
    	 		+ "FROM `product_models` "
  				+ " WHERE product_models.n_model IN ("
  				+ " SELECT n_model"
  				+ " FROM `products` INNER JOIN `consignment_note_rows` ON products.id = consignment_note_rows.id_product " + 
  			   	" INNER JOIN `consignment_notes` ON consignment_notes.n_cons = consignment_note_rows.n_cons" + 
  				" INNER JOIN `produsers` ON produsers.n_company = consignment_notes.n_company"
  				+ " WHERE produsers.n_company = "+n_company+")";
  		return ProductModel.getAll(command);
     }
     
	
	public static boolean deleteCompany(int n) {
		String command = "DELETE FROM `produsers`"
				+ " WHERE n_company = '"+n+"'";
		return db.update(command);
	}
	
	public static HashMap<Integer, Integer> getPeriodStatistic(LocalDate from, LocalDate to){
		HashMap<Integer, Integer> res = new HashMap<Integer, Integer>();
		String command = "SELECT n_company, SUM(num) AS f_num" + 
				" FROM consignment_note_rows INNER JOIN consignment_notes ON consignment_note_rows.n_cons = consignment_notes.n_cons" + 
				" WHERE c_date >= '2020-04-21' AND c_date <= '2020-04-22'" + 
				" GROUP BY n_company";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	int comp, num;
	    	while(rs.next()) {
	    		comp = rs.getInt("n_company");
				num = rs.getInt("f_num");
				res.put(comp, num);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public String toString() {
		return "Produsers [n_company=" + n_company + ", name=" + name + ", city=" + city + ", region=" + region
				+ ", street=" + street + ", building=" + building + ", office=" + office + ", main_phone=" + main_phone
				+ ", second_phone=" + second_phone + ", third_phone=" + third_phone + ", email=" + email + ", notes="
				+ notes + "]";
	}
	
	
	
}
