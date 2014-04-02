package DataModel;

import java.util.Random;


public class TagMap {

	private long tagId;
	private String tagKey;
	private String tagValue;

	
	public TagMap() {
		tagId = new Random().nextLong();
	}
	
	public long getTagId() {
		return tagId;
	}
	
	
	public String getTagKey() {
		return this.tagKey;
	}

	public void setTagKey(String key) {
		this.tagKey = key;
	}

	public String getTagValue() {
		return this.tagValue;
	}

	public void setTagValue(String value) {
		this.tagValue = value;
	}
}
