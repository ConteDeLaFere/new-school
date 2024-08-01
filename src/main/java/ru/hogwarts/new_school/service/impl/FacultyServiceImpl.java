package ru.hogwarts.new_school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.new_school.model.Faculty;
import ru.hogwarts.new_school.model.Student;
import ru.hogwarts.new_school.repository.FacultyRepository;
import ru.hogwarts.new_school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method to add a new faculty");
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method to find faculty with id {}", id);
        logger.warn("Faculty with id {} may not be found, if it doesn't exist", id);

        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty == null) {
            logger.error("Faculty with id {} not found", id);
        }
        return faculty;
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method to update faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Was invoked method to delete faculty with id {}", id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty findFacultyByNameOrColor(String name, String color) {
        logger.info("Was invoked method to find faculty by name {} or color {}", name, color);
        return facultyRepository.findFacultyByNameOrColor(name, color);
    }

    @Override
    public Collection<Student> findStudentsByFacultyId(Long facultyId) {
        logger.info("Was invoked method to find students by faculty id {}", facultyId);
        Faculty faculty = findFaculty(facultyId);
        if (faculty == null) {
            return null;
        }
        return faculty.getStudents();
    }

    @Override
    public List<Faculty> findAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public String longestFacultyName() {
        List<Faculty> faculties = findAllFaculties();
        return faculties.stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }
}
