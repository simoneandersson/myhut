package com.accenture.myhut.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cabin.
 */
@Entity
@Table(name = "cabin")
public class Cabin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "photos")
    private String photos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cabin cabin = (Cabin) o;
        if(cabin.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cabin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cabin{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", location='" + location + "'" +
            ", capacity='" + capacity + "'" +
            ", photos='" + photos + "'" +
            '}';
    }
}
