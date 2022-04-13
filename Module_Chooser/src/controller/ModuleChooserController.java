package controller;

import java.io.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
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
		cspp.darkmode(new Darkmodehandler());

		smp.ResetBTN(new CreateStudentProfileHandler());

		smp.addselectterm1(new SMPTerm1AddButtonHandler());
		smp.addselectterm2(new SMPTerm2AddButtonHandler());

		smp.removeBtn1(new SMPTerm1RemoveButtonHandler());
		smp.removeBtn2(new SMPTerm2RemoveButtonHandler());

		smp.SubmitBtn(new SMPSubmitModulesHandler());

		rmp.addSelectTerm1RMP(new RMPTerm1AddBtnHandler());
		rmp.addSelectTerm2RMP(new RMPTerm2AddBtnHandler());

		rmp.removeBtn1RMP(new RMPTerm1RemoveBtnHandler());
		rmp.removeBtn2RMP(new RMPTerm2RemoveBtnHandler());

		rmp.confirmTerm1RMP(new RMPConfirmTerm1ModulesHandler());
		rmp.confirmTerm2RMP(new RMPConfirmTerm2ModulesHandler());

		mstmb.addSaveHandler(new SaveHandler());
		mstmb.addLoadHandler(new LoadMenuHandler());

		// attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));

		osp.saveBTN(new SaveButtonOWHandler());
	}

	// Select module pane classes
	private class SMPTerm1AddButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smp.GetCredTerm1() == 60) {
				alertDialogBuilder(AlertType.INFORMATION, "Error", "60 Credits Maximum",
						"Reached the maximum modules/credits");
			} else if (smp.getTerm1UnSelection() != null) {
				Module m = smp.getTerm1UnSelection();
				smp.AddTerm1Selection(m);
				smp.RemoveTerm1UnSelection(m);
				smp.UpdateCredTerm1(m.getModuleCredits());
			} else if (smp.getTerm1Selection() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Nothing Selected", "Please select a module to add");
			}
		}
	}

	private class SMPTerm2AddButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smp.GetCredTerm2() == 60) {
				alertDialogBuilder(AlertType.INFORMATION, "Error", "60 Credits Maximum",
						"Reached the maximum modules/credits");
			} else if (smp.getTerm2UnSelection() != null) {
				Module m = smp.getTerm2UnSelection();
				smp.AddTerm2Selection(m);
				smp.RemoveTerm2UnSelection(m);
				smp.UpdateCredTerm2(m.getModuleCredits());
			} else if (smp.getTerm2Selection() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Nothing Selected", "Please select a module to add");
			}
		}
	}

	private class SMPTerm1RemoveButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smp.getTerm1Selection() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection", "Please select a module to remove");
			} else if (smp.getTerm1Selection().isMandatory() == true) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection",
						"Please Select an non mandatory module to remove");
			} else if (smp.getTerm1Selection() != null) {
				Module m = smp.getTerm1Selection();
				smp.RemoveTerm1Selection(m);
				smp.AddTerm1UnSelection(m);
				smp.DecremUpdateCredTerm1(m.getModuleCredits());
				rmp.reserveClearUnSelec();
			}
		}
	}

	private class SMPTerm2RemoveButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smp.getTerm2Selection() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection", "Please select a module to remove");
			} else if (smp.getTerm2Selection().isMandatory() == true) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection",
						"Please Select an non mandatory module to remove");
			} else if (smp.getTerm2Selection() != null) {
				Module m = smp.getTerm2Selection();
				smp.RemoveTerm2Selection(m);
				smp.AddTerm2UnSelection(m);
				smp.DecremUpdateCredTerm2(m.getModuleCredits());
				rmp.reserveClearUnSelec();
			}
		}
	}

	String selectedmodules = "";

	private class SMPSubmitModulesHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			rmp.reserveClearUnSelec();
			if (smp.GetCredTerm1() < 60 || smp.GetCredTerm2() < 60) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Not Enough Credits Selected",
						"Please select modules for both terms of 60 credits each");
			} else if (smp.GetCredTerm1() == 60 && smp.GetCredTerm2() == 60) {
				for (Module m : smp.getTerm1UnselectedLeftOver()) {
					rmp.rmpPopulateUnSlctTerm1(m);
				}
				for (Module m : smp.getTerm2UnselectedLeftOver()) {
					rmp.rmpPopulateUnSlctTerm2(m);
				}

				for (Module m : smp.getYearLongselectedmodules()) {
					selectedmodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + ", " + " Module name: " + m.getModuleName() + ", "
									+ " Credits: " + m.getModuleCredits() + ", " + "Mandatory Course ? "
									+ m.isMandatory()
									+ ", " + " Delivery: " + m.getDelivery());
					osp.setSelectModules(selectedmodules);
				}

				for (Module m : smp.getTerm1selectedmodules()) {
					selectedmodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + ", " + " Module name: " + m.getModuleName() + ", "
									+ " Credits: " + m.getModuleCredits() + ", " + "Mandatory Course ? "
									+ m.isMandatory()
									+ ", " + " Delivery: " + m.getDelivery());
					osp.setSelectModules(selectedmodules);
				}

				for (Module m : smp.getTerm2selectedmodules()) {
					selectedmodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + ", " + " Module name: " + m.getModuleName() + ", "
									+ " Credits: " + m.getModuleCredits() + ", " + "Mandatory Course ? "
									+ m.isMandatory()
									+ ", " + " Delivery: " + m.getDelivery());
					osp.setSelectModules(selectedmodules);
				}

				view.changeTab(2);
			}
		}
	}

	// Reserve module pane
	private class RMPTerm1AddBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmp.getCTerm1() >= 30) {
				alertDialogBuilder(AlertType.INFORMATION, "Error", "30 Credits Maximum",
						"Reached the maximum modules/credits");
			} else if (rmp.getRmpTerm1Unselect() != null) {
				Module m = rmp.getRmpTerm1Unselect();
				rmp.addTerm1rmp(m);
				rmp.removeTerm1Unselecrmp(m);
				rmp.updateCredT1(m.getModuleCredits());
			} else if (rmp.getRmpTerm1Unselect() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Nothing Selected", "Please select a module to add");
			}
		}
	}

	private class RMPTerm2AddBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmp.getCTerm2() >= 30) {
				alertDialogBuilder(AlertType.INFORMATION, "Error", "30 Credits Maximum",
						"Reached the maximum modules/credits");
			} else if (rmp.getRmpTerm2Unselect() != null) {
				Module m = rmp.getRmpTerm2Unselect();
				rmp.addTerm2rmp(m);
				rmp.removeTerm2Unselecrmp(m);
				rmp.updateCredT2(m.getModuleCredits());
			} else if (rmp.getRmpTerm2Unselect() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Nothing Selected", "Please select a module to add");
			}
		}
	}

	private class RMPTerm1RemoveBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmp.getRmpTerm1select() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection", "Please select a module to remove");
			} else if (rmp.getRmpTerm1select().isMandatory() == true) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection",
						"Please Select an non mandatory module to remove");
			} else if (rmp.getRmpTerm1select() != null) {
				Module m = rmp.getRmpTerm1select();
				rmp.removeTerm1rmp(m);
				rmp.addTerm1Unselecrmp(m);
				rmp.DecremUpdateCredTerm1(m.getModuleCredits());
			}
		}
	}

	private class RMPTerm2RemoveBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmp.getRmpTerm2select() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection", "Please select a module to remove");
			} else if (rmp.getRmpTerm2select().isMandatory() == true) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection",
						"Please Select an non mandatory module to remove");
			} else if (rmp.getRmpTerm2select() != null) {
				Module m = rmp.getRmpTerm2select();
				rmp.removeTerm2rmp(m);
				rmp.addTerm2Unselecrmp(m);
				rmp.DecremUpdateCredTerm2(m.getModuleCredits());
			}
		}
	}

	String reservemodules = "";

	private class RMPConfirmTerm1ModulesHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmp.getCTerm1() < 30) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Not Enough Credits Selected",
						"Please select modules for both terms of 30 credits each");
			} else if (rmp.getCTerm1() == 30) {
				rmp.expandnext();
				for (Module m : rmp.getTerm1selected()) {
					reservemodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + " Module name: " + m.getModuleName() + " Credits: "
									+ m.getModuleCredits() + " Delivery: " + m.getDelivery());
					osp.setReserveModule(reservemodules);
				}
			}
		}
	}

	private class RMPConfirmTerm2ModulesHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmp.getCTerm2() < 30) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Not Enough Credits Selected",
						"Please select modules for both terms of 30 credits each");
			} else if (rmp.getCTerm2() == 30) {
				for (Module m : rmp.getTerm2selected()) {
					reservemodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + ", " + " Module name: " + m.getModuleName() + ", "
									+ " Credits: " + m.getModuleCredits() + ", " + " Delivery: " + m.getDelivery());
					osp.setReserveModule(reservemodules);
				}
				view.changeTab(3);
			}
		}
	}

	String profilestring = "";

	// Create profile pane
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (cspp.getStudentPnumber().isEmpty() || cspp.getStudentName().getFirstName().isEmpty()
					|| cspp.getStudentName().getFamilyName().isEmpty() || cspp.getStudentEmail().isEmpty()
					|| cspp.getStudentDate() == null) {

				if (!cspp.getStudentPnumber().matches("[p P]" + "[1-9]+")) {
					cspp.changeToRed1();
				} else {
					cspp.clearcss1();
				}

				if (!cspp.getStudentName().getFirstName().matches("[a-z A-Z]+")) {
					cspp.changeToRed2();
				} else {
					cspp.clearcss2();
				}

				if (!cspp.getStudentName().getFamilyName().matches("[a-z A-Z]+")) {
					cspp.changeToRed3();
				} else {
					cspp.clearcss3();
				}

				if (!cspp.getStudentEmail().matches("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
					cspp.changeToRed4();
				} else {
					cspp.clearcss4();
				}

				if (cspp.getStudentDate() == null) {
					cspp.changeToRed5();
				} else {
					cspp.clearcss5();
				}
			} else {
				cspp.clearcss1();
				cspp.clearcss2();
				cspp.clearcss3();
				cspp.clearcss4();
				cspp.clearcss5();
				profilestring += String.format("%s%n", "PNumber: " + cspp.getStudentPnumber());
				profilestring += String.format("%s%n", "Name: " +
						cspp.getStudentName().getFirstName() + " " + cspp.getStudentName().getFamilyName());
				profilestring += String.format("%s%n", "Email: " + cspp.getStudentEmail());
				profilestring += String.format("%s%n", "Date: " + cspp.getStudentDate());
				profilestring += String.format("%s%n", "Course: " + cspp.getSelectedCourse());

				osp.setProfile(profilestring);
				smp.clearSelectedAll();
				rmp.reserveClearUnSelec();
				for (Module m : cspp.getSelectedCourse().getAllModulesOnCourse()) {
					if (m.isMandatory() == true && m.getDelivery() == Schedule.TERM_1) {
						smp.populateSelectTerm1(m);
						smp.UpdateCredTerm1(m.getModuleCredits());
					} else if (m.isMandatory() == true && m.getDelivery() == Schedule.TERM_2) {
						smp.populateSelectTerm2(m);
						smp.UpdateCredTerm2(m.getModuleCredits());
					} else if (m.getDelivery() == Schedule.TERM_1) {
						smp.populateUnSelectTerm1(m);
					} else if (m.getDelivery() == Schedule.TERM_2) {
						smp.populateUnSelectTerm2(m);
					} else if (m.getDelivery() == Schedule.YEAR_LONG) {
						smp.populateSelectYearlong(m);
						smp.UpdateCredTerm1(m.getModuleCredits() / 2);
						smp.UpdateCredTerm2(m.getModuleCredits() / 2);
						view.changeTab(1);
					}
				}
			}
		}
	}

	private class Darkmodehandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			view.getStylesheets().add(getClass().getResource("dark-theme.css").toString());
		}
	}

	private class SaveButtonOWHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extensionFilter);
			File file = fileChooser.showSaveDialog(null);

			try {
				PrintWriter printWriter = new PrintWriter(file);

				if (file != null) {
					printWriter.write(
							"Student Profile: \n" + osp.getStudentProfile() + "Selected Modules: \n"
									+ osp.getSelectedModules()
									+ "Reserved Modules: \n" + osp.getReservedModules());
					printWriter.close();
				}
			} catch (FileNotFoundException eror) {
				eror.printStackTrace();
			}
		}
	}

	private class SaveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

		}
	}

	private class LoadMenuHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {

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

	// helper method to build dialogs
	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
