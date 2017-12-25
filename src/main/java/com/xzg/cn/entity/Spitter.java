package com.xzg.cn.entity;

import javax.persistence.*;

@Entity
@Table(name = "spitter")
public class Spitter {

    public Spitter(long id,String name){
        super();
        this.id = id;
        this.name = name;
    }
    public Spitter(String name){
        super();
        this.name = name;
    }
    public Spitter(){}
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Spitter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
