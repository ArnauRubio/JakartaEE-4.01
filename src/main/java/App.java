import dao.CourseDao;
import dao.TeacherDao;
import models.Course;
import models.CourseMaterial;
import models.Teacher;
import jakarta.persistence.EntityTransaction;

public class App {
    public static void main(String[] args) {
        TeacherDao teacherDao = new TeacherDao();
        CourseDao courseDao = new CourseDao();

        try {
            //Tarea1
            Teacher teacher = new Teacher("Profesor", "Persistente");
            teacherDao.create(teacher);
            System.out.println("Profesor creado con id " + teacher.getIdTeacher());

            Course course = new Course("Curso persistido", teacher);
            courseDao.create(course);
            System.out.println("Curso creado con id " + course.getIdCourse());

            Course loadedCourse = courseDao.find(course.getIdCourse());
            System.out.println("Profesor del curso: " + loadedCourse.getTeacher().getFirstName());

            //Tarea2
            Teacher crudTeacher = new Teacher("CRUD", "Temporal");
            teacherDao.create(crudTeacher);
            crudTeacher.setFirstName("CRUD actualizado");
            teacherDao.update(crudTeacher);
            teacherDao.delete(teacherDao.find(crudTeacher.getIdTeacher()));
            System.out.println("CRUD de Teacher completado (create, update, delete).");

            //Tarea3
            try {
                courseDao.create(new Course("Curso sin profesor", null));
                System.out.println("ERROR: se esperaba un fallo al guardar un curso sin profesor.");
            } catch (RuntimeException exception) {
                System.out.println("Fallo esperado al guardar el curso sin profesor: "
                        + exception.getClass().getSimpleName());
            }

            //Tarea4
            Teacher transientTeacher = new Teacher("Profesor", "No persistido");
            Course courseWithTransientTeacher = new Course("Curso con profesor transitorio", transientTeacher);
            try {
                courseDao.create(courseWithTransientTeacher);
                System.out.println("ERROR: se esperaba un fallo al guardar el curso con un profesor transitorio.");
            } catch (RuntimeException exception) {
                System.out.println("Fallo esperado al guardar el curso con profesor transitorio: "
                        + exception.getClass().getSimpleName());
            }

            //Tarea5
            Course managedCourse = courseDao.getEntityManager().find(Course.class, course.getIdCourse());
            CourseMaterial material = new CourseMaterial("https://example.test/material", managedCourse);
            EntityTransaction transaction = courseDao.getEntityManager().getTransaction();
            transaction.begin();
            courseDao.getEntityManager().persist(material);
            transaction.commit();

            courseDao.getEntityManager().clear();
            CourseMaterial loadedMaterial = courseDao.getEntityManager()
                    .find(CourseMaterial.class, material.getIdCourMat());
            System.out.println("CourseMaterial encontrado: " + loadedMaterial.getUrl());
            System.out.println("Curso asociado encontrado desde CourseMaterial: "
                    + loadedMaterial.getCourse().getIdCourse());

            courseDao.getEntityManager().clear();
            Course loadedCourseWithMaterial = courseDao.getEntityManager()
                    .find(Course.class, course.getIdCourse());
            if (loadedCourseWithMaterial.getMaterial() == null) {
                throw new IllegalStateException("El material no se cargó al hacer find() del curso.");
            }
            System.out.println("Material encontrado desde Course: "
                    + loadedCourseWithMaterial.getMaterial().getUrl());
        } finally {
            courseDao.getEntityManager().close();
            teacherDao.getEntityManager().close();
        }
    }
}
