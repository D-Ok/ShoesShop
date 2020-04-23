package shoesShop.DB;

import shoesShop.Exceptions.ArgumentException;

public enum Season {
		SPRING("весна"), SUMMER("літо"), AUTUMN("осінь"), WINTER("зима"), DEMI("демі"), NOTDEFINED("не визначено");
		
		private String name;
		private int id;
		
		Season(String name) {
			this.name = name;
			try {
				id = DBConnector.getIdByName(name, "seasons");
			} catch (ArgumentException e) {
				e.printStackTrace();
			}
		}
		
        public String getName(){ return name;}
        public int getId(){ return id;}
        
        public static String getNameById(int id) throws ArgumentException {
        	for(int i=0; i<Season.values().length; i++) {
				if(Season.values()[i].getId()==id) return Season.values()[i].getName();
			}
        	throw new ArgumentException("No Seasons with id = "+id);
        }
        
        public static Season getSeasonById(int id) throws ArgumentException {
        	for(int i=0; i<Season.values().length; i++) {
				if(Season.values()[i].getId()==id) return Season.values()[i];
			}
        	throw new ArgumentException("No Seasons with id = "+id);
        }
        
        public static Season getSeasonByName(String name) throws ArgumentException {
        	for(int i=0; i<Season.values().length; i++) {
				if(Season.values()[i].getName().equals(name)) return Season.values()[i];
			}
        	throw new ArgumentException("No Seasons with name = "+name);
        }
}
