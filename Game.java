import java.lang.*;
import java.util.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
public class Game extends Application {
    Alert info = new Alert(Alert.AlertType.INFORMATION, "You lose if grade < 50, fatigue > 150, stress > 250 or you miss 250 assignments or more.\nBalance Doing Work, Cramming Work and Sleeping to win.\nCheat only when you have to.");

    private int workDone = 0;
    private int stressLevel = 0;
    private int missingWork = 0;
    private int fatigueLevel = 0;
    private double grade = 100.0;
    private int[] crasher = new int[2];

    private Label adone = new Label("Assignments done: 0");
    private Label stress = new Label("Stress level: 0");
    private Label miss = new Label("Missing Assignments: 0");
    private Label fat = new Label("Fatigue: 0");
    private Label gr = new Label("Grade: 100.0%");
    private Label status = new Label("Get to work!");
    
    private Button doWork = new Button("Do work");
    private Button cramwork = new Button("Cram Work");
    private Button rulesButton = new Button("Information");
    private Button sleep = new Button("Sleep");
    private Button cheat = new Button("Cheat");
    @Override
    public void start(Stage stage) {
        info.setHeaderText("Playing the Game");
        info.setTitle("Rules and Information");
        // Create a new grid pane
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setMinSize(300, 300);
        pane.setVgap(10);
        pane.setHgap(10);
        //set an action on the button using method reference
        doWork.setOnAction(this::doWork);
        cramwork.setOnAction(this::cramWork);
        cheat.setOnAction(this::cheating);
        sleep.setOnAction(this::sleep);
        rulesButton.setOnAction(this::showInfo);
        // Add the button and label into the pane
        pane.add(adone, 1, 0);
        pane.add(miss,1,1);
        pane.add(gr,1,2);
        pane.add(stress,1,3);
        pane.add(fat,1,4);
        pane.add(status,1,5);
        pane.add(doWork, 0, 0);
        pane.add(cramwork, 0, 1);
        pane.add(sleep, 0, 2);
        pane.add(cheat, 0, 3);
        pane.add(rulesButton, 0, 4);
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(pane, 320,210);
        stage.setTitle("High School Simulator 2020");
        stage.setScene(scene);
        // Show the Stage (window)
        stage.show();
        info.showAndWait();
    }

    private void updateLabel(){
        adone.setText("Assignments done: " + Integer.toString(workDone));
        stress.setText("Stress Level: " + Integer.toString(stressLevel));
        miss.setText("Missing Assignments: " + Integer.toString(missingWork));
        fat.setText("Fatigue: " + Integer.toString(fatigueLevel));
        gr.setText("Grade: " + grade + "%");
    }

    private void showInfo(ActionEvent event){
        info.setHeaderText("Sans Fortnite from Undertale");
        info.setTitle("You're going to lose");
        info.showAndWait();
    }

    private void doWork(ActionEvent event){
        status.setText("Getting work done!");
        checkStatus();
        workDone = workDone + 1;
        missingWork = missingWork + 2;
        fatigueLevel +=1;
        grade -=2;
        stressLevel +=2;
        updateLabel();
        checkStatus();
    }

    private void cheating(ActionEvent event){
        double caught = Math.random();
        Alert cheater = new Alert(Alert.AlertType.ERROR, "You got caught cheating");
        if(caught < 0.25){
            status.setText("You were Expelled!");
            cheater.setTitle("You lose");
            cheater.setHeaderText("You were expelled!");
            cheater.showAndWait();
            System.exit(0);
        } else {
            checkStatus();
            status.setText("Copying other's work...");
            fatigueLevel +=1;
            stressLevel -= 5;
            missingWork -=5;
            grade +=8;
            workDone += 6;
            updateLabel();
            checkStatus();
        }
    }

    private boolean cramWork(ActionEvent event){
        if(missingWork < 1){
            status.setText("You don't need to cram!");
            checkStatus();
            return false;
        }
        checkStatus();
        status.setText("Cramming lots of work");
        workDone += 2;
        fatigueLevel += 10;
        missingWork -= 1;
        grade +=3;
        stressLevel += 10;
        updateLabel();
        checkStatus();
        return true;
    }

    private void sleep(ActionEvent event){
        if(fatigueLevel < 1 || stressLevel < 1){
            status.setText("There is no need to sleep.");
            checkStatus();
            return;
        }
        status.setText("Sleeping the day away...");
        checkStatus();
        missingWork +=3;
        grade -= 4;
        stressLevel -=8;
        fatigueLevel -= 10;
        updateLabel();
        checkStatus();
    }

    private boolean checkStatus(){
        Alert lost = new Alert(Alert.AlertType.ERROR, "you lose");
        if(fatigueLevel < 0)
            fatigueLevel = 0;
        if(stressLevel < 0)
            stressLevel = 0;
        if(missingWork < 0)
            missingWork = 0;
        if(grade < 0)    
            grade = 0;
        updateLabel();
        if(fatigueLevel > 150 || stressLevel > 250 || missingWork >= 250){
            status.setText("You are too worn to keep working!");
            lost.showAndWait();
            System.exit(0);
        }
        if(grade < 50.0){
            status.setText("You failed school!");
            lost.showAndWait();
            int crash = crasher[1000];
        }
        return true;
    }
}
