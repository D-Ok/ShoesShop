package shoesShop.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import shoesShop.Exceptions.ArgumentException;
import shoesShop.Tools.Security;

public class Seller {
	private String name;
	private String surname;
	private String mid_name;
	private int num;
	private boolean isDirector;
	private String notes;
	private String password;
	private String username;
	private static DBConnector db = DBConnector.getInstance();
	
	private Seller() {}
	
	/**
	 * create user in the database
	 * @param num
	 * @param name
	 * @param surname
	 * @param midname
	 * @param isDirector
	 * @param notes
	 * @param username
	 * @param password
	 * @throws ArgumentException
	 */	public Seller(int num, String name, String surname,
			String midname, boolean isDirector, String notes, 
			String username, String password) throws ArgumentException {
		
		if(num<10000000 && DBConnector.isValidName(name) && DBConnector.isValidName(surname) 
				&&(!(DBConnector.isNotEmpty(midname)) || DBConnector.isValidName(midname))
				&& DBConnector.isNotEmpty(username) && DBConnector.isNotEmpty(password) 
				&& notes.length()<101 && password.length()>4 
				&& password.length()<46 && username.length()<46)  {
			String p = Security.encrypt(username, password);
			if(!(isUniqueNum(num))) throw new ArgumentException("Number employee mast be unique");
			if(!(isUniqueUsername(username))) throw new ArgumentException("Username mast be unique");
				
			try {
		    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `sellers`"
		    			                                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		    	ps.setInt(1, num);
		    	ps.setString(2, name);
		    	ps.setString(3, surname);
		    	ps.setString(4, midname);
		    	ps.setBoolean(5, isDirector);
		    	ps.setString(6, notes);
		    	ps.setString(7, p);
		    	ps.setString(8, username);
		    	ps.executeUpdate();
		    	ps.close();
	    	} catch(SQLIntegrityConstraintViolationException e) {
	    		System.out.println("User with this number exist.");
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
			
			this.name = name;
			this.surname = surname;
			this.mid_name = midname;
			this.num = num;
			this.isDirector = isDirector;
			this.notes = notes;
			this.password = p;
			this.username = username;
			
		} else {
			throw new ArgumentException("Something wrong with arguments");
		}
	};
	
	protected static boolean isUniqueNum(int n) {
		String command = "SELECT * "
				+ "FROM sellers "
				+ "WHERE n_employee = '"+n+"'";
		
		return DBConnector.isUnique(command);
	}
	
	private boolean isUniqueUsername(String username) {
		String command = "SELECT is_director "
				+ "FROM sellers "
				+ "WHERE username = '"+username+"'";
		
		return DBConnector.isUnique(command);
	}
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) throws ArgumentException {
		if(DBConnector.isValidName(name)) {
            String command = "UPDATE `sellers` "
            		        + "SET name = '"+name+"' "
            				+ "WHERE n_employee = '"+num+"'";
			if(db.update(command)) this.name = name;
		} else throw new ArgumentException();
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) throws ArgumentException {
		if(DBConnector.isValidName(surname)) {
            String command = "UPDATE `sellers` "
            		        + "SET surname = '"+surname+"' "
            				+ "WHERE n_employee = '"+num+"'";
			if(db.update(command)) this.surname = surname;
		} else throw new ArgumentException();
	}
	
	public String getMid_name() {
		return mid_name;
	}
	
	public void setMid_name(String mid_name) throws ArgumentException {
		if(!DBConnector.isNotEmpty(mid_name) || DBConnector.isValidName(mid_name)) {
            String command = "UPDATE `sellers` "
            		        + "SET mid_name = '"+mid_name+"' "
            				+ "WHERE n_employee = '"+num+"'";
			if(db.update(command)) this.mid_name = mid_name;
		} else throw new ArgumentException();
	}
	
	public int getNum() {
		return num;
	}

	public boolean isDirector() {
		return isDirector;
	}
	public void setDirector(boolean isDirector) {
		    String command = "UPDATE `sellers` "
            		        + "SET is_director = '"+isDirector+"' "
            				+ "WHERE n_employee = '"+num+"'";
			if(db.update(command)) this.isDirector = isDirector;
	}
	
	public String getNote() {
		return notes;
	}

	public void setNote(String notes) {
		 String command = "UPDATE `sellers` "
 		        + "SET notes = '"+notes+"' "
 				+ "WHERE n_employee = '"+num+"'";
	     if(db.update(command)) this.notes = notes;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws ArgumentException {
		if(DBConnector.isNotEmpty(password) && password.length()>5 && password.length()<46) {
			String p = Security.encrypt(username, password);
            String command = "UPDATE `sellers` "
            		        + "SET password = '"+p+"' "
            				+ "WHERE n_employee = '"+num+"'";
			if(db.update(command)) this.password = p;
		} else throw new ArgumentException();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws ArgumentException {
		if(DBConnector.isNotEmpty(username) && password.length()<46) {
            String command = "UPDATE `sellers` "
            		        + "SET username = '"+username+"' "
            				+ "WHERE n_employee = '"+num+"'";
			if(db.update(command)) this.username = username;
		} else throw new ArgumentException();
	}
	
	public static LinkedList<Seller> getAll() { 
		
		LinkedList<Seller> result = new LinkedList<Seller>();
		String command = "SELECT * FROM sellers";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	Seller s;
	    	while(rs.next()) {
	    		s = new Seller();
	    		s.num = rs.getInt("n_employee");
	    		s.name = rs.getString("name");
	    		s.surname = rs.getString("surname");
	    		s.mid_name = rs.getString("mid_name");
	    		s.isDirector = rs.getBoolean("is_director");
	    		s.notes = rs.getString("notes");
	    		s.password = rs.getString("password");
	    		s.username = rs.getString("username");
	    	    result.add(s);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
     public static Seller getSeller(int num) { 
		Seller s = null;
		String command = "SELECT * FROM `sellers`"
				+ "WHERE n_employee = '"+num+"'";
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		s = new Seller();
	    		s.num = rs.getInt("n_employee");
	    		s.name = rs.getString("name");
	    		s.surname = rs.getString("surname");
	    		s.mid_name = rs.getString("mid_name");
	    		s.isDirector = rs.getBoolean("is_director");
	    		s.notes = rs.getString("notes");
	    		s.password = rs.getString("password");
	    		s.username = rs.getString("username");
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static boolean isDirector(String username, String password) { 
		boolean res = false;
		String p = Security.encrypt(username, password);
		String command = "SELECT is_director "
				+ "FROM sellers "
				+ "WHERE password = '"+p+"' AND username = '"+username+"'";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if (rs.next())
	    		res = rs.getBoolean("is_director");
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
    public static Seller getSeller(String username, String password) {
    	String p = Security.encrypt(username, password);
		Seller s = null;
		String command = "SELECT * "
				+ "FROM sellers "
				+ "WHERE password = '"+p+"' AND username = '"+username+"'";
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		s = new Seller();
	    		s.num = rs.getInt("n_employee");
	    		s.name = rs.getString("name");
	    		s.surname = rs.getString("surname");
	    		s.mid_name = rs.getString("mid_name");
	    		s.isDirector = rs.getBoolean("is_director");
	    		s.notes = rs.getString("notes");
	    		s.password = rs.getString("password");
	    		s.username = rs.getString("username");
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static boolean delete(int num) {
		String command = "DELETE FROM sellers "
				+ "WHERE n_employee = "+num+" ";
		return db.update(command);
	}
	
	public static HashMap<Pair<Integer, String>, Integer> getStatisticOfSeller(){
		HashMap<Pair<Integer, String>, Integer> result = new HashMap<Pair<Integer, String>, Integer>();
		String command = "SELECT n_employee, surname, (SELECT SUM(num) "
				+ " FROM cheque_rows INNER JOIN cheques ON cheque_rows.n_cheque = cheques.n_cheque "
				+ " WHERE sellers.n_employee = cheques.n_employee) AS quontity"
				+ " FROM sellers";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	Pair<Integer, String> s;
	    	while(rs.next()) {
	    		int num = rs.getInt("n_employee");
	    		String surname = rs.getString("surname");
	    		int quontity = rs.getInt("quontity");
	    		s = new Pair<Integer, String>(num, surname);
	    	    result.put(s, quontity);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static HashMap<Pair<Integer, String>, Integer> getStatisticOfOfPeriod(int days){
		LocalDate date =  LocalDate.now().minusDays(days);
		System.out.println(date);
		HashMap<Pair<Integer, String>, Integer> result = new HashMap<Pair<Integer, String>, Integer>();
		String command = "SELECT n_employee, surname, (SELECT SUM(num) "
				+ " FROM cheque_rows INNER JOIN cheques ON cheque_rows.n_cheque = cheques.n_cheque "
				+ " WHERE sellers.n_employee = cheques.n_employee AND ch_date >= '"+date+"') AS quontity"
				+ " FROM sellers";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	Pair<Integer, String> s;
	    	while(rs.next()) {
	    		int num = rs.getInt("n_employee");
	    		String surname = rs.getString("surname");
	    		int quontity = rs.getInt("quontity");
	    		s = new Pair<Integer, String>(num, surname);
	    	    result.put(s, quontity);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static HashMap<Integer, Integer> getStatisticOfOfPeriod(LocalDate from, LocalDate to){
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		String command = "SELECT n_employee, SUM(num)" + 
		        		" FROM cheque_rows INNER JOIN cheques ON cheque_rows.n_cheque = cheques.n_cheque" + 
			        	" WHERE ch_date >= '"+from+"' AND ch_date <= '"+to+"'" + 
			        	" GROUP BY n_employee;";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	Pair<Integer, String> s;
	    	while(rs.next()) {
	    		int num = rs.getInt("n_employee");
	    		int quontity = rs.getInt("quontity");
	    	    result.put(num, quontity);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
//	SELECT n_employee, SUM(num)
//	FROM cheque_rows INNER JOIN cheques
//	WHERE ch_date >= '2020-04-21' AND ch_date <= '2020-04-22'
//	GROUP BY n_employee;

	@Override
	public String toString() {
		return "Seller [name=" + name + ", surname=" + surname + ", mid_name=" + mid_name + ", num=" + num
				+ ", isDirector=" + isDirector + ", notes=" + notes + ", password=" + password + ", username=" + username
				+ "]";
	}


	
	
}
