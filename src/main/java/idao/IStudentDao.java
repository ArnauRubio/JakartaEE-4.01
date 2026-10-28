package idao;

import models.Student;

import java.util.List;

public interface IStudentDao {
    List<Student> listStudentsByCourseId(Integer courseId);
}
