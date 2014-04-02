package DataModel;

import APIUtils.DumpString;

public class NodeMap extends ElementMap {

	private String nodeLat;
	private String nodeLon;

	public NodeMap() {
		elementType = OpenStreetMapType.node;
	}

	public String getNodeLat() {
		return this.nodeLat;
	}

	public void setelNodeLat(String latitude) {
		this.nodeLat = latitude;
	}

	public String getNodeLon() {
		return this.nodeLon;
	}

	public void setNodeLon(String longitude) {
		this.nodeLon = longitude;
	}
	
	@Override
	public String toString() {
		return super.toString()+"\n"+DumpString.dumpString(this);
	}
	
}
