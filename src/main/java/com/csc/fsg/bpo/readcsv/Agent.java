package com.csc.fsg.bpo.readcsv;

public class Agent {

	public String agentNumber, searchFirstName, searchLastName, companyId;	
	public int statusId;
	
	public Agent(){};
	
	public Agent(String agentNumber, String searchFirstName, String searchLastName, String companyId, int statusId) {
		super();
		this.agentNumber = agentNumber;
		this.searchFirstName = searchFirstName;
		this.searchLastName = searchLastName;
		this.companyId = companyId;
		this.statusId = statusId;
	}
	public String getAgentNumber() {
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}
	public String getSearchFirstName() {
		return searchFirstName;
	}
	public void setSearchFirstName(String searchFirstName) {
		this.searchFirstName = searchFirstName;
	}
	public String getSearchLastName() {
		return searchLastName;
	}
	public void setSearchLastName(String searchLastName) {
		this.searchLastName = searchLastName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "Agent [agentNumber=" + agentNumber + ", searchFirstName=" + searchFirstName + ", searchLastName="
				+ searchLastName + ", companyId=" + companyId + ", statusId=" + statusId + "]";
	}
	
	
}
