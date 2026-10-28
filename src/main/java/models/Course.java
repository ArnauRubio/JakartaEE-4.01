package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCourse", nullable = false)
    private Integer idCourse;
    @NotNull
    private String title;
    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", referencedColumnName = "idTeacher")
    private Teacher teacher;
    @OneToOne(mappedBy = "course")
    private CourseMaterial material;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "students_courses",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "idCourse"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "idStudent"))
    private List<Student> students = new ArrayList<>();

    public Course(String accesoADatos) {
    }

    public Course(String title, Teacher teacher) {
        this.title = title;
        this.teacher = teacher;
    }

    public Course() {

    }

    public void addStudent(Student s) {
        this.students.add(s);
    }

    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public CourseMaterial getMaterial() {
        return material;
    }

    public void setMaterial(CourseMaterial material) {
        this.material = material;
        if (material != null && material.getCourse() != this) {
            material.setCourse(this);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "idCourse=" + idCourse +
                ", title='" + title + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}
