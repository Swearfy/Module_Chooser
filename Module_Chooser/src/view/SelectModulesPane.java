package view;

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

public class SelectModulesPane extends VBox {

    public SelectModulesPane() {

        // lables
        Label lbl_Unselct1 = new Label("Unselected Term 1 modules");
        Label lbl_Unselct2 = new Label("Unselected Term 2 modules");
        Label lbl_Selected1 = new Label("Selected Term 1 Modules");
        Label lbl_Selected2 = new Label("Selected Term 2 Modules");
        Label lbl_SelectYearLong = new Label("Selected Year Long Modules");
        Label lbl_Term1 = new Label("Term 1");
        Label lbl_Term2 = new Label("Term 2");
        Label lbl_CurrentCred1 = new Label("Current Term 1 credits");
        Label lbl_CurrentCred2 = new Label("Current Credits Term 2");

        // Buttons
        Button btn_AddTerm1 = new Button("Add");
        Button btn_AddTerm2 = new Button("Add");
        Button btn_RemoveTerm1 = new Button("Remove");
        Button btn_RemoveTerm2 = new Button("Remove");
        Button btn_Reset = new Button("Reset");
        Button btn_Submit = new Button("Submit");

        // text field
        TextField CredTerm1 = new TextField();
        CredTerm1.setMaxWidth(50);
        CredTerm1.setEditable(false);
        CredTerm1.setMouseTransparent(true);
        CredTerm1.setFocusTraversable(false);

        TextField CredTerm2 = new TextField();
        CredTerm2.setMaxWidth(50);
        CredTerm2.setEditable(false);
        CredTerm2.setMouseTransparent(true);
        CredTerm2.setFocusTraversable(false);

        // List view
        ListView<String> unselectTerm1 = new ListView<>();
        ObservableList<String> options = FXCollections.observableArrayList();
        unselectTerm1.setItems(options);

        ListView<String> unselectTerm2 = new ListView<>();
        ObservableList<String> options2 = FXCollections.observableArrayList();
        unselectTerm2.setItems(options2);

        ListView<String> selectYearlong = new ListView<>();
        ObservableList<String> options3 = FXCollections.observableArrayList();
        selectYearlong.setItems(options3);

        ListView<String> selectterm1 = new ListView<>();
        ObservableList<String> options4 = FXCollections.observableArrayList();
        selectterm1.setItems(options4);

        ListView<String> selectterm2 = new ListView<>();
        ObservableList<String> options5 = FXCollections.observableArrayList();
        selectterm2.setItems(options5);

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
        leftbox.setSpacing(10);
        leftbox.getChildren().add(lbl_Unselct1);
        leftbox.getChildren().add(unselectTerm1);
        leftbox.getChildren().add(ButtonBOXTerm1);

        leftbox.getChildren().add(lbl_Unselct2);
        leftbox.getChildren().add(unselectTerm2);
        leftbox.getChildren().add(ButtonBOXTerm2);
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

        Main.setSpacing(10);
        Credterm12Box.setSpacing(40);

        this.setMargin(Main, new Insets(10, 10, 10, 10));
        this.setMargin(Credterm12Box, new Insets(10, 10, 10, 10));
        this.setMargin(ButtonResetSubBox, new Insets(10, 10, 10, 10));

        this.getChildren().add(Main);
        this.getChildren().add(Credterm12Box);
        this.getChildren().add(ButtonResetSubBox);

    }

}
