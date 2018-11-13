package org.liuzhibin.research.mycat.domain;

public class MemberAccount {
	private long memberId;
	private String account;
	private int accountHash;
	
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getAccountHash() {
		return accountHash;
	}
	public void setAccountHash(int accountHash) {
		this.accountHash = accountHash;
	}
}