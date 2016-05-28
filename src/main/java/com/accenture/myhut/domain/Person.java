package com.accenture.myhut.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "enterprise_id")
    private String enterpriseId;

    @Column(name = "sap")
    private String sap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getSap() {
        return sap;
    }

    public void setSap(String sap) {
        this.sap = sap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if(person.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", enterpriseId='" + enterpriseId + "'" +
            ", sap='" + sap + "'" +
            '}';
    }
}
