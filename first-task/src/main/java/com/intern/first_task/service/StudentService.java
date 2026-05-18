package com.intern.first_task.service;

import com.intern.first_task.entity.Student;
import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(Long id);
    Student create(Student student);
    Student update(Long id, Student studentDetails);
    void delete(Long id);
}