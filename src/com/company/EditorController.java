package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import static com.company.Main.*;

public class EditorController {

    @FXML
    private AnchorPane editor;

    @FXML
    private Button generateButton;

    @FXML
    private GridPane editorButtonGridpane;

    @FXML
    private ColorPicker particleColorPicker;

    @FXML
    private Slider lookSlider;

    @FXML
    private Text particleNumberText;

    @FXML
    private Text particleColorText;

    @FXML
    private Text visionText;

    @FXML
    private Text decayText;

    @FXML
    private Slider numberSlider;

    @FXML
    private Text BGColorText;

    @FXML
    private ColorPicker bgColorPicker;

    @FXML
    private CheckBox colorChanger;

    @FXML
    private Slider trailSlider;

    @FXML
    void changeColorMode(ActionEvent event) {

    }

    @FXML
    void generate(ActionEvent event) {
        Main.generateSim();
    }

    @FXML
    void pickBGColor(ActionEvent event) {
        backgroundColor = bgColorPicker.getValue();
    }

    @FXML
    void pickParticleColor(ActionEvent event) {
        particleColor = particleColorPicker.getValue();
    }

    @FXML
    void setLook(MouseEvent event) {
        LOOK_LENGTH = (int) lookSlider.getValue();
    }

    @FXML
    void setNumber(MouseEvent event) {
        PARTICLE_NUMBER = (int) numberSlider.getValue();
    }

    @FXML
    void setTrailDecay(MouseEvent event) {
        TRAIL_DECAY = 1- trailSlider.getValue();
    }

}
