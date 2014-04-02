package Hbase;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.taglibs.standard.tag.el.xml.ParseTag;
import org.jruby.compiler.ir.operands.Array;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {

	String path;

	public XmlParser(String filename) {
		path = filename;
	}

	// List<ItemHbase>
	public List<ItemHbase> parse() throws Exception {

		File xmlFile = new File(path);
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(xmlFile);
		Element root = doc.getDocumentElement();
		root.normalize();

		List<ItemHbase> lstElements = new LinkedList<>();
		// Parse file XML
		if (root.getTagName().equals("osm")) {
			for (Node node = root.getFirstChild(); node != null; node = node
					.getNextSibling()) {

				if (!(node instanceof Element)) {
					continue;
				}

				Element elt = (Element) node;
				if (elt.getTagName().equals("bounds")) {
					continue;
				}
				ItemHbase myElement = parseCommon(elt);

				if (elt.getTagName().equals("way")) {
					myElement.elementType = "way";
					parseWay(myElement, elt);
					List<ItemHbase> ltag = parseTags(myElement, elt);
					lstElements.addAll(ltag);

				} else if (elt.getTagName().equals("relation")) {
					myElement.elementType = "relation";
					parseRelation(myElement, elt);
					List<ItemHbase> ltag = parseTags(myElement, elt);
					lstElements.addAll(ltag);

				} else if (elt.getTagName().equals("node")) {
					myElement.elementType = "node";
					parseNode(myElement, elt);
					List<ItemHbase> ltag = parseTags(myElement, elt);
					lstElements.addAll(ltag);
				}

				lstElements.add(myElement);
			}
		}
		return lstElements;
	}

	ItemHbase parseCommon(Element elt) {
		ItemHbase myElement = new ItemHbase();
		for (int j = 0; j < elt.getAttributes().getLength(); j++) {

			switch (elt.getAttributes().item(j).getNodeName()) {
			case "id":
				myElement.elementId = elt.getAttributes().item(j)
						.getNodeValue();
				break;
			case "visible":
				myElement.elementVisible = elt.getAttributes().item(j)
						.getNodeValue();
				break;
			case "version":
				myElement.elementVersion = elt.getAttributes().item(j)
						.getNodeValue();
				break;
			case "changeset":
				myElement.elementChangeset = elt.getAttributes().item(j)
						.getNodeValue();
				break;
			case "timestamp":
				myElement.elementTimestamp = elt.getAttributes().item(j)
						.getNodeValue();
				break;
			case "user":
				myElement.elementUser = elt.getAttributes().item(j)
						.getNodeValue();
				break;
			case "uid":
				myElement.elementUId = elt.getAttributes().item(j)
						.getNodeValue();
				break;
			default:
				break;
			}
		}

		return myElement;
	}

	private List<ItemHbase> parseTags(ItemHbase myElement, Element elt) {
		List<ItemHbase> tags = new ArrayList<ItemHbase>();

		if (elt.getFirstChild() != null) {
			for (Node child = elt.getFirstChild(); child != null; child = child
					.getNextSibling()) {

				if (!(child instanceof Element)) {
					continue;
				}

				Element elti = (Element) child;
				if (elti.getTagName().equals("tag")) {
					ItemHbase myTag = new ItemHbase();
					myTag.elementType = "tag";
					myTag.tagKey = elti.getAttribute("k");
					myTag.tagValue = elti.getAttribute("v");
					myTag.tagElementId = myElement.elementId;
					myTag.tagId = new Random().nextLong();
					// System.out.println(myTag);
					tags.add(myTag);
				}
				
				if (elti.getTagName().equals("nd")) {
					ItemHbase myNd = new ItemHbase();
					myNd.elementType = "nd";
					myNd.ndElementId = myElement.elementId;
					myNd.ndRef = elti.getAttribute("ref");
					myNd.ndId = new Random().nextLong();
					tags.add(myNd);
					// System.out.println(myTag);
				}
				
				if (elti.getTagName().equals("member")) {
					ItemHbase myMember = new ItemHbase();
					myMember.elementType = "member";
					myMember.memberElementId = myElement.elementId;
					myMember.memberType=elti.getAttribute("type");
					myMember.memberRef=elti.getAttribute("ref");
					myMember.memberRole=elti.getAttribute("role");
					tags.add(myMember);
				}
			}
		}
		return tags;
	}

	private void parseNode(ItemHbase myElement, Element elt) {
		myElement.elementType = "node";
		for (int k = 0; k < elt.getAttributes().getLength(); k++) {

			switch (elt.getAttributes().item(k).getNodeName()) {

			case "lat":
				myElement.elementLat = elt.getAttributes().item(k)
						.getNodeValue();
				break;
			case "lon":
				myElement.elementLon = elt.getAttributes().item(k)
						.getNodeValue();
				break;
			default:
				break;
			}
		}
	}

	private void parseRelation(ItemHbase myElement, Element elt) {

	}

	private void parseWay(ItemHbase myElement, Element elt) {

	}
}
