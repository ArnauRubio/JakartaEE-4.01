package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCourMat", nullable = false)
    private Integer idCourMat;
    @NotNull
    private String url;
    @OneToOne(optional = false)
    @JoinColumn(name = "course_id", unique = true)
    private Course course;

    protected CourseMaterial() {
    }

    public CourseMaterial(String url, Course course) {
        this.url = url;
        setCourse(course);
    }

    public CourseMaterial(Integer idCourMat, String url, Course course) {
        this.idCourMat = idCourMat;
        this.url = url;
        setCourse(course);
    }

    public Integer getIdCourMat() {
        return idCourMat;
    }

    public void setIdCourMat(Integer idCourMat) {
        this.idCourMat = idCourMat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        if (course != null && course.getMaterial() != this) {
            course.setMaterial(this);
        }
    }

    @Override
    public String toString() {
        return "CourseMaterial{" +
                "idCourMat=" + idCourMat +
                ", url='" + url + '\'' +
                ", course=" + course +
                '}';
    }
}
