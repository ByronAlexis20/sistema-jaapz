package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class ResponsableLecturaDAO extends ClaseDAO {
	@SuppressWarnings("unchecked")
	public List<ResponsableLectura> getListaResponsableApertura(Integer idApertura,Integer idUsuario){
		List<ResponsableLectura> resultado = new ArrayList<ResponsableLectura>();
		Query query = getEntityManager().createNamedQuery("ResponsableLectura.buscarPorResponsable");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idApertura",idApertura);
		query.setParameter("idUsuario",idUsuario);
		resultado = (List<ResponsableLectura>) query.getResultList();
		return resultado;
	}
}
