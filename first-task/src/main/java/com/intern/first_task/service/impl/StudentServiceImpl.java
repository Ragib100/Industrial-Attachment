package com.intern.first_task.service.impl;

import com.intern.first_task.entity.Student;
import com.intern.first_task.exception.StudentNotFoundException;
import com.intern.first_task.repository.StudentRepository;
import com.intern.first_task.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student update(Long id, Student studentDetails) {
        Student existingStudent = findById(id);
        
        existingStudent.setName(studentDetails.getName());
        existingStudent.setEmail(studentDetails.getEmail());
        existingStudent.setDept(studentDetails.getDept());
        
        return studentRepository.save(existingStudent);
    }

    @Override
    public void delete(Long id) {
        Student existingStudent = findById(id);
        studentRepository.delete(existingStudent);
    }
}