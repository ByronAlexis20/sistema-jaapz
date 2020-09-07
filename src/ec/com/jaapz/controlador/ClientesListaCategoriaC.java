package ec.com.jaapz.controlador;

import java.util.List;

import ec.com.jaapz.modelo.Categoria;
import ec.com.jaapz.modelo.CategoriaDAO;
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

public class ClientesListaCategoriaC {
	@FXML TableView<Categoria> tvDatos;
	@FXML TextField txtBuscar;
	
	CategoriaDAO categoriaDAO = new CategoriaDAO();
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
				TableRow<Categoria> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
						if(tvDatos.getSelectionModel().getSelectedItem() != null){
							Context.getInstance().setCategoria(tvDatos.getSelectionModel().getSelectedItem());
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
			List<Categoria> listaCategoria;
			ObservableList<Categoria> datos = FXCollections.observableArrayList();
			listaCategoria = categoriaDAO.getListaCategorias(busqueda);
			datos.setAll(listaCategoria);

			//llenar los datos en la tabla
			TableColumn<Categoria, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Categoria,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Categoria, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdCategoria()));
				}
			});
			TableColumn<Categoria, String> descripcionColum = new TableColumn<>("Descripción");
			descripcionColum.setMinWidth(10);
			descripcionColum.setPrefWidth(100);
			descripcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Categoria,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Categoria, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getDescripcion());
				}
			});
			
			TableColumn<Categoria, String> precioColum = new TableColumn<>("Precio M3");
			precioColum.setMinWidth(10);
			precioColum.setPrefWidth(80);
			precioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Categoria,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Categoria, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getValorM3()));
				}
			});
						
			TableColumn<Categoria, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(100);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Categoria,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Categoria, String> param) {
					String estado;
					if(param.getValue().getEstado().equals("A"))
						estado = "Activo";
					else
						estado = "Inactivo";
					return new SimpleObjectProperty<String>(estado);
				}
			});
			
			tvDatos.getColumns().addAll(idColum, descripcionColum,precioColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void buscarCategoria() {
		llenarDatos(txtBuscar.getText());
	}
}
