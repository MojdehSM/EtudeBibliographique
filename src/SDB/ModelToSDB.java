package SDB;

import java.util.Iterator;
import java.util.Random;

import DataModel.ElementMap;
import DataModel.MemberMap;
import DataModel.ModelToDb;
import DataModel.NodeMap;
import DataModel.RelationMap;
import DataModel.TagMap;
import DataModel.WayMap;
import DataModel.XmlToModel;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntProperty;

public class ModelToSDB {

	XmlToModel data;
	ModelFactory model;

	public ModelToSDB(XmlToModel data) {
		this.data = data;
		model = ModelFactory.getMElement();

	}

	public void convertAll() {
		for (NodeMap node : data.getNodes()) {
			ConvertNodeToDb(node);
		}

		for (RelationMap relation : data.getRelations()) {
			ConvertRelationToDb(relation);
		}

		for (WayMap way : data.getWays()) {
			ConvertWayToDb(way);
		}

	}

	public void ConvertElementToDb(Individual m, ElementMap node) {
		/*
		 * set Entity's Individual Properties
		 */
		Iterator<OntProperty> stmt = model.getElement().listDeclaredProperties();

		
		//System.err.println(node);
		while (stmt.hasNext()) {
			OntProperty currentProperty = stmt.next();

			if (currentProperty.getLocalName().equals("elementId")) {
				m.addProperty(currentProperty, node.getElementId());
			} else if (currentProperty.getLocalName().equals("elementVisible")) {
				m.addProperty(currentProperty, node.getElementVisible());
			} else if (currentProperty.getLocalName().equals("elementVersion")) {
				if (node.getElementVisible() != null) {
					m.addProperty(currentProperty, node.getElementVersion());
				}
			} else if (currentProperty.getLocalName().equals("elementChangeset")) {
				m.addProperty(currentProperty, node.getElementChangeset());
			} else if (currentProperty.getLocalName().equals("elementTimestamp")) {
				if (node.getElementTimestamp() != null) {
					m.addProperty(currentProperty, node.getElementTimestamp());
				}
			} else if (currentProperty.getLocalName().equals("elementUser")) {
				m.addProperty(currentProperty, node.getElementUser());
			} else if (currentProperty.getLocalName().equals("elementUId")) {
				m.addProperty(currentProperty, node.getElementUId());
			} else if (currentProperty.getLocalName().equals("elementType")) {
				m.addProperty(currentProperty, node.getElementType().toString());
			}
		}
		for (TagMap tag : node.getTags()) {
			ConvertTagToDb(m, tag);
		}

	}

	public void ConvertNodeToDb(NodeMap node) {
		Individual m = model.getNode().createIndividual(model.getNs_element() + node.getElementId());
		ConvertElementToDb(m, node);

		Iterator<OntProperty> stmt = model.getNode().listDeclaredProperties();

		while (stmt.hasNext()) {
			OntProperty currentProperty = stmt.next();

			if (currentProperty.getLocalName().equals("lon")) {
				m.addProperty(currentProperty, node.getNodeLon());
			} else if (currentProperty.getLocalName().equals("lat")) {
				m.addProperty(currentProperty, node.getNodeLat());
			}
		}

	}

	public void ConvertRelationToDb(RelationMap node) {
		Individual m = model.getRelation().createIndividual(model.getNs_element() + node.getElementId());
		ConvertElementToDb(m, node);

		for (MemberMap mem : node.getMembers()) {
			ConvertMemeberToDb(m, mem);
		}
	}

	public void ConvertWayToDb(WayMap node) {
		Individual m = model.getWay().createIndividual(model.getNs_element() + node.getElementId());
		ConvertElementToDb(m, node);

	}

	public void ConvertMemeberToDb(Individual ele, MemberMap node) {
		Iterator<OntProperty> stmt = model.getTag().listDeclaredProperties();

		Individual m = model.getNode().createIndividual(model.getNs_tags() + node.getmId());

		while (stmt.hasNext()) {
			OntProperty currentProperty = stmt.next();

			if (currentProperty.getLocalName().equals("memberId")) {
				m.addProperty(currentProperty, node.getmId() + "");
			} else if (currentProperty.getLocalName().equals("mType")) {
				m.addProperty(currentProperty, node.getTypeM());
			} else if (currentProperty.getLocalName().equals("mRef")) {
				m.addProperty(currentProperty, model.getNs_element() + node.getRef());
			} else if (currentProperty.getLocalName().equals("role")) {
				m.addProperty(currentProperty, node.getRole());
			} else if (currentProperty.getLocalName().equals("memberRelationId")) {
				m.addProperty(currentProperty, ele);
			}

		}

	}

	/**
	 * Convert Tag to RDF
	 * 
	 * @param ele
	 * @param node
	 */
	public void ConvertTagToDb(Individual ele, TagMap node) {
		Iterator<OntProperty> stmt = model.getTag().listDeclaredProperties();

		Individual m = model.getNode().createIndividual(model.getNs_tags() + node.getTagId());

		while (stmt.hasNext()) {
			OntProperty currentProperty = stmt.next();

			if (currentProperty.getLocalName().equals("tagId")) {
				m.addProperty(currentProperty, node.getTagId() + "");
			} else if (currentProperty.getLocalName().equals("tagElementId")) {
				m.addProperty(currentProperty, ele);
			} else if (currentProperty.getLocalName().equals("tagKey")) {
				m.addProperty(currentProperty, node.getTagKey());
			} else if (currentProperty.getLocalName().equals("tagValue")) {
				m.addProperty(currentProperty, node.getTagValue());
			}

		}

	}

}
