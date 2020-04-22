package shoesShop.DB;

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
	
	public static Cheque sellShoes(int n_employee, HashMap<Integer, Integer> shoes, String notes) throws ArgumentException {
		Cheque cons = new Cheque(n_employee, notes);
		
		for(Integer s: shoes.keySet()) {
			if(shoes.get(s)<1) throw new ArgumentException("Uncorrect num");
			cons.addRow(s, shoes.get(s));
		}
		return cons;
	}
	
	 
}