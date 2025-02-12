package es.cursosprhib.mediosdepago.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ModeloTest {

	private EntityManagerFactory emf = getEmf();
	private EntityManager em;

	@BeforeEach
	public void setUp() {
		em = emf.createEntityManager();
		borraTablas();
	}

	private void borraTablas() {
		String jpql;
		jpql = "delete from Movimiento";
		em.getTransaction().begin();
		em.createQuery(jpql).executeUpdate();
		em.getTransaction().commit();
		
		jpql = "delete from TipoMovimiento";
		em.getTransaction().begin();
		em.createQuery(jpql).executeUpdate();
		em.getTransaction().commit();

		jpql = "delete from Tarjeta";
		em.getTransaction().begin();
		em.createQuery(jpql).executeUpdate();
		em.getTransaction().commit();

		jpql = "delete from Extracto";
		em.getTransaction().begin();
		em.createQuery(jpql).executeUpdate();
		em.getTransaction().commit();

		jpql = "delete from Cuenta";
		em.getTransaction().begin();
		em.createQuery(jpql).executeUpdate();
		em.getTransaction().commit();

		jpql = "delete from Cliente";
		em.getTransaction().begin();
		em.createQuery(jpql).executeUpdate();
		em.getTransaction().commit();
	
		jpql = "delete from PersonaFisica";
		em.getTransaction().begin();
		em.createQuery(jpql).executeUpdate();
		em.getTransaction().commit();
	}

	@Test
	public void testClientePersistencia() {
		Cliente cliente = new Cliente("Juan", "Perez", "Gomez", "12345678A", Genero.H, "Madrid", "Madrid", 1);
		em.getTransaction().begin();
		em.persist(cliente);
		em.getTransaction().commit();

		Cliente clientePersistido = em.find(Cliente.class, cliente.getIdPersona());
		assertNotNull(clientePersistido);
		assertEquals("Juan", clientePersistido.getNombre());
		assertEquals("Perez", clientePersistido.getApellido1());
		assertEquals(1, clientePersistido.getNroCliente());
	}

	@Test
	public void testCuentaPersistencia() {
		Cliente cliente = new Cliente("Maria", "Lopez", "Garcia", "87654321B", Genero.M, "Barcelona", "Barcelona", 2);
		Cuenta cuenta = new Cuenta("ES1234567890123456789012", cliente);

		em.getTransaction().begin();
		em.persist(cliente);
		em.persist(cuenta);
		em.getTransaction().commit();

		Cuenta cuentaPersistida = em.find(Cuenta.class, cuenta.getIdCuenta());
		assertNotNull(cuentaPersistida);
		assertEquals("ES1234567890123456789012", cuentaPersistida.getNroCuenta());
		assertEquals(cliente.getIdPersona(), cuentaPersistida.getCliente().getIdPersona());
	}

	@Test
	public void testExtractoPersistencia() {
		Cliente cliente = new Cliente("Ana", "Martinez", "Sanchez", "11223344C", Genero.M, "Valencia", "Valencia", 3);
		Cuenta cuenta = new Cuenta("ES9876543210987654321098", cliente);
		Extracto extracto = new Extracto(2023, 10, cuenta);

		em.getTransaction().begin();
		em.persist(cliente);
		em.persist(cuenta);
		em.persist(extracto);
		em.getTransaction().commit();

		Extracto extractoPersistido = em.find(Extracto.class, extracto.getIdExtracto());
		assertNotNull(extractoPersistido);
		assertEquals(2023, extractoPersistido.getAnyo());
		assertEquals(10, extractoPersistido.getMes());
		assertEquals(cuenta.getIdCuenta(), extractoPersistido.getCuenta().getIdCuenta());
	}

	@Test
	public void testMovimientoPersistencia() {
		Cliente cliente = new Cliente("Carlos", "Gomez", "Fernandez", "55667788D", Genero.H, "Sevilla", "Sevilla", 4);
		Cuenta cuenta = new Cuenta("ES1231231231231231231231", cliente);
		Extracto extracto = new Extracto(2023, 9, cuenta);
		TipoMovimiento tipo = new TipoMovimiento("Compra");
		Tarjeta tarjeta = new Tarjeta("1234567890123456", "VISA", TipoTarjeta.CREDITO, 2033, 3, cuenta);
		Movimiento movimiento = new Movimiento(tipo, tarjeta, new Date(), 100.50, "Proveedor XYZ", extracto);

		em.getTransaction().begin();
		em.persist(cliente);
		em.persist(cuenta);
		em.persist(extracto);
		em.persist(tipo);
		em.persist(tarjeta);
		em.persist(movimiento);
		em.getTransaction().commit();

		Movimiento movimientoPersistido = em.find(Movimiento.class, movimiento.getIdMovimiento());
		assertNotNull(movimientoPersistido);
		assertEquals(100.50, movimientoPersistido.getImporte());
		assertEquals("Proveedor XYZ", movimientoPersistido.getProveedor());
		assertEquals(extracto.getIdExtracto(), movimientoPersistido.getExtracto().getIdExtracto());
	}

	@Test
	public void testRelacionClienteCuenta() {
		Cliente cliente = new Cliente("Laura", "Diaz", "Ruiz", "99887766E", Genero.M, "Bilbao", "Vizcaya", 5);
		Cuenta cuenta1 = new Cuenta("ES1111111111111111111111", cliente);
		Cuenta cuenta2 = new Cuenta("ES2222222222222222222222", cliente);

		em.getTransaction().begin();
		em.persist(cliente);
		em.persist(cuenta1);
		em.persist(cuenta2);
		cliente.addCuenta(cuenta1);
		cliente.addCuenta(cuenta2);
		em.getTransaction().commit();

		Cliente clientePersistido = em.find(Cliente.class, cliente.getIdPersona());
		assertNotNull(clientePersistido);
		assertEquals(2, clientePersistido.getCuentas().size());
	}

	@Test
	public void testRelacionCuentaTarjeta() {
		Cliente cliente = new Cliente("Pedro", "Sanchez", "Lopez", "33445566F", Genero.H, "Zaragoza", "Zaragoza", 6);
		Cuenta cuenta = new Cuenta("ES3333333333333333333333", cliente);
		Tarjeta tarjeta1 = new Tarjeta("1111222233334444", "MasterCard", TipoTarjeta.DEBITO, 2029, 8, cuenta);
		Tarjeta tarjeta2 = new Tarjeta("5555666677778888", "VISA", TipoTarjeta.PREPAGO, 2027, 4, cuenta);

		em.getTransaction().begin();
		em.persist(cliente);
		em.persist(cuenta);
		em.persist(tarjeta1);
		em.persist(tarjeta2);
		cuenta.addTarjeta(tarjeta1);
		cuenta.addTarjeta(tarjeta2);
		em.getTransaction().commit();

		Cuenta cuentaPersistida = em.find(Cuenta.class, cuenta.getIdCuenta());
		assertNotNull(cuentaPersistida);
		assertEquals(2, cuentaPersistida.getTarjetas().size());
	}

	@Test
	public void testRelacionExtractoMovimiento() {
		Cliente cliente = new Cliente("Sofia", "Garcia", "Martinez", "77889900G", Genero.M, "Malaga", "Malaga", 7);
		Cuenta cuenta = new Cuenta("ES4444444444444444444444", cliente);
		Extracto extracto = new Extracto(2023, 8, cuenta);
		TipoMovimiento tipo = new TipoMovimiento("Pago");
		Tarjeta tarjeta = new Tarjeta("9999888877776666", "American Express", TipoTarjeta.CREDITO, 2030, 6, cuenta);
		Movimiento movimiento1 = new Movimiento(tipo, tarjeta, new Date(), 200.00, "Proveedor ABC", extracto);
		Movimiento movimiento2 = new Movimiento(tipo, tarjeta, new Date(), 150.00, "Proveedor DEF", extracto);

		em.getTransaction().begin();
		em.persist(cliente);
		em.persist(cuenta);
		em.persist(extracto);
		em.persist(tipo);
		em.persist(tarjeta);
		em.persist(movimiento1);
		em.persist(movimiento2);
		extracto.addMovimiento(movimiento1);
		extracto.addMovimiento(movimiento2);
		em.getTransaction().commit();

		Extracto extractoPersistido = em.find(Extracto.class, extracto.getIdExtracto());
		assertNotNull(extractoPersistido);
		assertEquals(2, extractoPersistido.getMovimientos().size());
	}
	
	public static EntityManagerFactory getEmf() {
		Map<String, Object> prop = new HashMap<>();

		prop.put("javax.persistence.jdbc.user", "root");
		prop.put("javax.persistence.jdbc.password", "root");
		prop.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
		prop.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3399/mediosdepago_tests");
		prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

		return Persistence.createEntityManagerFactory("mediosdepago",prop);
	}

}