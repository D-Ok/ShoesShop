package shoesShop.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

import shoesShop.Exceptions.ArgumentException;

public class Matherial {
	private String name;
	private int id;
	private static DBConnector db = DBConnector.getInstance();
	
	private Matherial() {};
	
	public Matherial(String name) throws ArgumentException {
		if(!DBConnector.isOnlyLetters(name)) throw new ArgumentException("Not valid name");
		if(!isNameUnique(name)) throw new ArgumentException("This name is already exist");
		
		int id = generateId();
		try {
	    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `materials`"
	    			                                            + " (`name`, `id`) VALUES (?, ?)");
	    	ps.setInt(2, id);
	    	ps.setString(1, name);
	    	ps.executeUpdate();
	    	ps.close();
    	} catch(SQLIntegrityConstraintViolationException e) {
    		System.out.println("Material with this number exist.");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.name = name;
		this.id = id;
		
	}
	
	private int generateId() {
		Random r = new Random();
		int id = 1;
		while(!isUnique(id)) id = r.nextInt(1000000000); 
		return id;
	}
	
	protected static boolean isUnique(int name) {
		String command = "SELECT * "
				+ "FROM materials "
				+ "WHERE id = "+name+"";
		
		return DBConnector.isUnique(command);
	}
	
	private boolean isNameUnique(String name) {
		String command = "SELECT * "
				+ "FROM materials "
				+ "WHERE name = '"+name+"'";
		
		return DBConnector.isUnique(command);
	}
	
    public String getName(){ return name;}
    public int getId(){ return id;}
    
    public static int getidByName(String name) throws ArgumentException {
    	return DBConnector.getIdByName(name, "materials");
    }

    public static String getNameById(int id) throws ArgumentException {
    	return DBConnector.getNameById(id, "materials");
    }
    
    public static LinkedList<Matherial> getAllMatherials() {
    	LinkedList<Matherial> result = new LinkedList<Matherial>();
		String command = "SELECT * FROM materials";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	Seller s;
	    	while(rs.next()) {
	    		Matherial m = new Matherial();
	    		m.id = rs.getInt("id");
	    		m.name = rs.getString("name");
	    	    result.add(m);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
    }
    
    @Override
	public String toString() {
		return "Matherial [name=" + name + "]";
	}
    
	public static boolean deleteMatherial(int id) {
		String command = "DELETE FROM `materials`"
				+ " WHERE id = '"+id+"'";
		return db.update(command);
	}

	public static LinkedList<Matherial> getAllMatherialsOfShoe(String n_model) {
    	LinkedList<Matherial> result = new LinkedList<Matherial>();
		String command = "SELECT * "
				+ "FROM `materials` INNER JOIN `model_materials` ON `materials`.`id` = `model_materials`.`material_id`"
				+ " WHERE `n_model` = '"+n_model+"'";
		
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	Seller s;
	    	while(rs.next()) {
	    		Matherial m = new Matherial();
	    		m.id = rs.getInt("id");
	    		m.name = rs.getString("name");
	    	    result.add(m);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
    }
}
