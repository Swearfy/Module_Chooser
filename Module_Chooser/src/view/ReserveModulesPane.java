package view;

import java.io.Serializable;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;

public class ReserveModulesPane extends VBox implements Serializable {

    ListView<Module> txtfld_UnselecTerm1, txtfld_UnselecTerm2, txtfld_ReservTerm1, txtfld_ReservTerm2;

    Button btn_AddTerm1, btn_AddTerm2, btn_RMVTerm1, btn_RMVTerm2, btn_CONFTerm1, btn_CONFTerm2;

    TextField CTerm1, CTerm2;

    int CredT1, CredT2;

    Accordion mainAcord;

    TitledPane tpanel1, tpanel2;

    public ReserveModulesPane() {

        // Labels
        Label lbl_Term1Cred = new Label("Reserve 30 credtis worth of term 1 modules");
        Label lbl_Term2Cred = new Label("Reserve 30 credtis worth of term 2 modules");
        Label lbl_UnselecTerm1 = new Label("Unselect Term 1 Modules");
        Label lbl_UnselecTerm2 = new Label("Unselect Term 2 Modules");
        Label lbl_RevervTerm1 = new Label("Reserve Term 1 Modules");
        Label lbl_RevervTerm2 = new Label("Reserve Term 2 Modules");

        // buttons
        btn_AddTerm1 = new Button("Add");
        btn_AddTerm2 = new Button("Add");
        btn_RMVTerm1 = new Button("Remove");
        btn_RMVTerm2 = new Button("Remove");
        btn_CONFTerm1 = new Button("Confirm");
        btn_CONFTerm2 = new Button("Confirm");

        CTerm1 = new TextField("0");
        CTerm1.setMaxWidth(50);
        CTerm1.setEditable(false);
        CTerm1.setMouseTransparent(true);
        CTerm1.setFocusTraversable(false);

        CTerm2 = new TextField("0");
        CTerm2.setMaxWidth(50);
        CTerm2.setEditable(false);
        CTerm2.setMouseTransparent(true);
        CTerm2.setFocusTraversable(false);

        // List view
        txtfld_UnselecTerm1 = new ListView<>();
        txtfld_UnselecTerm1.setPrefSize(10000, 10000);

        txtfld_UnselecTerm2 = new ListView<>();
        txtfld_UnselecTerm2.setPrefSize(10000, 10000);

        txtfld_ReservTerm1 = new ListView<>();
        txtfld_ReservTerm1.setPrefSize(10000, 10000);

        txtfld_ReservTerm2 = new ListView<>();
        txtfld_ReservTerm2.setPrefSize(10000, 10000);

        // Main Accordion
        mainAcord = new Accordion();

        // Accordion Titled Panes 1 and 2
        tpanel1 = new TitledPane();
        tpanel1.setText("Term 1 Modules");
        tpanel2 = new TitledPane();
        tpanel2.setText("Term 2 Modules");
        tpanel2.setVisible(false);

        VBox Term1Main = new VBox();
        Term1Main.setSpacing(10);

        HBox Term1SubMain = new HBox();
        Term1SubMain.setAlignment(Pos.CENTER);
        Term1SubMain.setSpacing(10);

        VBox LeftTerm1 = new VBox();
        LeftTerm1.getChildren().addAll(lbl_UnselecTerm1, txtfld_UnselecTerm1);
        LeftTerm1.prefWidthProperty().bind(this.widthProperty());
        LeftTerm1.prefHeightProperty().bind(this.heightProperty());

        VBox rightTerm1 = new VBox();
        rightTerm1.getChildren().addAll(lbl_RevervTerm1, txtfld_ReservTerm1);
        rightTerm1.prefWidthProperty().bind(this.widthProperty());
        rightTerm1.prefHeightProperty().bind(this.heightProperty());

        Term1SubMain.getChildren().addAll(LeftTerm1, rightTerm1);

        // buttons
        HBox term1BoxHButtons = new HBox();
        term1BoxHButtons.setAlignment(Pos.CENTER);
        term1BoxHButtons.setSpacing(20);
        term1BoxHButtons.getChildren().addAll(lbl_Term1Cred, CTerm1, btn_AddTerm1, btn_RMVTerm1, btn_CONFTerm1);

        Term1Main.getChildren().addAll(Term1SubMain, term1BoxHButtons);

        VBox Term2Main = new VBox();
        Term2Main.setSpacing(10);

        HBox Term2SubMain = new HBox();
        Term2SubMain.setAlignment(Pos.CENTER);
        Term2SubMain.setSpacing(10);

        VBox LeftTerm2 = new VBox();
        LeftTerm2.getChildren().addAll(lbl_UnselecTerm2, txtfld_UnselecTerm2);
        LeftTerm2.prefWidthProperty().bind(this.widthProperty());
        LeftTerm2.prefHeightProperty().bind(this.heightProperty());

        VBox rightTerm2 = new VBox();
        rightTerm2.getChildren().addAll(lbl_RevervTerm2, txtfld_ReservTerm2);
        rightTerm2.prefWidthProperty().bind(this.widthProperty());
        rightTerm2.prefHeightProperty().bind(this.heightProperty());

        Term2SubMain.getChildren().addAll(LeftTerm2, rightTerm2);

        // buttons 2
        HBox term2BoxHButtons = new HBox();
        term2BoxHButtons.setAlignment(Pos.CENTER);
        term2BoxHButtons.setSpacing(20);
        term2BoxHButtons.getChildren().addAll(lbl_Term2Cred, CTerm2, btn_AddTerm2, btn_RMVTerm2, btn_CONFTerm2);

        Term2Main.getChildren().addAll(Term2SubMain, term2BoxHButtons);

        // Tpane Term 1 and Term 2 + content
        tpanel1.setContent(Term1Main);
        tpanel2.setContent(Term2Main);
        mainAcord.setExpandedPane(tpanel1);

        // Tpane 1 and 2 in acord
        mainAcord.getPanes().addAll(tpanel1, tpanel2);

        this.getChildren().addAll(mainAcord);

    }

    // Populate listviews
    public void rmpPopulateUnSlctTerm1(Module m) {
        txtfld_UnselecTerm1.getItems().addAll(m);
    }

    public void rmpPopulateUnSlctTerm2(Module m) {
        txtfld_UnselecTerm2.getItems().addAll(m);
    }

    public void reservePopulateSelectTerm1(Module m) {
        txtfld_ReservTerm1.getItems().addAll(m);
    }

    public void reservePopulateSelectTerm2(Module m) {
        txtfld_ReservTerm2.getItems().addAll(m);
    }

    // Clear
    public void reserveClearUnSelec() {
        CredT1 = 0;
        CredT2 = 0;
        CTerm1.setText(String.valueOf(CredT1));
        CTerm2.setText(String.valueOf(CredT2));

        txtfld_ReservTerm1.getItems().clear();
        txtfld_ReservTerm2.getItems().clear();
        txtfld_UnselecTerm1.getItems().clear();
        txtfld_UnselecTerm2.getItems().clear();

    }

    // Get selection
    public Module getRmpTerm1Unselect() {
        return txtfld_UnselecTerm1.getSelectionModel().getSelectedItem();
    }

    public ObservableList<Module> getRmpTerm1UnselectLeftOver() {
        return txtfld_UnselecTerm1.getItems();
    }

    public Module getRmpTerm2Unselect() {
        return txtfld_UnselecTerm2.getSelectionModel().getSelectedItem();
    }

    public ObservableList<Module> getRmpTerm2UnselectLeftOver() {
        return txtfld_UnselecTerm2.getItems();
    }

    public Module getRmpTerm1select() {
        return txtfld_ReservTerm1.getSelectionModel().getSelectedItem();
    }

    public ObservableList<Module> getRmpTerm1selectLeftOver() {
        return txtfld_ReservTerm1.getItems();
    }

    public Module getRmpTerm2select() {
        return txtfld_ReservTerm2.getSelectionModel().getSelectedItem();
    }

    public ObservableList<Module> getRmpTerm2selectLeftOver() {
        return txtfld_ReservTerm2.getItems();
    }

    // Add Selection
    public void addTerm1rmp(Module m) {
        txtfld_ReservTerm1.getItems().addAll(m);
    }

    public void addTerm2rmp(Module m) {
        txtfld_ReservTerm2.getItems().addAll(m);
    }

    public void addTerm1Unselecrmp(Module m) {
        txtfld_UnselecTerm1.getItems().addAll(m);
    }

    public void addTerm2Unselecrmp(Module m) {
        txtfld_UnselecTerm2.getItems().addAll(m);
    }

    // Remove Selection
    public void removeTerm1Unselecrmp(Module m) {
        txtfld_UnselecTerm1.getItems().remove(m);
    }

    public void removeTerm2Unselecrmp(Module m) {
        txtfld_UnselecTerm2.getItems().remove(m);
    }

    public void removeTerm1rmp(Module m) {
        txtfld_ReservTerm1.getItems().remove(m);
    }

    public void removeTerm2rmp(Module m) {
        txtfld_ReservTerm2.getItems().remove(m);
    }

    // Update Credits
    public void updateCredT1(int count) {
        CredT1 = CredT1 + count;
        CTerm1.setText(String.valueOf(CredT1));
    }

    public void updateCredT2(int count) {
        CredT2 = CredT2 + count;
        CTerm2.setText(String.valueOf(CredT2));
    }

    // Return Credit
    public int getCTerm1() {
        return CredT1;
    }

    public int getCTerm2() {
        return CredT2;
    }

    public void expandnext() {
        tpanel2.setVisible(true);
        mainAcord.setExpandedPane(tpanel2);
    }
    public void expandprevious() {
        tpanel2.setVisible(false);
        mainAcord.setExpandedPane(tpanel1);

    }

    // Substract credits
    public void DecremUpdateCredTerm1(int count) {
        CredT1 = CredT1 - count;
        CTerm1.setText(String.valueOf(CredT1));
    }

    public void DecremUpdateCredTerm2(int count) {
        CredT2 = CredT2 - count;
        CTerm2.setText(String.valueOf(CredT2));
    }

    // Leftover
    public ObservableList<Module> getTerm1selected() {
        return txtfld_ReservTerm1.getItems();
    }

    public ObservableList<Module> getTerm2selected() {
        return txtfld_ReservTerm2.getItems();
    }

    // Button handler
    public void addSelectTerm1RMP(EventHandler<ActionEvent> handler) {
        btn_AddTerm1.setOnAction(handler);
    }

    public void addSelectTerm2RMP(EventHandler<ActionEvent> handler) {
        btn_AddTerm2.setOnAction(handler);
    }

    public void removeBtn1RMP(EventHandler<ActionEvent> handler) {
        btn_RMVTerm1.setOnAction(handler);
    }

    public void removeBtn2RMP(EventHandler<ActionEvent> handler) {
        btn_RMVTerm2.setOnAction(handler);
    }

    public void confirmTerm1RMP(EventHandler<ActionEvent> handler) {
        btn_CONFTerm1.setOnAction(handler);
    }

    public void confirmTerm2RMP(EventHandler<ActionEvent> handler) {
        btn_CONFTerm2.setOnAction(handler);
    }
}
