package SubsistemaIncidencias;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Test_P9_CrearIncidencia {

	private GestorIncidencias GI;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		GI = new GestorIncidencias();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test_P9_CrearIncidencia: caminos válidos (CP109-CP110, CP114-CP117)")
	void P9_CrearIncidencia_CV() throws Exception {
		// Arrange
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaDate = formato.parse("03/03/2021");
		
		// Act
		assertAll(() -> {
			assertDoesNotThrow(() -> {
				GI.crearIncidencia("Jose maria", "49665809T", "693633448", fechaDate, "descripcion", "Santiago","Suministro");
				}, "(CP109) La creación de la incidencia con valores válidos y caso: "
					+ "(Tipo = 'Suministro') lanza una excepción.");
				},
				() -> {
					assertDoesNotThrow(() -> {
						GI.crearIncidencia("Jose maria", "49665809T", "693633448", fechaDate, "descripcion","Santiago", "Limpieza");
					}, "(CP114) La creación de la incidencia con valores válidos y caso: "
							+ "(Tipo = 'Limpieza') lanza una excepción.");
				},
				() -> {
					assertDoesNotThrow(() -> {
						GI.crearIncidencia("Jose maria", "49665809T", "693633448", fechaDate, "descripcion", "Santiago","Urbanística");
						}, "(CP115) La creación de la incidencia con valores válidos y caso: "
							+ "(Tipo = 'Urbanística') lanza una excepción.");
				},
				() -> {
					assertDoesNotThrow(() -> {
						GI.crearIncidencia("Jose maria", "49665809T", "693633448", fechaDate, "descripcion","Santiago", "Legislativa");
					}, "(CP116) La creación de la incidencia con valores válidos y caso: "
							+ "(Tipo = 'Legislativa') lanza una excepción.");
				},
				() -> {
					assertDoesNotThrow(() -> {
						GI.crearIncidencia("Jose maria", "49665809T", "693633448", fechaDate, "descripcion","Santiago", "Otros");
					}, "(CP117) La creación de la incidencia con valores válidos y caso: "
							+ "(Tipo = 'Otros') lanza una excepción.");
				},
				() -> {
					assertDoesNotThrow(() -> {
						GI.crearIncidencia("Jose maria", "49665809T", "12345678901234567890123456789012345678", fechaDate, "descripcion","Santiago", "Otros");
					}, "(CP110) La creación de la incidencia con valores válidos y caso: "
							+ "(n-1 iteraciones del bucle for) lanza una excepción.");
				}
				);
				
	}
	@Test
	@DisplayName("Test_P9_CrearIncidencia: caminos no válidos (CP095-CP108, CP111-CP113)")
	void P9_CrearIncidencia_CNV() throws Exception {
		// Arrange
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaDate = formato.parse("03/03/2021");
		
		// Act
		assertAll(
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia(null, null, null, null, null, null,null);
					}, "(CP095) La creación de la incidencia con valores no válidos y caso: "
							+ "(ciudadano = null) no lanza una excepción.");
					assertEquals("el ciudadano introducido es null", ex.getMessage(),
							"(CP095) La creación del proceso con valores no válidos y caso: "
					+ "(ciudadano = null) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", null, null, null, null, null,null);
					}, "(CP096) La creación de la incidencia con valores no válidos y caso: "
							+ "(dni = null) no lanza una excepción.");
					assertEquals("el dni introducido es null", ex.getMessage(),
							"(CP096) La creación del proceso con valores no válidos y caso: "
					+ "(dni = null) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", null, null, null, null,null);
					}, "(CP097) La creación de la incidencia con valores no válidos y caso: "
							+ "(telefono = null) no lanza una excepción.");
					assertEquals("el telefono introducido es null", ex.getMessage(),
							"(CP097) La creación del proceso con valores no válidos y caso: "
					+ "(telefono = null) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "123123123", null, null, null,null);
					}, "(CP098) La creación de la incidencia con valores no válidos y caso: "
							+ "(fecha = null) no lanza una excepción.");
					assertEquals("la fecha introducida es null", ex.getMessage(),
							"(CP098) La creación del proceso con valores no válidos y caso: "
					+ "(fecha = null) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "123123123", fechaDate, null, null,null);
					}, "(CP099) La creación de la incidencia con valores no válidos y caso: "
							+ "(descripcion = null) no lanza una excepción.");
					assertEquals("la descripción introducida es null", ex.getMessage(),
							"(CP099) La creación del proceso con valores no válidos y caso: "
					+ "(descripcion = null) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "123123123", fechaDate, "descripcion", null,null);
					}, "(CP100) La creación de la incidencia con valores no válidos y caso: "
							+ "(localizacion = null) no lanza una excepción.");
					assertEquals("la localizacion introducida es null", ex.getMessage(),
							"(CP100) La creación del proceso con valores no válidos y caso: "
					+ "(localizacion = null) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "123123123", fechaDate, "descripcion", "Santiago",null);
					}, "(CP101) La creación de la incidencia con valores no válidos y caso: "
							+ "(tipo = null) no lanza una excepción.");
					assertEquals("el tipo introducido es null", ex.getMessage(),
							"(CP101) La creación del proceso con valores no válidos y caso: "
					+ "(tipo = null) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("JoseMariaIglesiasMartinJorgeTomasFernandez", "49665809T", "123123123", fechaDate, "descripcion", "Santiago","Suministro");
					}, "(CP102) La creación de la incidencia con valores no válidos y caso: "
							+ "(nombre > 40) no lanza una excepción.");
					assertEquals("nombre de ciudadano demasiado largo", ex.getMessage(),
							"(CP102) La creación del proceso con valores no válidos y caso: "
					+ "(nomnbre > 40) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("", "49665809T", "123123123", fechaDate, "descripcion", "Santiago","Suministro");
					}, "(CP103) La creación de la incidencia con valores no válidos y caso: "
							+ "(nombre < 1) no lanza una excepción.");
					assertEquals("nombre de ciudadano demasiado largo", ex.getMessage(),
							"(CP103) La creación del proceso con valores no válidos y caso: "
					+ "(nomnbre < 1) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809E", "123123123", fechaDate, "descripcion", "Santiago","Suministro");
					}, "(CP104) La creación de la incidencia con valores no válidos y caso: "
							+ "(dni no valido) no lanza una excepción.");
					assertEquals("dni introducido no valido", ex.getMessage(),
							"(CP104) La creación del proceso con valores no válidos y caso: "
					+ "(dni no valido) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "123", fechaDate, "descripcion", "Santiago","Suministro");
					}, "(CP105) La creación de la incidencia con valores no válidos y caso: "
							+ "(telefono.length() < 9) no lanza una excepción.");
					assertEquals("longitud de numero de teléfono no válida", ex.getMessage(),
							"(CP105) La creación del proceso con valores no válidos y caso: "
					+ "(telefono.length() < 9) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "00987654321098765432109876543210987654321", fechaDate, "descripcion", "Santiago","Suministro");
					}, "(CP106) La creación de la incidencia con valores no válidos y caso: "
							+ "(telefono.length() > 40) no lanza una excepción.");
					assertEquals("longitud de numero de teléfono no válida", ex.getMessage(),
							"(CP106) La creación del proceso con valores no válidos y caso: "
					+ "(telefono.length() > 40) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "a33998833", fechaDate, "descripcion", "Santiago","Suministro");
					}, "(CP107) La creación de la incidencia con valores no válidos y caso: "
							+ "(1 iteracion del bucle) no lanza una excepción.");
					assertEquals("formato del numero de telefono no válido", ex.getMessage(),
							"(CP107) La creación del proceso con valores no válidos y caso: "
					+ "(1 iteracion del bucle) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "3p3998833", fechaDate, "descripcion", "Santiago","Suministro");
					}, "(CP108) La creación de la incidencia con valores no válidos y caso: "
							+ "(2 iteraciones del bucle for) no lanza una excepción.");
					assertEquals("formato del numero de telefono no válido", ex.getMessage(),
							"(CP108) La creación del proceso con valores no válidos y caso: "
					+ "(2 iteraciones del bucle for) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "693633448", fechaDate, "Descripción de proba que excede o tamaño máximo de palabras aceptado. Asdasdasdasdasdasdasdasdas"
								+ "dasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasda sdasdasdasdasdasdasdasdasdasdasdas"
								+ "dasdasdasdasdasdasdasdasdasdasdasdas dasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd", 
								"Santiago","Suministro");
					}, "(CP111) La creación de la incidencia con valores no válidos y caso: "
							+ "(descripcion.length() > 400) no lanza una excepción.");
					assertEquals("descripcion demasiado larga", ex.getMessage(),
							"(CP111) La creación del proceso con valores no válidos y caso: "
					+ "(descripcion.length() > 400) tiene un mensaje de excepción erróneo.");
					},
				
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "693633448", fechaDate, "descripcion",
								"SantiagodeCompostelaGalciiaEspañaEuropaSantiagodeCompostelaGalciiaEspañaEuropaSantiagodeCompostelaGalciiaEspañaEuropa","Suministro");
					}, "(CP112) La creación de la incidencia con valores no válidos y caso: "
							+ "(localizacion.length() > 100) no lanza una excepción.");
					assertEquals("localizacion demasiado larga", ex.getMessage(),
							"(CP112) La creación del proceso con valores no válidos y caso: "
					+ "(localizacion.length() > 100) tiene un mensaje de excepción erróneo.");
					},
				() -> {
					Exception ex = assertThrows(Exception.class, () -> {
						GI.crearIncidencia("Jose maria", "49665809T", "693633448", fechaDate, "descripcion",
								"Santiago","Vecindario");
					}, "(CP113) La creación de la incidencia con valores no válidos y caso: "
							+ "(tipo = Vecindario) no lanza una excepción.");
					assertEquals("tipo no válido", ex.getMessage(),
							"(CP113) La creación del proceso con valores no válidos y caso: "
					+ "(tipo = Vecindario) tiene un mensaje de excepción erróneo.");
					}
				
				);
				
	}

}