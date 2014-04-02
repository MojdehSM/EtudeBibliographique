package DataModel;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import Hbase.ItemHbase;

public class XmlToModel {

	String path;
	List<NodeMap> nodes = new LinkedList<>();
	List<RelationMap> relations = new LinkedList<>();
	List<WayMap> ways = new LinkedList<>();

	public List<NodeMap> getNodes() {
		return nodes;
	}

	public List<RelationMap> getRelations() {
		return relations;
	}

	public List<WayMap> getWays() {
		return ways;
	}

	public XmlToModel(String filename) {
		path = filename;
	}

	// List<ItemHbase>
	public void parse() throws Exception {
		File xmlFile = new File(path);
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
		Element root = doc.getDocumentElement();
		root.normalize();

		if (!root.getTagName().equals("osm")) {
			throw new Exception(" The file is note a good xml");
		}

		// Parse file XML
		for (Node node = root.getFirstChild(); node != null; node = node.getNextSibling()) {

			if (!(node instanceof Element)) {
				continue;
			}

			Element elt = (Element) node;
			if (elt.getTagName().equals("bounds")) {
				continue;
			} else if (elt.getTagName().equals("way")) {
				WayMap myElement = new WayMap();
				parseCommon(myElement, elt);
				parseWay(myElement, elt);
				parseTags(myElement, elt);
				parseNd(myElement, elt);
				ways.add(myElement);
			} else if (elt.getTagName().equals("relation")) {
				RelationMap myElement = new RelationMap();
				parseCommon(myElement, elt);
				parseRelation(myElement, elt);
				parseTags(myElement, elt);
				parseMember(myElement, elt);
				relations.add(myElement);
			} else if (elt.getTagName().equals("node")) {
				NodeMap myElement = new NodeMap();
				parseCommon(myElement, elt);
				parseNode(myElement, elt);
				parseTags(myElement, elt);
				System.err.println(myElement);
				nodes.add(myElement);
			}
		}
	}

	/**
	 * Parse Common data
	 * @param myElement
	 * @param elt
	 */
	private void parseCommon(ElementMap myElement, Element elt) {

		for (int j = 0; j < elt.getAttributes().getLength(); j++) {

			switch (elt.getAttributes().item(j).getNodeName()) {
			case "id":
				myElement.elementId = elt.getAttributes().item(j).getNodeValue();
				break;
			case "visible":
				myElement.elementVisible = elt.getAttributes().item(j).getNodeValue();
				break;
			case "version":
				myElement.elementVersion = elt.getAttributes().item(j).getNodeValue();
				break;
			case "changeset":
				myElement.elementChangeset = elt.getAttributes().item(j).getNodeValue();
				break;
			case "timestamp":
				myElement.elementTimestamp = elt.getAttributes().item(j).getNodeValue();
				break;
			case "user":
				myElement.elementUser = elt.getAttributes().item(j).getNodeValue();
				break;
			case "uid":
				myElement.elementUId = elt.getAttributes().item(j).getNodeValue();
				break;
			default:
				break;
			}
		}

	}

	/**
	 * Parse Nd of Way
	 * @param myElement
	 * @param elt
	 */
	private void parseNd(WayMap myElement, Element elt) {
		if (elt.getFirstChild() != null) {
			for (Node child = elt.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (!(child instanceof Element)) {
					continue;
				}

				Element elti = (Element) child;

				if (elti.getTagName().equals("nd")) {
					myElement.addNodeid(elti.getAttribute("ref"));
				}
			}
		}
	}

	/**
	 * Parse Memeber of RElations 
	 * @param myElement
	 * @param elt
	 */
	private void parseMember(RelationMap myElement, Element elt) {
		if (elt.getFirstChild() != null) {
			for (Node child = elt.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (!(child instanceof Element)) {
					continue;
				}

				Element elti = (Element) child;
				if (elti.getTagName().equals("member")) {
					MemberMap myMember = new MemberMap();
					myMember.setTypeM(elti.getAttribute("type"));
					myMember.setRef(elti.getAttribute("ref"));
					myMember.setRole(elti.getAttribute("role"));
					myElement.addMember(myMember);
				}

			}
		}

	}

	/**
	 * Parse Tag ElementMap
	 * @param myElement
	 * @param elt
	 */
	private void parseTags(ElementMap myElement, Element elt) {
		if (elt.getFirstChild() != null) {
			for (Node child = elt.getFirstChild(); child != null; child = child.getNextSibling()) {

				if (!(child instanceof Element)) {
					continue;
				}

				Element elti = (Element) child;
				if (elti.getTagName().equals("tag")) {
					TagMap myTag = new TagMap();
					myTag.setTagKey(elti.getAttribute("k"));
					myTag.setTagValue(elti.getAttribute("v"));
					myElement.addTags(myTag);
				}
			}
		}
	}

	/**
	 * Parse Node Information
	 * @param myElement
	 * @param elt
	 */
	private void parseNode(NodeMap myElement, Element elt) {

		for (int k = 0; k < elt.getAttributes().getLength(); k++) {

			switch (elt.getAttributes().item(k).getNodeName()) {
			case "lat":
				myElement.setelNodeLat(elt.getAttributes().item(k).getNodeValue());
				break;
			case "lon":
				myElement.setNodeLon(elt.getAttributes().item(k).getNodeValue());
				break;
			default:
				break;
			}
		}
	}

	private void parseRelation(ElementMap myElement, Element elt) {

	}

	private void parseWay(ElementMap myElement, Element elt) {

	}

}
