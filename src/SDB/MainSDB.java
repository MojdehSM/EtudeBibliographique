package SDB;

import DataModel.XmlToModel;

public class MainSDB {

	public static void main(String[] args) throws Exception {
		XmlToModel x = new XmlToModel("mapMontpellier.xml");
		x.parse();
		System.err.println(x.getNodes().size() +" "+x.getRelations().size() +" "+ x.getWays().size());
		
		ModelToSDB conv = new ModelToSDB(x);
		conv.convertAll();
		
	}

}
