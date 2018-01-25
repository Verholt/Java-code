package com.dsv.road.shared.masterdata.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

public class DtoPackageType extends AbstractDto implements Serializable {

    private static final long serialVersionUID = -1351910369278522340L;
    @NotNull
    @Size(max = 5)
    String shortAbbreviation;
    @NotNull
    @Size(max = 50)
    String longAbbreviation;
    @NotNull
    @Size(max = 50)
    String description;

    @Min(0)
    int length;

    @Min(0)
    int height;

    @Min(0)
    int width;

    BigDecimal ldm;
    boolean allowStack;

    @Min(0)
    int stackMaxWeight;

    @Min(0)
    int nonStackMaxWeight;

    @Min(0)
    int stackMaxHeight;

    @Min(0)
    int nonStackMaxHeight;

    public DtoPackageType() {
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public String toString() {
        return getShortAbbreviation() + " " + getLongAbbreviation() + " " + isAllowStack();

    }
}
