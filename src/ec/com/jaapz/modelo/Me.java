package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the mes database table.
 * 
 */
@Entity
@Table(name="mes")
@NamedQueries({
	@NamedQuery(name="Me.findAll", query="SELECT m FROM Me m where m.estado = 'A' order by m.idMes asc"),
	@NamedQuery(name="Me.buscarMes", query="SELECT m FROM Me m where m.estado = 'A' and m.idMes = :patron")
})
public class Me implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_mes")
	private Integer idMes;

	private String descripcion;

	private String estado;

	//bi-directional many-to-one association to AperturaLectura
	@OneToMany(mappedBy="me", cascade = CascadeType.ALL)
	private List<AperturaLectura> aperturaLecturas;

	public Me() {
	}

	public Integer getIdMes() {
		return this.idMes;
	}

	public void setIdMes(Integer idMes) {
		this.idMes = idMes;
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

	public List<AperturaLectura> getAperturaLecturas() {
		return this.aperturaLecturas;
	}

	public void setAperturaLecturas(List<AperturaLectura> aperturaLecturas) {
		this.aperturaLecturas = aperturaLecturas;
	}

	public AperturaLectura addAperturaLectura(AperturaLectura aperturaLectura) {
		getAperturaLecturas().add(aperturaLectura);
		aperturaLectura.setMe(this);

		return aperturaLectura;
	}

	public AperturaLectura removeAperturaLectura(AperturaLectura aperturaLectura) {
		getAperturaLecturas().remove(aperturaLectura);
		aperturaLectura.setMe(null);

		return aperturaLectura;
	}
	@Override
	public String toString() {
		return this.descripcion;
	}
}