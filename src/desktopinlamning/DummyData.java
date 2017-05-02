/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlamning;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author johan
 * Class for creating dummy data
 */
public class DummyData {
    
    /*Static method that returns an observable list loaded with dummy data*/
    public static ObservableList<Person> getDummyData(){
        ObservableList<Person> persons = FXCollections.observableArrayList();
        persons.add(new Person("Jennie", "James", "Assasin", 60000, 40,"[Headshot, Mortal combat]"));
        persons.add(new Person("Hilda", "Heinz", "Rollerblader", 400000, 25, "[Rolling backwards, Backflips]"));
        persons.add(new Person("Maxine", "Persson", "Entertainer", 200000, 30, "[Fire eating, Dragon whispering]"));
        return persons;
    }
}
