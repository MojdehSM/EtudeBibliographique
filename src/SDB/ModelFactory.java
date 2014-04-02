package SDB;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import APIUtils.OntClassType;
import DataModel.OpenStreetMapType;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.XSD;

public class ModelFactory {

	private OntModel model;
	private String namespace = "http://localhost:9000/openstreetmap#";
	private String ns_tag = "http://localhost:9000/openstreetmap/tag#";
	private String ns_member = "http://localhost:9000/openstreetmap/member#";
	private String ns_element = "http://localhost:9000/openstreetmap/element#";

	private OntClass tags;
	private OntClass members;
	private OntClass element;
	private OntClass node;
	private OntClass way;
	private OntClass relation;

	static ModelFactory singleton = null;

	private ModelFactory() {
		CreateIfNotExistOntologie();
	}

	public OntModel getModel() {
		return model;
	}

	public static ModelFactory getMElement() {
		if (singleton == null) {
			singleton = new ModelFactory();
		}
		return singleton;

	}

	/**
	 * Creation of Ontologie if does not exist
	 */
	public void CreateIfNotExistOntologie() {

		model = SDBConfig.getModelSDB();
		Iterator<OntClass> cl = model.listClasses();

		/*
		 * If base is created
		 */
		if (cl.hasNext()) {
			System.out.println("Getting existing ");
			do {
				OntClass c = cl.next();

				OntClassType type = OntClassType.valueOf(c.getLocalName());
				switch (type) {
				case tag:
					tags = c;
					break;
				case member:
					members = c;
					break;
				case element:
					element = c;
					break;
				case node:
					node = c;
					break;
				case way:
					way = c;
					break;
				case relation:
					relation = c;
					break;
				default:
					break;
				}

				System.err.println(c.getLocalName());
				Iterator<OntProperty> pso = c.listDeclaredProperties();
				while (pso.hasNext()) {
					OntProperty p = pso.next();
					System.out.println(p.getLocalName());
				}

			} while (cl.hasNext());
		}

		/*
		 * If not we create
		 */
		else {
			System.out.println("Creating Ont Class ");
			CreateOntClasses();
		}
	}

	/**
	 * Create Ont Classes of OpenStreetMap Model
	 */
	public void CreateOntClasses() {

		model.setNsPrefix("data", namespace);
		model.setNsPrefix("tags", ns_tag);
		model.setNsPrefix("members", ns_member);
		model.setNsPrefix("elements", ns_element);

		tags = model.createClass(namespace + "tag");
		members = model.createClass(namespace + "member");
		element = model.createClass(namespace + "element");
		node = model.createClass(namespace + "node");
		way = model.createClass(namespace + "way");
		relation = model.createClass(namespace + "relation");

		AddTagProperty();
		AddElementProperty();
		AddMemberProperty();
		AddNodeLocationProperty();
		AddSubClasses();
	}

	public OntProperty CreateProperty(OntClass classe, String namespace, String propertyName, String comment, String label, Resource resource) {
		OntProperty property = model.createOntProperty(namespace + propertyName);
		property.setDomain(classe);
		property.setRange(resource);
		property.addComment(comment, "fr");
		property.setLabel(label, "en");

		return property;
	}

	void AddTagProperty() {
		tags.addProperty(CreateProperty(tags, ns_tag, "tagId", "Tag id", "Tag id", XSD.xstring), ns_tag);
		tags.addProperty(CreateProperty(tags, ns_tag, "tagElementId", "Ref Node id", "Tag node id", element), ns_tag);
		tags.addProperty(CreateProperty(tags, ns_tag, "tagKey", "l'attribut K de tag", "Tag Key", XSD.xstring), ns_tag);
		tags.addProperty(CreateProperty(tags, ns_tag, "tagValue", "l'attribut value de tag ", "Tag Value", XSD.xstring), ns_tag);
	}

	void AddMemberProperty() {
		members.addProperty(CreateProperty(members, ns_member, "mId", "le type de son element", "Type de son element", XSD.xstring), ns_member);
		members.addProperty(CreateProperty(members, ns_member, "mType", "le type de son element", "Type de son element", XSD.xstring), ns_member);
		members.addProperty(CreateProperty(members, ns_member, "mRef", "reference de way", "wayRef", element), ns_member);
		members.addProperty(CreateProperty(members, ns_member, "role", "le role de member", "Role", XSD.xstring), ns_member);
		members.addProperty(CreateProperty(members, ns_member, "memberRelationId", "Ref Realtion id", "Relation node id", element), ns_member);
		
	}
	
	void AddElementProperty() {

		element.addProperty(CreateProperty(element, ns_element, "elementId", "le identifiant de l'element", "Id", XSD.xstring), ns_element);
		element.addProperty(CreateProperty(element, ns_element, "elementVisible", "la visibilité", "Visible", XSD.xstring), ns_element);
		element.addProperty(CreateProperty(element, ns_element, "elementVersion", "la version", "Version", XSD.xstring), ns_element);
		element.addProperty(CreateProperty(element, ns_element, "elementChangeset", "le changeset", "ChangeSet", XSD.xstring), ns_element);
		element.addProperty(CreateProperty(element, ns_element, "elementTimestamp", "le timestamp", "Timestamp", XSD.xstring), ns_element);
		element.addProperty(CreateProperty(element, ns_element, "elementUser", "le utilisateur", "User", XSD.xstring), ns_element);
		element.addProperty(CreateProperty(element, ns_element, "elementUId", "l'identifiant de l'utilisateur", "User Id", XSD.xstring), ns_element);
		element.addProperty(CreateProperty(element, ns_element, "elementType", "le type de l'element", "Element type", XSD.xstring), ns_element);
	}

	void AddNodeLocationProperty() {
		node.addProperty(CreateProperty(element, ns_element, "lat", "latitude de node", "Latitude", XSD.xdouble), ns_element);
		node.addProperty(CreateProperty(element, ns_element, "lon", "longitude de node", "Longitude", XSD.xdouble), ns_element);
	}

	void AddSubClasses() {

		// element.addSubClass(tags);
		element.addSubClass(node);
		element.addSubClass(way);
		element.addSubClass(relation);
	}

	/*
	 * Display ontologie
	 */
	public void toConsole() {
		try {
			model.write(new OutputStreamWriter(System.out, "UTF8"), "RDF/XML-ABBREV");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get Tag OntClass
	 */
	public OntClass getTag() {
		return tags;
	}
	
	public OntClass getRelation() {
		return relation;
	}

	public OntClass getMember() {
		return members;
	}

	public OntClass getElement() {
		return element;
	}

	public OntClass getNode() {
		return node;
	}

	public OntClass getWay() {
		return way;
	}

	/*
	 * Get OntCLASS FROM OpenStreetMap TYPE
	 */
	public OntClass getClassByString(String openStreetMapType) {

		OntClass ret = null;
		try {
			OpenStreetMapType name = OpenStreetMapType.valueOf(openStreetMapType);
			switch (name) {
			case tags:
				ret = tags;
				break;
			case members:
				ret = members;
				break;
			case node:
			case way:
			case relation:
				ret = element;
				break;
			default:
				ret = null;
			}

		} catch (Exception e) {
			System.out.println(" ONT CLASS Dont EXIST : " + openStreetMapType);
		}
		return ret;
	}

	/*
	 * Get Tag NameSpace
	 */
	public String getNs_tags() {
		return ns_tag;
	}

	/*
	 * Get Element NameSpace
	 */
	public String getNs_element() {
		return ns_element;
	}

	/*
	 * Get Member NameSpace
	 */
	public String getNs_member() {
		return ns_member;
	}

	public String getNamespace() {
		return namespace;
	}

}