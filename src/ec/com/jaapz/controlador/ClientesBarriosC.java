package ec.com.jaapz.controlador;

import java.util.List;

import ec.com.jaapz.modelo.Barrio;
import ec.com.jaapz.modelo.BarrioDAO;
import ec.com.jaapz.util.Context;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class ClientesBarriosC {
	@FXML TableView<Barrio> tvDatos;
	@FXML TextField txtBuscar;
	
	BarrioDAO barrioDAO = new BarrioDAO();
	public void initialize() {
		try {
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
				TableRow<Barrio> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
						if(tvDatos.getSelectionModel().getSelectedItem() != null){
							Context.getInstance().setBarrio(tvDatos.getSelectionModel().getSelectedItem());
							Context.getInstance().getStageModal().close();
						}
					}
				});
				return row ;
			});
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	void llenarDatos(String busqueda){
		try{
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<Barrio> listaBarrios;
			ObservableList<Barrio> datos = FXCollections.observableArrayList();
			listaBarrios = barrioDAO.getListaBarriosPatron(busqueda);
			datos.setAll(listaBarrios);

			//llenar los datos en la tabla
			TableColumn<Barrio, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Barrio,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Barrio, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdBarrio()));
				}
			});
			TableColumn<Barrio, String> nombreColum = new TableColumn<>("Nombre");
			nombreColum.setMinWidth(10);
			nombreColum.setPrefWidth(150);
			nombreColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Barrio,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Barrio, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getNombre());
				}
			});
			
			TableColumn<Barrio, String> descripcionColum = new TableColumn<>("Descripción");
			descripcionColum.setMinWidth(10);
			descripcionColum.setPrefWidth(150);
			descripcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Barrio,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Barrio, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getDescripcion());
				}
			});
						
			TableColumn<Barrio, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(100);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Barrio,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Barrio, String> param) {
					String estado;
					if(param.getValue().getEstado().equals("A"))
						estado = "Activo";
					else
						estado = "Inactivo";
					return new SimpleObjectProperty<String>(estado);
				}
			});
			
			tvDatos.getColumns().addAll(idColum, nombreColum,descripcionColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void buscarBarrio() {
		llenarDatos(txtBuscar.getText());
	}
}
