package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReserveModulesPane extends VBox {

    ListView<Module> txtfld_UnselecTerm1, txtfld_UnselecTerm2, txtfld_ReservTerm1, txtfld_ReservTerm2;

    Button btn_AddTerm1,btn_AddTerm2,btn_RMVTerm1,btn_RMVTerm2,btn_CONFTerm1,btn_CONFTerm2;
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
        Accordion mainAcord = new Accordion();

        // Accordion Titled Panes 1 and 2
        TitledPane tpanel1 = new TitledPane();
        tpanel1.setText("Term 1 Modules");
        TitledPane tpanel2 = new TitledPane();
        tpanel2.setText("Term 2 Modules");

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
        term1BoxHButtons.getChildren().addAll(lbl_Term1Cred, btn_AddTerm1, btn_RMVTerm1, btn_CONFTerm1);

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
        term2BoxHButtons.getChildren().addAll(lbl_Term2Cred, btn_AddTerm2, btn_RMVTerm2, btn_CONFTerm2);

        Term2Main.getChildren().addAll(Term2SubMain, term2BoxHButtons);

        // Tpane Term 1 and Term 2 + content
        tpanel1.setContent(Term1Main);
        tpanel2.setContent(Term2Main);
        mainAcord.setExpandedPane(tpanel1);

        // Tpane 1 and 2 in acord
        mainAcord.getPanes().addAll(tpanel1, tpanel2);

        this.getChildren().addAll(mainAcord);

    }

}
