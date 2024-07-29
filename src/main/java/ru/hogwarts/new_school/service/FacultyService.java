package ru.hogwarts.new_school.service;

import ru.hogwarts.new_school.model.Faculty;
import ru.hogwarts.new_school.model.Student;

import java.util.Collection;

public interface FacultyService {

    Faculty addFaculty(Faculty faculty);
    Faculty findFaculty(Long id);
    Faculty updateFaculty(Faculty faculty);
    void deleteFaculty(Long id);

    Faculty findFacultyByNameOrColor(String name, String color);

    Collection<Student> findStudentsByFacultyId(Long facultyId);
}
