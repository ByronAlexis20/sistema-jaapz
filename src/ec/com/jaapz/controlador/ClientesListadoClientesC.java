package ec.com.jaapz.controlador;

import java.util.List;

import ec.com.jaapz.modelo.Cliente;
import ec.com.jaapz.modelo.ClienteDAO;
import ec.com.jaapz.util.Context;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientesListadoClientesC {
	ClienteDAO clienteDAO = new ClienteDAO();
	private @FXML TableView<Cliente> tvDatos;
	private @FXML TextField txtBuscar;
	public void initialize(){
		llenarDatos("");
		txtBuscar.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				String cadena = txtBuscar.getText().toUpperCase();
				txtBuscar.setText(cadena);
			}
		});
		tvDatos.setRowFactory(tv -> {
			TableRow<Cliente> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					if(tvDatos.getSelectionModel().getSelectedItem() != null){
						Context.getInstance().setCliente(tvDatos.getSelectionModel().getSelectedItem());
						Context.getInstance().getStageModal().close();
					}
				}
			});
			return row ;
		});
		
	}
	@SuppressWarnings("unchecked")
	void llenarDatos(String busqueda){
		try{
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<Cliente> listaClientes;
			ObservableList<Cliente> datos = FXCollections.observableArrayList();
			listaClientes = clienteDAO.getListaClientesPatron(busqueda);
			datos.setAll(listaClientes);

			//llenar los datos en la tabla
			TableColumn<Cliente, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("idCliente"));
			TableColumn<Cliente, String> nombresColum = new TableColumn<>("Nombres");
			nombresColum.setMinWidth(10);
			nombresColum.setPrefWidth(200);
			nombresColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
			TableColumn<Cliente, String> apellidosColum = new TableColumn<>("Apellidos");
			apellidosColum.setMinWidth(10);
			apellidosColum.setPrefWidth(200);
			apellidosColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
			TableColumn<Cliente, String> generoColum = new TableColumn<>("Género");
			generoColum.setMinWidth(10);
			generoColum.setPrefWidth(100);
			generoColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("genero"));
			TableColumn<Cliente, String> fechColum = new TableColumn<>("Telefono");
			fechColum.setMinWidth(10);
			fechColum.setPrefWidth(100);
			fechColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
			TableColumn<Cliente, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(50);
			estadoColum.setCellValueFactory(new PropertyValueFactory<Cliente, String>("estado"));
			tvDatos.getColumns().addAll(idColum, nombresColum,apellidosColum,generoColum,fechColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void buscarCliente() {
		llenarDatos(txtBuscar.getText());
	}
}
