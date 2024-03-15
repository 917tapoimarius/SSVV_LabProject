package ssvv.example;

import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

public class TestAddStudent{
    public static Service service;

    @BeforeAll
    public static void setup(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addStudentWorks(){
        String id = "15";
        String nume = "varga";
        int grupa = 937;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        service.addStudent(varga);
    }

    @Test
    public void addStudentFails(){
        String id = null;
        String nume = "varga";
        int grupa = 937;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        service.addStudent(varga);
    }
}
