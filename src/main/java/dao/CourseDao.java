package dao;

import idao.ICourseDao;
import models.Course;

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
}