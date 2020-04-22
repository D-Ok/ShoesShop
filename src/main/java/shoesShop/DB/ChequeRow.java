package shoesShop.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

import shoesShop.Exceptions.ArgumentException;

public class ChequeRow {
	
	private int id_product;
	private int n_cheque;
	private int num;
	private double sum;
	private static DBConnector db = DBConnector.getInstance();
	
	private ChequeRow() {}
	
	public ChequeRow(int id_product, int n_cheque, int num) throws ArgumentException {
	
		if(Cheque.isUnique(n_cheque)) throw new ArgumentException("Cheque doesn`t exist.");
		if(Product.isKeyUnique(id_product)) throw new ArgumentException("id_product doesn`t exist.");
		if(!isUnique(n_cheque, id_product)) throw new ArgumentException("row already exist");
		if(num<1) throw new ArgumentException("num not valid");
		
		try {
	    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `cheque_rows`"
	    			                                            + " (`id_product`, `n_cheque`, `num`)"
	    			                                            + " VALUES (?, ?, ?)");
	    	ps.setInt(1, id_product);
	    	ps.setInt(2, n_cheque);
	    	ps.setDouble(3, num);
	    	ps.executeUpdate();
	    	ps.close();
    	} catch(SQLIntegrityConstraintViolationException e) {
    		e.printStackTrace();
    	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.id_product = id_product;
		this.n_cheque = n_cheque;
		this.num = num;
		
		Product.addNumber(id_product, -(num));
	}
	

	
	public static boolean isUnique(int n_cheque, int id_product) {
		String command = "SELECT * "
				+ "FROM cheque_rows "
				+ "WHERE n_cheque = '"+n_cheque+"' AND id_product = "+id_product+" ";
		
		return DBConnector.isUnique(command);
	}
	
	public int getid_product() {
		return id_product;
	}
	public int getN_cheque() {
		return n_cheque;
	}
	public int getNum() {
		return num;
	}
	public double getSum() {
		return sum;
	}
	
	public static double getSumPrice(int id_product, int n_cheque) {
		double d =0;
		String command = "SELECT price_sum "
				+ "FROM ch_rows_price "
				+ "WHERE id_product = "+id_product+" AND n_cheque = "+n_cheque+" ";
	
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
	
	private static LinkedList<ChequeRow> getAll (String command){
		LinkedList<ChequeRow> s = new LinkedList<ChequeRow>();
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		ChequeRow r = new ChequeRow();
	    		r.num = rs.getInt("num");
	    		r.sum = rs.getDouble("price_sum");
	    		r.n_cheque = rs.getInt("n_cheque");
	    		r.id_product = rs.getInt("id_product");
	    		s.add(r);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static LinkedList<ChequeRow> getAllRowsOfCons(int n_ch) {
		String command = "SELECT * "
				+ "FROM ch_rows_price "
				+ "WHERE n_cheque = "+n_ch+" ";
	
		return getAll(command);
	}
	
	public static LinkedList<ConsignmentRow> getAllRowsOfModel(int id_product) {
		LinkedList<ConsignmentRow> s = new LinkedList<ConsignmentRow>();
		String command = "SELECT * "
				+ "FROM ch_rows_price "
				+ "WHERE id_product = "+id_product+" ";
		return s;
	}
	
	public static boolean delete(int id_product, int n_ch) {
		String command = "DELETE FROM `cheque_rows`"
				+ "WHERE id_product = "+id_product+" AND n_cheque = "+n_ch+"";
		return db.update(command);
	}
	
	@Override
	public String toString() {
		return "ChequeRow [id_product=" + id_product + ", n_cheque=" + n_cheque + ", num=" + num + ", sum=" + sum + "]";
	}
	

}
