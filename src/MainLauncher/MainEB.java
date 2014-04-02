package MainLauncher;

import java.util.List;

import Hbase.HBaseDBConfig;
import Hbase.ItemHbase;
import Hbase.XmlParser;
import MongoDB.*;

public class MainEB {

	public static void main(String[] args) throws Exception {
		/*
		 * Run SDB
		 */
		
		
		/*
		 * Run MongeDB
		 */
		//MongoDbConnecter.config();

		/*
		 * Run HbaseDB
		 */
//		HBaseDBConfig.configTable(ItemHbase.elementTable,
//				ItemHbase.communFamily, ItemHbase.nodeLocFamily);
//		HBaseDBConfig.configTable(ItemHbase.tagTable, ItemHbase.tagInfoFamily);
//		HBaseDBConfig.configTable(ItemHbase.ndTable, ItemHbase.NdInfoFamily);
//		HBaseDBConfig.configTable(ItemHbase.memberTable,
//				ItemHbase.memberInfoFamily);
//
//		XmlParser pars = new XmlParser("mapMontpellier.xml");
//		List<ItemHbase> lst = pars.parse();
//		for (ItemHbase item : lst) {
//			 if (item.elementType.equals("tag")) {
//		// if (item.ndElementId.equals("261707007"))			 
//			System.out.println(item);
//			//item.save();
//			 }
//		}
	}
}
