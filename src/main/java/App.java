import dao.CourseDao;
import dao.StudentDao;
import dao.TeacherDao;
import idao.IStudentDao;
import models.Course;
import models.CourseMaterial;
import models.Student;
import models.Teacher;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        TeacherDao teacherDao = new TeacherDao();
        CourseDao courseDao = new CourseDao();

        try {

            //Tarea1
            Teacher teacher = new Teacher("Sheldon", "Copper");
            teacherDao.create(teacher);
            System.out.println("Profesor creado con id " + teacher.getIdTeacher());

            Course course = new Course("Fisica AvanzadaIII", teacher);
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

            //Tarea6
            StudentDao studentDao = new StudentDao();
            Student s1 = new Student();
            s1.setFirstName("Ana");
            s1.setLastName("Pérez");
            s1.setBirthDate(LocalDate.of(2000, 1, 1));
            Student s2 = new Student();
            s2.setFirstName("Luis");
            s2.setLastName("Gómez");
            s2.setBirthDate(LocalDate.of(2001, 5, 10));

            Course c = new Course("Acceso a Datos", teacher);
            c.addStudent(s1);
            c.addStudent(s2);
            courseDao.create(c);

            Student s3 = new Student();
            s3.setFirstName("Eva");
            s3.setLastName("Ruiz");
            s3.setBirthDate(LocalDate.of(1999, 3, 3));
            course.addStudent(s3);
            courseDao.update(course);

            System.out.println(courseDao.find(c.getIdCourse()));

            //Tarea7
            Student nuevo = new Student();
            nuevo.setFirstName("Marta");
            nuevo.setLastName("Sanz");
            nuevo.setBirthDate(LocalDate.of(2002, 7, 7));
            nuevo.addCourse(course);
            nuevo.addCourse(c);
            studentDao.create(nuevo);
            System.out.println("Estudiante creado con id " + nuevo.getIdStudent());

            //Tarea8
            courseDao.listCoursesByTeacherLastName("Copper").forEach(System.out::println);

            //Tarea9
            studentDao.listStudentsByCourseId(c.getIdCourse()).forEach(s ->
                    System.out.println("Estudiante: " + s.getFirstName() + " " + s.getLastName()));
        } finally {
            courseDao.getEntityManager().close();
            teacherDao.getEntityManager().close();
        }
    }
}
