package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test
  public void PersonMultipleChildrenTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{
            new Person("Alice", 'F', null, LocalDateTime.of(1050, 1, 1, 0, 0)),
            new Person("Bob", 'M', null, LocalDateTime.of(1045, 1, 1, 0, 0)),
            new Person("Carl", 'M', null, LocalDateTime.of(1046, 1, 1, 0, 0))
        },
        LocalDateTime.of(1040, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Bob";
    assertEquals(expected, result);
  }

  @Test
  public void PersonMultipleGenerationsTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{
            new Person("Alice", 'F', new Person[]{
                new Person("Charlie", 'M', null, LocalDateTime.of(1070, 1, 1, 0, 0))
            }, LocalDateTime.of(1045, 1, 1, 0, 0)),
            new Person("Bob", 'M', new Person[]{
                new Person("Carl", 'M',
                  new Person[]{
                    new Person("Max", 'M', null, LocalDateTime.of(1070, 1, 1, 0, 0))
                  },
                LocalDateTime.of(1060, 1, 1, 0, 0))
            }, LocalDateTime.of(1050, 1, 1, 0, 0))
        },
        LocalDateTime.of(1030, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandfather of Max";
    assertEquals(expected, result);
  }

  public void PersonMultipleGenerationsTest2() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{
            new Person("Alice", 'F', new Person[]{
                new Person("Charlie", 'M', 
                new Person[]{
                    new Person("Luisa", 'F', null, LocalDateTime.of(1055, 1, 1, 0, 0))
                  },
                LocalDateTime.of(1050, 1, 1, 0, 0))
            }, LocalDateTime.of(1045, 1, 1, 0, 0)),
            new Person("Bob", 'M', new Person[]{
                new Person("Carl", 'M',
                new Person[]{
                  new Person("Max", 'M', null, LocalDateTime.of(1070, 1, 1, 0, 0))
                },
                LocalDateTime.of(1060, 1, 1, 0, 0))
            }, LocalDateTime.of(1050, 1, 1, 0, 0))
        },
        LocalDateTime.of(1030, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandmother of Luisa";
    assertEquals(expected, result);
  }

  @Test
  public void PersonGreatGreatGrandparentTest() {
    Person person = new Person(
        "Joane",
        'F',
        new Person[]{
            new Person("Alice", 'F', new Person[]{
                new Person("Charlie", 'M', new Person[]{
                    new Person("David", 'M', new Person[]{
                        new Person("Eve", 'F', null, LocalDateTime.of(1110, 1, 1, 0, 0))
                    }, LocalDateTime.of(1090, 1, 1, 0, 0))
                }, LocalDateTime.of(1070, 1, 1, 0, 0))
            }, LocalDateTime.of(1050, 1, 1, 0, 0))
        },
        LocalDateTime.of(1030, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-grandmother of Eve";
    assertEquals(expected, result);
  }
}