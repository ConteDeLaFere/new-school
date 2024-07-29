package ru.hogwarts.new_school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.new_school.model.Faculty;
import ru.hogwarts.new_school.model.Student;
import ru.hogwarts.new_school.repository.FacultyRepository;
import ru.hogwarts.new_school.service.FacultyService;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty findFacultyByNameOrColor(String name, String color) {
        return facultyRepository.findFacultyByNameOrColor(name, color);
    }

    @Override
    public Collection<Student> findStudentsByFacultyId(Long facultyId) {
        Faculty faculty = findFaculty(facultyId);
        if (faculty == null) {
            return null;
        }
        return faculty.getStudents();
    }
}
