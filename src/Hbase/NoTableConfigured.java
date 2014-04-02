package Hbase;

class NoTableConfigured extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoTableConfigured() {
		super("il faut appeler la fonction configTable(String name, String... family)");
	}
}
