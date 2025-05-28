
package CheckInOut;

import Codigo.Reserva;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CheckInOutController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void pulsarBotonListo(ActionEvent click) throws IOException{

        //CAMBIA EL ESTADO DE PENDIENTE A ACTIVA, y DE ACTIVA A FINALIZADA
        
        
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
    private ComboBox<String> ComboHuesped, ComboReserva;
    List<ComboBox<String>> combos;
    @FXML
    private Button botonListo;
    
    @FXML
    private TableView TablaReserva;
    @FXML
    private TableColumn ColumnReserva, ColumnHuesped, ColumnInicio, ColumnSalida, ColumnEstado;
    
    
    //HAY QUE CAMBIAR EL TIPO DE LISTA QUE ES listaFiltrada. Reserva es una clase generica que puse
    List<Reserva> listaFiltrada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combos = List.of(ComboHuesped, ComboReserva);
        List<String> huespedes;
        huespedes = List.of("David Hernández", "Eder Esquivel", "Patricia Rodriguez");
        //huespedes = mostrarNombreApellidoHuesped(); 
        ComboHuesped.getItems().addAll(huespedes);
        ComboHuesped.setEditable(true);
        
        ComboReserva.setDisable(true);
        ComboReserva.setEditable(true);
        
        ColumnReserva.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        ColumnHuesped.setCellValueFactory(new PropertyValueFactory<>("idHuesped"));
        ColumnInicio.setCellValueFactory(new PropertyValueFactory<>("fechaEntrada"));
        ColumnSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        ColumnEstado.setCellValueFactory(new PropertyValueFactory<>("estadoReserva"));


        // Listener para cuando el usuario seleccione un estado
        ComboHuesped.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                
                List<String> reservas;
                //Fechas de entrada
                reservas = List.of("11-06-2025","07-08-2025","03-06-2025");
                //municipios = obtenerMunicipiosDesdeMySQL(newVal); AQUI HAY QUE PONER LOS MUNICIPIOS DE X ESTADO
                ComboReserva.getItems().setAll(reservas);
                ComboReserva.setDisable(false);
            }
        });
        Runnable validarTodo = () -> {
        boolean algunComboVacio = combos.stream()
            .peek(cb -> cb.setStyle(cb.getValue() == null ? "-fx-border-color: red;" : null))
            .anyMatch(cb -> cb.getValue() == null);
        botonListo.setDisable(algunComboVacio);
        };
        combos.forEach(cb -> cb.valueProperty().addListener((obs, o, n) -> validarTodo.run()));

        if(ComboHuesped.getValue() != null && ComboReserva.getValue() != null){
            //listaFiltrada = CONSULTA USANDO EL HUESPED Y LA RESERVA COMO CRITERIOS(SIMILAR A ESTADO Y MUNICIPIO)
        }

        ObservableList<Reserva> data = FXCollections.observableArrayList(listaFiltrada);
        TablaReserva.setItems(data);
        
    }
    
}
