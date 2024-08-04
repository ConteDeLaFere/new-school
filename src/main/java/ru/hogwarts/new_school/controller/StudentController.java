package ru.hogwarts.new_school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.new_school.model.Faculty;
import ru.hogwarts.new_school.model.Student;
import ru.hogwarts.new_school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student createdStudent = studentService.addStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent( @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("age-in-between")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam Integer minAge,
                                                                       @RequestParam Integer maxAge) {
        return ResponseEntity.ok(studentService.getStudentsByAgeBetween(minAge, maxAge));
    }

    @GetMapping("{id}/find-faculty")
    public ResponseEntity<Faculty> findFacultyByStudentId(@PathVariable Long id) {
        Faculty faculty = studentService.getFacultyByStudentId(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("students-count")
    public Long getStudentsCount() {
        return studentService.getAllStudentsCount();
    }

    @GetMapping("students-average-age")
    public Double getStudentsAverageAge() {
        return studentService.getAllStudentsAverageAge();
    }

    @GetMapping("last-five-students")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/names-beginning-with-letter-A")
    public ResponseEntity<List<String>> getNamesWithBeginningWithLetterA() {
        List<String> names = studentService.findStudentNamesBeginningWithLetterA();
        return ResponseEntity.ok(names);
    }

    @GetMapping("/students-average-age-stream")
    public ResponseEntity<Double> getAverageAge() {
        Double averageAge = studentService.calculateAverageAge();
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/print-parallel")
    public ResponseEntity<Void> printParallel() {
        studentService.printParallel();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/print-synchronized")
    public ResponseEntity<Void> printSynchronized() {
        studentService.printSynchronized();
        return ResponseEntity.noContent().build();
    }

}
