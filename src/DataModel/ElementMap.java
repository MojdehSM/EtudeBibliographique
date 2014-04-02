package DataModel;

import java.util.LinkedList;
import java.util.List;

import APIUtils.DumpString;
public class ElementMap implements Cloneable {

	protected String elementId;
	protected String elementVisible;
	protected String elementVersion;
	protected String elementChangeset;
	protected String elementTimestamp;
	protected String elementUser;
	protected String elementUId;
	protected OpenStreetMapType elementType;
	protected List<TagMap> tags = new LinkedList<>();

	public List<TagMap> getTags() {
		return tags;
	}
	
	public void addTags(TagMap tag) {
		
		
		this.tags.add(tag);
	}
	
	public String getElementId() {
		return this.elementId;

	}

	public void setElementId(String id) {
		this.elementId = id;
	}

	public String getElementVisible() {
		return this.elementVisible;
	}

	public void setElementVisible(String visible) {
		this.elementVisible = visible;
	}

	public String getElementVersion() {
		return this.elementVersion;
	}

	public void setElementVersion(String version) {
		this.elementVersion = version;
	}

	public String getElementChangeset() {
		return this.elementChangeset;
	}

	public void setEementChangeset(String changset) {
		this.elementChangeset = changset;
	}

	public String getElementTimestamp() {
		return this.elementTimestamp;
	}

	public void setElementTimestamp(String timestamp) {
		this.elementTimestamp = timestamp;
	}

	public String getElementUser() {
		return this.elementUser;
	}

	public void setElementUser(String user) {
		this.elementUser = user;
	}

	public String getElementUId() {
		return this.elementUId;
	}

	public void setElementUId(String userId) {
		this.elementUId = userId;
	}

	public OpenStreetMapType getElementType() {
		return this.elementType;
	}

	public void setElementType(OpenStreetMapType type) {
		this.elementType = type;
	}
	


	@Override
	public String toString() {
		return elementId +" "+elementType+" "+elementVersion+" "+elementChangeset;
	}
}
