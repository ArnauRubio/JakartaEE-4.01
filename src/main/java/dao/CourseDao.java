package dao;

import idao.ICourseDao;
import models.Course;

import java.util.List;

public class CourseDao extends Dao<Course, Integer> implements ICourseDao {
    @Override
    public Course find(Integer id) {
        return em.createQuery(
                "select course from Course course join fetch course.teacher "
                        + "left join fetch course.material where course.idCourse = :id",
                Course.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
    @Override
    public List<Course> listCoursesByTeacherLastName(String lastName) {
        return em.createQuery(
                        "SELECT c FROM Course c WHERE c.teacher.lastName = :ln", Course.class)
                .setParameter("ln", lastName).getResultList();
    }
}