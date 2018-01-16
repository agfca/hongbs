package com.llrj.hongbs.service;

import com.llrj.hongbs.domain.StudentCount;
import com.llrj.hongbs.domain.StudentCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentCountService {
    @Autowired
    StudentCountRepository studentCountRepository;

    /**
     * @param studentCount
     * @return
     *
     * 新建或更新studentCount
     */
    public StudentCount save(StudentCount studentCount){
        return studentCountRepository.save(studentCount);
    }

    /**
     *
     * @param id
     * @return
     *
     * 根据表id查询studentCount
     */
    public StudentCount getById(Long id) {
        return studentCountRepository.findOne(id);
    }

    /**
     *
     * @param oddNum
     * @return
     *
     * 根据单号查询学生统计
     */
    public StudentCount getByOddNum(String oddNum) {
        return studentCountRepository.findByOddNum(oddNum);
    }

}
