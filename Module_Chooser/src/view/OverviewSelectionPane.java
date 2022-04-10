package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OverviewSelectionPane extends VBox {

    Button btn_save;

    TextArea profile, txt_slcdModules, txt_resvdModules;
    String d;

    public OverviewSelectionPane() {
        // Button
        btn_save = new Button("Save");

        // Text Area
        profile = new TextArea();
        profile.setPrefSize(5000, 5000);
        profile.setEditable(false);
        profile.setFocusTraversable(false);
        profile.setPromptText("Profile will display here");

        txt_slcdModules = new TextArea();
        txt_slcdModules.setPrefSize(5000, 5000);
        txt_slcdModules.setEditable(false);
        txt_slcdModules.setFocusTraversable(false);
        txt_slcdModules.setPromptText("Selected Module will show here");
        txt_slcdModules.setWrapText(true);

        txt_resvdModules = new TextArea();
        txt_resvdModules.setPrefSize(5000, 5000);
        txt_resvdModules.setEditable(false);
        txt_resvdModules.setFocusTraversable(false);
        txt_resvdModules.setPromptText("Reserved Module will show here");
        txt_resvdModules.setWrapText(true);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(txt_slcdModules, txt_resvdModules);

        this.getChildren().addAll(profile, hbox, btn_save);

        // Styling
        hbox.prefHeightProperty().bind(this.heightProperty());
        hbox.prefWidthProperty().bind(this.widthProperty());
        profile.prefHeightProperty().bind(hbox.heightProperty());
        profile.prefWidthProperty().bind(hbox.widthProperty());

        this.setAlignment(Pos.CENTER);
        this.setMargin(hbox, new Insets(10, 10, 10, 10));
        this.setMargin(profile, new Insets(10, 10, 10, 10));
        this.setMargin(btn_save, new Insets(10, 10, 10, 10));

    }

    public void setProfile(String s) {
        profile.setText(s);
    }

    public void setSelectModules(String s) {
        txt_slcdModules.setText(s);
    }

    public void setReserveModule(String s) {
        txt_resvdModules.setText(s);
    }
}
