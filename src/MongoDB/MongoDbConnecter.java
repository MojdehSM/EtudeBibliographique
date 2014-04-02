package MongoDB;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

public class MongoDbConnecter {

	public static void config() throws Exception {
		try {

			File xmlFile = new File("mapMontpellier.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(xmlFile);
			Element root = doc.getDocumentElement();
			root.normalize();

			MongoClient mongoClient = null;
			try {
				mongoClient = new MongoClient("localhost", 27017);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			// Now connect to your databases
			DB db = mongoClient.getDB("db_EB");
			System.out.println("Connect to database successfully");
			
			// create the collections
			DBCollection nodeCollection = db.getCollection("nodeCollection");
			DBCollection wayCollection = db.getCollection("wayCollection");
			DBCollection relationCollection = db
					.getCollection("relationCollection");
			// Parse file XML
			if (root.getTagName().equals("osm")) {
				List<String> str = new ArrayList<String>();
				for (Node node = root.getFirstChild(); node != null; node = node
						.getNextSibling()) {
					BasicDBObject oNode = new BasicDBObject();
					BasicDBObject oWay = new BasicDBObject();
					BasicDBObject oRelation = new BasicDBObject();
					if (!(node instanceof Element)) {
						continue;
					}

					Element elt = (Element) node;

					if (elt.getTagName().equals("bounds")) {

					} else if (elt.getTagName().equals("node")) {

						for (int j = 0; j < elt.getAttributes().getLength(); j++) {
							oNode.put(
									elt.getAttributes().item(j).getNodeName(),
									elt.getAttributes().item(j).getNodeValue());
						}
						 nodeCollection.insert(oNode);
						// System.out.println("trouve:" + oNode);

					} else if (elt.getTagName().equals("way")) {

						for (int j = 0; j < elt.getAttributes().getLength(); j++) {
							oWay.put(elt.getAttributes().item(j).getNodeName(),
									elt.getAttributes().item(j).getNodeValue());
						}
						 wayCollection.insert(oWay);
						// System.out.println("trouve:" + oWay);

					} else if (elt.getTagName().equals("relation")) {

						for (int j = 0; j < elt.getAttributes().getLength(); j++) {
							oRelation.put(elt.getAttributes().item(j)
									.getNodeName(), elt.getAttributes().item(j)
									.getNodeValue());
						}
						 relationCollection.insert(oRelation);
						// System.out.println("trouve:" + oRelation);
					}
				}

				DBCursor cursor = nodeCollection.find();
				try {
					while (cursor.hasNext()) {
						DBObject o = cursor.next();
						String myO = (String) o.get("uid");
						if (myO.equals("663859")) {
							for (String key : o.keySet()) {
								str.add(key + ":" + o.get(key));
							}
						}
					}
					for (int i = 0; i < str.size(); i++) {
						System.out.println(str);
					}

				} finally {
					cursor.close();
				}

			} else {
				throw new Error("Bad tag name: " + root.getTagName());
			}

		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

}
