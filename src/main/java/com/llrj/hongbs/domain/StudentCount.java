package com.llrj.hongbs.domain;

import javax.persistence.*;

@Entity
@Table(name="t_student_count")
public class StudentCount {
    @Id
    @GeneratedValue
    private Long id;
    private String oddNum;/*单号*/
    private String schoolName;/*学校*/
    @Column(nullable = false)
    private Integer sum;/*总数*/
    @Column(nullable = false)
    private Integer updateSign;/*是否需要更新   1需要，0不需要*/
    @Column(nullable = false)
    private Integer s;/*尺码  -- S,M,L,XL,XXL,XXXL*/
    @Column(nullable = false)
    private Integer m;
    @Column(nullable = false)
    private Integer l;
    @Column(nullable = false)
    private Integer xl;
    @Column(nullable = false)
    private Integer xxl;
    @Column(nullable = false)
    private Integer xxxl;

    // getter和setter
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getUpdateSign() {
        return updateSign;
    }

    public void setUpdateSign(Integer updateSign) {
        this.updateSign = updateSign;
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
        this.s = s;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public Integer getL() {
        return l;
    }

    public void setL(Integer l) {
        this.l = l;
    }

    public Integer getXl() {
        return xl;
    }

    public void setXl(Integer xl) {
        this.xl = xl;
    }

    public Integer getXxl() {
        return xxl;
    }

    public void setXxl(Integer xxl) {
        this.xxl = xxl;
    }

    public Integer getXxxl() {
        return xxxl;
    }

    public void setXxxl(Integer xxxl) {
        this.xxxl = xxxl;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
