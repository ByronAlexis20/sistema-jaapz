package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the instalacion_detalle database table.
 * 
 */
@Entity
@Table(name="instalacion_detalle")
@NamedQuery(name="InstalacionDetalle.findAll", query="SELECT i FROM InstalacionDetalle i")
public class InstalacionDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_instalacion_det")
	private Integer idInstalacionDet;

	private Integer cantidad;

	private String estado;

	private double precio;

	private double subtotal;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to Instalacion
	@ManyToOne
	@JoinColumn(name="id_instalacion")
	private Instalacion instalacion;

	//bi-directional many-to-one association to Rubro
	@ManyToOne
	@JoinColumn(name="id_rubro")
	private Rubro rubro;

	public InstalacionDetalle() {
	}

	public Integer getIdInstalacionDet() {
		return this.idInstalacionDet;
	}

	public void setIdInstalacionDet(Integer idInstalacionDet) {
		this.idInstalacionDet = idInstalacionDet;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public Instalacion getInstalacion() {
		return this.instalacion;
	}

	public void setInstalacion(Instalacion instalacion) {
		this.instalacion = instalacion;
	}

	public Rubro getRubro() {
		return this.rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

}