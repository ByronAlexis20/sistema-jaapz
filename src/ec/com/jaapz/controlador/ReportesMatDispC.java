package ec.com.jaapz.controlador;

import ec.com.jaapz.modelo.RubroDAO;
import ec.com.jaapz.util.PrintReport;

public class ReportesMatDispC {
	RubroDAO rubroDAO = new RubroDAO();
	public void initialize(){
		try {
			PrintReport pr = new PrintReport();
			pr.crearReporte("/recursos/informes/lista_materiales_disponibles.jasper", rubroDAO, null);
			pr.showReport("Listado");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}