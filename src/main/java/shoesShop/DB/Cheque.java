package shoesShop.DB;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import shoesShop.Exceptions.ArgumentException;

public class Cheque {

	private int n_cheque;
	private int n_employee;
	private Date date;
	private String notes;
	private double total_sum;
	private static DBConnector db = DBConnector.getInstance();
	
	private Cheque() {}
	
	public Cheque(int n_employee, String note) throws ArgumentException {
		
		if(Seller.isUniqueNum(n_employee)) throw new ArgumentException("employee does not exist");
		if(DBConnector.isNotEmpty(note) && note.length()>101) throw new ArgumentException("notes is too long");
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		int nc = generateId();
		try {
	    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `cheques`"
	    			                                            + " (`notes`, `n_cheque`, `n_employee`, `ch_date`)"
	    			                                            + " VALUES (?, ?, ?, ?)");
	    	ps.setString(1, notes);
	    	ps.setInt(2, nc);
	    	ps.setInt(3, n_employee);
	    	ps.setTimestamp(4, timestamp);
	    	ps.executeUpdate();
	    	ps.close();
    	} catch(SQLIntegrityConstraintViolationException e) {
    		System.out.println("Material with this number exist.");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.n_employee = n_employee;
		this.notes = note;
		this.n_cheque = nc;
		this.date = date;
	}
	
	
	
	public ChequeRow addRow(int id_product, int num) throws ArgumentException {
		ChequeRow c = new ChequeRow(id_product, n_cheque, num);
		return c;
	}

	public String getNote() {
		return notes;
	}

	public void setNote(String notes)  throws ArgumentException {
		if(notes.length()<101) {
            String command = "UPDATE `cheques` "
            		        + "SET notes = '"+notes+"' "
            				+ "WHERE n_cheque = '"+n_cheque+"'";
			if(db.update(command)) this.notes = notes;
		} else throw new ArgumentException();
	}

	public int getN_cheque() {
		return n_cheque;
	}

	public int getN_employee() {
		return n_employee;
	}

	public Date getDate() {
		return date;
	}

	public double getTotal_sum() {
		return total_sum;
	}

	protected static boolean isUnique(int n_cheque) {
		String command = "SELECT * "
				+ "FROM `cheques` "
				+ "WHERE n_cheque = "+n_cheque+"";
		
		return DBConnector.isUnique(command);
	}
	
	private static LinkedList<Cheque> getCons(String command) {
		LinkedList<Cheque> s = new LinkedList<Cheque>();

		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		Cheque r = new Cheque();
	    		r.n_employee = rs.getInt("n_employee");
	    		r.total_sum = rs.getDouble("total_sum");
	    		r.n_cheque = rs.getInt("n_cheque");
	    		r.notes = rs.getString("notes");
	    		r.date = rs.getDate("ch_date");
	    		s.add(r);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static LinkedList<Cheque> getAll(){
		String command = "SELECT * "
				+ "FROM ch_price ";
		return getCons(command);
	}
	
	public static LinkedList<Cheque> getAllBySeller(int n_employee){
		String command = "SELECT * "
				+ "FROM ch_price "
				+ "WHERE n_employee = "+n_employee+"";
		return getCons(command);
	}
	
	private int generateId() {
		Random r = new Random();
		int id = 1;
		while(!isUnique(id)) id = r.nextInt(1000000000); 
		return id;
	}
	
	public static boolean delete( int n_ch) {
		String command = "DELETE FROM `cheques`"
				+ "WHERE n_cheque = "+n_ch+"";
		return db.update(command);
	}

}
