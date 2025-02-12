package es.cursosprhib.mediosdepago.modelo;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")

@Entity
@Table(name = "tarjetas")
public class Tarjeta implements Comparable<Tarjeta>, Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtarjetas")
	private Integer idTarjeta;
	private String pan;
	private String marca;
	
	@Enumerated(EnumType.STRING)
	private TipoTarjeta tipo;
	
	@Column(name = "anyo_vencimiento")
	private Integer anyoVencimiento;
	
	@Column(name = "mes_vencimiento")
	private Integer mesVencimiento;
	
	@ManyToOne
	@JoinColumn(name = "idcuentas")
	private Cuenta cuenta;

	public Tarjeta() {
	}

	public Tarjeta(String pan, String marca, TipoTarjeta tipo, Integer anyoVencimiento, Integer mesVencimiento, Cuenta cuenta) {
		this.pan = pan;
		this.marca = marca;
		this.tipo = tipo;
		this.anyoVencimiento = anyoVencimiento;
		this.mesVencimiento = mesVencimiento;
		this.cuenta = cuenta;
	}

	public Integer getIdTarjeta() {
		return idTarjeta;
	}

	public void setIdTarjeta(Integer idTarjeta) {
		this.idTarjeta = idTarjeta;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public TipoTarjeta getTipo() {
		return tipo;
	}

	public void setTipo(TipoTarjeta tipo) {
		this.tipo = tipo;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
	public Integer getAnyoVencimiento() {
		return anyoVencimiento;
	}

	public void setAnyoVencimiento(Integer anyoVencimiento) {
		this.anyoVencimiento = anyoVencimiento;
	}

	public Integer getMesVencimiento() {
		return mesVencimiento;
	}

	public void setMesVencimiento(Integer mesVencimiento) {
		this.mesVencimiento = mesVencimiento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idTarjeta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarjeta other = (Tarjeta) obj;
		return Objects.equals(idTarjeta, other.idTarjeta);
	}

	@Override
	public int compareTo(Tarjeta o) {
		return Integer.compare(this.idTarjeta, o.idTarjeta);
	}
}
