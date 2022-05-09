package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@NotNull
@ExtendWith(MockitoExtension.class)
public class ServiceMockTest {

    @Mock
    StudentXMLRepository fileRepository1;
    @Mock
    HomeworkXMLRepository fileRepository2;
    @Mock
    GradeXMLRepository fileRepository3;

    @InjectMocks
    Service service;

    @Before
    public void setUp() throws Exception {
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void studentShouldBeSaved() {
        Student student = new Student("krim1923", "Kelemen Reka", 532);
        Mockito.doReturn(student).when(fileRepository1).save(student);
        int returnValue = service.saveStudent("krim1923", "Kelemen Reka", 532);
        assertEquals(0, returnValue);
    }

    @Test
    public void studentShouldBeDeleted() {
        Student student = new Student("krim1923", "Kelemen Reka", 532);
        Mockito.when(fileRepository1.delete(anyString())).thenReturn(student);
        service.deleteStudent("krim1923");
        Mockito.verify(fileRepository1).delete("krim1923");
    }

    @Test
    public void findAllStudents() {
        Student student = new Student("krim1923", "Kelemen Reka", 532);
        List<Student> students = new ArrayList<Student>();
        students.add(student);
        Mockito.when(fileRepository1.findAll()).thenReturn(students);
        Iterable<Student> all_student= service.findAllStudents();
        assertTrue(StreamSupport.stream(all_student.spliterator(), false).anyMatch(student::equals));
    }

}