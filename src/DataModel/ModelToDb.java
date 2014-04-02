package DataModel;

public interface ModelToDb {
	void ConvertElementToDb(ElementMap node);
	void ConvertNodeToDb(NodeMap node);
	void ConvertRelationToDb(RelationMap node);
	void ConvertWayToDb(WayMap node);

	void ConvertMemeberToDb(MemberMap node);
	void ConvertTagToDb(TagMap node);
}
