package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OverviewSelectionPane extends VBox{

    public OverviewSelectionPane(){
        //Button
        Button btn_save = new Button("Save");

        //Text Area
        TextArea profile = new TextArea ();
        profile.setPrefSize(5000, 5000);
        profile.setEditable(false);
        profile.setMouseTransparent(true);
        profile.setFocusTraversable(false);

        TextArea txt_slcdModules = new TextArea ();
        txt_slcdModules.setPrefSize(5000, 5000);
        txt_slcdModules.setEditable(false);
        txt_slcdModules.setMouseTransparent(true);
        txt_slcdModules.setFocusTraversable(false);

        TextArea txt_resvdModules = new TextArea ();
        txt_resvdModules.setPrefSize(5000, 5000);
        txt_resvdModules.setEditable(false);
        txt_resvdModules.setMouseTransparent(true);
        txt_resvdModules.setFocusTraversable(false);



        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(txt_slcdModules,txt_resvdModules);

        this.getChildren().addAll(profile,hbox,btn_save);

        //Styling
        hbox.prefHeightProperty().bind(this.heightProperty());
        hbox.prefWidthProperty().bind(this.widthProperty());
        profile.prefHeightProperty().bind(hbox.heightProperty());
        profile.prefWidthProperty().bind(hbox.widthProperty());

        this.setAlignment(Pos.CENTER);
        this.setMargin(hbox, new Insets(10, 10, 10, 10));
        this.setMargin(profile, new Insets(10, 10, 10, 10));
        this.setMargin(btn_save, new Insets(10, 10, 10, 10));






    }
    
}
