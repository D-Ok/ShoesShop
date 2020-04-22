package shoesShop.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

import shoesShop.Exceptions.ArgumentException;

public class Product {

	private int id;
	private String n_model;
	private int size;
	private int number;
	private String color;
	private static DBConnector db = DBConnector.getInstance();
	
	private Product() {};
	
	public Product(String n_model, int size, String color, int number) throws ArgumentException {
		
		if(!DBConnector.isOnlyLetters(color)) throw new ArgumentException("Not valid name");
		if(size<26 && size>46) throw new ArgumentException("Not valid size");
		if(ProductModel.isUniqueNum(n_model)) throw new ArgumentException("This n_model doesn`t exist");
		if(!isUnique(size, color, n_model)) throw new ArgumentException("This combination is already exists");
		if(number<0) throw new ArgumentException("Not valid number");
		
		int id = generateId();
		try {
	    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `products`"
	    														+ "(n_model, size, num, color, id)"
	    														+ "VALUES (?, ?, ?, ?, ?)");
	    	ps.setString(1, n_model);
	    	ps.setInt(2, size);
	    	ps.setInt(3, number);
	    	ps.setString(4, color);
	    	ps.setInt(5, id);
	    	ps.executeUpdate();
	    	ps.close();
    	} catch(SQLIntegrityConstraintViolationException e) {
    		System.out.println("Color with this number exist.");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.n_model = n_model;
		this.size = size;
		this.number = number;
		this.color=color;
		this.id = id;
	}
	
	private int generateId() {
		Random r = new Random();
		int id = 1;
		while(!isKeyUnique(id)) id = r.nextInt(1000000000); 
		return id;
	}
	
	public static void addNumber(int id, int n) throws ArgumentException {
		int cur = Product.getNumber(id);
		System.out.println("HERE "+cur);
		int newN = cur+n;
		
		if(newN>-1 && !Product.isKeyUnique(id)) {
		String command = "UPDATE `products` "
     		        + "SET num = '"+newN+"' "
     				+ "WHERE id = "+id+"";
		
		 db.update(command);
		} else throw new ArgumentException();
	}
	
	public Product(String n_model, int size, String color) throws ArgumentException {
		this(n_model, size, color, 0);
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) throws ArgumentException {
		if(size>26 && size<46 && isUnique(size, color, n_model)) {
            String command = "UPDATE `products` "
            		        + "SET size = '"+size+"' "
            				+ "WHERE id = "+id+"";
			if(db.update(command)) 
				this.size = size;
		} else throw new ArgumentException();
	}

	public int getNumber() {
		return number;
	}
	
	

	public String getColor() {
		return color;
	}

	public void setColor(String color) throws ArgumentException {
		if(DBConnector.isOnlyLetters(color) && isUnique(size, color, n_model)) {
            String command = "UPDATE `products` "
            		        + "SET color = '"+color+"' "
            				+ "WHERE id = "+id+"";
			if(db.update(command)) 
				this.color = color;
		} else throw new ArgumentException();
	}

	public int getId() {
		return id;
	}

	protected void setNumber(int number) throws ArgumentException {
		if(number>-1) {
            String command = "UPDATE `products` "
            		        + "SET num = '"+number+"' "
            				+ "WHERE id = "+id+"";
			if(db.update(command)) 
				this.number = number;
		} else throw new ArgumentException();
	}

	public String getN_model() {
		return n_model;
	}

	protected static boolean isKeyUnique(int id) {
		String command = "SELECT * "
				+ "FROM products "
				+ "WHERE id = "+id+"";
		
		return db.isUnique(command);
	}
	
	protected static boolean isUnique(int size, String color, String n_model) {
		String command = "SELECT * "
				+ "FROM products "
				+ "WHERE size = "+size+" AND color ='"+color+"' AND n_model = '"+n_model+"'";
		return db.isUnique(command);
	}
	
	public static LinkedList<Integer> getAllSizesOfModel(String n_model) {
    	LinkedList<Integer> result = new LinkedList<Integer>();
		String command = "SELECT DISTINCT size FROM products WHERE n_model = '"+n_model+"'";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		int s = rs.getInt("size");
	    	    result.add(s);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
    }
	
	public static LinkedList<String> getAllColorsOfModel(String n_model) {
    	LinkedList<String> result = new LinkedList<String>();
		String command = "SELECT DISTINCT color FROM products WHERE n_model = '"+n_model+"'";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		String s = rs.getString("colos");
	    	    result.add(s);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
    }
	
	public static LinkedList<Product> getAllproductsOfModel(String n_model) {
    	LinkedList<Product> result = new LinkedList<Product>();
		String command = "SELECT * FROM products WHERE n_model = '"+n_model+"'";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		Product s = new Product();
	    		s.n_model = rs.getString("n_model");
	    		s.size = rs.getInt("size");
	    		s.number = rs.getInt("num");
	    		s.id = rs.getInt("id");
	    		s.color = rs.getString("color");
	    	    result.add(s);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
    }
	
	public static Product get(String n_model, String color, int size) {
		String command = "SELECT * FROM products WHERE n_model = '"+n_model+"' AND color = '"+color+"' AND size = "+size+"";
		Product s = new Product();
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		s.n_model = rs.getString("n_model");
	    		s.size = rs.getInt("size");
	    		s.number = rs.getInt("num");
	    		s.id = rs.getInt("id");
	    		s.color = rs.getString("color");
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
    }
	
	public static Product get(int id) {
		String command = "SELECT * FROM products WHERE id = "+id+"";
		Product s = new Product();
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		s.n_model = rs.getString("n_model");
	    		s.size = rs.getInt("size");
	    		s.number = rs.getInt("num");
	    		s.id = rs.getInt("id");
	    		s.color = rs.getString("color");
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
    }
	
	/**
	 * Рахує загальну кількість моделі по її розмірах 
	 * @param n_model
	 * @return
	 */
	public static int getNumberOfModel(String n_model) {
		String command = "SELECT SUM(num) AS mod_num FROM products WHERE n_model = '"+n_model+"'";
		int n = 0;
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) 
	    		n = rs.getInt("mod_num");
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(n);
		return n;
    }
	

	public static int getNumber(int id) {
		String command = "SELECT num FROM products "
				+ "WHERE id = "+id+"";
		int n = 0;
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) 
	    		n = rs.getInt("num");
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
    }
	
	public static boolean delete( int id) {
		String command = "DELETE FROM `products`"
				+ "WHERE id = "+id+"";
		return db.update(command);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", n_model=" + n_model + ", size=" + size + ", number=" + number + ", color="
				+ color + "]";
	}
	
}
