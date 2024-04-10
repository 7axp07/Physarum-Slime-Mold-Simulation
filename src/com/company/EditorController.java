package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;




import static com.company.Main.*;
import static javafx.scene.paint.Color.*;

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
    private Slider visionSlider;

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
    private Slider diffusionSlider;

    @FXML
    private CheckBox colorChanger;

    @FXML
    private Slider trailSlider;


    @FXML
    private Text presetText;

    @FXML
    private ComboBox<Preset> presetComboBox;

    @FXML
    private TextField presetNameTextField;

    @FXML
    void initialize(){
        presetComboBox.getItems().addAll(Main.presets);

    };


    @FXML
    void generate(ActionEvent event) {
        simulationStage.close();
        isColourChanging = false;
        PARTICLE_NUMBER = 1000;
        TRAIL_DECAY = 0.3;
        LOOK_LENGTH = 7;
        DIFFUSION_RATE = 0.01;
        ZOOM = 4;
        particleColor = WHITE;

        particleColor = particleColorPicker.getValue();
        if (colorChanger.isSelected()){
            isColourChanging = true;
        }
        LOOK_LENGTH = (int) visionSlider.getValue();
        PARTICLE_NUMBER = (int) numberSlider.getValue();
        TRAIL_DECAY = 1- trailSlider.getValue();
        DIFFUSION_RATE = diffusionSlider.getValue();
        Main.generateSim();
    }

    @FXML
    void setPreset(ActionEvent event) {
        Preset preset = presetComboBox.getSelectionModel().getSelectedItem();
        numberSlider.setValue(preset.number);
        particleColorPicker.setValue(preset.color);
        if (preset.isColourChanging){
            colorChanger.setSelected(true);
        }
        else {
            colorChanger.setSelected(false);

        }
        trailSlider.setValue(1-preset.trailDecay);
        diffusionSlider.setValue(preset.diffusionRate);
        visionSlider.setValue(preset.visionRange);

    }


    @FXML
    void setNewPreset(ActionEvent event) {
        if (!(presetNameTextField.getText() == null)){
            Preset preset = new Preset(presetNameTextField.getText(), (int) numberSlider.getValue(),
                    particleColorPicker.getValue(), colorChanger.isSelected(), 1-trailSlider.getValue(),
                    diffusionSlider.getValue(), (int) visionSlider.getValue());
            Main.presets.add(preset);
            FileController.addPreset();
            presetComboBox.getItems().add(preset);
            presetNameTextField.clear();
        }
    }




}
