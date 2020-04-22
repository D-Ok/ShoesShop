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

public class Consignment {

	private Date date;
	private int n_cons;
	private int n_company;
	private double total_sum;
	private String notes;
	private static DBConnector db = DBConnector.getInstance();

	private Consignment() {}
	
	public Consignment(int n_company, String notes) throws ArgumentException {
		
		if(Produser.isUniqueNum(n_company)) throw new ArgumentException("company does not exist");
		if(notes.length()>101) throw new ArgumentException("notes is too long");
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		int nc = generateId();
		try {
	    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `consignment_notes`"
	    			                                            + " (`notes`, `n_cons`, `n_company`, `c_date`)"
	    			                                            + " VALUES (?, ?, ?, ?)");
	    	ps.setString(1, notes);
	    	ps.setInt(2, nc);
	    	ps.setInt(3, n_company);
	    	ps.setTimestamp(4, timestamp);
	    	ps.executeUpdate();
	    	ps.close();
    	} catch(SQLIntegrityConstraintViolationException e) {
    		System.out.println("Material with this number exist.");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.n_cons = nc;
		this.n_company = n_company;
		this.notes = notes;
		this.total_sum = getTotalSum(n_company);
		init();
	}
	
	public ConsignmentRow addRow(int id_model, int num) throws ArgumentException {
		ConsignmentRow c = new ConsignmentRow(id_model, n_cons, num);
		return c;
	}
	
	private void init() {
		String command = "SELECT c_date "
				+ "FROM consignment_notes "
				+ "WHERE n_cons = "+n_cons+" ";
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		date = rs.getDate("c_date");
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int generateId() {
		Random r = new Random();
		int id = 1;
		while(!isUnique(id)) id = r.nextInt(1000000000); 
		return id;
	}


	public LinkedList<ConsignmentRow> getRows(){
		return ConsignmentRow.getAllRowsOfCons(n_cons);
	}
	
	
	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) throws ArgumentException {
		if(notes.length()<101) {
            String command = "UPDATE `consignment_notes` "
            		        + "SET notes = '"+notes+"' "
            				+ "WHERE n_cons = '"+n_cons+"'";
			if(db.update(command)) this.notes = notes;
		} else throw new ArgumentException();
	}


	public Date getDate() {
		return date;
	}


	public int getN_cons() {
		return n_cons;
	}


	public int getN_company() {
		return n_company;
	}


	public double getTotal_sum() {
		return total_sum;
	}


	public static boolean isUnique(int n_cons) {
			String command = "SELECT * "
					+ "FROM consignment_notes "
					+ "WHERE n_cons = '"+n_cons+"'";
			
			return DBConnector.isUnique(command);
	}
	
	public static double getTotalSum( int n_cons) {
		double d =0;
		String command = "SELECT total_sum "
				+ "FROM con_price "
				+ "WHERE n_cons = "+n_cons+" ";
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		d = rs.getDouble("total_sum");
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	private static LinkedList<Consignment> getCons(String command) {
		LinkedList<Consignment> s = new LinkedList<Consignment>();

		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		Consignment r = new Consignment();
	    		r.n_company = rs.getInt("n_company");
	    		r.total_sum = rs.getDouble("total_sum");
	    		r.n_cons = rs.getInt("n_cons");
	    		r.notes = rs.getString("notes");
	    		r.date = rs.getTimestamp("c_date");
	    		s.add(r);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static LinkedList<Consignment> getAll(){
		String command = "SELECT *"
				+ "FROM con_price ";
		return getCons(command);
	}
	
	public static LinkedList<Consignment> getAllByCompany(int n_company){
		String command = "SELECT * "
				+ "FROM con_price "
				+ "WHERE n_company = "+n_company+"";
		return getCons(command);
	}

	public static boolean delete(int n_cons) {
		String command = "DELETE FROM `consignment_notes`"
				+ "WHERE n_cons = '"+n_cons+"'";
		return db.update(command);
	}
	
}
