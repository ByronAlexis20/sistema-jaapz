package ec.com.jaapz.controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ec.com.jaapz.modelo.Cliente;
import ec.com.jaapz.modelo.Instalacion;
import ec.com.jaapz.modelo.InstalacionDAO;
import ec.com.jaapz.modelo.LiquidacionOrden;
import ec.com.jaapz.modelo.LiquidacionOrdenDAO;
import ec.com.jaapz.modelo.Pago;
import ec.com.jaapz.modelo.Planilla;
import ec.com.jaapz.modelo.PlanillaDAO;
import ec.com.jaapz.modelo.SolInspeccionIn;
import ec.com.jaapz.modelo.SolInspeccionInDAO;
import ec.com.jaapz.util.Constantes;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class SolicitudVerProcesoInsC {
	@FXML TableView<Datos> tvDatosInstalacion;
	SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
	
	@FXML Button btnBuscar;
	@FXML TextField txtNombres;
	@FXML TextField txtApellidos;
	@FXML TextField txtDireccion;
	@FXML TextField txtCedula;
	
	ControllerHelper helper = new ControllerHelper();
	SolInspeccionInDAO inspeccionDAO = new SolInspeccionInDAO();
	public void initialize() {
		limpiarDatos();
		btnBuscar.setStyle("-fx-cursor: hand;");
		List<Datos> datos = new ArrayList<Datos>();
		cargarDatosInstalacion(datos);
		
		ObservableList<Datos> datoIns = FXCollections.observableArrayList();
		List<Datos> mostrar = llenarDatosInstalacion();
		datoIns.setAll(mostrar);
		tvDatosInstalacion.setItems(datoIns);

	
	}
	@SuppressWarnings("unchecked")
	private void cargarDatosInstalacion(List<Datos> datosMostrar) {
		try {
			ObservableList<Datos> datos = FXCollections.observableArrayList();
			datos.setAll(datosMostrar);
			TableColumn<Datos, String> descripcionColum = new TableColumn<>("Descripci�n");
			descripcionColum.setMinWidth(10);
			descripcionColum.setPrefWidth(500);
			descripcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Datos,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Datos, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getDescripcion());
				}
			});
			TableColumn<Datos, String> resultadoColum = new TableColumn<>("Resultado");
			resultadoColum.setMinWidth(10);
			resultadoColum.setPrefWidth(200);
			resultadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Datos,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Datos, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getAnalisis());
				}
			});
			tvDatosInstalacion.getColumns().addAll(descripcionColum,resultadoColum);
			tvDatosInstalacion.setItems(datos);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private List<Datos> llenarDatosInstalacion() {
		try {
			List<Datos> lista = new ArrayList<Datos>();
			Datos objeto;
			objeto = new Datos();
			objeto.setId(1);
			objeto.setDescripcion("Solicitud de nuevo medidor");
			lista.add(objeto);
			objeto = new Datos();
			objeto.setId(2);
			objeto.setDescripcion("Asignaci�n de personal de inspecci�n");
			lista.add(objeto);
			objeto = new Datos();
			objeto.setId(3);
			objeto.setDescripcion("Cierre de solicitud de inspecci�n");
			lista.add(objeto);
			objeto = new Datos();
			objeto.setId(4);
			objeto.setDescripcion("Orden de Liquidaci�n");
			lista.add(objeto);
			objeto = new Datos();
			objeto.setId(5);
			objeto.setDescripcion("Cancelar el 60% del valor de instalaci�n");
			lista.add(objeto);
			objeto = new Datos();
			objeto.setId(6);
			objeto.setDescripcion("Asignaci�n del personal de instalaci�n");
			lista.add(objeto);
			objeto = new Datos();
			objeto.setId(7);
			objeto.setDescripcion("Orden de retiro de materiales de bodega");
			lista.add(objeto);
			objeto = new Datos();
			objeto.setId(8);
			objeto.setDescripcion("Cierre de instalaci�n");
			lista.add(objeto);
			return lista;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public void buscarCliente(){
		try{
			helper.abrirPantallaModal("/solicitudes/SolicitudesNoAtendidas.fxml","Listado de solicitudes no atendidas", Context.getInstance().getStage());
			if (Context.getInstance().getInspeccion() != null) {
				limpiarDatos();
				Cliente datoSeleccionado = Context.getInstance().getInspeccion().getCliente();
				txtNombres.setText(datoSeleccionado.getNombre());
				txtApellidos.setText(datoSeleccionado.getApellido());
				txtDireccion.setText(datoSeleccionado.getDireccion());
				txtCedula.setText(datoSeleccionado.getCedula());
				cargarResultadosInstalacion(Context.getInstance().getInspeccion());
				Context.getInstance().setInspeccion(null);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	private void cargarResultadosInstalacion(SolInspeccionIn inspeccion) {
		try {
			LiquidacionOrdenDAO ordenDAO = new LiquidacionOrdenDAO();
			PlanillaDAO planillaDAO = new PlanillaDAO();
			InstalacionDAO instalacionDAO = new InstalacionDAO();
			for(Datos dato : tvDatosInstalacion.getItems()) {
				if(dato.getId() == 8) {
					List<Instalacion> lista = instalacionDAO.getBuscarPorSolicitud(inspeccion.getIdSolInspeccion());
					if(lista.size() > 0) {
						if(lista.get(0).getEstadoInstalacion() != null) {
							if(lista.get(0).getEstadoInstalacion().equals(Constantes.EST_INSPECCION_PENDIENTE)) 
								dato.setAnalisis("A�N NO PROCESADO");
							else
								dato.setAnalisis("REALIZADO");
						}else
							dato.setAnalisis("A�N NO PROCESADO");
					}
					else
						dato.setAnalisis("A�N NO PROCESADO");
				}else if(dato.getId() == 7) {
					List<Instalacion> lista = instalacionDAO.getBuscarPorSolicitud(inspeccion.getIdSolInspeccion());
					if(lista.size() > 0) 
						dato.setAnalisis("SE HA REALIZADO EL RETIRO DE MATERIALES");
					else
						dato.setAnalisis("A�N NO PROCESADO");
				}else if(dato.getId() == 6) {
					List<LiquidacionOrden> lista = ordenDAO.getBuscarPorSolicitud(inspeccion.getIdSolInspeccion());
					if(lista.size() > 0) {
						if(lista.get(0).getUsuarioInstalacion() != null)
							dato.setAnalisis("SE HA REALIZADO LA ASIGNACI�N");
						else
							dato.setAnalisis("A�N NO PROCESADO");
					}
					else
						dato.setAnalisis("A�N NO PROCESADO");
				}else if(dato.getId() == 5) {
					List<Planilla> listaPlanilla = planillaDAO.getPlanillaSolicitud(inspeccion.getIdSolInspeccion());
					double porcentaje = 0.0;
					double valorPagado = 0.0;
					for(Planilla pl : listaPlanilla) {
						if(pl.getIdentInstalacion().equals(Constantes.IDENT_INSTALACION)) {
							porcentaje = pl.getTotalPagar() * 0.6;//60 % del total a pagar
							for(Pago pag : pl.getPagos()) {
								if(pag.getEstado().equals(Constantes.ESTADO_ACTIVO))
									valorPagado = valorPagado + pag.getValor();
							}
						}
						if(valorPagado >= porcentaje)
							dato.setAnalisis("SE HA REALIZADO EL PAGO");
						else
							dato.setAnalisis("A�N NO PROCESADO");
						
					}
					if(listaPlanilla.size() == 0)
						dato.setAnalisis("A�N NO PROCESADO");
				}else if(dato.getId() == 4) {
					List<LiquidacionOrden> lista = ordenDAO.getBuscarPorSolicitud(inspeccion.getIdSolInspeccion());
					if(lista.size() > 0)
						dato.setAnalisis("ORDEN DE LIQUIDACI�N GENERADA");
					else
						dato.setAnalisis("A�N NO PROCESADO");
					
				}else if(dato.getId() == 3) {
					if(inspeccion.getEstadoInspeccion().equals(Constantes.EST_INSPECCION_PENDIENTE))
						dato.setAnalisis("A�N NO PROCESADO");
					else {
						String resultado = "REALIZADO";
						if(inspeccion.getFactibilidad() != null) {
							if(inspeccion.getFactibilidad().equals(Constantes.EST_FACTIBLE))
								resultado = resultado + " - FACTIBLE";
							else
								resultado = resultado + " - NO FACTIBLE";	
						}
						dato.setAnalisis(resultado);
					}
				}else if(dato.getId() == 2) {
					if(inspeccion.getIdUsuEncargado() != null)
						dato.setAnalisis("REALIZADO");
					else
						dato.setAnalisis("A�N NO PROCESADO");
				}else if(dato.getId() == 1) {
					dato.setAnalisis("REALIZADO");
				}
			}
			tvDatosInstalacion.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void limpiarDatos() {
		tvDatosInstalacion.getItems().clear();
		ObservableList<Datos> dato = FXCollections.observableArrayList();
		List<Datos> mostrar = llenarDatosInstalacion();
		dato.setAll(mostrar);
		tvDatosInstalacion.setItems(dato);
		tvDatosInstalacion.refresh();
		
		txtApellidos.setText("");
		txtNombres.setText("");
		txtDireccion.setText("");	
		txtCedula.setText("");
	}
	public class Datos {
		private Integer id;
		private String descripcion;
		private String analisis;
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getAnalisis() {
			return analisis;
		}
		public void setAnalisis(String analisis) {
			this.analisis = analisis;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		
	}
}