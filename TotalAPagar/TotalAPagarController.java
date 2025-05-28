
package TotalAPagar;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TotalAPagarController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void pulsarBotonListo(ActionEvent click) throws IOException{

        root = FXMLLoader.load(getClass().getResource("/Menu/Menu.fxml"));

        stage = (Stage) ((Node)click.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    
    public void pulsarBotonVolver(ActionEvent click) throws IOException{
        //Creamos una alerta que se activara al hacer click en el boton Salir
        Alert alertaClose = new Alert(Alert.AlertType.CONFIRMATION);
        //Le damos un titulo a la ventana de alerta
        alertaClose.setTitle("ADVERTENCIA");
        //Establecemos el texto de la alerta
        alertaClose.setHeaderText("¿Estas seguro que deseas salir de la ventana? El registro no se guardará");
        
        //Si hacemos click en aceptar la alerta, la ventana será cerrada
        if(alertaClose.showAndWait().get() == ButtonType.OK){
            root = FXMLLoader.load(getClass().getResource("/Menu/Menu.fxml"));

            stage = (Stage) ((Node)click.getSource()).getScene().getWindow();

            scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }   
    }

    @FXML
    private ComboBox<String> ComboHuesped, ComboMetodo;
    List<ComboBox<String>> combos;
    @FXML
    private Label LabelTotal;
    @FXML
    private Button botonListo, botonVolver;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combos = List.of(ComboHuesped, ComboMetodo);
        List<String> huespedes;
        huespedes = List.of("David Hernández", "Eder Esquivel", "Patricia Rodriguez");
        //huespedes = mostrarNombreApellidoHuesped(); 
        ComboHuesped.getItems().addAll(huespedes);
        ComboHuesped.setEditable(true);
        
        ComboMetodo.setDisable(true);
        ComboMetodo.setEditable(true);
        
        ComboHuesped.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                
                List<String> metodos;
                //Fechas de entrada
                /*
                if(HUESPED TIENE TARJETA){
                    metodos = List.of("Tarjeta","Efectivo");
                }
                else{
                    metodos = List.of("Efectivo");
                }
                */
                metodos = List.of("Tarjeta","Efectivo");
                ComboMetodo.getItems().setAll(metodos);
                ComboMetodo.setDisable(false);
            }
        });
        Runnable validarTodo = () -> {
        boolean algunComboVacio = combos.stream()
            .peek(cb -> cb.setStyle(cb.getValue() == null ? "-fx-border-color: red;" : null))
            .anyMatch(cb -> cb.getValue() == null);
        botonListo.setDisable(algunComboVacio);
        //FUNCION PARA SABER CUANTO PAGAR
        int TotalPagar = 0;
        if(!botonListo.isDisabled()){
            LabelTotal.setText("$ " + TotalPagar);
        }
        else{
            LabelTotal.setText("$$$$");
        }
        };

        combos.forEach(cb -> cb.valueProperty().addListener((obs, o, n) -> validarTodo.run()));

        

    }
    
}
