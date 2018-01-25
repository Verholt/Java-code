package com.dsv.road.masterdata.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.interceptor.Interceptors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.dsv.shared.logger.LoggerInterceptor;

@Entity
@Table(name = "PACKAGE_TYPE")
@NamedQuery(name = "findAllPackageTypes", query = "SELECT i FROM PackageType i")
@Interceptors(LoggerInterceptor.class)
public class PackageType {

	@Id
	@Column(name = "SHORT_ABBREVIATION", length = 5)
	String shortAbbreviation;

	@Column(name = "LONG_ABBREVIATION", nullable = false, length = 50)
	String longAbbreviation;

	@Column(length = 50, nullable = false)
	String description;

	int lenght;

	int height;

	int width;

	BigDecimal ldm;

	@Column(name = "ALLOW_STACK")
	boolean allowStack;

	@Column(name = "STACK_MAX_WEIGHT")
	int stackMaxWeight;

	@Column(name = "NON_STACK_MAX_WEIGHT")
	int nonStackMaxWeight;

	@Column(name = "STACK_MAX_HEIGHT")
	int stackMaxHeight;

	@Column(name = "NON_STACK_MAX_HEIGHT")
	int nonStackMaxHeight;

	@Column(name = "CREATED_AT", nullable = false)
	Timestamp createdAt;

	@Column(name = "CREATED_BY", length = 50, nullable = false)
	String createdBy;

	@Column(name = "UPDATED_AT")
	Timestamp updatedAt;

	@Column(name = "UPDATED_BY", length = 50)
	String updatedBy;

	public BigDecimal getLdm() {
		return ldm;
	}

	public void setLdm(BigDecimal ldm) {
		this.ldm = ldm;
	}

	public boolean isAllowStack() {
		return allowStack;
	}

	public void setAllowStack(boolean allowStack) {
		this.allowStack = allowStack;
	}

	public int getStackMaxWeight() {
		return stackMaxWeight;
	}

	public void setStackMaxWeight(int stackMaxWeight) {
		this.stackMaxWeight = stackMaxWeight;
	}

	public int getNonStackMaxWeight() {
		return nonStackMaxWeight;
	}

	public void setNonStackMaxWeight(int nonStackMaxWeight) {
		this.nonStackMaxWeight = nonStackMaxWeight;
	}

	public int getStackMaxHeight() {
		return stackMaxHeight;
	}

	public void setStackMaxHeight(int stackMaxHeight) {
		this.stackMaxHeight = stackMaxHeight;
	}

	public int getNonStackMaxHeight() {
		return nonStackMaxHeight;
	}

	public void setNonStackMaxHeight(int nonStackMaxHeight) {
		this.nonStackMaxHeight = nonStackMaxHeight;
	}

	public PackageType() {
		super();
	}

	public String getShortAbbreviation() {
		return shortAbbreviation;
	}

	public void setShortAbbreviation(String shortAbbreviation) {
		this.shortAbbreviation = shortAbbreviation;
	}

	public String getLongAbbreviation() {
		return longAbbreviation;
	}

	public void setLongAbbreviation(String longAbbreviation) {
		this.longAbbreviation = longAbbreviation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
