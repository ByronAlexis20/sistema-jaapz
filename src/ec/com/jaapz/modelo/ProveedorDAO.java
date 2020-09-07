package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class ProveedorDAO extends ClaseDAO{
	//para recuperar proveedor
	@SuppressWarnings("unchecked")
	public List<Proveedor> getRecuperaProveedor(String ruc){
		List<Proveedor> resultado = new ArrayList<Proveedor>();
		Query query = getEntityManager().createNamedQuery("Proveedor.recuperaProveedor");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("ruc", ruc);
		resultado = (List<Proveedor>) query.getResultList();
		return resultado;
	}
}