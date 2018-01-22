package com.xzg.cn.java9Test.javadesage.data_mapper;

import java.io.Serializable;

public final class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private int studentId;
    private String name;
    private char grade;


    /**
     * Use this constructor to create a Student with all details
     *
     * @param studentId as unique student id
     * @param name as student name
     * @param grade as respective grade of student
     */
    public Student(final int studentId, final String name, final char grade) {
        super();

        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
    }

    /**
     *
     * @return the student id
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     *
     * @param studentId as unique student id
     */
    public void setStudentId(final int studentId) {
        this.studentId = studentId;
    }

    /**
     *
     * @return name of student
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name as 'name' of student
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @return grade of student
     */
    public char getGrade() {
        return grade;
    }

    /**
     *
     * @param grade as 'grade of student'
     */
    public void setGrade(final char grade) {
        this.grade = grade;
    }

    /**
     *
     */
    @Override
    public boolean equals(final Object inputObject) {

        boolean isEqual = false;

    /* Check if both objects are same */
        if (this == inputObject) {

            isEqual = true;
        } else if (inputObject != null && getClass() == inputObject.getClass()) {

            final Student inputStudent = (Student) inputObject;

      /* If student id matched */
            if (this.getStudentId() == inputStudent.getStudentId()) {

                isEqual = true;
            }
        }

        return isEqual;
    }

    /**
     *
     */
    @Override
    public int hashCode() {

    /* Student id is assumed to be unique */
        return this.getStudentId();
    }

    /**
     *
     */
    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", name=" + name + ", grade=" + grade + "]";
    }
}