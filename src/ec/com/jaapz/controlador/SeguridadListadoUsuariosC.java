package ec.com.jaapz.controlador;

import java.util.List;

import ec.com.jaapz.modelo.SegUsuario;
import ec.com.jaapz.modelo.SegUsuarioDAO;
import ec.com.jaapz.util.Context;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SeguridadListadoUsuariosC {
	private @FXML TableView<SegUsuario> tvDatos;
	private @FXML TextField txtBuscarUsuario;
	
	public void initialize(){
		llenarDatos();
		tvDatos.setRowFactory(tv -> {
			TableRow<SegUsuario> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					if(tvDatos.getSelectionModel().getSelectedItem() != null){
						Context.getInstance().setUsuarios(tvDatos.getSelectionModel().getSelectedItem());
						Context.getInstance().getStageModal().close();
					}
				}
			});
			return row ;
		});
	}
	@SuppressWarnings("unchecked")
	void llenarDatos(){
		try{
			tvDatos.getColumns().clear();
			SegUsuarioDAO usuarioDAO = new SegUsuarioDAO();
			List<SegUsuario> listaUsuarios;
			listaUsuarios = usuarioDAO.getListaUsuarios();
			ObservableList<SegUsuario> datos = FXCollections.observableArrayList();
			datos.setAll(listaUsuarios);

			//llenar los datos en la tabla
			TableColumn<SegUsuario, String> idColum = new TableColumn<>("Codigo");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(60);
			idColum.setCellValueFactory(new PropertyValueFactory<SegUsuario, String>("idUsuario"));
			TableColumn<SegUsuario, String> nombresColum = new TableColumn<>("Nombres");
			nombresColum.setMinWidth(10);
			nombresColum.setPrefWidth(200);
			nombresColum.setCellValueFactory(new PropertyValueFactory<SegUsuario, String>("nombre"));
			TableColumn<SegUsuario, String> apellidosColum = new TableColumn<>("Apellidos");
			apellidosColum.setMinWidth(10);
			apellidosColum.setPrefWidth(200);
			apellidosColum.setCellValueFactory(new PropertyValueFactory<SegUsuario, String>("apellido"));
			TableColumn<SegUsuario, String> generoColum = new TableColumn<>("Telefono");
			generoColum.setMinWidth(10);
			generoColum.setPrefWidth(90);
			generoColum.setCellValueFactory(new PropertyValueFactory<SegUsuario, String>("telefono"));
			TableColumn<SegUsuario, String> fechColum = new TableColumn<>("Cargo");
			fechColum.setMinWidth(10);
			fechColum.setPrefWidth(90);
			fechColum.setCellValueFactory(new PropertyValueFactory<SegUsuario, String>("cargo"));
			TableColumn<SegUsuario, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(50);
			estadoColum.setCellValueFactory(new PropertyValueFactory<SegUsuario, String>("estado"));

			tvDatos.getColumns().addAll(idColum, nombresColum,apellidosColum,generoColum,fechColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}