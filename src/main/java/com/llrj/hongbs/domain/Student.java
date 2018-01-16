package com.llrj.hongbs.domain;

import javax.persistence.*;

@Entity
@Table(name="t_student")
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String oddNum;/*单号*/
    private String schoolName;/*学校*/
    private String gradeName;/*年级*/
    private String className;/*班级*/
    private String studentName;/*学生名字*/
    private Integer sex;/*性别*/
    private Integer age;/*年龄*/
    private Integer height;/*身高*/
    private Integer size;/*尺码  -- S,M,L,XL,XXL,XXXL  0,1,2,3,4,5 */
    private String remark;/*备注*/

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
