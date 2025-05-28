
package InfoHuesped;

import Codigo.Huesped;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InfoHuespedController  implements Initializable{

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
    
    //HAY QUE CAMBIAR EL TIPO
    List<Huesped> listaFiltrada;
    
    @FXML
    private ComboBox ComboHuesped;
    @FXML
    private TableView TablaHuesped;
    @FXML
    private TableColumn ColumnID, ColumnNombre, ColumnApellidos, ColumnTelefono, ColumnCorreo, ColumnDireccion, ColumnEstado, ColumnMunicipio;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<String> huespedes;
        huespedes = List.of("David Hernández", "Eder Esquivel", "Patricia Rodriguez");
        //huespedes = mostrarNombreApellidoHuesped(); 
        ComboHuesped.getItems().addAll(huespedes);
        ComboHuesped.setEditable(true);
        
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("idHuesped"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        ColumnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        ColumnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        ColumnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        ColumnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        ColumnMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        
        
        if(ComboHuesped.getValue() != null){
            //listaFiltrada = CONSULTA USANDO EL HUESPED Y LA RESERVA COMO CRITERIOS(SIMILAR A ESTADO Y MUNICIPIO)
        }

        //ObservableList<Huesped> data = FXCollections.observableArrayList(listaFiltrada);
        //TablaHuesped.setItems(data);
    }
    
}
