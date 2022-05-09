package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServiceTest {

    Service service;

    @Before
    @BeforeEach
    public void setUp() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "./src/main/resources/students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "./src/main/resources/homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "./src/main/resources/grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void findAllStudents() {
        service.saveStudent("krim1923", "Kelemen Reka", 532);
        Student student = new Student("krim1923", "Kelemen Reka", 532);
        Iterable<Student> students = service.findAllStudents();
        assertTrue(StreamSupport.stream(students.spliterator(), false).anyMatch(student::equals));
    }

    @Test
    public void studentShouldBeSaved() {
        int returnValue = service.saveStudent("krim1923", "Kelemen Reka", 532);
        assertEquals(0, returnValue);
    }

    @Test
    public void studentShouldBeDeleted() {
        int returnValue = service.deleteStudent("krim1923");
        assertTrue(returnValue == 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {512, 511, 513})
    public void updateStudent(int groupId) {
        service.saveStudent("lpim1923", "Kelemen Reka", 532);
        assertEquals(1, service.updateStudent( "krim1923", "KR", groupId));
    }

//    @ParameterizedTest
//    @CsvSource({"1,8", "2,11"})
//    public void deadlineShouldBeExtended(String id, int noWeeks) {
//        assertEquals(1, service.extendDeadline(id, noWeeks));
//    }

    @Test
    public void homeworkShouldBeDeleted() {
        service.saveHomework("lab1", "no desc", 10, 5);
        int returnValue = service.deleteHomework("lab1");
        assertEquals(1, returnValue);
    }
}