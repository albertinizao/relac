package com.opipo.relac.model;

import java.util.Date;

public class Relation implements Comparable<Relation>{
	private Date date;
	private Integer working;
	private Integer confidential;
	private Integer loyalty;
	private Integer trust;
	private Integer respect;
	private Integer funny;
	private Integer affection;
	public Date getDate() {
		return date==null?null:new Date(date.getTime());
	}
	public void setDate(Date date) {
		this.date = date==null?null:new Date(date.getTime());
	}
	public Integer getWorking() {
		return working;
	}
	public void setWorking(Integer working) {
		this.working = working;
	}
	public Integer getConfidential() {
		return confidential;
	}
	public void setConfidential(Integer confidential) {
		this.confidential = confidential;
	}
	public Integer getLoyalty() {
		return loyalty;
	}
	public void setLoyalty(Integer loyalty) {
		this.loyalty = loyalty;
	}
	public Integer getTrust() {
		return trust;
	}
	public void setTrust(Integer trust) {
		this.trust = trust;
	}
	public Integer getRespect() {
		return respect;
	}
	public void setRespect(Integer respect) {
		this.respect = respect;
	}
	public Integer getFunny() {
		return funny;
	}
	public void setFunny(Integer funny) {
		this.funny = funny;
	}
	public Integer getAffection() {
		return affection;
	}
	public void setAffection(Integer affection) {
		this.affection = affection;
	}	
//	public Double getAverage(){
//		return (working+confidential+loyalty+trust+respect+funny+affection)/7.0;
//	}
	@Override
	public int compareTo(Relation o) {
		return getDate().compareTo(o.getDate());
	}
}
