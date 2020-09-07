package ec.com.jaapz.modelo;

import java.util.List;

import javax.persistence.Query;

public class SegPermisoDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<SegPermiso> getPermiso(int idPerfil){
		List<SegPermiso> resultado; 
		Query query = getEntityManager().createNamedQuery("SegPermiso.buscarPermiso");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", idPerfil);
		resultado = (List<SegPermiso>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<SegPermiso> getPermisoPerfil(int idPerfil){
		List<SegPermiso> resultado; 
		Query query = getEntityManager().createNamedQuery("SegPermiso.buscarPermisoPerfil");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", idPerfil);
		resultado = (List<SegPermiso>) query.getResultList();
		return resultado;
	}
	
}
