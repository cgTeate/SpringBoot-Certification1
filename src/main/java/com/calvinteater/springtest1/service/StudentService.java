package com.calvinteater.springtest1.service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calvinteater.springtest1.model.Student;
import com.calvinteater.springtest1.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudents(){
        return studentRepository.findAll();
    }


    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
            .findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()) {   
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }


    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
    }
    studentRepository.deleteById(studentId);
}

    /*  
     * no query used in studentRepository because of @Transactional
     * where the entity goes into a managed state
    */
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
            Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                    "Student with id " + studentId + " does not exist"));

            if(name != null &&
                    name.length() > 0 && 
                    !Objects.equals(student.getName(), name)) {
                student.setName(name);
            }

            if(email != null &&
                    email.length() > 0 && 
                    !Objects.equals(student.getEmail(), email)) {
               Optional<Student> studentOptional = studentRepository
                        .findStudentByEmail(email);
                if(studentOptional.isPresent()) {
                    throw new IllegalStateException("email taken");
                }
                student.setEmail(email);
            }
    }
}
