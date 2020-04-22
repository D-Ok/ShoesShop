package shoesShop.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;


import shoesShop.Exceptions.ArgumentException;


public class DBConnector {
	    private static DBConnector instance;
		protected static Connection connection;
		
		private DBConnector() {
			String user = "root";
	    	String password = "root35";
	    	String connectionURL = "jdbc:mysql://localhost:3306/shop?serverTimezone=Europe/Kiev&useSSL=false";
	    	try {
				Class.forName("com.mysql.cj.jdbc.Driver");
		    	connection = DriverManager.getConnection(connectionURL, user, password);
		    	//creatingTables();
	    	}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static DBConnector getInstance() {
			if(instance == null) 
				instance = new DBConnector();
			return instance;
		}
		
		protected boolean update(String command) {
			try {
				Statement st = connection.createStatement();
				st.executeUpdate(command);
				st.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	
		   public static boolean isNotEmpty(String s) {
				String res ="";
				for(int i = 0; i<s.length(); i++) {
					if(s.charAt(i)!=' ') res+= s.charAt(i); 
				}
				return res.length()>0;
			}

		   public static boolean isOnlyLetters(String s) {
			   if(DBConnector.isNotEmpty(s)) {
					for(int i =0; i<s.length(); i++) {
						if(!Character.isLetter(s.charAt(i)) && s.charAt(i)!=' '
								&& s.charAt(i)!='`' && s.charAt(i)!='\'' && s.charAt(i)!='-') return false;
					}
				} else return false;
				return true;
			}
		   
		   public static boolean isValidModelNumber(String s) {
				if(DBConnector.isNotEmpty(s) && s.length()<14) {
					for(int i = 0; i<s.length(); i++) {
						if(!Character.isDigit(s.charAt(i)) &&!Character.isLetter(s.charAt(i)) && s.charAt(i)!='-' && s.charAt(i)!='/') return false;
					}
				} else return false;
				return true;
			}
		   
			public static boolean isValidName(String s) {
				if(s.length()<31 && isNotEmpty(s) && Character.isLetter(s.charAt(0)) && Character.isLetter(s.charAt(s.length()-1))) {
					for(int i = 1; i<s.length()-1; i++) {
						if(!Character.isLetter(s.charAt(i)) && s.charAt(i)!='-' && s.charAt(i)!='`' && s.charAt(i)!='\'') return false;
					}
				} else return false;
				return true;
			}
			
			public static boolean isValidPhone(String phone) {
				if(DBConnector.isNotEmpty(phone) && phone.length()<15 && phone.charAt(0) == '+') {
					for(int i =1; i<phone.length(); i++) {
						if(!Character.isDigit(phone.charAt(i))) return false;
					}
				} else return false;
				return true;
			}
			
			public static boolean isValidBuilding(String b) {
				if(DBConnector.isNotEmpty(b) && b.length()<15 && b.charAt(0)!='-'
						&& b.charAt(0)!='/' && b.charAt(b.length()-1)!='-' &&  b.charAt(b.length()-1)!='/') {
					boolean ch = false;
					for(int i =1; i<b.length(); i++) {
						if(!ch && (b.charAt(i)=='-' || b.charAt(i)=='/'))  ch = true;
						else if(!ch) if(!Character.isDigit(b.charAt(i))) return false;
						else if(!Character.isDigit(b.charAt(i)) 
								&& !Character.isLetter(b.charAt(i)) 
								&& b.charAt(i)!='-' && b.charAt(i)!='/') return false;
					}
				} else return false;
				return true;
			}
		
			public static boolean isValidEmail(String email) {
				      String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
				      return email.matches(regex);
		    }
			
			
		   protected static int getIdByName(String name, String table) throws ArgumentException {
			   String command = "SELECT * FROM `"+table+"`"
						+ "WHERE name = '"+name+"'";
			   int id = -1;
				try {
					Statement statement = connection.createStatement();
			    	ResultSet rs = statement.executeQuery(command);
			    	if(rs.next()) {
						id = rs.getInt("id");
			    	} else throw new ArgumentException("No such name");
			    	statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return id;
		   }
		   
		   protected static String getNameById(int id, String table) throws ArgumentException {
			   String command = "SELECT * FROM `"+table+"`"
						+ "WHERE id = '"+id+"'";
			   String s = "";
				try {
					Statement statement = connection.createStatement();
			    	ResultSet rs = statement.executeQuery(command);
			    	if(rs.next()) {
						s = rs.getString("name");
			    	} else throw new ArgumentException("No such id");
			    	statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return s;
		   }
		   
		   protected static boolean isUnique(String command) {
			   boolean res = false;
				try {
					Statement statement = connection.createStatement();
			    	ResultSet rs = statement.executeQuery(command);
			    	res = !(rs.next()); 
			    	statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return res;
		   }
		   
		   
			
		public static void main(String[] args){
//			Seller.delete(1);
//			try {
//				Seller s = new Seller(1, "Daryna", "Oliinyk", "Volodynyrivna", true, "", "dok", "11111");
//			} catch (ArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
//			try {
//				Produser.deleteCompany(1);
//				Produser p1 = new Produser(1, "MIDA", "Запоріжжя", "Запорожська", "вул Шевченка",
//						"333", 333, "+380999999991", "", "", "mida@gmai.com", "");
//			} catch (ArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
//			try {
//				ProductModel p1 = ProductModel.getModel("2");
//				p1.setName("черевики-fasion");
//				LinkedList<ProductModel> l = ProductModel.getAllModels();
				
//				HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
//				m.put(Product.get("1", "бежевий", 39).getId(), 10);
//				m.put(Product.get("1", "чорний", 37).getId(), 10);
//				m.put(Product.get("1", "чорний", 38).getId(), 5);
//				m.put(Product.get("1", "чорний", 36).getId(), 5);
//				Consignment con = Main.buyShoes(2, m, "");
//				Cheque che = Main.sellShoes(1, m, "added");
//				
//				LinkedList<Product> l = Product.getAllproductsOfModel("1");
//				ProductModel p = ProductModel.getModel("1");
//				
//				for(Product pr: l) {
//					System.out.println(pr);
//				}
//				
//				LinkedList<Cheque> ch = Cheque.getAll();
//				for(Cheque c: ch) {
//					System.out.println(c);
//				}
//				
//				LinkedList<ChequeRow> r = ChequeRow.getAllRowsOfCons(che.getN_cheque());
//				for(ChequeRow c: r) {
//					System.out.println(c);
//				}
//				
//				LinkedList<Consignment> consignments = Consignment.getAll();
//				for(Consignment c: consignments) {
//					System.out.println(c);
//					for(ConsignmentRow cr: c.getRows()) {
//						System.out.println(cr);
//					}
//				}
//				
				LinkedList<ProductModel> p = new LinkedList<ProductModel>(Produser.getAllModel(2));
				for(ProductModel pr: p) {
					System.out.println(pr);
				}

				Produser pr = Produser.getProdOfModel("1");
					System.out.println(pr);
				
				
//			} catch (ArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} 
}
