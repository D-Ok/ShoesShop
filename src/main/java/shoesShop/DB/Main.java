package shoesShop.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;

import shoesShop.Exceptions.ArgumentException;

public class Main {
	 
	//private static Main instance;
	private static DBConnector db = DBConnector.getInstance();

	 /*
	  * HashMap<Integer, Integer> shoes
	  * В якості ключа виступає id продукту, яке однозначно ідентифікує поля: номер моделі, колір, розмір 
	  */
	public static Consignment buyShoes(int n_company, HashMap<Integer, Integer> shoes, String notes) throws ArgumentException {
		Consignment cons = new Consignment(n_company, notes);
		
		for(Integer s: shoes.keySet()) {
			if(shoes.get(s)<1) throw new ArgumentException("Uncorrect num");
			cons.addRow(s, shoes.get(s));
		}
		return cons;
	}
	
	public static Consignment buySetOfColor(int n_company, String color, int fromSize, int toSize, int num, String n_model, String notes) throws ArgumentException {
		Consignment cons = new Consignment(n_company, notes);
		for(int i =fromSize; i<=toSize; i++) {
			if(Product.isUnique(i, color, n_model)) 
				new Product(n_model, i, color);
			cons.addRow(Product.getId(n_model, color, i), num);
		}
		return cons;
	}
	
	public static Consignment buySetColorAndSizes(int n_company, LinkedList<String> colors, LinkedList<Integer> sizes, int num, String n_model, String notes) throws ArgumentException {
		Consignment cons = new Consignment(n_company, notes);
		for(String c: colors) {
			for(Integer s: sizes) {
				if(Product.isUnique(s, c, n_model)) 
					new Product(n_model, s, c);
				cons.addRow(Product.getId(n_model, c, s), num);
			}
		}
		return cons;
	}
	
	public static Consignment buyFullSetOfModel(int n_company, ProductModel p, int num, String notes) throws ArgumentException {
		Consignment cons = new Consignment(n_company, notes);
		LinkedList<String> colors = p.getColors();
		LinkedList<Integer> sizes = p.getSizes();
		for(String c: colors) {
			for(Integer s: sizes) {
				if(Product.isUnique(s, c, p.getN_model())) 
					new Product(p.getN_model(), s, c);
				cons.addRow(Product.getId(p.getN_model(), c, s), num);
			}
		}
		return cons;
	}
	
	public static void addRowToCons(int n_cons, String n_model, String color, int sizeFrom, int sizeTo, int num) throws ArgumentException {
			for(int i=sizeFrom; i<=sizeTo; i++) {
				if(Product.isUnique(i, color, n_model)) 
					new Product(n_model, i, color);
				ConsignmentRow r = new ConsignmentRow(Product.getId(n_model, color, i), n_cons, num);
			}
	}
	
	public static Cheque sellShoes(int n_employee, HashMap<Integer, Integer> shoes, String notes) throws ArgumentException {
		Cheque cons = new Cheque(n_employee, notes);
		
		for(Integer s: shoes.keySet()) {
			if(shoes.get(s)<1) throw new ArgumentException("Uncorrect num");
			cons.addRow(s, shoes.get(s));
		}
		return cons;
	}

	
	public static LinkedList<String> getAllColors() {
		LinkedList<String> colors = new LinkedList<String>();
		String command = "SELECT DISTINCT color FROM products ";
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		colors.add(rs.getString("color"));
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colors;
	}
	
	 
}