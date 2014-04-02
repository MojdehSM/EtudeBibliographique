package DataModel;

import java.util.ArrayList;
import java.util.List;

public class WayMap extends ElementMap {

	private List<String> nodesid = new ArrayList<String>();

	public WayMap() {
		elementType = OpenStreetMapType.way;
	}

	public List<String> getNodesid() {
		return nodesid;
	}

	public void addNodeid(String nodeid) {
		this.nodesid.add(nodeid);
	}
}
