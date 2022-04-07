package controller;

import org.w3c.dom.events.Event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Course;
import model.Schedule;
import model.Module;
import model.StudentProfile;
import view.ModuleChooserRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateStudentProfilePane;
import view.ModuleChooserMenuBar;

public class ModuleChooserController {

	// fields to be used throughout class
	private ModuleChooserRootPane view;
	private StudentProfile model;

	private CreateStudentProfilePane cspp;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;
	private ModuleChooserMenuBar mstmb;

	public ModuleChooserController(ModuleChooserRootPane view, StudentProfile model) {
		// initialise view and model fields
		this.view = view;
		this.model = model;

		// initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		smp = view.gSelectModulesPane();
		rmp = view.getReserveModulesPane();
		osp = view.getoOverviewSelectionPane();
		mstmb = view.getModuleSelectionToolMenuBar();

		// add courses to combobox in create student profile pane using the
		// generateAndGetCourses helper method below
		cspp.addCoursesToComboBox(generateAndGetCourses());
		// attach event handlers to view using private helper method
		
		this.attachEventHandlers();
	}
	
	// helper method - used to attach event handlers
	private void attachEventHandlers() {
		// attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());

		smp.ResetBTN(new CreateStudentProfileHandler());
		
		smp.addselectterm1(new Term1AddButtonHandler());
		smp.addselectterm2(new Term2AddButtonHandler());
		
		smp.removeBtn1(new Term1RemoveButtonHandler());
		smp.removeBtn2(new Term2RemoveButtonHandler());

		
		// attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
	}

	private class Term1AddButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			if(smp.GetCredTerm1() >= 60){
				alertDialogBuilder(AlertType.INFORMATION, "Error", "60 Credits Maximum", "Reached the maximum modules/credits");
			}else if(smp.getTerm1UnSelection() != null){
				Module m = smp.getTerm1UnSelection();
				smp.AddTerm1Selection(m);
				smp.RemoveTerm1UnSelection(m);
				smp.UpdateCredTerm1(m.getModuleCredits());
			}else if(smp.getTerm1Selection() == null){
				alertDialogBuilder(AlertType.ERROR, "Error","Nothing Selected", "Please select a module to add");
			}
		}
	}
	
	private class Term2AddButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			if(smp.GetCredTerm2() >= 60){
				alertDialogBuilder(AlertType.INFORMATION, "Error","60 Credits Maximum", "Reached the maximum modules/credits");
			}else if(smp.getTerm2UnSelection() != null){
				Module m = smp.getTerm2UnSelection();
				smp.AddTerm2Selection(m);
				smp.RemoveTerm2UnSelection(m);
				smp.UpdateCredTerm2(m.getModuleCredits());
			}else if(smp.getTerm2Selection() == null){
				alertDialogBuilder(AlertType.ERROR, "Error","Nothing Selected", "Please select a module to add");
			}
		}
	}
	
	private class Term1RemoveButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			if(smp.getTerm1Selection() == null){
				alertDialogBuilder(AlertType.ERROR, "Error","Wrong selection", "Please select a module to remove");
			}else if(smp.getTerm1Selection().isMandatory() == true){
				alertDialogBuilder(AlertType.ERROR, "Error","Wrong selection", "Please Select an non mandatory module to remove");
			}else if(smp.getTerm1Selection() != null){
				Module m = smp.getTerm1Selection();
				smp.RemoveTerm1Selection(m);
				smp.AddTerm1UnSelection(m);
				smp.DecremUpdateCredTerm1(m.getModuleCredits());
				
			}
		}
	}
	
	
	private class Term2RemoveButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			if(smp.getTerm2Selection() == null){
				alertDialogBuilder(AlertType.ERROR, "Error","Wrong selection", "Please select a module to remove");
			}else if(smp.getTerm2Selection().isMandatory() == true){
				alertDialogBuilder(AlertType.ERROR, "Error","Wrong selection", "Please Select an non mandatory module to remove");
			}else if(smp.getTerm2Selection() != null){
				Module m = smp.getTerm2Selection();
				smp.RemoveTerm2Selection(m);
				smp.AddTerm2UnSelection(m);
				smp.DecremUpdateCredTerm2(m.getModuleCredits());
			}
		}
	}
	
	// event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			smp.clearSelectedAll();
			for (Module m : cspp.getSelectedCourse().getAllModulesOnCourse()){
				if(m.isMandatory() == true && m.getDelivery() == Schedule.TERM_1){
					smp.populateSelectTerm1(m);
					smp.UpdateCredTerm1(m.getModuleCredits());
				}else if(m.isMandatory() == true && m.getDelivery() == Schedule.TERM_2){
					smp.populateSelectTerm2(m);
					smp.UpdateCredTerm2(m.getModuleCredits());
				}else if(m.getDelivery() == Schedule.TERM_1){
					smp.populateUnSelectTerm1(m);
				}else if(m.getDelivery() == Schedule.TERM_2){
					smp.populateUnSelectTerm2(m);
				}else if(m.getDelivery() == Schedule.YEAR_LONG){
					smp.populateSelectYearlong(m);
					smp.UpdateCredTerm1(m.getModuleCredits()/2);
					smp.UpdateCredTerm2(m.getModuleCredits()/2);
				}
			}
		}
	}

	private class SubmitModulesHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			
		}
	}

	
	// helper method - generates course and module data and returns courses within
	// an array
	private Course[] generateAndGetCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Schedule.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Schedule.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Schedule.TERM_2);
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Schedule.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Schedule.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Schedule.TERM_1);
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Schedule.TERM_2);
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Schedule.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Schedule.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Schedule.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Schedule.TERM_1);
		Module ctec3911 = new Module("CTEC3911", "Mobile Application Development", 15, false, Schedule.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Schedule.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Schedule.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Schedule.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Schedule.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Schedule.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false,
				Schedule.TERM_2);

		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(ctec3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(ctec3911);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(ctec3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(ctec3911);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

	//helper method to build dialogs
	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
