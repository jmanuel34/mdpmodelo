package es.cursosprhib.mediosdepago.modelo;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@SuppressWarnings("serial")

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas")
public abstract class PersonaFisica implements Serializable, Comparable<PersonaFisica> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idpersonas")
	Integer idPersona;
	String nombre;
	String apellido1;
	String apellido2;
	String nif;
	
	@Enumerated(EnumType.STRING)
	Genero sexo;
	String municipio;
	String provincia;
	
	public PersonaFisica() {
	}

	public PersonaFisica(String nombre, String apellido1, String apellido2, String nif, Genero sexo, String municipio,
			String provincia) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nif = nif;
		this.sexo = sexo;
		this.municipio = municipio;
		this.provincia = provincia;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	
	public String getApellidos() {
		return apellido1 + " " + apellido2;
	}

	public String getApellidosYNombre() {
		return getApellidos() + " " + getNombre();
	}
	
	public String getNombreYApellidos() {
		return nombre + " " + getApellidos();
	}
	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Genero getSexo() {
		return sexo;
	}

	public void setSexo(Genero sexo) {
		this.sexo = sexo;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	@Override
	public int hashCode() {
		return Objects.hash(idPersona);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonaFisica other = (PersonaFisica) obj;
		return Objects.equals(idPersona, other.idPersona);
	}

	@Override
	public String toString() {
		return "PersonaFisica [" + idPersona + ", " + nombre + ", " + apellido1 + ", " + apellido2 + ", " + nif + "]";
	}

	@Override
	public int compareTo(PersonaFisica o) {
		return Integer.compare(this.idPersona, o.idPersona);
	}

	public static <T extends PersonaFisica> Comparator<T> getComparatorAlfabetico() {
		return new Comparator<T>() {
			@Override
			public int compare(T pf1, T pf2) {
				Collator col = Collator.getInstance(new Locale("es_ES"));
				String p1 = pf1.getApellidosYNombre() + pf1.getIdPersona();
				String p2 = pf2.getApellidosYNombre() + pf2.getIdPersona();
				return col.compare(p1, p2);
			}
		};
	}
}
