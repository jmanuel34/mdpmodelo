package es.cursosprhib.mediosdepago.modelo;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
// comentario
@Entity
@Table(name = "clientes")
public class Cliente extends PersonaFisica {
	
	@Column(name = "nrocliente")
	private Integer nroCliente;
	
	@OneToMany(mappedBy = "cliente")
	private Set<Cuenta> cuentas;
	
	public Cliente() {
	}

	public Cliente(String nombre, String apellido1, String apellido2, String nif, Genero sexo, String municipio, String privincia, Integer nroCliente) {
		super(nombre, apellido1, apellido2, nif, sexo, municipio, privincia);
		this.nroCliente = nroCliente;
	}

	public Integer getNroCliente() {
		return nroCliente;
	}

	public void setNroCliente(Integer nroCliente) {
		this.nroCliente = nroCliente;
	}

	public Set<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(Set<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public void addCuenta(Cuenta cuenta) {
		if (cuentas == null) cuentas = new HashSet<Cuenta>();
		cuentas.add(cuenta);
	}
}
