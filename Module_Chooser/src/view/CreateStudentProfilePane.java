package view;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Course;
import model.Name;

public class CreateStudentProfilePane extends GridPane {

	private ComboBox<Course> cboCourses;
	private DatePicker inputDate;
	private TextField txtFirstName, txtSurname, txtPnumber, txtEmail;
	private Button btnCreateProfile, btn_darkmode;

	public CreateStudentProfilePane() {
		// styling
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHalignment(HPos.RIGHT);

		this.getColumnConstraints().addAll(column0);

		// create labels
		Label lblTitle = new Label("Select course: ");
		Label lblPnumber = new Label("Input P number: ");
		Label lblFirstName = new Label("Input first name: ");
		Label lblSurname = new Label("Input surname: ");
		Label lblEmail = new Label("Input email: ");
		Label lblDate = new Label("Input date: ");

		// initialise combobox
		cboCourses = new ComboBox<Course>(); // this is populated via method towards end of class

		// setup text fields
		txtFirstName = new TextField();
		txtSurname = new TextField();
		txtPnumber = new TextField();
		txtEmail = new TextField();

		inputDate = new DatePicker();

		// initialise create profile button
		btnCreateProfile = new Button("Create Profile");
		btn_darkmode = new Button("Dark mode");

		// add controls and labels to container
		this.add(lblTitle, 0, 0);
		this.add(cboCourses, 1, 0);

		this.add(lblPnumber, 0, 1);
		this.add(txtPnumber, 1, 1);

		this.add(lblFirstName, 0, 2);
		this.add(txtFirstName, 1, 2);

		this.add(lblSurname, 0, 3);
		this.add(txtSurname, 1, 3);

		this.add(lblEmail, 0, 4);
		this.add(txtEmail, 1, 4);

		this.add(lblDate, 0, 5);
		this.add(inputDate, 1, 5);

		this.add(new HBox(), 0, 6);
		this.add(btnCreateProfile, 1, 6);
		this.add(btn_darkmode, 0, 0, 4, 1);
	}

	// method to allow the controller to add courses to the combobox
	public void addCoursesToComboBox(Course[] courses) {
		cboCourses.getItems().addAll(courses);
		cboCourses.getSelectionModel().select(0); // select first course by default
	}

	// methods to retrieve the form selection/input
	public Course getSelectedCourse() {
		return cboCourses.getSelectionModel().getSelectedItem();
	}

	public String getStudentPnumber() {
		return txtPnumber.getText();
	}

	public Name getStudentName() {
		return new Name(txtFirstName.getText(), txtSurname.getText());
	}

	public String getStudentEmail() {
		return txtEmail.getText();
	}

	public LocalDate getStudentDate() {
		return inputDate.getValue();
	}

	public void changeToRed1() {
		txtPnumber.setStyle("-fx-border-color: red ");
	}

	public void changeToRed2() {
		txtFirstName.setStyle("-fx-border-color: red ");
	}

	public void changeToRed3() {
		txtSurname.setStyle("-fx-border-color: red ");
	}

	public void changeToRed4() {
		txtEmail.setStyle("-fx-border-color: red ");
	}

	public void changeToRed5() {
		inputDate.setStyle("-fx-border-color: red ");
	}

	public void clearcss1() {
		txtPnumber.setStyle("");

	}

	public void clearcss2() {
		txtFirstName.setStyle("");

	}

	public void clearcss3() {
		txtSurname.setStyle("");

	}

	public void clearcss4() {
		txtEmail.setStyle("");

	}

	public void clearcss5() {
		inputDate.setStyle("");

	}

	// method to attach the create student profile button event handler
	public void addCreateStudentProfileHandler(EventHandler<ActionEvent> handler) {
		btnCreateProfile.setOnAction(handler);
	}

	public void darkmode(EventHandler<ActionEvent> handler) {
		btn_darkmode.setOnAction(handler);
	}

}
