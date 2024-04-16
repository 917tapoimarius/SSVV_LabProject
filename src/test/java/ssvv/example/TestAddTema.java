package ssvv.example;

import domain.Tema;
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

import java.util.List;

public class TestAddTema {

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
        Tema testTema = new Tema("100","ala bala portocala", 13,12);
        service.addTema(testTema);

        assert(service.findTema("100") != null);

    }

    @Test
    public void testCase2(){
        Tema testTema = new Tema("","ala bala portocala", 13,12);
        try {
            service.addTema(testTema);
        }catch (ValidationException e){
            assert(e.getMessage().equals("Numar tema invalid!"));
        }
    }

    @Test
    public void testCase3(){
        Tema testTema = new Tema("100","", 13,12);
        try {
            service.addTema(testTema);
        }catch (ValidationException e){
            assert(e.getMessage().equals("Descriere invalida!"));
        }
    }

    @Test
    public void testCase4(){
        Tema testTema = new Tema("100","ala bala portocala", 0,12);
        try {
            service.addTema(testTema);
        }catch (ValidationException e){
            assert(e.getMessage().equals("Deadlineul trebuie sa fie intre 1-14."));
        }
    }

    @Test
    public void testCase5(){
        Tema testTema = new Tema("100","ala bala portocala", 13,0);
        try {
            service.addTema(testTema);
        }catch (ValidationException e){
            assert(e.getMessage().equals("Saptamana primirii trebuie sa fie intre 1-14."));
        }
    }

    @Test
    public void testCase6(){
        Tema testTema = new Tema(null,"ala bala portocala", 13,12);
        try {
            service.addTema(testTema);
        }catch (ValidationException e){
            assert(e.getMessage().equals("Numar tema invalid!"));
        }
    }

    @Test
    public void testCase7(){
        Tema testTema = new Tema("100","ala bala portocala", 15,12);
        try {
            service.addTema(testTema);
        }catch (ValidationException e){
            assert(e.getMessage().equals("Deadlineul trebuie sa fie intre 1-14."));
        }
    }

    @Test
    public void testCase8(){
        Tema testTema = new Tema("100","ala bala portocala", 13,15);
        try {
            service.addTema(testTema);
        }catch (ValidationException e){
            assert(e.getMessage().equals("Saptamana primirii trebuie sa fie intre 1-14."));
        }
    }
}
