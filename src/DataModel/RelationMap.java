package DataModel;

import java.util.ArrayList;
import java.util.List;

public class RelationMap extends ElementMap {
	private List<MemberMap> members = new ArrayList<MemberMap>();

	public RelationMap() {
		elementType = OpenStreetMapType.relation;
	}

	public List<MemberMap> getMembers() {
		return this.members;
	}

	public void addMember(MemberMap member) {
		this.members.add( member);
	}
}
