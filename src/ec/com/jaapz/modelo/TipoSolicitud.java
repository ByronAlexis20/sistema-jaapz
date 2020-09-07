package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_rubro database table.
 * 
 */
@Entity
@Table(name="tipo_solicitud")
@NamedQueries({
	@NamedQuery(name="TipoSolicitud.findAll", query="SELECT t FROM TipoSolicitud t where t.estado = 'A'"),
	@NamedQuery(name="TipoSolicitud.findId", query="SELECT t FROM TipoSolicitud t where t.estado = 'A' and t.idTipoSolicitud = :idTipo")
})
public class TipoSolicitud implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_solicitud")
	private Integer idTipoSolicitud;

	private String descripcion;

	private String estado;

	//bi-directional many-to-one association to Rubro
	@OneToMany(mappedBy="tipoSolicitud", cascade = CascadeType.ALL)
	private List<SolInspeccionIn> SolInspeccionIns;

	//bi-directional many-to-one association to Rubro
	@OneToMany(mappedBy="tipoSolicitud", cascade = CascadeType.ALL)
	private List<SolInspeccionRep> SolInspeccionReps;

	public Integer getIdTipoSolicitud() {
		return idTipoSolicitud;
	}

	public void setIdTipoSolicitud(Integer idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<SolInspeccionIn> getSolInspeccionIns() {
		return SolInspeccionIns;
	}

	public void setSolInspeccionIns(List<SolInspeccionIn> solInspeccionIns) {
		SolInspeccionIns = solInspeccionIns;
	}

	public SolInspeccionIn addSolInspeccionIn(SolInspeccionIn solInspeccionIn) {
		getSolInspeccionIns().add(solInspeccionIn);
		solInspeccionIn.setTipoSolicitud(this);
		return solInspeccionIn;
	}

	public SolInspeccionIn removeSolInspeccionIn(SolInspeccionIn solInspeccionIn) {
		getSolInspeccionIns().remove(solInspeccionIn);
		solInspeccionIn.setTipoSolicitud(null);

		return solInspeccionIn;
	}
	
	
	
	public List<SolInspeccionRep> getSolInspeccionReps() {
		return SolInspeccionReps;
	}

	public void setSolInspeccionReps(List<SolInspeccionRep> solInspeccionReps) {
		SolInspeccionReps = solInspeccionReps;
	}
	public SolInspeccionRep addSolInspeccionRep(SolInspeccionRep solInspeccionRep) {
		getSolInspeccionReps().add(solInspeccionRep);
		solInspeccionRep.setTipoSolicitud(this);
		return solInspeccionRep;
	}

	public SolInspeccionRep removeSolInspeccionRep(SolInspeccionRep solInspeccionRep) {
		getSolInspeccionReps().remove(solInspeccionRep);
		solInspeccionRep.setTipoSolicitud(null);

		return solInspeccionRep;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.descripcion;
	}
	
	
}
