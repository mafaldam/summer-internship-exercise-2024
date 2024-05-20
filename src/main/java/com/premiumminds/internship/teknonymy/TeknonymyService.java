package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;
import java.time.LocalDateTime;

class TeknonymyService implements ITeknonymyService {

  class TeknonymyGroup {
      Person person;
      int numberOfGenerations;

      public TeknonymyGroup(Person person, int numberOfGenerations) {
          this.person = person;
          this.numberOfGenerations = numberOfGenerations;
      }

      public Person getPerson() {
          return person;
      }

      public int getNumberOfGenerations() {
          return numberOfGenerations;
      }

      public void setPerson(Person person) {
          this.person = person;
      }

      public void setNumberOfGenerations(int numberOfGenerations) {
          this.numberOfGenerations = numberOfGenerations;
      }
  }

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    //throw new RuntimeException("Not Implemented Yet");
    if (person == null) {
      throw new IllegalArgumentException("Person cant be null");
    }

    if (person.children() == null || person.children().length == 0) {
      return "";
    }

    TeknonymyGroup generation = new TeknonymyGroup(person, 0);
    getLastGeneration(generation);

    boolean isMother = person.sex().equals('F');
    String lastGenerationName = generation.getPerson().name();
    int numberOfGenerations = generation.getNumberOfGenerations();

    return getTeknonymyName(numberOfGenerations, lastGenerationName, isMother);
  };

  private void getLastGeneration(TeknonymyGroup group) {
    Person person = group.getPerson();

    if (person.children() == null || person.children().length == 0) {
      return;
    }

    Person oldestChild = null;
    LocalDateTime oldestDateOfBirth = null;
    int generations = 0;

    for (Person child : person.children()) {
        if (child != null && child.dateOfBirth() != null) {

          TeknonymyGroup childGroup = new TeknonymyGroup(child, group.getNumberOfGenerations() + 1);
          getLastGeneration(childGroup);
          
          if (oldestChild == null || 
          childGroup.getNumberOfGenerations() > generations ||
          (childGroup.getNumberOfGenerations() == generations && 
          childGroup.getPerson().dateOfBirth().isBefore(oldestDateOfBirth))) {
            oldestChild = childGroup.getPerson();
            oldestDateOfBirth = childGroup.getPerson().dateOfBirth();
            generations = childGroup.getNumberOfGenerations();
          }
        }
    }

    if (oldestChild == null) {
        return;
    }

    group.setPerson(oldestChild);
    group.setNumberOfGenerations(generations);
    
    return;
  }

  private String getTeknonymyName(int generations, String name, boolean isMother) {
    StringBuilder teknonymyName = new StringBuilder();

    if (generations == 1) {
      if (isMother) {
        teknonymyName.append("mother of ");
      } else {
        teknonymyName.append("father of ");
      }
    } else {
      for(int i=0; i<generations-2; i++) {
        teknonymyName.append("great-");
      }
      if (isMother) {
        teknonymyName.append("grandmother of ");
      } else {
        teknonymyName.append("grandfather of ");
      }
    }

    teknonymyName.append(name);
    return teknonymyName.toString();
  }
}
