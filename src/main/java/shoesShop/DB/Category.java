package shoesShop.DB;

import shoesShop.Exceptions.ArgumentException;

public enum Category {
	
    BOOTS("черевики"), CHOBOTY("чоботи"), HOME("домашнє"), SPORT("спортивне"),
    BEACH("пляжне"), LIGHT("легке");
	
	private String name;
	private int id;
	
	Category(String name) {
		this.name = name;
		try {
			id = DBConnector.getIdByName(name, "categories");
		} catch (ArgumentException e) {
			e.printStackTrace();
		}
	}
	
    public String getName(){ return name;}
    public int getId(){ return id;}
    
    public static String getNameById(int id) throws ArgumentException {
    	for(int i=0; i<Category.values().length; i++) {
			if(Category.values()[i].getId()==id) return Category.values()[i].getName();
		}
    	throw new ArgumentException("No Category with id = "+id);
    }
    
    public static Category getCategorynById(int id) throws ArgumentException {
    	for(int i=0; i<Category.values().length; i++) {
			if(Category.values()[i].getId()==id) return Category.values()[i];
		}
    	throw new ArgumentException("No Category with id = "+id);
    }

}
