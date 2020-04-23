package shoesShop.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

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
	
	public Product createProduct(String color, int size) throws ArgumentException {
		return new Product(n_model, size, color);
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
	
	public void setMatherial(int matherial) throws ArgumentException {
		setMatherial(n_model, matherial);
	}
	
	public static void setMatherial(String n_model, int matherial) throws ArgumentException {
		if(!Matherial.isUnique(matherial) || !ProductModel.isUniqueNum(n_model)) {
			try {
		    	PreparedStatement ps = db.connection.prepareStatement("INSERT INTO `model_materials`"
		    			                                            + "(`n_model`, `material_id`) VALUES (?, ?)");
		    	ps.setString(1, n_model);
		    	ps.setInt(2, matherial);
		    	ps.executeUpdate();
		    	ps.close();
	    	} catch(SQLIntegrityConstraintViolationException e) {
	    		System.out.println("Material with this number exist.");
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
		} else throw new ArgumentException();
	}

	public void setN_model(String n_model) throws ArgumentException {
		if(DBConnector.isValidModelNumber(n_model)) {
            String command = "UPDATE `product_models` "
            		        + " SET n_model = '"+n_model+"' "
            				+ " WHERE n_model = '"+this.n_model+"'";
			if(db.update(command)) this.n_model = n_model;
		} else throw new ArgumentException();
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
            String command = "UPDATE `product_models` "
            		        + " SET season_id = '"+season.getId()+"' "
            				+ " WHERE n_model = '"+this.n_model+"'";
			if(db.update(command)) this.season = season;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		   String command = "UPDATE `product_models` "
   		        + " SET category_id = '"+category.getId()+"' "
   				+ " WHERE n_model = '"+this.n_model+"'";
		   if(db.update(command)) this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws ArgumentException {
		if(DBConnector.isNotEmpty(name)&&name.length()<41) {
            String command = "UPDATE `product_models` "
            		        + " SET name = '"+name+"' "
            				+ " WHERE n_model = '"+this.n_model+"'";
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
            		        + " SET price_purchase = '"+pp+"' "
            				+ " WHERE n_modele = '"+n_model+"'";
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
            		        + " SET extra_charge = '"+extra_charge+"' "
            				+ " WHERE n_modele = '"+n_model+"'";
			if(db.update(command)) this.extra_charge = extra_charge;
			shop_price = getShop_price(n_model);
		} else throw new ArgumentException();
	}

	public boolean isIs_full() {
		return is_full;
	}

	public void setIs_full(boolean is_full) {
		String command = "UPDATE `product_models` "
		        + " SET is_full = '"+is_full+"' "
				+ " WHERE n_modele = '"+n_model+"'";
		if(db.update(command)) this.is_full = is_full;
	}

	public boolean isIs_female() {
		return is_female;
	}

	public void setIs_female(boolean is_female) {
		String command = "UPDATE `product_models` "
		        + " SET is_female = '"+is_female+"' "
				+ " WHERE n_modele = '"+n_model+"'";
		if(db.update(command)) this.is_female = is_female;
	}

	public boolean isIs_male() {
		return is_male;
	}

	public void setIs_male(boolean is_male) {
		String command = "UPDATE `product_models` "
		        + " SET is_male = '"+is_male+"' "
				+ " WHERE n_modele = '"+n_model+"'";
		if(db.update(command)) this.is_male = is_male;
	}

	public boolean isIs_child() {
		return is_child;
	}

	public void setIs_child(boolean is_child) {
		String command = "UPDATE `product_models` "
		        + " SET is_child = '"+is_child+"' "
				+ " WHERE n_modele = '"+n_model+"'";
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
				+ " WHERE n_model = '"+n_model+"'";
	
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
				+ " FROM product_models "
				+ " WHERE n_model ='"+n_model+"'";
	
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
    
    protected static LinkedList<ProductModel> getAll(String command) { 
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
    
    public static LinkedList<String> getAllId() { 
    	LinkedList<String> res = new LinkedList<String>();
    	String command = "SELECT n_model "
				+ "FROM product_models ";
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		res.add(rs.getString("n_model"));
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
    

	public static boolean delete(String n_model) {
		String command = "DELETE FROM `product_models`"
				+ " WHERE n_model = '"+n_model+"'";
		return db.update(command);
	}
    
    
	public static boolean isUniqueNum(String n_model) {
		String command = "SELECT * "
				+ "FROM product_models "
				+ "WHERE n_model = '"+n_model+"'";
		
		return db.isUnique(command);
	}
	
	public static LinkedList<ProductModel> getAllBySizesInStock(int fromSize, int toSize) throws ArgumentException{
		if(fromSize>toSize) throw new ArgumentException();
		String command = "SELECT n_model, name " + 
				"		FROM product_models " + 
				"		WHERE NOT EXISTS(" + 
				"		              SELECT *" + 
				"					  FROM products" + 
				"		              WHERE size>"+fromSize+" AND size<"+toSize+" AND" + 
				"		                    size NOT IN (SELECT size" + 
				"									     FROM products" + 
				"		                                 WHERE products.n_model = product_models.n_model" + 
				"		                                 AND num>0))";
		
		return getAll(command);
	}
	
    public static ProductModel getProduser(String n_model) {
   	 String command = "SELECT * "
   	 		+ "FROM `produsers` INNER JOIN `consignment_notes` ON produsers.n_company = consignment_notes.n_company"
   	 		+ " INNER JOIN `consignment_note_rows` ON consignment_notes.n_cons = consignment_note_rows.n_cons "
   	 		+ " INNER JOIN `product_models` ON product_models.n_model = consignment_note_rows.n_model"
 			+ " WHERE product_models.n_model = '"+n_model+"'";
 	
 		return getOne(command);
    }
    
    protected static ProductModel getOne(String command) { 
    	ProductModel res = new ProductModel();
	
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	if(rs.next()) {
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
	    	}
	    	statement.close();
		} catch (SQLException | ArgumentException e) {
			e.printStackTrace();
		}
		return res;
	}
/**
 * 
 * @return Наявна кількість кожної моделі на складі
 */
    public static HashMap<String, Integer> getAllProductsNumber() {
    	HashMap<String, Integer> res = new HashMap<String, Integer>();
    	String command = "SELECT n_model, SUM(num) AS all_num" + 
    					" FROM products" + 
    					" GROUP BY n_model";
		try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	String n_model;
	    	Integer num;
	    	while(rs.next()) {
	    		n_model = rs.getString("n_model");
	    		num = rs.getInt("all_num");
	    		res.put(n_model, num);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
    }
    
    /**
     * Повертає кількість проданих пар за певний період
     * @param from
     * @param to
     * @return
     */
    public static HashMap<String, Integer> getStatisticOfPeriod(LocalDate from, LocalDate to){
    	String command = "SELECT n_model, SUM(cheque_rows.num) AS all_num" + 
    			" FROM products INNER JOIN cheque_rows ON products.id = cheque_rows.id_product" + 
    			"     INNER JOIN cheques ON cheques.n_cheque = cheque_rows.n_cheque" + 
    			" WHERE ch_date >= '"+from+"' AND ch_date <= '"+to+"'" + 
    			" GROUP BY n_model;";
    	HashMap<String, Integer> h = new HashMap<String, Integer>();
    	try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	String n_model;
	    	Integer num;
	    	while(rs.next()) {
	    		n_model = rs.getString("n_model");
	    		num = rs.getInt("all_num");
	    		h.put(n_model, num);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return h;
    }
    
    public static LinkedList<ProductModel> getProductModelWithoutProduser(){
    	String command = "SELECT DISTINCT * " + 
    			"FROM product_models " + 
    			" WHERE product_models.n_model NOT IN (" + 
    			"  				SELECT n_model\r\n" + 
    			"  				FROM `products` INNER JOIN consignment_note_rows ON products.id = consignment_note_rows.id_product " + 
    			"  			   	INNER JOIN consignment_notes ON consignment_notes.n_cons = consignment_note_rows.n_cons " + 
    			"  				INNER JOIN produsers ON produsers.n_company = consignment_notes.n_company)";
    			
    	return getAll(command);
    }
    
    public static HashMap<ProductModel, Integer> filterOnStockByCategory(int category_id, int size){
    	String command = "SELECT *, (SELECT SUM(num)" + 
    			"           FROM products" + 
    			"		   WHERE n_model = product_models.n_model  AND size="+size+") AS num" + 
    			" FROM product_models " + 
    			" WHERE category_id = "+category_id+" " + 
    			"					  AND EXISTS (SELECT * " + 
    			"                                  FROM products" + 
    			"                                  WHERE n_model = product_models.n_model  AND size="+size+" AND num>0)";
    	return filterOnStock(command);
    }
    
    public static HashMap<ProductModel, Integer> filterOnStock(int season_id, int size){
    	String command = "SELECT *, (SELECT SUM(num)" + 
    			"           FROM products" + 
    			"		   WHERE n_model = product_models.n_model  AND size="+size+") AS num" + 
    			" FROM product_models " + 
    			" WHERE season_id = "+season_id+" " + 
    			"					  AND EXISTS (SELECT * " + 
    			"                                  FROM products" + 
    			"                                  WHERE n_model = product_models.n_model  AND size="+size+" AND num>0)";
    	return filterOnStock(command);
    }
    
    public static HashMap<ProductModel, Integer> filterOnStock(int category_id, int season_id, int size, int matherial){
    	String command = "SELECT *, (SELECT SUM(num)" + 
    			"           FROM products" + 
    			"		   WHERE n_model = product_models.n_model  AND size="+size+") AS num" + 
    			" FROM product_models " + 
    			" WHERE category_id = "+category_id+" AND season_id = "+season_id+" " + 
    			"					  AND EXISTS (SELECT * " + 
    			"                                  FROM products" + 
    			"                                  WHERE n_model = product_models.n_model  AND size="+size+" AND num>0)" + 
    			"                      AND n_model IN (SELECT n_model" + 
    			"                                      FROM model_materials" + 
    			"                                      WHERE material_id = "+matherial+" )" + 
    			"";
    	return filterOnStock(command);
    }
    
    public static HashMap<ProductModel, Integer> filterOnStock(int category_id, int season_id, int size){
    	String command = "SELECT *, (SELECT SUM(num)" + 
    			"           FROM products" + 
    			"		   WHERE n_model = product_models.n_model  AND size="+size+") AS num" + 
    			" FROM product_models " + 
    			" WHERE category_id = "+category_id+" AND season_id = "+season_id+" " + 
    			"					  AND EXISTS (SELECT * " + 
    			"                                  FROM products" + 
    			"                                  WHERE n_model = product_models.n_model  AND size="+size+" AND num>0)";
    	return filterOnStock(command);
    }
    
    public static HashMap<ProductModel, Integer> filterOnStockSetOfSizes(int category_id, int season_id, int matherial, int from, int to){
    	String command = "SELECT *, (SELECT SUM(num)" + 
    			"           FROM products" + 
    			"		   WHERE n_model = product_models.n_model AND size>=36 AND size<=40) AS num" + 
    			" FROM product_models " + 
    			" WHERE category_id = "+category_id+" AND season_id = "+season_id+"" + 
    			"					  AND EXISTS (SELECT * " + 
    			"                                  FROM products" + 
    			"                                  WHERE n_model = product_models.n_model  AND size>="+from+" AND size<="+to+")\r\n" + 
    			"                      AND n_model IN (SELECT n_model" + 
    			"                                      FROM model_materials" + 
    			"                                      WHERE material_id = "+matherial+")";
    	return filterOnStock(command);
    }
    
    protected static HashMap<ProductModel, Integer> filterOnStock(String command){
    	HashMap<ProductModel, Integer> res = new HashMap<ProductModel, Integer>();
    	
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
	    		int n = rs.getInt("num");
	    		res.put(s, n);
	    	}
	    	statement.close();
		} catch (SQLException | ArgumentException e) {
			e.printStackTrace();
		}
    	return res;
    }
    
    /**
     * 
     * @param category_id
     * @param season_id
     * @param matherial
     * @return Повертає відсортовані моделі та рахує наявну кількість кожного розміру моделі
     */
    public static HashMap<Pair<String, Integer>, Integer> filterBySizes(int category_id, int season_id, int matherial){
    	String command = "SELECT products.n_model, size, SUM(num) AS num" + 
    			" FROM product_models INNER JOIN products ON products.n_model = product_models.n_model" + 
    			" WHERE category_id = "+category_id+" AND season_id = "+season_id+"" + 
    			"                      AND products.n_model IN (SELECT n_model" + 
    			"                                           FROM model_materials" + 
    			"                                           WHERE material_id = "+matherial+")" + 
    			" GROUP BY  products.n_model, size" + 
    			"";
    	return filterBySizes(command);
    }
    
    /**
     * @return Повертає відсортовані моделі та рахує наявну кількість кожного розміру моделі
     */
    public static HashMap<Pair<String, Integer>, Integer> filterBySizes(int category_id){
    	String command = "SELECT products.n_model, size, SUM(num) AS num" + 
    			" FROM product_models INNER JOIN products ON products.n_model = product_models.n_model" + 
    			" WHERE category_id = "+category_id+" " + 
    			" GROUP BY  products.n_model, size " ;
    	return filterBySizes(command);
    }
    
    /**
     * @return Повертає відсортовані моделі та рахує наявну кількість кожного розміру моделі
     */
    public static HashMap<Pair<String, Integer>, Integer> filterBySeasonBySizes(int season_id){
    	String command = "SELECT products.n_model, size, SUM(num) AS num" + 
    			" FROM product_models INNER JOIN products ON products.n_model = product_models.n_model" + 
    			" WHERE season_id = "+season_id+" " + 
    			" GROUP BY  products.n_model, size" + 
    			"";
    	return filterBySizes(command);
    }
    
    
 
    /**
     * @return Повертає відсортовані моделі та рахує наявну кількість кожного розміру моделі
     */
    protected static HashMap<Pair<String, Integer>, Integer> filterBySizes(String command){
    	HashMap<Pair<String, Integer>, Integer> res = new HashMap<Pair<String, Integer>, Integer>();
    	
    	try {
			Statement statement = db.connection.createStatement();
	    	ResultSet rs = statement.executeQuery(command);
	    	while(rs.next()) {
	    		
	    		String n_model = rs.getString("n_model");
	    		int size = rs.getInt("size");
	    		int n = rs.getInt("num");
	    		Pair<String, Integer> p= new Pair<String, Integer>(n_model, size);
	    		res.put(p, n);
	    	}
	    	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return res;
    }
    
	@Override
	public String toString() {
		return "ProductModel [n_model=" + n_model + ",\n category=" + category + ",\n season=" + season + ","
				+ "\n name=" + name + ",\n price_purchase="
				+ price_purchase + ",\n extra_charge=" + extra_charge + ",\n shop_price=" + shop_price + ",\n number="
				+ number + ",\n is_full=" + is_full + ",\n is_female=" + is_female + ",\n is_male=" + is_male + ",\n is_child="
				+ is_child + "]";
	}

	
}
