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

	private CreateStudentProfilePane cspPane;
	private SelectModulesPane smPane;
	private ReserveModulesPane rmPane;
	private OverviewSelectionPane osPane;
	private ModuleChooserMenuBar mstMenuBar;

	public ModuleChooserController(ModuleChooserRootPane view, StudentProfile model) {
		// initialise view and model fields
		this.view = view;
		this.model = model;

		// initialise view subcontainer fields
		cspPane = view.getCreateStudentProfilePane();
		smPane = view.gSelectModulesPane();
		rmPane = view.getReserveModulesPane();
		osPane = view.getoOverviewSelectionPane();
		mstMenuBar = view.getModuleSelectionToolMenuBar();

		// add courses to combobox in create student profile pane using the
		// generateAndGetCourses helper method below
		cspPane.addCoursesToComboBox(generateAndGetCourses());
		// attach event handlers to view using private helper method

		this.attachEventHandlers();
	}

	// helper method - used to attach event handlers
	private void attachEventHandlers() {
		// attach an event handler to the create student profile pane
		cspPane.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		cspPane.darkmode(new Darkmodehandler());

		smPane.ResetBTN(new CreateStudentProfileHandler());

		smPane.addselectterm1(new smPaneTerm1AddButtonHandler());
		smPane.addselectterm2(new smPaneTerm2AddButtonHandler());

		smPane.removeBtn1(new smPaneTerm1RemoveButtonHandler());
		smPane.removeBtn2(new smPaneTerm2RemoveButtonHandler());

		smPane.SubmitBtn(new smPaneSubmitModulesHandler());

		rmPane.addSelectTerm1RMP(new rmPaneTerm1AddBtnHandler());
		rmPane.addSelectTerm2RMP(new rmPaneTerm2AddBtnHandler());

		rmPane.removeBtn1RMP(new rmPaneTerm1RemoveBtnHandler());
		rmPane.removeBtn2RMP(new rmPaneTerm2RemoveBtnHandler());

		rmPane.confirmTerm1RMP(new rmPaneConfirmTerm1ModulesHandler());
		rmPane.confirmTerm2RMP(new rmPaneConfirmTerm2ModulesHandler());

		mstMenuBar.addSaveHandler(new SaveMenuHandler());
		mstMenuBar.addLoadHandler(new LoadMenuHandler());

		// attach an event handler to the menu bar that closes the application
		mstMenuBar.addExitHandler(e -> System.exit(0));
		mstMenuBar.addAboutHandler(new AboutButtonHandler());

		osPane.saveBTN(new SaveButtonOWHandler());
	}

	// Select module pane classes
	private class smPaneTerm1AddButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smPane.GetCredTerm1() == 60) {
				alertDialogBuilder(AlertType.INFORMATION, "Error", "60 Credits Maximum",
						"Reached the maximum modules/credits");
			} else if (smPane.getTerm1UnSelection() != null) {
				Module m = smPane.getTerm1UnSelection();
				smPane.AddTerm1Selection(m);
				smPane.RemoveTerm1UnSelection(m);
				smPane.UpdateCredTerm1(m.getModuleCredits());
			} else if (smPane.getTerm1Selection() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Nothing Selected", "Please select a module to add");
			}
		}
	}

	private class smPaneTerm2AddButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smPane.GetCredTerm2() == 60) {
				alertDialogBuilder(AlertType.INFORMATION, "Error", "60 Credits Maximum",
						"Reached the maximum modules/credits");
			} else if (smPane.getTerm2UnSelection() != null) {
				Module m = smPane.getTerm2UnSelection();
				smPane.AddTerm2Selection(m);
				smPane.RemoveTerm2UnSelection(m);
				smPane.UpdateCredTerm2(m.getModuleCredits());
			} else if (smPane.getTerm2Selection() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Nothing Selected", "Please select a module to add");
			}
		}
	}

	private class smPaneTerm1RemoveButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smPane.getTerm1Selection() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection", "Please select a module to remove");
			} else if (smPane.getTerm1Selection().isMandatory() == true) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection",
						"Please Select an non mandatory module to remove");
			} else if (smPane.getTerm1Selection() != null) {
				Module m = smPane.getTerm1Selection();
				smPane.RemoveTerm1Selection(m);
				smPane.AddTerm1UnSelection(m);
				smPane.DecremUpdateCredTerm1(m.getModuleCredits());
				rmPane.reserveClearUnSelec();
			}
		}
	}

	private class smPaneTerm2RemoveButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (smPane.getTerm2Selection() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection", "Please select a module to remove");
			} else if (smPane.getTerm2Selection().isMandatory() == true) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection",
						"Please Select an non mandatory module to remove");
			} else if (smPane.getTerm2Selection() != null) {
				Module m = smPane.getTerm2Selection();
				smPane.RemoveTerm2Selection(m);
				smPane.AddTerm2UnSelection(m);
				smPane.DecremUpdateCredTerm2(m.getModuleCredits());
				rmPane.reserveClearUnSelec();
			}
		}
	}

	String selectedmodules = "";

	private class smPaneSubmitModulesHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			rmPane.reserveClearUnSelec();
			if (smPane.GetCredTerm1() < 60 || smPane.GetCredTerm2() < 60) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Not Enough Credits Selected",
						"Please select modules for both terms of 60 credits each");
			} else if (smPane.GetCredTerm1() == 60 && smPane.GetCredTerm2() == 60) {
				for (Module m : smPane.getTerm1UnselectedLeftOver()) {
					rmPane.rmpPopulateUnSlctTerm1(m);
				}
				for (Module m : smPane.getTerm2UnselectedLeftOver()) {
					rmPane.rmpPopulateUnSlctTerm2(m);
				}

				for (Module m : smPane.getYearLongselectedmodules()) {
					String truefalse = m.isMandatory() ? "yes" : "no";
					selectedmodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + ", " + " Module name: " + m.getModuleName() + ", "
									+ " Credits: " + m.getModuleCredits() + ", " + "Mandatory Course: "
									+ truefalse
									+ ", " + " Delivery: "
									+ m.getDelivery().toString().replaceAll(m.getDelivery().toString(), "Term 1")
									+ "\n");
					osPane.setSelectModules(selectedmodules);
				}

				for (Module m : smPane.getTerm1selectedmodules()) {
					String truefalse = m.isMandatory() ? "yes" : "no";
					selectedmodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + ", " + " Module name: " + m.getModuleName() + ", "
									+ " Credits: " + m.getModuleCredits() + ", " + "Mandatory Course: "
									+ truefalse
									+ ", " + " Delivery: "
									+ m.getDelivery().toString().replaceAll(m.getDelivery().toString(), "Term 2")
									+ "\n");
					osPane.setSelectModules(selectedmodules);
				}

				for (Module m : smPane.getTerm2selectedmodules()) {
					String truefalse = m.isMandatory() ? "yes" : "no";
					selectedmodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + ", " + " Module name: " + m.getModuleName() + ", "
									+ " Credits: " + m.getModuleCredits() + ", " + "Mandatory Course: "
									+ truefalse
									+ ", " + " Delivery: "
									+ m.getDelivery().toString().replaceAll(m.getDelivery().toString(), "Year Long")
									+ "\n");
					osPane.setSelectModules(selectedmodules);
				}

				view.changeTab(2);
			}
		}
	}

	// Reserve module pane
	private class rmPaneTerm1AddBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmPane.getCTerm1() >= 30) {
				alertDialogBuilder(AlertType.INFORMATION, "Error", "30 Credits Maximum",
						"Reached the maximum modules/credits");
			} else if (rmPane.getRmpTerm1Unselect() != null) {
				Module m = rmPane.getRmpTerm1Unselect();
				rmPane.addTerm1rmp(m);
				rmPane.removeTerm1Unselecrmp(m);
				rmPane.updateCredT1(m.getModuleCredits());
			} else if (rmPane.getRmpTerm1Unselect() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Nothing Selected", "Please select a module to add");
			}
		}
	}

	private class rmPaneTerm2AddBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmPane.getCTerm2() >= 30) {
				alertDialogBuilder(AlertType.INFORMATION, "Error", "30 Credits Maximum",
						"Reached the maximum modules/credits");
			} else if (rmPane.getRmpTerm2Unselect() != null) {
				Module m = rmPane.getRmpTerm2Unselect();
				rmPane.addTerm2rmp(m);
				rmPane.removeTerm2Unselecrmp(m);
				rmPane.updateCredT2(m.getModuleCredits());
			} else if (rmPane.getRmpTerm2Unselect() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Nothing Selected", "Please select a module to add");
			}
		}
	}

	private class rmPaneTerm1RemoveBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmPane.getRmpTerm1select() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection", "Please select a module to remove");
			} else if (rmPane.getRmpTerm1select().isMandatory() == true) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection",
						"Please Select an non mandatory module to remove");
			} else if (rmPane.getRmpTerm1select() != null) {
				Module m = rmPane.getRmpTerm1select();
				rmPane.removeTerm1rmp(m);
				rmPane.addTerm1Unselecrmp(m);
				rmPane.DecremUpdateCredTerm1(m.getModuleCredits());
			}
		}
	}

	private class rmPaneTerm2RemoveBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmPane.getRmpTerm2select() == null) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection", "Please select a module to remove");
			} else if (rmPane.getRmpTerm2select().isMandatory() == true) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Wrong selection",
						"Please Select an non mandatory module to remove");
			} else if (rmPane.getRmpTerm2select() != null) {
				Module m = rmPane.getRmpTerm2select();
				rmPane.removeTerm2rmp(m);
				rmPane.addTerm2Unselecrmp(m);
				rmPane.DecremUpdateCredTerm2(m.getModuleCredits());
			}
		}
	}

	String reservemodules = "";

	private class rmPaneConfirmTerm1ModulesHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmPane.getCTerm1() < 30) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Not Enough Credits Selected",
						"Please select modules for both terms of 30 credits each");
			} else if (rmPane.getCTerm1() == 30) {
				rmPane.expandnext();
				for (Module m : rmPane.getTerm1selected()) {
					reservemodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + " Module name: " + m.getModuleName() + " Credits: "
									+ m.getModuleCredits() + " Delivery: "
									+ m.getDelivery().toString().replaceAll(m.getDelivery().toString(), "Term 1")
									+ "\n");
					osPane.setReserveModule(reservemodules);
				}
			}
		}
	}

	private class rmPaneConfirmTerm2ModulesHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (rmPane.getCTerm2() < 30) {
				alertDialogBuilder(AlertType.ERROR, "Error", "Not Enough Credits Selected",
						"Please select modules for both terms of 30 credits each");
			} else if (rmPane.getCTerm2() == 30) {
				for (Module m : rmPane.getTerm2selected()) {
					reservemodules += String.format("%s%n",
							"Module code: " + m.getModuleCode() + ", " + " Module name: " + m.getModuleName() + ", "
									+ " Credits: " + m.getModuleCredits() + ", " + " Delivery: "
									+ m.getDelivery().toString().replaceAll(m.getDelivery().toString(), "Term 2")
									+ "\n");
					osPane.setReserveModule(reservemodules);
				}
				view.changeTab(3);
			}
		}
	}

	String profilestring = "";

	// Create profile pane
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (cspPane.getStudentPnumber().isEmpty() || cspPane.getStudentName().getFirstName().isEmpty()
					|| cspPane.getStudentName().getFamilyName().isEmpty() || cspPane.getStudentEmail().isEmpty()
					|| cspPane.getStudentDate() == null) {

				if (!cspPane.getStudentPnumber().matches("[p P]" + "[1-9]+")) {
					cspPane.changeToRed1();
				} else {
					cspPane.clearcss1();
				}

				if (!cspPane.getStudentName().getFirstName().matches("[a-z A-Z]+")) {
					cspPane.changeToRed2();
				} else {
					cspPane.clearcss2();
				}

				if (!cspPane.getStudentName().getFamilyName().matches("[a-z A-Z]+")) {
					cspPane.changeToRed3();
				} else {
					cspPane.clearcss3();
				}

				if (!cspPane.getStudentEmail().matches("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
					cspPane.changeToRed4();
				} else {
					cspPane.clearcss4();
				}

				if (cspPane.getStudentDate() == null) {
					cspPane.changeToRed5();
				} else {
					cspPane.clearcss5();
				}
			} else {
				cspPane.clearcss1();
				cspPane.clearcss2();
				cspPane.clearcss3();
				cspPane.clearcss4();
				cspPane.clearcss5();
				profilestring += String.format("%s%n", "PNumber: " + cspPane.getStudentPnumber());
				profilestring += String.format("%s%n", "Name: " +
						cspPane.getStudentName().getFirstName() + " " + cspPane.getStudentName().getFamilyName());
				profilestring += String.format("%s%n", "Email: " + cspPane.getStudentEmail());
				profilestring += String.format("%s%n", "Date: " + cspPane.getStudentDate());
				profilestring += String.format("%s%n", "Course: " + cspPane.getSelectedCourse() + "\n");

				osPane.setProfile(profilestring);
				smPane.clearSelectedAll();
				rmPane.reserveClearUnSelec();
				for (Module m : cspPane.getSelectedCourse().getAllModulesOnCourse()) {
					if (m.isMandatory() == true && m.getDelivery() == Schedule.TERM_1) {
						smPane.populateSelectTerm1(m);
						smPane.UpdateCredTerm1(m.getModuleCredits());
					} else if (m.isMandatory() == true && m.getDelivery() == Schedule.TERM_2) {
						smPane.populateSelectTerm2(m);
						smPane.UpdateCredTerm2(m.getModuleCredits());
					} else if (m.getDelivery() == Schedule.TERM_1) {
						smPane.populateUnSelectTerm1(m);
					} else if (m.getDelivery() == Schedule.TERM_2) {
						smPane.populateUnSelectTerm2(m);
					} else if (m.getDelivery() == Schedule.YEAR_LONG) {
						smPane.populateSelectYearlong(m);
						smPane.UpdateCredTerm1(m.getModuleCredits() / 2);
						smPane.UpdateCredTerm2(m.getModuleCredits() / 2);
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
					printWriter.write(osPane.getStudentProfile()
							+ osPane.getSelectedModules()
							+ osPane.getReservedModules());
					printWriter.close();
				}
			} catch (FileNotFoundException eror) {
				eror.printStackTrace();
			}
		}
	}

	private class AboutButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", null,
					"Enter your details then press create to be able to select modules for third year . \n Once you complete you can save the progress you done or the final text file.");
		}
	}

	private class SaveMenuHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("modulesObj.dat"));) {

				oos.writeObject(cspPane.getSelectedCourse().getCourseName());
				oos.writeUTF(cspPane.getStudentPnumber());
				oos.writeUTF(cspPane.getStudentName().getFirstName());
				oos.writeUTF(cspPane.getStudentName().getFamilyName());
				oos.writeUTF(cspPane.getStudentEmail());
				oos.writeUTF(cspPane.getStudentDate().toString());

				oos.writeObject(smPane.getTerm1selectedmodules().toString());
				oos.writeObject(smPane.getTerm2selectedmodules().toString());
				oos.writeObject(smPane.getYearLongselectedmodules().toString());
				oos.writeObject(rmPane.getRmpTerm1select().toString());
				oos.writeObject(rmPane.getRmpTerm2select().toString());

				oos.flush();

				alertDialogBuilder(AlertType.CONFIRMATION, "Information Dialog", "Save success",
						"Journey Return saved to modulesObj.dat");
			} catch (IOException ioExcep) {
				System.out.println("Error saving");
				System.out.println(ioExcep);

			}
		}
	}

	private class LoadMenuHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			// load in the data model
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("modulesObj.dat"));) {

				model = (StudentProfile) ois.readObject(); // reads the model object back from a file

				alertDialogBuilder(AlertType.CONFIRMATION, "Information Dialog", "Load success",
						"Journey Return loaded from modulesObj.dat");
			} catch (IOException ioExcep) {
				System.out.println("Error loading");
				System.out.println(ioExcep);

			} catch (ClassNotFoundException c) {
				System.out.println("Class Not found");
				System.out.println(c);

			}

			smPane.clearSelectedAll();
			rmPane.reserveClearUnSelec();
			osPane.clearAll();
			cspPane.clearALLCSPp();

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
