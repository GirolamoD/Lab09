package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> boxP;

    @FXML
    private ComboBox<Fermata> boxA;

    @FXML
    private TextArea txtResult;
    

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.appendText(model.calcolaPercorso(boxP.getValue(), boxA.getValue()));
    }

    @FXML
    void initialize() {
        assert boxP != null : "fx:id=\"boxP\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert boxA != null : "fx:id=\"boxA\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }

	public void setModel(Model m) {
		this.model=m ;
		boxP.getItems().addAll(m.getFermate());
		boxA.getItems().addAll(m.getFermate());

	}
}
