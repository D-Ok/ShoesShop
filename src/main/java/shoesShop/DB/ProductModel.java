package shoesShop.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;

import shoesShop.Exceptions.ArgumentException;

public class ProductModel {
	
	private String n_model;
	private Category category;
	private Season season;
	private String name;
	private double price_purchase;
	private int extra_charge;
	private double shop_price;
	private int number;
	private boolean is_full;
	private boolean is_female;
	private boolean is_male;
	private boolean is_child;
	
	private static DBConnector db = DBConnector.getInstance();
	
	
	private ProductModel() {};
	
	public ProductModel(String n_model, Category category, Season season,
			String name, double price_purchase, int extra_charge,
			boolean is_full, boolean is_female, boolean is_male, boolean is_child) throws ArgumentException {
	
		if(!DBConnector.isValidModelNumber(n_model)) throw new ArgumentException("This n_model is not valid");
		if(!isUniqueNum(n_model)) throw new ArgumentException("This n_model is already exist");
		if(!DBConnector.isNotEmpty(name)&&name.length()>40) throw new ArgumentException("This name is not valid");
		if(price_purchase<1 || price_purchase>9999) throw new ArgumentException("This price is not valid");
		if(price_purchase<1 || price_purchase>999) throw new ArgumentException("This extra_charge is not valid");
		
		double pp = round(price_purchase, 2);
		
		try {
	    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `product_models` "
	    			                                            + " (`n_model`, `name`, `price_purchase`, `extra_charge`, "
	    			                                            + "`is_full`, `is_female`, `is_male`, `is_child`, `category_id`, `season_id`)"
	    			                                            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	    	ps.setString(1, n_model);
	    	ps.setString(2, name);
	    	ps.setDouble(3, pp);
	    	ps.setInt(4, extra_charge);
	    	ps.setBoolean(5, is_full);
	    	ps.setBoolean(6, is_female);
	    	ps.setBoolean(7, is_male);
	    	ps.setBoolean(8, is_child);
	    	ps.setInt(9, category.getId());
	    	ps.setInt(10, season.getId());
	    	ps.executeUpdate();
	    	ps.close();
    	} catch(SQLIntegrityConstraintViolationException e) {
    		System.out.println("Material with this number exist.");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.n_model = n_model;
		this.category = category;
		this.season = season;
		this.name = name;
		this.price_purchase = price_purchase;
		this.extra_charge = extra_charge;
		this.number = 0;
		this.is_full = is_full;
		this.is_female = is_female;
		this.is_male = is_male;
		this.is_child = is_child;
		getShop_price(n_model);
	}
	
	public ProductModel(String n_model, Category category, Season season, String name, double price_purchase, int extra_charge) throws ArgumentException {
		this(n_model, category, season, name, price_purchase, extra_charge, true, true, true, true);
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public String getN_model() {
		return n_model;
	}

	public void setN_model(String n_model) throws ArgumentException {
		if(DBConnector.isValidModelNumber(n_model)) {
            String command = "UPDATE `product_models` "
            		        + "SET n_model = '"+n_model+"' "
            				+ "WHERE n_model = '"+this.n_model+"'";
			if(db.update(command)) this.n_model = n_model;
		} else throw new ArgumentException();
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
            String command = "UPDATE `product_models` "
            		        + "SET season_id = '"+season.getId()+"' "
            				+ "WHERE n_model = '"+this.n_model+"'";
			if(db.update(command)) this.season = season;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		   String command = "UPDATE `product_models` "
   		        + "SET category_id = '"+category.getId()+"' "
   				+ "WHERE n_model = '"+this.n_model+"'";
		   if(db.update(command)) this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws ArgumentException {
		if(DBConnector.isNotEmpty(name)&&name.length()<41) {
            String command = "UPDATE `product_models` "
            		        + "SET name = '"+name+"' "
            				+ "WHERE n_model = '"+this.n_model+"'";
			if(db.update(command)) this.name = name;
		} else throw new ArgumentException();
	}

	public double getPrice_purchase() {
		return price_purchase;
	}

	public void setPrice_purchase(double price_purchase) throws ArgumentException {
		if(price_purchase>1 && price_purchase<10000) {
			double pp = round(price_purchase, 2);
            String command = "UPDATE `product_models` "
            		        + "SET price_purchase = '"+pp+"' "
            				+ "WHERE n_modele = '"+n_model+"'";
			if(db.update(command)) this.price_purchase = pp;
			shop_price = getShop_price(n_model);
		} else throw new ArgumentException();
	}

	public int getExtra_charge() {
		return extra_charge;
	}

	public void setExtra_charge(int extra_charge) throws ArgumentException {
		if(extra_charge>0 && extra_charge<1000) {
            String command = "UPDATE `product_models` "
            		        + "SET extra_charge = '"+extra_charge+"' "
            				+ "WHERE n_modele = '"+n_model+"'";
			if(db.update(command)) this.extra_charge = extra_charge;
			shop_price = getShop_price(n_model);
		} else throw new ArgumentException();
	}

	public boolean isIs_full() {
		return is_full;
	}

	public void setIs_full(boolean is_full) {
		String command = "UPDATE `product_models` "
		        + "SET is_full = '"+is_full+"' "
				+ "WHERE n_modele = '"+n_model+"'";
		if(db.update(command)) this.is_full = is_full;
	}

	public boolean isIs_female() {
		return is_female;
	}

	public void setIs_female(boolean is_female) {
		String command = "UPDATE `product_models` "
		        + "SET is_female = '"+is_female+"' "
				+ "WHERE n_modele = '"+n_model+"'";
		if(db.update(command)) this.is_female = is_female;
	}

	public boolean isIs_male() {
		return is_male;
	}

	public void setIs_male(boolean is_male) {
		String command = "UPDATE `product_models` "
		        + "SET is_male = '"+is_male+"' "
				+ "WHERE n_modele = '"+n_model+"'";
		if(db.update(command)) this.is_male = is_male;
	}

	public boolean isIs_child() {
		return is_child;
	}

	public void setIs_child(boolean is_child) {
		String command = "UPDATE `product_models` "
		        + "SET is_child = '"+is_child+"' "
				+ "WHERE n_modele = '"+n_model+"'";
		if(db.update(command)) this.is_child = is_child;
	}
	
	public Produser getProduser() {
		return Produser.getProdOfModel(n_model);
	}

	public LinkedList<Matherial> getMaterials() {
		return Matherial.getAllMatherialsOfShoe(n_model);
	}

	public LinkedList<Integer> getSizes() {
		return Product.getAllSizesOfModel(n_model);
	}

	public LinkedList<String> getColors() {
		return Product.getAllColorsOfModel(n_model);
	}
	
	public LinkedList<Product> getProducts() {
		return Product.getAllproductsOfModel(n_model);
	}
	
	public double getShop_price() {
		return shop_price;
	}

	public static double getShop_price(String n_model) {
		double r = 0;
		String command = "SELECT shop_price FROM `product_models`"
				+ "WHERE n_model = '"+n_model+"'";
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		r = rs.getDouble("shop_price");
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	public int getNumber() {
		return number;
	}

    public static ProductModel getModel(String n_model) { 
    	ProductModel s = null;
		String command = "SELECT * "
				+ "FROM product_models "
				+ "WHERE n_model = '"+n_model+"'";
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
	    		s = new ProductModel();
	    		s.n_model = rs.getString("n_model");
	    		s.name = rs.getString("name");
	    		s.category = Category.getCategorynById(rs.getInt("category_id"));
	    		s.season = Season.getSeasonById(rs.getInt("season_id"));
	    		s.price_purchase = rs.getDouble("price_purchase");
	    		s.extra_charge = rs.getInt("extra_charge");
	    		s.is_full = rs.getBoolean("is_full");
	    		s.is_female = rs.getBoolean("is_female");
	    		s.is_male = rs.getBoolean("is_male");
	    		s.is_child = rs.getBoolean("is_child");
	    		s.shop_price = getShop_price(s.n_model);
	    		s.number = Product.getNumberOfModel(s.n_model);
	    	}
	    	statement.close();
		} catch (SQLException | ArgumentException e) {
			e.printStackTrace();
		}
		return s;
	}
    
    public static LinkedList<ProductModel> getAllModels() { 
		String command = "SELECT * "
				+ "FROM product_models ";
	
		return getAll(command);
	}
    
    public static LinkedList<ProductModel> getAll(String command) { 
    	LinkedList<ProductModel> res = new LinkedList<ProductModel>();
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		ProductModel s = new ProductModel();
	    		s.n_model = rs.getString("n_model");
	    		s.name = rs.getString("name");
	    		s.category = Category.getCategorynById(rs.getInt("category_id"));
	    		s.season = Season.getSeasonById(rs.getInt("season_id"));
	    		s.price_purchase = rs.getDouble("price_purchase");
	    		s.extra_charge = rs.getInt("extra_charge");
	    		s.is_full = rs.getBoolean("is_full");
	    		s.is_female = rs.getBoolean("is_female");
	    		s.is_male = rs.getBoolean("is_male");
	    		s.is_child = rs.getBoolean("is_child");
	    		s.shop_price = getShop_price(s.n_model);
	    		s.number = Product.getNumberOfModel(s.n_model);
	    		res.add(s);
	    	}
	    	statement.close();
		} catch (SQLException | ArgumentException e) {
			e.printStackTrace();
		}
		return res;
	}
    

	public static boolean delete(String n_model) {
		String command = "DELETE FROM `product_models`"
				+ "WHERE n_model = '"+n_model+"'";
		return db.update(command);
	}
    
    
	public static boolean isUniqueNum(String n_model) {
		String command = "SELECT * "
				+ "FROM product_models "
				+ "WHERE n_model = '"+n_model+"'";
		
		return db.isUnique(command);
	}
	
//    public static Seller getSellers(String n_model) {
//   	 String command = "SELECT * "
//   	 		+ "FROM `produsers` INNER JOIN `consignment_notes` ON produsers.n_company = consignment_notes.n_company"
//   	 		+ " INNER JOIN `consignment_note_rows` ON consignment_notes.n_cons = consignment_note_rows.n_cons "
//   	 		+ " INNER JOIN `product_models` ON product_models.n_model = consignment_note_rows.n_model"
// 				+ "WHERE product_models.n_model = '"+n_model+"'";
// 	
// 		return getOne(command);
//    }

	@Override
	public String toString() {
		return "ProductModel [n_model=" + n_model + ",\n category=" + category + ",\n season=" + season + ","
				+ "\n name=" + name + ",\n price_purchase="
				+ price_purchase + ",\n extra_charge=" + extra_charge + ",\n shop_price=" + shop_price + ",\n number="
				+ number + ",\n is_full=" + is_full + ",\n is_female=" + is_female + ",\n is_male=" + is_male + ",\n is_child="
				+ is_child + "]";
	}

	
}
