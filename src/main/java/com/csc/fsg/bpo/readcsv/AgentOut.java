package com.csc.fsg.bpo.readcsv;

public class AgentOut {
	private String companyCode;
	private String status;
	
	public String agentNumber, searchFirstName, searchLastName, companyId;	
	public int statusId;
	
	public AgentOut(String companyCode, String status, String agentNumber, String searchFirstName,
			String searchLastName, String companyId, int statusId) {
		super();
		this.companyCode = companyCode;
		this.status = status;
		this.agentNumber = agentNumber;
		this.searchFirstName = searchFirstName;
		this.searchLastName = searchLastName;
		this.companyId = companyId;
		this.statusId = statusId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	
}
