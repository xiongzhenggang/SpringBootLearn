package com.xzg.cn.java9Test.javadesage.data_mapper;

import java.util.Optional;
import java.util.logging.Logger;

public final class App {

    private static Logger log = Logger.getLogger(App.class.getName());

    /**
     * Program entry point.
     *
     * @param args command line args.
     */
    public static void main(final String... args) {

    /* Create new data mapper for type 'first' */
        final StudentDataMapper mapper = new StudentDataMapperImpl();

    /* Create new student */
        Student student = new Student(1, "Adam", 'A');

    /* Add student in respectibe store */
        mapper.insert(student);

        log.info("App.main(), student : " + student + ", is inserted");

    /* Find this student */
        final Optional<Student> studentToBeFound = mapper.find(student.getStudentId());

        log.info("App.main(), student : " + studentToBeFound + ", is searched");

    /* Update existing student object */
        student = new Student(student.getStudentId(), "AdamUpdated", 'A');

    /* Update student in respectibe db */
        mapper.update(student);

        log.info("App.main(), student : " + student + ", is updated");
        log.info("App.main(), student : " + student + ", is going to be deleted");

    /* Delete student in db */
        mapper.delete(student);
    }

    private App() {}
}