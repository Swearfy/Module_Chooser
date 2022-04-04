package view;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;
import model.Schedule;

public class SelectModulesPane extends VBox {

    Label lbl_Unselct1, lbl_Unselct2, lbl_Selected1, lbl_Selected2, lbl_SelectYearLong, lbl_Term1, lbl_Term2,
            lbl_CurrentCred1, lbl_CurrentCred2;
    Button btn_AddTerm1, btn_AddTerm2, btn_RemoveTerm1, btn_RemoveTerm2, btn_Reset, btn_Submit;
    TextField CredTerm1, CredTerm2;

    ListView<Module> unselectTerm1, unselectTerm2, selectYearlong, selectterm1, selectterm2;

    public SelectModulesPane() {

        // lables
        lbl_Unselct1 = new Label("Unselected Term 1 modules");
        lbl_Unselct2 = new Label("Unselected Term 2 modules");
        lbl_Selected1 = new Label("Selected Term 1 Modules");
        lbl_Selected2 = new Label("Selected Term 2 Modules");
        lbl_SelectYearLong = new Label("Selected Year Long Modules");
        lbl_Term1 = new Label("Term 1");
        lbl_Term2 = new Label("Term 2");
        lbl_CurrentCred1 = new Label("Current Term 1 credits");
        lbl_CurrentCred2 = new Label("Current Credits Term 2");

        // Buttons
        btn_AddTerm1 = new Button("Add");
        btn_AddTerm2 = new Button("Add");
        btn_RemoveTerm1 = new Button("Remove");
        btn_RemoveTerm2 = new Button("Remove");
        btn_Reset = new Button("Reset");
        btn_Submit = new Button("Submit");

        // text field
        CredTerm1 = new TextField();
        CredTerm1.setMaxWidth(50);
        CredTerm1.setEditable(false);
        CredTerm1.setMouseTransparent(true);
        CredTerm1.setFocusTraversable(false);

        CredTerm2 = new TextField();
        CredTerm2.setMaxWidth(50);
        CredTerm2.setEditable(false);
        CredTerm2.setMouseTransparent(true);
        CredTerm2.setFocusTraversable(false);

        // List view
        unselectTerm1 = new ListView<>();
        unselectTerm1.setPrefSize(10000, 10000);

        // ListView<String>
        unselectTerm2 = new ListView<>();

        unselectTerm2.setPrefSize(10000, 10000);

        selectYearlong = new ListView<>();
        selectYearlong.setPrefSize(10000, 10000);

        selectterm1 = new ListView<>();
        selectterm1.setPrefSize(10000, 10000);

        selectterm2 = new ListView<>();
        selectterm2.setPrefSize(10000, 10000);

        HBox Main = new HBox();
        Main.setAlignment(Pos.CENTER);

        // BOXES
        HBox ButtonBOXTerm1 = new HBox();
        ButtonBOXTerm1.setAlignment(Pos.CENTER);
        ButtonBOXTerm1.setSpacing(10);
        ButtonBOXTerm1.getChildren().addAll(lbl_Term1, btn_AddTerm1, btn_RemoveTerm1);

        HBox ButtonBOXTerm2 = new HBox();
        ButtonBOXTerm2.setAlignment(Pos.CENTER);
        ButtonBOXTerm2.setSpacing(10);
        ButtonBOXTerm2.getChildren().addAll(lbl_Term2, btn_AddTerm2, btn_RemoveTerm2);

        HBox ButtonResetSubBox = new HBox();
        ButtonResetSubBox.setAlignment(Pos.BOTTOM_CENTER);
        ButtonResetSubBox.setSpacing(20);
        ButtonResetSubBox.getChildren().add(btn_Reset);
        ButtonResetSubBox.getChildren().add(btn_Submit);

        HBox Credterm1Box = new HBox();
        Credterm1Box.setSpacing(10);
        Credterm1Box.getChildren().addAll(lbl_CurrentCred1, CredTerm1);

        HBox Credterm2Box = new HBox();
        Credterm2Box.setSpacing(10);
        Credterm2Box.getChildren().addAll(lbl_CurrentCred2, CredTerm2);

        HBox Credterm12Box = new HBox();
        Credterm12Box.setAlignment(Pos.CENTER);
        Credterm12Box.getChildren().addAll(Credterm1Box, Credterm2Box);

        VBox leftbox = new VBox();
        leftbox.getChildren().add(lbl_Unselct1);
        leftbox.getChildren().add(unselectTerm1);
        leftbox.getChildren().add(ButtonBOXTerm1);
        this.setMargin(ButtonBOXTerm1, new Insets(10, 10, 10, 10));

        leftbox.getChildren().add(lbl_Unselct2);
        leftbox.getChildren().add(unselectTerm2);
        leftbox.getChildren().add(ButtonBOXTerm2);
        this.setMargin(ButtonBOXTerm2, new Insets(10, 10, 10, 10));

        leftbox.prefWidthProperty().bind(this.widthProperty());
        leftbox.prefHeightProperty().bind(this.heightProperty());

        Main.getChildren().add(leftbox);

        VBox rightbox = new VBox();
        rightbox.setSpacing(10);
        rightbox.getChildren().add(lbl_SelectYearLong);
        rightbox.getChildren().add(selectYearlong);
        rightbox.getChildren().add(lbl_Selected1);
        rightbox.getChildren().add(selectterm1);
        rightbox.getChildren().add(lbl_Selected2);
        rightbox.getChildren().add(selectterm2);

        rightbox.prefWidthProperty().bind(this.widthProperty());
        rightbox.prefHeightProperty().bind(this.heightProperty());

        Main.getChildren().add(rightbox);

        Main.setSpacing(20);
        Credterm12Box.setSpacing(40);

        this.setMargin(Main, new Insets(10, 10, 10, 10));
        this.setMargin(Credterm12Box, new Insets(10, 10, 10, 10));
        this.setMargin(ButtonResetSubBox, new Insets(10, 10, 10, 10));

        this.getChildren().add(Main);
        this.getChildren().add(Credterm12Box);
        this.getChildren().add(ButtonResetSubBox);
    }


    public void populateListView(Collection<Module> m) {
        for (Module test : m) {
            if (test.isMandatory() == true && test.getDelivery() == Schedule.TERM_1) {
                selectterm1.getItems().addAll(test);
            } else if (test.isMandatory() == true && test.getDelivery() == Schedule.TERM_2) {
                selectterm2.getItems().addAll(test);
            } else if (test.getDelivery() == Schedule.TERM_1) {
                unselectTerm1.getItems().addAll(test);
            } else if (test.getDelivery() == Schedule.TERM_2) {
                unselectTerm2.getItems().addAll(test);
            } else {
                selectYearlong.getItems().addAll(test);
            }
        }

    }
}
