/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package RegistrarReserva;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegistrarReservaController implements Initializable{

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
    
    boolean todasHabitacionesRegistradas = false;
    
    @FXML
    private DatePicker DateEntrada, DateSalida;
    List<DatePicker> dates;
    @FXML
    private ComboBox<String> ComboHuesped, ComboTipo, ComboNumero, ComboEmpleado;
    List<ComboBox<String>> combosListo;
    List<ComboBox<String>> combosRegistro;
    @FXML
    private Spinner<Integer> SpinnerNumero;
    int numHabitaciones;
    int habitacionesRegistradas = 0;
    @FXML
    private Label LabelHabitacion, LabelPiso;
    @FXML
    private Button botonListo, botonRegistrar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //GENERAR EL ID DE RESERVA; ES NECESARIO HACERLO DESDE ANTES QUE EMPECEMOS A REGISTRAR HABITACIONES
        dates = List.of(DateEntrada, DateSalida);
        combosRegistro = List.of(ComboTipo, ComboNumero);
        combosListo = List.of(ComboHuesped, ComboEmpleado);
        List<String> huespedes;
        huespedes = List.of("David Hernández", "Eder Esquivel", "Patricia Rodriguez");
        //huespedes = mostrarNombreApellidoHuesped(); 
        ComboHuesped.getItems().addAll(huespedes);
        ComboHuesped.setEditable(true);
        
        List<String> tipoHabitacion;
        tipoHabitacion = List.of("Tipo1","tipo2","tipo3");
        //tipoHabitacion = mostrarTiposHabitacion();
        ComboTipo.getItems().addAll(tipoHabitacion);
        ComboTipo.setEditable(true);
        
        List<String> numerosDisponibles;
        numerosDisponibles = List.of("101","102","103");
        //numerosDisponibles = CONSULTA QUE COINCIDA EL TIPO DE HABITACION && HABITACION DISPONIBLE
        ComboNumero.getItems().addAll(numerosDisponibles);
        ComboNumero.setEditable(true);
        
        List<String> empleados;
        empleados = List.of("emp1","emp2","emp3");
        //empleados = CONSULTA DE LOS EMPLEADOS QUE PUEDAN REALIZAR UNA RESERVA
        ComboEmpleado.getItems().addAll(empleados);
        ComboEmpleado.setEditable(true);
        
        SpinnerValueFactory<Integer> valueFactory = 
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
		
	valueFactory.setValue(1);
		
	SpinnerNumero.setValueFactory(valueFactory);
        
        SpinnerNumero.valueProperty().addListener((ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) -> {
            numHabitaciones = SpinnerNumero.getValue();
        });
        
        

        Runnable validarHabitacion = () -> {
            boolean algunComboVacio = combosListo.stream()
            .peek(cb -> cb.setStyle(cb.getValue() == null ? "-fx-border-color: red;" : null))
            .anyMatch(cb -> cb.getValue() == null);
            
        boolean dateVacio = dates.stream()
                .peek(dp -> dp.setStyle(dp.getValue() == null ? "-fx-border-color: red;" : null))
                .anyMatch(dp -> dp.getValue() == null);
        
            boolean algunComboVacio2 = combosRegistro.stream()
            .peek(cb -> cb.setStyle(cb.getValue() == null ? "-fx-border-color: red;" : null))
            .anyMatch(cb -> cb.getValue() == null);
            botonRegistrar.setDisable(algunComboVacio2 || algunComboVacio || dateVacio);
        };
        combosListo.forEach(cb -> cb.valueProperty().addListener((obs, o, n) -> validarHabitacion.run()));
        dates.forEach(dp -> dp.valueProperty().addListener((obs, o, n) -> validarHabitacion.run()));
        combosRegistro.forEach(cb -> cb.valueProperty().addListener((obs, o, n) -> validarHabitacion.run()));
    }
    
    @FXML
    private AnchorPane PanelHabitacion;
    
    public void pulsarBotonRegistrar(ActionEvent click) throws IOException{
        SpinnerNumero.setDisable(true);
        habitacionesRegistradas++;
        LabelHabitacion.setText("Habitacion: " + (habitacionesRegistradas+1));
        if(habitacionesRegistradas >= numHabitaciones){
            PanelHabitacion.setDisable(true);
            LabelHabitacion.setText("Habitacion: ");
            botonListo.setDisable(false);
        }
        
        //GUARDAR LOS DATOS EN UN REGISTRO DE RESERVAHABITACION
       combosRegistro.forEach(cb -> cb.setValue(""));
    }
}
