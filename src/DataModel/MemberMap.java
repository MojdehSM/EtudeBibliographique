package DataModel;

import java.util.Random;

public class MemberMap {
	
	private long mId;
	private String mType;
	private String mRef;
	private String role;

	public MemberMap() {
		mId = new Random().nextLong();
	}
	
	
	public long getmId() {
		return mId;
	}
	
	public String getTypeM() {
		return this.mType;
	}

	public void setTypeM(String type) {
		this.mType = type;
	}

	public String getRef() {
		return this.mRef;
	}

	public void setRef(String wayREf) {
		this.mRef = wayREf;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String memberRole) {
		this.role = memberRole;
	}

}
