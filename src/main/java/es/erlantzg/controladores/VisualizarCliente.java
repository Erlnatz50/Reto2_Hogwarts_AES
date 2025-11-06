package es.erlantzg.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class VisualizarCliente {

    @FXML
    private DatePicker SeleCumpleanios;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtNombre;

    @FXML
    void btnAniadir(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();

        if(nombre.isEmpty() || apellidos.isEmpty()){
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos incompletos");
            alerta.setHeaderText("Falta información");
            alerta.setContentText("Debes rellenar el nombre, los apellidos y el cumpleaños.");
            alerta.showAndWait();
            return;
        }

        txtNombre.clear();
        txtApellidos.clear();
    }

    @FXML
    void btnEliminarFilaSelec(ActionEvent event) {

    }

    @FXML
    void btnRestaurarFilas(ActionEvent event) {

    }
}
