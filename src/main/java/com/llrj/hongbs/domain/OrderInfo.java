package com.llrj.hongbs.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_info")
public class OrderInfo {

	@Id
	@GeneratedValue
	private Long id;
	private Long orderid;				/* 单号 */

	private String fabric;				/* 面料 ,json，多个面料，包括颜色 */
	private String styleImage;		/* 款式图片，多张款试图，json */
	private String style;				/* 款式 */
 	private String gongyi;				/* 工艺，多条，每条用分割符分开 */
	@Column(columnDefinition = "INT default 0")
	private int total;				/* 数量 */
	private String giveKH;				/* 是否给客户留有样衣 */
	private String containFabric;		/* 含量 */
	private String logoStation;		/* 校徽位置 */
	private String logoFile;			/* 校徽文件 */
	private String infoName;			/* 配置名称 */
	@Column(columnDefinition = "INT default 0")
	private int XXS;					/* 110型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XXS_M;				/* 115型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XS;					/* 120型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XS_M;					/* 125型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int S;						/* 130型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int S_M;					/* 135型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int M;						/* 140型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int M_M;					/* 145型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int L;						/* 150型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int L_M;					/* 155型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XL;					/* 160型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XL_M;					/* 165型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XXL;					/* 170型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XXL_M;				/* 175型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XXXL;					/* 180型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XXXL_M;				/* 185型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XXXXS;				/* 190型号多少人 */
	@Column(columnDefinition = "INT default 0")
	private int XXXXS_M;				/* 195型号多少人 */

	public OrderInfo(){}

 	public OrderInfo(Long orderid,String fabric,String styleImage,String style,String gongyi,
					 int total,String giveKH,String containFabric,String logoStation,
					 String logoFile,String infoName){
		this.orderid = orderid;
		this.fabric = fabric;
		this.styleImage = styleImage;
		this.style = style;
		this.gongyi = gongyi;
		this.total = total;
		this.giveKH = giveKH;
		this.containFabric = containFabric;
		this.logoStation = logoStation;
		this.logoFile = logoFile;
		this.infoName = infoName;
	}

	public int getXXS_M() {
		return XXS_M;
	}

	public void setXXS_M(int xXS_M) {
		XXS_M = xXS_M;
	}

	public int getXS_M() {
		return XS_M;
	}

	public void setXS_M(int xS_M) {
		XS_M = xS_M;
	}

	public int getS_M() {
		return S_M;
	}

	public void setS_M(int s_M) {
		S_M = s_M;
	}

	public int getM_M() {
		return M_M;
	}

	public void setM_M(int m_M) {
		M_M = m_M;
	}

	public int getL_M() {
		return L_M;
	}

	public void setL_M(int l_M) {
		L_M = l_M;
	}

	public int getXL_M() {
		return XL_M;
	}

	public void setXL_M(int xL_M) {
		XL_M = xL_M;
	}

	public int getXXL_M() {
		return XXL_M;
	}

	public void setXXL_M(int xXL_M) {
		XXL_M = xXL_M;
	}

	public int getXXXL_M() {
		return XXXL_M;
	}

	public void setXXXL_M(int xXXL_M) {
		XXXL_M = xXXL_M;
	}

	public int getXXXXS_M() {
		return XXXXS_M;
	}

	public void setXXXXS_M(int xXXXS_M) {
		XXXXS_M = xXXXS_M;
	}


	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

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

	public String getFabric() {
		return fabric;
	}

	public void setFabric(String fabric) {
		this.fabric = fabric;
	}

	public String getStyleImage() {
		return styleImage;
	}

	public void setStyleImage(String styleImage) {
		this.styleImage = styleImage;
	}

	public String getGongyi() {
		return gongyi;
	}

	public void setGongyi(String gongyi) {
		this.gongyi = gongyi;
	}

	public int getXXS() {
		return XXS;
	}

	public void setXXS(int xXS) {
		XXS = xXS;
	}

	public int getXS() {
		return XS;
	}

	public void setXS(int xS) {
		XS = xS;
	}

	public int getS() {
		return S;
	}

	public void setS(int s) {
		S = s;
	}

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public int getXL() {
		return XL;
	}

	public void setXL(int xL) {
		XL = xL;
	}

	public int getXXL() {
		return XXL;
	}

	public void setXXL(int xXL) {
		XXL = xXL;
	}

	public int getXXXL() {
		return XXXL;
	}

	public void setXXXL(int xXXL) {
		XXXL = xXXL;
	}

	public int getXXXXS() {
		return XXXXS;
	}

	public void setXXXXS(int xXXXS) {
		XXXXS = xXXXS;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getGiveKH() {
		return giveKH;
	}

	public void setGiveKH(String giveKH) {
		this.giveKH = giveKH;
	}

	public String getContainFabric() {
		return containFabric;
	}

	public void setContainFabric(String containFabric) {
		this.containFabric = containFabric;
	}

	public String getLogoStation() {
		return logoStation;
	}

	public void setLogoStation(String logoStation) {
		this.logoStation = logoStation;
	}

	public String getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(String logoFile) {
		this.logoFile = logoFile;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
