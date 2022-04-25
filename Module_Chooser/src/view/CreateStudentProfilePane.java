package view;

import java.io.Serializable;
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

public class CreateStudentProfilePane extends GridPane implements Serializable {

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

	public void setSelectedCourse(Course d) {
		if (d.getCourseName().equals("Computer Science")) {
			cboCourses.getSelectionModel().select(0);
		} else if (d.getCourseName().equals("Software Engineering")) {
			cboCourses.getSelectionModel().select(1);
		} else {
			cboCourses.getSelectionModel().select(0);
		}
	}

	public String getStudentPnumber() {
		return txtPnumber.getText();
	}

	public void setStudentPnumber(String d) {
		txtPnumber.setText(d);
	}

	public Name getStudentName() {
		return new Name(txtFirstName.getText(), txtSurname.getText());
	}

	public void setStudentName(Name d) {
		txtFirstName.setText(d.getFirstName());
		txtSurname.setText(d.getFamilyName());
	}

	public String getStudentEmail() {
		return txtEmail.getText();
	}

	public void setStudentEmail(String d) {
		txtEmail.setText(d);
	}

	public LocalDate getStudentDate() {
		return inputDate.getValue();
	}

	public void setStudentDate(LocalDate date) {
		inputDate.setValue(date);
	}

	public void changeToRed1(Boolean d) {
		if (d.equals(true)) {
			txtPnumber.setStyle("-fx-border-color: red ");
		} else {
			txtPnumber.setStyle("");
		}
	}

	public void changeToRed2(Boolean d) {
		if (d.equals(true)) {
			txtFirstName.setStyle("-fx-border-color: red ");
		} else {
			txtFirstName.setStyle("");
		}
	}

	public void changeToRed3(Boolean d) {
		if (d.equals(true)) {
			txtSurname.setStyle("-fx-border-color: red ");
		} else {
			txtSurname.setStyle("");
		}
	}

	public void changeToRed4(Boolean d) {
		if (d.equals(true)) {
			txtEmail.setStyle("-fx-border-color: red ");
		} else {
			txtEmail.setStyle("");
		}
	}

	public void changeToRed5(Boolean d) {
		if (d.equals(true)) {
			inputDate.setStyle("-fx-border-color: red ");
		} else {
			inputDate.setStyle("");
		}
	}

	public void clearALLCSPp() {
		cboCourses.getSelectionModel().clearSelection();
		txtPnumber.setStyle("");
		txtFirstName.setStyle("");
		txtSurname.setStyle("");
		txtEmail.setStyle("");
		inputDate.setStyle("");
		txtPnumber.clear();
		txtFirstName.clear();
		txtSurname.clear();
		txtEmail.clear();
		inputDate.setValue(null);
	}

	// method to attach the create student profile button event handler
	public void addCreateStudentProfileHandler(EventHandler<ActionEvent> handler) {
		btnCreateProfile.setOnAction(handler);
	}

	public void darkmode(EventHandler<ActionEvent> handler) {
		btn_darkmode.setOnAction(handler);
	}

}
