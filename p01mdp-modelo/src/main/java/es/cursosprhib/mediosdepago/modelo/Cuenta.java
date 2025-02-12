package es.cursosprhib.mediosdepago.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")

@Entity
@Table(name = "cuentas")
public class Cuenta implements Comparable<Cuenta>, Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcuentas")
	private Integer idCuenta;
	
	@Column(name = "numero")
	private String nroCuenta;
	
	@ManyToOne
	@JoinColumn(name = "idclientes")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "cuenta")
	private Set<Tarjeta> tarjetas;
	
	@OneToMany(mappedBy = "cuenta")
	private Set<Extracto> extractos;
	
	public Cuenta() {  
	}

	public Cuenta(String nroCuenta, Cliente cliente) {
		this(null, nroCuenta, cliente);
	}
	
	public Cuenta(Integer idCuenta, String nroCuenta, Cliente cliente) {
		this.idCuenta = idCuenta;
		this.nroCuenta = nroCuenta;
		this.cliente = cliente;
	}

	public Integer getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Integer idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getNroCuenta() {
		return nroCuenta;
	}

	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(Set<Tarjeta> tarjetas) {
		this.tarjetas = tarjetas;
	}
	
	public void addTarjeta(Tarjeta tarjeta) {
		if(tarjetas == null) tarjetas = new HashSet<>();
		tarjetas.add(tarjeta);
	}

	public Set<Extracto> getExtractos() {
		return extractos;
	}

	public void setExtractos(Set<Extracto> extractos) {
		this.extractos = extractos;
	}
	
	public void addExtracto(Extracto extracto) {
		if(extractos == null) extractos = new TreeSet<>();
		extractos.add(extracto);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCuenta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		return Objects.equals(idCuenta, other.idCuenta);
	}

	@Override
	public String toString() {
		StringBuilder cuenta = new StringBuilder();
		for (int i = 0; i < nroCuenta.length(); i++) {
			if (i % 4 == 0 && i != 0) cuenta.append(" ");
			cuenta.append(nroCuenta.charAt(i));
		}
		return cuenta.toString();
	}

	@Override
	public int compareTo(Cuenta o) {
		return Integer.compare(this.idCuenta, o.idCuenta);
	}
}
