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
    public void testCase1(){
        String id = "15";
        String nume = "varga";
        int grupa = 1;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        service.addStudent(varga);
    }

    @Test
    public void testCase2(){
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
    public void testCase3(){
        String id = null;
        String nume = "varga";
        int grupa = 937;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        try {
            service.addStudent(varga);
        }catch (NullPointerException n){
            assert(n.getMessage().equals("Cannot invoke \"String.equals(Object)\" because the return value of \"domain.Student.getID()\" is null"));
        }
    }

    @Test
    public void testCase4(){
        String id = "15";
        String nume = "varga";
        int grupa = 937;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        service.addStudent(varga);
        assert(service.addStudent(varga).equals(varga)); // daca nu se face asa te rog schimba
    }

    @Test
    public void testCase5(){
        String id = "16";
        String nume = "";
        int grupa = 937;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        try {
            service.addStudent(varga);
        }catch (ValidationException v){
            assert(v.getMessage().equals("Nume incorect!"));
        }
    }

    @Test
    public void testCase6(){
        String id = "17";
        String nume = null;
        int grupa = 937;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        try {
            service.addStudent(varga);
        }catch (ValidationException v){
            assert(v.getMessage().equals("Nume incorect!"));
        }
    }

    @Test
    public void testCase7(){
        String id = "18";
        String nume = "varga";
        int grupa = -1;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        try {
            service.addStudent(varga);
        }catch (ValidationException v){
            assert(v.getMessage().equals("Grupa incorecta!"));
        }
    }

    @Test
    public void testCase8(){
        String id = "19";
        String nume = "varga";
        int grupa = Integer.MAX_VALUE+1; // ma astept sa puste aici, nush cum putem verifica asta, sper ca iti dai seama tu :D
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        try {
            service.addStudent(varga);
        }catch (ValidationException v){
            assert(v.getMessage().equals("Grupa incorecta!"));
        }
    }

//    @Test
//    public void testCase9(){
//        String id = "20";
//        String nume = "varga";
//        double grupa = 937.7; // si aici aceeasi situatie ca la TC8, nush cum putem testa asta sincer, poate iti dai seama tu
//        String email = "a@b.com";
//        Student varga = new Student(id,nume,grupa,email);
//
//        service.addStudent(varga);
//    }

    @Test
    public void testCase10(){
        String id = "21";
        String nume = "varga";
        int grupa = 937;
        String email = "";
        Student varga = new Student(id,nume,grupa,email);

        try {
            service.addStudent(varga);
        }catch (ValidationException v){
            assert(v.getMessage().equals("Email incorect!"));
        }
    }

    @Test
    public void testCase11(){
        String id = "22";
        String nume = "varga";
        int grupa = 937;
        String email = null;
        Student varga = new Student(id,nume,grupa,email);

        try {
            service.addStudent(varga);
        }catch (ValidationException v){
            assert(v.getMessage().equals("Email incorect!"));
        }
    }

    @Test
    public void testCase12(){
        String id = "23";
        String nume = "varga";
        int grupa = 0;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        service.addStudent(varga);
    }

    @Test
    public void testCase13(){
        String id = "24";
        String nume = "varga";
        int grupa = Integer.MAX_VALUE;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        service.addStudent(varga);
    }

    @Test
    public void testCase14(){
        String id = "25";
        String nume = "varga";
        int grupa = Integer.MAX_VALUE-1;
        String email = "a@b.com";
        Student varga = new Student(id,nume,grupa,email);

        service.addStudent(varga);
    }
}