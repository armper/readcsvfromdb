package com.csc.fsg.bpo.readcsv;

public class AgentIn {

	private String companyCode;
	private String agentNumber;
	private String status;
	
	public AgentIn(String companyCode, String agentNumber, String status) {
		super();
		this.companyCode = companyCode;
		this.agentNumber = agentNumber;
		this.status = status;
	}
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAgentNumber() {
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
