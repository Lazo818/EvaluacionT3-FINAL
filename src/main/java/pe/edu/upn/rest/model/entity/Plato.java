package pe.edu.upn.rest.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "plato")
public class Plato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_plato")
	private Integer id_plato;

	@Column(name = "nombre_plato", length = 30)
	private String nombre_plato;
	
	@Column(name = "precio_plato")
	private float precio_plato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo")
	private Tipo tipo;

	@OneToMany(mappedBy = "plato", fetch = FetchType.LAZY)
	private List<Pedido> pedidos;

	public Plato() {
		pedidos = new ArrayList<>();
	}

	

	public Integer getId_plato() {
		return id_plato;
	}



	public void setId_plato(Integer id_plato) {
		this.id_plato = id_plato;
	}



	public String getNombre_plato() {
		return nombre_plato;
	}



	public void setNombre_plato(String nombre_plato) {
		this.nombre_plato = nombre_plato;
	}



	public float getPrecio_plato() {
		return precio_plato;
	}



	public void setPrecio_plato(float precio_plato) {
		this.precio_plato = precio_plato;
	}



	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	


	

}
