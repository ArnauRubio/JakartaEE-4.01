package dao;

import idao.IStudentDao;
import models.Student;

import java.util.List;

public class StudentDao extends Dao<Student,Integer> implements IStudentDao {
    @Override public Student find(Integer id) { return em.find(Student.class, id); }

    @Override
    public List<Student> listStudentsByCourseId(Integer courseId) {
        return em.createQuery(
                        "SELECT s FROM Student s JOIN s.courses c WHERE c.id = :id", Student.class)
                .setParameter("id", courseId).getResultList();
    }
}
