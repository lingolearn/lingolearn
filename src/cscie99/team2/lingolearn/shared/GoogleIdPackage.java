package cscie99.team2.lingolearn.shared;

import java.io.Serializable;

public class GoogleIdPackage implements Serializable {

	private String gmail;
	private String gplusId;
	
	public GoogleIdPackage(){}
	
	public GoogleIdPackage( String gmail, String gplusId ){
		this.gmail = gmail;
		this.gplusId = gplusId;
	}

	public boolean isValid(){
		return !gmail.equals("") && !gplusId.equals("");
	}
	
	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getGplusId() {
		return gplusId;
	}

	public void setGplusId(String gplusId) {
		this.gplusId = gplusId;
	}
	
	
}
