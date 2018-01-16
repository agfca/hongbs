package com.llrj.hongbs.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="order_material")
public class OrderMaterial {
	
    @Id
    @GeneratedValue
    private Long id;
    private Long orderid;				/*单号*/
    private Long infoid;				/*配置id*/
    private String materialName;		/*材料名称*/
    private String supplierName;		/*供应商*/
    private int quantity;				/*数量*/
    private String unit;				/*单位耗料/幅宽*/
    private int summation;			/*合计耗料/米*/
	private String summationStr;		/*合计耗材  带单位*/
    private String isorder;				/*是否下单*/
    private String isover;				/*是否到货*/
    private String remark;				/*备注*/
    private Integer userid;			/*采买人id*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public Long getInfoid() {
		return infoid;
	}

	public void setInfoid(Long infoid) {
		this.infoid = infoid;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getSummation() {
		return summation;
	}

	public void setSummation(int summation) {
		this.summation = summation;
	}

	public String getSummationStr() {
		return summationStr;
	}

	public void setSummationStr(String summationStr) {
		this.summationStr = summationStr;
	}

	public String getIsorder() {
		return isorder;
	}

	public void setIsorder(String isorder) {
		this.isorder = isorder;
	}

	public String getIsover() {
		return isover;
	}

	public void setIsover(String isover) {
		this.isover = isover;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
}
