package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idStudent", nullable = false)
    private Integer idStudent;
    @NotNull
    private String lastName;
    @NotNull
    private String firstName;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private boolean wantsNewsletter;
    @ManyToMany(mappedBy = "students")
    private List<Course> courses = new ArrayList<>();

    public void addCourse(Course c) {   // mantiene los dos lados sincronizados
        courses.add(c);
        c.addStudent(this);
    }

    public Student() {
    }

    public Student(Integer idStudent, String lastName, String firstName, LocalDate birthDate, boolean wantsNewsletter, List<Course> courses) {
        this.idStudent = idStudent;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.wantsNewsletter = wantsNewsletter;
        this.courses = courses;
    }

    public Integer getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isWantsNewsletter() {
        return wantsNewsletter;
    }

    public void setWantsNewsletter(boolean wantsNewsletter) {
        this.wantsNewsletter = wantsNewsletter;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthDate=" + birthDate +
                ", wantsNewsletter=" + wantsNewsletter +
                ", courses=" + courses +
                '}';
    }
}