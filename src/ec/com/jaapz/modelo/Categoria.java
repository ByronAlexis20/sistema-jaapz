package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categoria database table.
 * 
 */
@Entity
@Table(name="categoria")
@NamedQueries({
	@NamedQuery(name="Categoria.findAll", query="SELECT c FROM Categoria c order by c.idCategoria asc"),
	@NamedQuery(name="Categoria.findCategorias", query="SELECT c FROM Categoria c where c.estado = 'A' "
			+ "and lower(c.descripcion) like lower(:patron) order by c.idCategoria asc"),
	@NamedQuery(name="Categoria.findCatNormal", query="SELECT c FROM Categoria c where c.idCategoria = 1"),
	@NamedQuery(name="Categoria.buscarCategoria", query="SELECT c FROM Categoria c where c.descripcion like :patron"),
	@NamedQuery(name="Categoria.buscarCategoriaNombre", query="SELECT c FROM Categoria c where lower(c.descripcion) like lower(:patron) and c.estado  = 'A'")
})
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_categoria")
	private Integer idCategoria;

	private String descripcion;

	private String estado;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	@Column(name="valor_iva")
	private double valorIva;

	@Column(name="valor_m3")
	private double valorM3;

	//bi-directional many-to-one association to CuentaCliente
	@OneToMany(mappedBy="categoria", cascade = CascadeType.ALL)
	private List<CuentaCliente> cuentaClientes;

	public Categoria() {
	}

	public Integer getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
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

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public double getValorIva() {
		return this.valorIva;
	}

	public void setValorIva(double valorIva) {
		this.valorIva = valorIva;
	}

	public double getValorM3() {
		return this.valorM3;
	}

	public void setValorM3(double valorM3) {
		this.valorM3 = valorM3;
	}

	public List<CuentaCliente> getCuentaClientes() {
		return this.cuentaClientes;
	}

	public void setCuentaClientes(List<CuentaCliente> cuentaClientes) {
		this.cuentaClientes = cuentaClientes;
	}

	public CuentaCliente addCuentaCliente(CuentaCliente cuentaCliente) {
		getCuentaClientes().add(cuentaCliente);
		cuentaCliente.setCategoria(this);

		return cuentaCliente;
	}

	public CuentaCliente removeCuentaCliente(CuentaCliente cuentaCliente) {
		getCuentaClientes().remove(cuentaCliente);
		cuentaCliente.setCategoria(null);

		return cuentaCliente;
	}
	@Override
	public String toString() {
		return this.descripcion;
	}
}