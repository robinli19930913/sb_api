package com.cn.lxg.web.test.excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CellBean implements Serializable {
    private static final long serialVersionUID = 4297269195062053587L;
    private Date valueByDate;
    private String dateFormat;
    private String valueByString;
    private Double valueByDouble;
    private Boolean valueByBoolean;
    private Integer valueByInteger;
    private int cellType;

    public CellBean() {
    }

    public CellBean(Date date, String dateFormat) {
        this.valueByDate = date;
        this.dateFormat = dateFormat;
    }

    public CellBean(String valueByString) {
        this.valueByString = valueByString;
    }

    public CellBean(Double valueByDouble) {
        this.valueByDouble = valueByDouble;
    }

    public CellBean(Boolean valueByBoolean) {
        this.valueByBoolean = valueByBoolean;
    }

    public CellBean(Integer valueByInteger) {
        this.valueByInteger = valueByInteger;
    }

    public String getValue() {
        if(this.valueByString != null) {
            return this.valueByString;
        } else if(this.valueByDate != null) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sf.format(this.valueByDate);
        } else {
            return this.valueByDouble != null?subZeroAndDot((new BigDecimal(this.valueByDouble.doubleValue())).toString()):(this.valueByBoolean != null?this.valueByBoolean.toString():(this.valueByInteger != null?this.valueByInteger.toString():""));
        }
    }

    public static String subZeroAndDot(String s) {
        if(s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");
            s = s.replaceAll("[.]$", "");
        }

        return s;
    }

    public Date getValueByDate() {
        return this.valueByDate;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public String getValueByString() {
        return this.valueByString;
    }

    public Double getValueByDouble() {
        return this.valueByDouble;
    }

    public Boolean getValueByBoolean() {
        return this.valueByBoolean;
    }

    public Integer getValueByInteger() {
        return this.valueByInteger;
    }

    public int getCellType() {
        return this.cellType;
    }

    public void setValueByDate(Date valueByDate) {
        this.valueByDate = valueByDate;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setValueByString(String valueByString) {
        this.valueByString = valueByString;
    }

    public void setValueByDouble(Double valueByDouble) {
        this.valueByDouble = valueByDouble;
    }

    public void setValueByBoolean(Boolean valueByBoolean) {
        this.valueByBoolean = valueByBoolean;
    }

    public void setValueByInteger(Integer valueByInteger) {
        this.valueByInteger = valueByInteger;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof CellBean)) {
            return false;
        } else {
            CellBean other = (CellBean)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                label87: {
                    Date this$valueByDate = this.getValueByDate();
                    Date other$valueByDate = other.getValueByDate();
                    if(this$valueByDate == null) {
                        if(other$valueByDate == null) {
                            break label87;
                        }
                    } else if(this$valueByDate.equals(other$valueByDate)) {
                        break label87;
                    }

                    return false;
                }

                String this$dateFormat = this.getDateFormat();
                String other$dateFormat = other.getDateFormat();
                if(this$dateFormat == null) {
                    if(other$dateFormat != null) {
                        return false;
                    }
                } else if(!this$dateFormat.equals(other$dateFormat)) {
                    return false;
                }

                label73: {
                    String this$valueByString = this.getValueByString();
                    String other$valueByString = other.getValueByString();
                    if(this$valueByString == null) {
                        if(other$valueByString == null) {
                            break label73;
                        }
                    } else if(this$valueByString.equals(other$valueByString)) {
                        break label73;
                    }

                    return false;
                }

                Double this$valueByDouble = this.getValueByDouble();
                Double other$valueByDouble = other.getValueByDouble();
                if(this$valueByDouble == null) {
                    if(other$valueByDouble != null) {
                        return false;
                    }
                } else if(!this$valueByDouble.equals(other$valueByDouble)) {
                    return false;
                }

                label59: {
                    Boolean this$valueByBoolean = this.getValueByBoolean();
                    Boolean other$valueByBoolean = other.getValueByBoolean();
                    if(this$valueByBoolean == null) {
                        if(other$valueByBoolean == null) {
                            break label59;
                        }
                    } else if(this$valueByBoolean.equals(other$valueByBoolean)) {
                        break label59;
                    }

                    return false;
                }

                Integer this$valueByInteger = this.getValueByInteger();
                Integer other$valueByInteger = other.getValueByInteger();
                if(this$valueByInteger == null) {
                    if(other$valueByInteger != null) {
                        return false;
                    }
                } else if(!this$valueByInteger.equals(other$valueByInteger)) {
                    return false;
                }

                if(this.getCellType() != other.getCellType()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof CellBean;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        Date $valueByDate = this.getValueByDate();
        int result1 = result * 59 + ($valueByDate == null?43:$valueByDate.hashCode());
        String $dateFormat = this.getDateFormat();
        result1 = result1 * 59 + ($dateFormat == null?43:$dateFormat.hashCode());
        String $valueByString = this.getValueByString();
        result1 = result1 * 59 + ($valueByString == null?43:$valueByString.hashCode());
        Double $valueByDouble = this.getValueByDouble();
        result1 = result1 * 59 + ($valueByDouble == null?43:$valueByDouble.hashCode());
        Boolean $valueByBoolean = this.getValueByBoolean();
        result1 = result1 * 59 + ($valueByBoolean == null?43:$valueByBoolean.hashCode());
        Integer $valueByInteger = this.getValueByInteger();
        result1 = result1 * 59 + ($valueByInteger == null?43:$valueByInteger.hashCode());
        result1 = result1 * 59 + this.getCellType();
        return result1;
    }

    public String toString() {
        return "CellBean(valueByDate=" + this.getValueByDate() + ", dateFormat=" + this.getDateFormat() + ", valueByString=" + this.getValueByString() + ", valueByDouble=" + this.getValueByDouble() + ", valueByBoolean=" + this.getValueByBoolean() + ", valueByInteger=" + this.getValueByInteger() + ", cellType=" + this.getCellType() + ")";
    }
}
