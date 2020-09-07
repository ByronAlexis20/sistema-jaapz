package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_rubro database table.
 * 
 */
@Entity
@Table(name="tipo_rubro")
@NamedQuery(name="TipoRubro.findAll", query="SELECT t FROM TipoRubro t where t.estado = 'A'")
public class TipoRubro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo")
	private Integer idTipo;

	private String descripcion;

	private String estado;

	//bi-directional many-to-one association to Rubro
	@OneToMany(mappedBy="tipoRubro", cascade = CascadeType.ALL)
	private List<Rubro> rubros;

	public TipoRubro() {
	}

	public Integer getIdTipo() {
		return this.idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Rubro> getRubros() {
		return this.rubros;
	}

	public void setRubros(List<Rubro> rubros) {
		this.rubros = rubros;
	}

	public Rubro addRubro(Rubro rubro) {
		getRubros().add(rubro);
		rubro.setTipoRubro(this);

		return rubro;
	}

	public Rubro removeRubro(Rubro rubro) {
		getRubros().remove(rubro);
		rubro.setTipoRubro(null);

		return rubro;
	}
	@Override
	public String toString() {
		return this.descripcion;
	}
}