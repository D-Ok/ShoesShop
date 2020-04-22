package shoesShop.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;

import shoesShop.Exceptions.ArgumentException;

public class ConsignmentRow {

	private int id_product;
	private int n_cons;
	private int num;
	private double price_sum;
	private static DBConnector db = DBConnector.getInstance();
	
	private ConsignmentRow() {}
	
	public ConsignmentRow(int id_product, int n_cons, int num) throws ArgumentException {
		
		if(Consignment.isUnique(n_cons)) throw new ArgumentException("Cons doesn`t exist.");
		if(Product.isKeyUnique(id_product)) throw new ArgumentException("id_product doesn`t exist.");
		if(!isUnique(n_cons, id_product)) throw new ArgumentException("row already exist");
		if(num<1) throw new ArgumentException("num not valid");

		try {
	    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `consignment_note_rows`"
	    			                                            + " (`id_product`, `n_cons`, `num`)"
	    			                                            + " VALUES (?, ?, ?)");
	    	ps.setInt(1, id_product);
	    	ps.setInt(2, n_cons);
	    	ps.setDouble(3, num);
	    	ps.executeUpdate();
	    	ps.close();
    	} catch(SQLIntegrityConstraintViolationException e) {
    		System.out.println("Material with this number exist.");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.id_product = id_product;
		this.n_cons = n_cons;
		this.num = num;
		this.price_sum = getSumPrice(id_product, n_cons);
		
		Product.addNumber(id_product, num);
	}
	
	public static boolean isUnique(int n_cons, int id_product) {
		String command = "SELECT * "
				+ "FROM consignment_note_rows "
				+ "WHERE n_cons = '"+n_cons+"' AND id_product = "+id_product+" ";
		
		return DBConnector.isUnique(command);
	}
	
	public int getid_product() {
		return id_product;
	}
	
	public int getN_cons() {
		return n_cons;
	}
	
	public int getNum() {
		return num;
	}
	
	public double getPrice_sum() {
		return price_sum;
	}
	
	public static double getSumPrice(int id_product, int n_cons) {
		double d =0;
		String command = "SELECT price_sum "
				+ "FROM con_rows_price "
				+ "WHERE id_product = "+id_product+" AND n_cons = "+n_cons+" ";
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		d = rs.getDouble("price_sum");
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static LinkedList<ConsignmentRow> getAllRowsOfCons(int n_cons) {
		LinkedList<ConsignmentRow> s = new LinkedList<ConsignmentRow>();
		String command = "SELECT * "
				+ "FROM con_rows_price "
				+ "WHERE n_cons = "+n_cons+" ";
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		ConsignmentRow r = new ConsignmentRow();
	    		r.num = rs.getInt("num");
	    		r.price_sum = rs.getDouble("price_sum");
	    		r.n_cons = rs.getInt("n_cons");
	    		r.id_product = rs.getInt("id_product");
	    		s.add(r);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static LinkedList<ConsignmentRow> getAllRowsOfModel(String id_product) {
		LinkedList<ConsignmentRow> s = new LinkedList<ConsignmentRow>();
		String command = "SELECT * "
				+ "FROM con_rows_price "
				+ "WHERE id_product = "+id_product+" ";
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		ConsignmentRow r = new ConsignmentRow();
	    		r.num = rs.getInt("num");
	    		r.price_sum = rs.getDouble("price_sum");
	    		r.n_cons = rs.getInt("n_cons");
	    		r.id_product = rs.getInt("id_product");
	    		s.add(r);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static boolean delete(String id_product, int n_cons) {
		String command = "DELETE FROM `consignment_note_rows`"
				+ " WHERE id_product = "+id_product+" AND n_cons = "+n_cons+"";
		return db.update(command);
	}

	@Override
	public String toString() {
		return "ConsignmentRow [id_product=" + id_product + ", n_cons=" + n_cons + ", num=" + num + ", price_sum=" + price_sum
				+ "]";
	}
	
	
}
