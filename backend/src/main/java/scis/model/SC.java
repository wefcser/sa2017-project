package scis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

import scis.rest.JacksonCustomSCSerializer;
import scis.rest.JacksonCustomSCDeserializer;

/**
 * Created by wangyifei on 2017/6/11.
 */

@Entity
@Table(name = "sct")
@JsonSerialize(using = JacksonCustomSCSerializer.class)
@JsonDeserialize(using = JacksonCustomSCDeserializer.class)
public class SC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String no;
    private String name;
    private String depart;
    private String course;
    private Integer grade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}