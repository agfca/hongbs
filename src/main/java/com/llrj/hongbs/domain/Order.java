package com.llrj.hongbs.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="t_order")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String oddNum;              /*单号*/
    private String partyA;              /*学校名称,甲方*/
    private String orderTime;           /*下单时间*/
    private String deliveryTime;        /*交货时间*/
    private String shengcanTime;        /*生产时间*/
 	private String userName;            /*销售名字*/
    private String fileName;            /*下单人名字*/
    private String file2Name;           /*制版文件名:单号+甲方*/
    private String orderStatu;          /*订单状态  1.销售添加订单  2.销售确认订单  3.工艺已核对,需要下单  4.制版 5.工艺师核对制版  6.采购,7_11_12.生成安排,8.厂部,9.配料,15.裁缝 ,
                                            10.扫尾,11_1/11_2/11_3.直营/招商/外贸,9_1/9_2/9_3.衬衫/礼服/运动,13_1/13_2/13_3.售后完毕，未收到钱14.财务*/
    private String remark;              /*备注*/
    private String orderName;           /*订单名称*/
    @Column(columnDefinition = "INT default 0")
    private Integer material;            /*是否有材料单  0  没有  1 有*/

    @Column(columnDefinition = "INT default 0")
    private Integer orderSx;            /*订单生产顺序*/
    private String mainFabric;          /*主面料*/
    private String mainFabricFile;      /*制版文件*/
    @Column(columnDefinition = "INT default 0")
    private Integer mainNum;            /*主面料数量*/
    private String auxFabric;           /*辅料*/
    private String auxFabricFile;       /*辅料文件*/
    @Column(columnDefinition = "INT default 0")
    private Integer auxNum;             /*辅料数量*/
    private String model;               /*款式*/
    private String modelFile;           /*款式文件*/
    @Column(columnDefinition = "INT default 0")
    private Integer modelNum;           /*款式数量*/

    // getter和setter

    public Integer getMaterial() {
        return material;
    }

    public void setMaterial(Integer material) {
        this.material = material;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOddNum() {
        return oddNum;
    }

    public void setOddNum(String oddNum) {
        this.oddNum = oddNum;
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getShengcanTime() {
		return shengcanTime;
	}

	public void setShengcanTime(String shengcanTime) {
		this.shengcanTime = shengcanTime;
	}
    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOrderStatu() {
        return orderStatu;
    }

    public void setOrderStatu(String orderStatu) {
        this.orderStatu = orderStatu;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getFile2Name() {
        return file2Name;
    }

    public void setFile2Name(String file2Name) {
        this.file2Name = file2Name;
    }

    public Integer getOrderSx() {
        return orderSx;
    }

    public void setOrderSx(Integer orderSx) {
        this.orderSx = orderSx;
    }

    public String getMainFabric() {
        return mainFabric;
    }

    public void setMainFabric(String mainFabric) {
        this.mainFabric = mainFabric;
    }

    public String getMainFabricFile() {
        return mainFabricFile;
    }

    public void setMainFabricFile(String mainFabricFile) {
        this.mainFabricFile = mainFabricFile;
    }

    public Integer getMainNum() {
        return mainNum;
    }

    public void setMainNum(Integer mainNum) {
        this.mainNum = mainNum;
    }

    public String getAuxFabric() {
        return auxFabric;
    }

    public void setAuxFabric(String auxFabric) {
        this.auxFabric = auxFabric;
    }

    public String getAuxFabricFile() {
        return auxFabricFile;
    }

    public void setAuxFabricFile(String auxFabricFile) {
        this.auxFabricFile = auxFabricFile;
    }

    public Integer getAuxNum() {
        return auxNum;
    }

    public void setAuxNum(Integer auxNum) {
        this.auxNum = auxNum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelFile() {
        return modelFile;
    }

    public void setModelFile(String modelFile) {
        this.modelFile = modelFile;
    }

    public Integer getModelNum() {
        return modelNum;
    }

    public void setModelNum(Integer modelNum) {
        this.modelNum = modelNum;
    }
}
