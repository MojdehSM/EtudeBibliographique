package Hbase;

public class ItemHbase {

	public static final String elementTable = "Elements";
	public static final String tagTable = "Tags";
	public static final String ndTable = "NDs";
	public static final String memberTable = "Members";

	public static final String communFamily = "CommunsINfo";
	public static final String nodeLocFamily = "NodeLocation";
	public static final String tagInfoFamily = "TagInfo";
	public static final String NdInfoFamily = "NDInfo";
	public static final String memberInfoFamily = "MemberInfo";
	public String elementId;
	public String elementVisible;
	public String elementVersion;
	public String elementChangeset;
	public String elementTimestamp;
	public String elementUser;
	public String elementUId;
	public String elementLat;
	public String elementLon;
	public String elementType;

	public Long tagId;
	public String tagKey;
	public String tagValue;
	public String tagElementId;

	public Long ndId;
	public String ndElementId;
	public String ndRef;

	public Long memberId;
	public String memberElementId;
	public String memberType;
	public String memberRef;
	public String memberRole;

	@Override
	public String toString() {
		String str = "";

		if (elementType.equals("tag")) {
			str = "tagId:" + tagId + ", Type:" + elementType + ", tagKey:"
					+ tagKey + ", tagValue:" + tagValue + ", tagNodeId:"
					+ tagElementId;

		} else if (elementType.equals("nd")) {
			str = "ndId:" + ndId + ", ndRef:" + ndRef + ", ndElementId:"
					+ ndElementId;
		} else if (elementType.equals("member")) {
			str = "";
		} else {
			str = "Id:" + elementId + ", Type:" + elementType + ", Timestamp:"
					+ elementTimestamp + ", User:" + elementUser + ", UID:"
					+ elementUId + ", Version:" + elementVersion + ", Visible:"
					+ elementVisible + ", Changeset:" + elementChangeset
					+ ", Lat:" + elementLat + ", Lon:" + elementLon;
		}
		return str;
	}

	// putRaw(String tableName,String raw, String family, String
	// qualifier,String value)
	public void save() {

		if (elementType.equals("tag")) {
			HBaseDBConfig.putRaw(tagTable, tagId.toString(), tagInfoFamily,
					"key", tagKey);
			HBaseDBConfig.putRaw(tagTable, tagId.toString(), tagInfoFamily,
					"value", tagValue);
			HBaseDBConfig.putRaw(tagTable, tagId.toString(), tagInfoFamily,
					"tagNodeId", tagElementId);
			return;
		}
		if (elementType.equals("nd")) {
			HBaseDBConfig.putRaw(ndTable, ndId.toString(), NdInfoFamily,
					"ndRef", ndRef);
			HBaseDBConfig.putRaw(ndTable, ndId.toString(), NdInfoFamily,
					"ndElementId", ndElementId);
			return;
		}
		if (elementType.equals("member")) {
			HBaseDBConfig.putRaw(memberTable, memberId.toString(),
					memberInfoFamily, "memberType", memberType);
			HBaseDBConfig.putRaw(memberTable, memberId.toString(),
					memberInfoFamily, "memberRef", memberRef);
			HBaseDBConfig.putRaw(memberTable, memberId.toString(),
					memberInfoFamily, "memberRole", memberRole);
			HBaseDBConfig.putRaw(memberTable, memberId.toString(),
					memberInfoFamily, "memberElementId", memberElementId);
			return;
		}
		HBaseDBConfig.putRaw(elementTable, elementId, communFamily, "visible",
				elementVisible);
		HBaseDBConfig.putRaw(elementTable, elementId, communFamily, "version",
				elementVersion);
		HBaseDBConfig.putRaw(elementTable, elementId, communFamily,
				"changeset", elementChangeset);
		HBaseDBConfig.putRaw(elementTable, elementId, communFamily,
				"timestamp", elementTimestamp);
		HBaseDBConfig.putRaw(elementTable, elementId, communFamily, "user",
				elementUser);
		HBaseDBConfig.putRaw(elementTable, elementId, communFamily, "uid",
				elementUId);

		if (elementType.equals("node")) {
			HBaseDBConfig.putRaw(elementTable, elementId, nodeLocFamily, "lat",
					elementLat);
			HBaseDBConfig.putRaw(elementTable, elementId, nodeLocFamily, "lon",
					elementLon);
		}

	}

}
