package com.intern.first_task.controller;

import com.intern.first_task.dto.StudentDTO;
import com.intern.first_task.entity.Student;
import com.intern.first_task.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/list";
    }

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "students/details";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("studentDTO", new StudentDTO());
        return "students/create";
    }

    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "students/create";
        }
        
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setDept(studentDTO.getDept());
        
        studentService.create(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setDept(student.getDept());
        
        model.addAttribute("studentDTO", studentDTO);
        return "students/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "students/edit";
        }
        
        Student studentDetails = new Student();
        studentDetails.setName(studentDTO.getName());
        studentDetails.setEmail(studentDTO.getEmail());
        studentDetails.setDept(studentDTO.getDept());
        
        studentService.update(id, studentDetails);
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/students";
    }
}
