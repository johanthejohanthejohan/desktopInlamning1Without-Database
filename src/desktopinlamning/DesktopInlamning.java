/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopinlamning;



import java.util.Comparator;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author johan
 * Class with general logic
 */
public class DesktopInlamning extends Application{
    
    private int windowHeight = 500;
    private int windowWidth = 1000;
    GridPane root = new GridPane();
    Scene scene = new Scene(root, windowWidth, windowHeight);
    TextArea outputTextArea  = new TextArea();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        // TODO code application logic here
    }
    
    @Override
    public void start(Stage primaryStage){
        
        /*Load data into observable list and bind to tableView*/
        ObservableList<Person> data = DummyData.getDummyData();
        TableView<Person> tableView;
        tableView = new TableView<>();
        tableView.setItems(data);
        
        /*Tableview setup columns*/
      
            //First name
        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First name");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
            //Last name
        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
            //Profession
        TableColumn<Person, String> professionColumn = new TableColumn<>("Profession");
        professionColumn.setMinWidth(200);
        professionColumn.setCellValueFactory(new PropertyValueFactory<>("profession"));
        
            //Wage
        TableColumn<Person, Double> wageColumn = new TableColumn<>("Wage");
        wageColumn.setMinWidth(100);
        wageColumn.setCellValueFactory(new PropertyValueFactory<>("wage"));
        
            //Age
        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setMinWidth(100);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        
            //Skill
        TableColumn<Person, String> skillColumn = new TableColumn("Skill");
        skillColumn.setMinWidth(200);
        skillColumn.setCellValueFactory(new PropertyValueFactory<>("skills"));
        
            //Empty
        TableColumn emptyColumn = new TableColumn();
        emptyColumn.setMinWidth(185);
        
            // add columns to tableview
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, professionColumn, wageColumn, ageColumn, skillColumn, emptyColumn);
        
        
        
        
        /*Find oldest*/
        Button oldestButton = new Button();
        oldestButton.setText("Find oldest");
        oldestButton.setOnAction(e->{
            Comparator<Person> byAge = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
            data.stream()
                .sorted(byAge)
                .forEach(em->outputTextArea.setText(em.getFirstName()));
        });
        
        /*Find youngest*/
        Button youngestButton = new Button();
        youngestButton.setText("Find youngest");
        youngestButton.setOnAction(e->{
            Comparator<Person> byAge = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
            data.stream()
                .sorted(byAge.reversed())
                .forEach(em->outputTextArea.setText(em.getFirstName()));   
        });
        
        /*Find richest*/
        Button richestButton = new Button();
        richestButton.setText("Find richest");
        richestButton.setOnAction(e->{
            Comparator<Person> byWage = (p1, p2) -> Double.compare(p1.getWage(), p2.getWage());
            data.stream()
                .sorted(byWage)
                .forEach(em->outputTextArea.setText(em.getFirstName()));
        });
        
        /*Sort*/
        Button sortButton = new Button();
        sortButton.setText("Sort by first name");
        sortButton.setOnAction(e->{
            outputTextArea.setText("");
            
            data.stream()
                    .sorted((e1, e2) -> e1.getFirstName()
                    .compareTo(e2.getFirstName()))
                    .forEach(em -> outputTextArea.appendText(em.getFirstName()+"\n"));
        });
        
        
        
        
        /*Setup filter*/
        TextField filterTextField = new TextField();
        
        filterTextField.setOnMouseClicked(e->{
            outputTextArea.setText("");
        });
            
        
        
        FilteredList<Person> filteredData = new FilteredList<>(data, p -> true);
        
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                //Convert wage and age to strings
                String wage = person.getWage()+" ";
                String age = person.getAge()+" ";

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                

                if (person.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (person.getSkills().toLowerCase().contains(lowerCaseFilter)){
                    return true; //Filter matches skills.
                } else if (person.getProfession().toLowerCase().contains(lowerCaseFilter)){
                    return true; //Filter matches profession
                } else if (age.contains(lowerCaseFilter)){
                    return true; //Filter matches age.
                } else if(wage.contains(lowerCaseFilter)){
                    return true; //Filter matches wage.
                }  
                return false; // Does not match.
            });
        });

        SortedList<Person> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
        
        /*Style layout*/
        root.setHgap(5);
        root.setPadding(new Insets(5));
        GridPane.setMargin(oldestButton, new Insets(0,0,10,0));
        GridPane.setMargin(youngestButton, new Insets(0,0,10,0));
        GridPane.setMargin(richestButton, new Insets(0,0,10,0));
        GridPane.setMargin(sortButton, new Insets(0,0,10,0));
        GridPane.setMargin(filterTextField, new Insets(0,0,10,0));
        GridPane.setMargin(outputTextArea, new Insets(10,0,0,0));
        
        /*Add components to layout*/
        root.add(oldestButton, 0, 0);
        root.add(youngestButton, 1, 0);
        root.add(richestButton, 2, 0);
        root.add(sortButton, 3, 0);
        root.add(filterTextField, 0, 1, 4, 1);
        root.add(tableView, 0, 2, 4, 1);
        root.add(outputTextArea, 0, 3, 4, 1);
        
        /*Setup stage*/
        primaryStage.setTitle("Inlamning");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
