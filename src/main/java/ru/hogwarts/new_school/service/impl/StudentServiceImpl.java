package ru.hogwarts.new_school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.new_school.model.Faculty;
import ru.hogwarts.new_school.model.Student;
import ru.hogwarts.new_school.repository.StudentRepository;
import ru.hogwarts.new_school.service.StudentService;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method to add a student");
        student.setId(null);
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(Long id) {
        logger.info("Was invoked method to find a student with id {}", id);
        logger.warn("The student with id {} may not be found, if he doesn't exist", id);

        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            logger.error("Student with id {} was not found", id);
        }
        return student;
    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("Was invoked method to update a student");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Was invoked method to delete a student with id {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method to find students in age between {} and {}", minAge, maxAge);
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFacultyByStudentId(Long studentId) {
        logger.info("Was invoked method to find faculty by student id {}", studentId);
        Student student = findStudent(studentId);
        if (student == null) {
            return null;
        }
        return student.getFaculty();
    }

    @Override
    public Long getAllStudentsCount() {
        logger.info("Was invoked method to count the amount of students");
        return studentRepository.getAllStudentsCount();
    }

    @Override
    public Double getAllStudentsAverageAge() {
        logger.info("Was invoked method to calculate the average age of students");
        return studentRepository.getAllStudentsAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method to find last five students");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<String> findStudentNamesBeginningWithLetterA() {
        List<Student> students = getAllStudents();
        return students.stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    @Override
    public Double calculateAverageAge() {
        List<Student> students = getAllStudents();
        return students.stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0.0);
    }

    @Override
    public void printParallel() {
        logger.info("Was invoked method to print students' name in parallel streams");
        List<Student> students = getAllStudents();
        if (students.size() < 6) {
            logger.error("Amount of students less than 6");
            throw new RuntimeException();
        }

        logger.info("Student {}", students.get(0).getName());
        logger.info("Student {}", students.get(1).getName());

        Thread thread1 = new Thread(() -> {
            logger.info("Student {}", students.get(2).getName());
            logger.info("Student {}", students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            logger.info("Student {}", students.get(4).getName());
            logger.info("Student {}", students.get(5).getName());
        });

        thread1.start();
        thread2.start();
    }

    @Override
    public void printSynchronized() {
        logger.info("Was invoked method to print students' name in synchronized time");
        List<Student> students = getAllStudents();
        if (students.size() < 6) {
            logger.error("Amount of students less than 6");
        }

        printStudent(students.get(0));
        printStudent(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        });

        Thread thread2 = new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        });

        thread1.start();
        thread2.start();
    }

    @Override
    public synchronized void printStudent(Student student) {
        logger.info("Student {}", student.getName());
    }
}
