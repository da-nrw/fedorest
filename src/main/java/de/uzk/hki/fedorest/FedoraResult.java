package de.uzk.hki.fedorest;

public class FedoraResult {
	
	private String content;
	private int status;
	
	public FedoraResult(int status, String content) {
		this.status = status;
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

}
