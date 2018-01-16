package com.llrj.hongbs.service;

import com.llrj.hongbs.domain.Student;
import com.llrj.hongbs.domain.StudentCount;
import com.llrj.hongbs.domain.StudentCountRepository;
import com.llrj.hongbs.domain.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentCountRepository studentCountRepository;

    /**
     * @param student
     * @return
     *
     * 添加一个学生记录  学生记录需要有size字段
     * 每次添加时会更新 studentCount
     * 返回当前学生统计表
     * */
    public StudentCount create(Student student){
        /*创建新的学生记录*/
        student = studentRepository.save(student);
        /*查询该记录绑定的学生尺码统计表  并更新*/
        StudentCount studentCount = studentCountRepository.findByOddNum(student.getOddNum());
        if(studentCount==null){//没有该单号的学生尺码统计
            studentCount = new StudentCount();
            studentCount.setOddNum(student.getOddNum());//单号
            studentCount.setSchoolName(student.getSchoolName());//学校名称
        }
        if(student.getSize()==0){// S,M,L,XL,XXL,XXXL  0,1,2,3,4,5
            studentCount.setS(studentCount.getS()+1);
        }else if(student.getSize()==1){
            studentCount.setM(studentCount.getM()+1);
        }else if(student.getSize()==2){
            studentCount.setL(studentCount.getL()+1);
        }else if(student.getSize()==3){
            studentCount.setXl(studentCount.getXl()+1);
        }else if(student.getSize()==4){
            studentCount.setXxl(studentCount.getXxl()+1);
        }else if(student.getSize()==5){
            studentCount.setXxxl(studentCount.getXxxl()+1);
        }else{//TODO 没有匹配的尺码？？？？

        }
        studentCount.setSum(studentCount.getSum()+1);//统计总数+1
        return studentCountRepository.save(studentCount);//更新或新建学生统计表
    }
}
