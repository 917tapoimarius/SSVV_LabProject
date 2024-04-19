package ssvv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
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

import java.time.LocalDate;

public class TestIntegrationAdd {

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
    public void testAddStudent(){
        String id = "15";
        String nume = "varga";
        int grupa = 1;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        service.addStudent(varga);
        assert(service.findStudent("15").equals(varga));
    }

    @Test
    public void testAddAssignment(){
        Tema testTema = new Tema("100","ala bala portocala", 13,3);
        service.addTema(testTema);

        assert(service.findTema("100") != null);
    }

    @Test
    public void testAddGrade(){
        Nota nota = new Nota("98","23","10",9.3, LocalDate.now());
        service.addNota(nota,"iannis e un zeu");

        assert(service.findNota("23#10").equals(nota));
    }

    @Test
    public void testIntegration(){
        String id = "15";
        String nume = "varga";
        int grupa = 1;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        service.addStudent(varga);
        assert(service.findStudent("15").equals(varga));

        Tema testTema = new Tema("100","ala bala portocala", 13,3);
        service.addTema(testTema);

        assert(service.findTema("100") != null);
        Nota nota = new Nota("100","15","100",9.3, LocalDate.now());
        service.addNota(nota,"iannis e un zeu");

        Nota nota1 = service.findNota("15#100");
        assert(nota1.equals(nota));
    }

    @Test
    public void testAddStudentException(){
        String id = "";
        String nume = "varga";
        int grupa = 937;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        try {
            service.addStudent(varga);
        }catch (ValidationException v){
            assert(v.getMessage().equals("Id incorect!"));
        }
    }

    @Test
    public void testAddStudentAndAddAssignmentException(){
        String id = "15";
        String nume = "varga";
        int grupa = 1;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);
        service.addStudent(varga);
        assert(service.findStudent("15").equals(varga));

        Tema testTema = new Tema("100","ala bala portocala", 15,3);
        try {
            service.addTema(testTema);
        }catch (ValidationException v){
            assert(v.getMessage().equals("Deadlineul trebuie sa fie intre 1-14."));
        }
    }

    @Test
    public void testAddStudentAddAssignmentAndAddGradeException(){
        String id = "15";
        String nume = "varga";
        int grupa = 1;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);
        service.addStudent(varga);
        assert(service.findStudent("15").equals(varga));

        Tema testTema = new Tema("100","ala bala portocala", 13,3);
        service.addTema(testTema);

        Nota nota = new Nota("98","23","10",10.1, LocalDate.now());
        
        try {
            service.addNota(nota,"iannis e un zeu");
        }catch (ValidationException v){
            assert(v.getMessage().equals("Valoarea notei nu este corecta!"));
        }
    }


    @AfterEach
    public void cleanup(){
        service.deleteStudent("15");
        service.deleteTema("100");
        service.deleteNota("23#10");
        service.deleteNota("15#100");
    }
}
