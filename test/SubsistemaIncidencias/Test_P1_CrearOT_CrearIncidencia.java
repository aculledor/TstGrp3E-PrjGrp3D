package SubsistemaIncidencias;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class Test_P1_CrearOT_CrearIncidencia {

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

	@Nested
	@DisplayName("Funcionalidad")
	class Funcionalidad {

		@Test
		@DisplayName("Test_P1_CrearOT_CrearIncidencia: Casos válidos (CP001 - CP005)")
		void P1_CrearOT_CrearIncidencia_CV() throws Exception {

			assertAll(() -> {
				assertDoesNotThrow(() -> {
					GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo", "El pueblo",
							"Suministro");
				}, "(CP001) La creación de la incidencia con valores válidos y caso: "
						+ "(Tipo = 'Suministro') lanza una excepción.");
			},

					() -> {
						assertDoesNotThrow(() -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Limpieza");
						}, "(CP002) La creación de la incidencia con valores válidos y caso: "
								+ "(Tipo = 'Limpieza') lanza una excepción.");
					},

					() -> {
						assertDoesNotThrow(() -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Urbanística");
						}, "(CP003) La creación de la incidencia con valores válidos y caso: "
								+ "(Tipo = 'Urbanística') lanza una excepción.");
					},

					() -> {
						assertDoesNotThrow(() -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Legislativa");
						}, "(CP004) La creación de la incidencia con valores válidos y caso: "
								+ "(Tipo = 'Legislativa') lanza una excepción.");
					},

					() -> {
						assertDoesNotThrow(() -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP005) La creación de la incidencia con valores válidos y caso: "
								+ "(Tipo = 'Otros') lanza una excepción.");
					});
		}

		@Test
		@DisplayName("Test_P1_CrearOT_CrearIncidencia: Casos no válidos (CP006 - CP0024")
		void P1_CrearOT_CrearIncidencia_CNV() throws Exception {

			assertAll(() -> {
				Exception ex = assertThrows(Exception.class, () -> {
					GI.crearIncidencia(null, "50047393Z", "934729383", new Date(), "Texto de ejemplo", "El pueblo",
							"Otros");
				}, "(CP006) La creación de la incidencia con valores no válidos y caso: "
						+ "(ciudadano = null) no lanza una excepción.");
				assertEquals("el ciudadano introducido es null", ex.getMessage(),
						"(CP006) La creación del proceso con valores no válidos y caso: "
								+ "(ciudadano = null) tiene un mensaje de excepción erróneo.");
			}, () -> {
				Exception ex = assertThrows(Exception.class, () -> {
					GI.crearIncidencia(null, "50047393Z", "934729383", new Date(), "Texto de ejemplo", "El pueblo",
							"Otros");
				}, "(CP006) La creación de la incidencia con valores no válidos y caso: "
						+ "(ciudadano = null) no lanza una excepción.");
				assertEquals("el ciudadano introducido es null", ex.getMessage(),
						"(CP006) La creación del proceso con valores no válidos y caso: "
								+ "(ciudadano = null) tiene un mensaje de excepción erróneo.");
			},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("0876421\\nañM@~#€-_{}’¿?¡!<>(&$=).;^´¨*Ç +", "50047393Z", "934729383",
									new Date(), "Texto de ejemplo", "El pueblo", "Otros");
						}, "(CP007) La creación de la incidencia con valores no válidos y caso: "
								+ "(ciudadano.length > 40) no lanza una excepción.");
						assertEquals("nombre de ciudadano demasiado largo", ex.getMessage(),
								"(CP007) La creación de la incidencia con valores no válidos y caso: "
										+ "(ciudadano.length > 40) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP008) La creación de la incidencia con valores no válidos y caso: "
								+ "(DNI sin letra) no lanza una excepción.");
						assertEquals("dni introducido no valido", ex.getMessage(),
								"(CP008) La creación de la incidencia con valores no válidos y caso: "
										+ "(DNI sin letra) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393ZA", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP009) La creación de la incidencia con valores no válidos y caso: "
								+ "(DNI con más de una letra) no lanza una excepción.");
						assertEquals("dni introducido no valido", ex.getMessage(),
								"(CP009) La creación de la incidencia con valores no válidos y caso: "
										+ "(DNI con más de una letra) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "5004739Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP010) La creación de la incidencia con valores no válidos y caso: "
								+ "(DNI con menos de 8 números) no lanza una excepción.");
						assertEquals("dni introducido no valido", ex.getMessage(),
								"(CP010) La creación de la incidencia con valores no válidos y caso: "
										+ "(DNI con menos de 8 números) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "500473933Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP011) La creación de la incidencia con valores no válidos y caso: "
								+ "(DNI con más de 8 números) no lanza una excepción.");
						assertEquals("dni introducido no valido", ex.getMessage(),
								"(CP011) La creación de la incidencia con valores no válidos y caso: "
										+ "(DNI con más de 8 números) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393L", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP012) La creación de la incidencia con valores no válidos y caso: "
								+ "(DNI con cifrado incorrecto) no lanza una excepción.");
						assertEquals("dni introducido no valido", ex.getMessage(),
								"(CP012) La creación de la incidencia con valores no válidos y caso: "
										+ "(DNI con cifrado incorrecto) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", null, "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP013) La creación de la incidencia con valores no válidos y caso: "
								+ "(DNI = null) no lanza una excepción.");
						assertEquals("el dni introducido es null", ex.getMessage(),
								"(CP013) La creación de la incidencia con valores no válidos y caso: "
										+ "(DNI = null) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "12345678", new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP014) La creación de la incidencia con valores no válidos y caso: "
								+ "(telefono.length < 9) no lanza una excepción.");
						assertEquals("longitud de numero de teléfono no válida", ex.getMessage(),
								"(CP014) La creación de la incidencia con valores no válidos y caso: "
										+ "(telefono.length < 9) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "12345678901234567890123456789012345678901",
									new Date(), "Texto de ejemplo", "El pueblo", "Otros");
						}, "(CP015) La creación de la incidencia con valores no válidos y caso: "
								+ "(telefono.length > 40) no lanza una excepción.");
						assertEquals("longitud de numero de teléfono no válida", ex.getMessage(),
								"(CP015) La creación de la incidencia con valores no válidos y caso: "
										+ "(telefono.length > 40) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "abcabcabc", new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP016) La creación de la incidencia con valores no válidos y caso: "
								+ "(telefono contiene caracteres no numéricos) no lanza una excepción.");
						assertEquals("formato del numero de telefono no válido", ex.getMessage(),
								"(CP016) La creación de la incidencia con valores no válidos y caso: "
										+ "(telefono contiene caracteres no numéricos) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", null, new Date(), "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP017) La creación de la incidencia con valores no válidos y caso: "
								+ "(telefono = null) no lanza una excepción.");
						assertEquals("el telefono introducido es null", ex.getMessage(),
								"(CP017) La creación de la incidencia con valores no válidos y caso: "
										+ "(telefono = null) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", null, "Texto de ejemplo",
									"El pueblo", "Otros");
						}, "(CP018) La creación de la incidencia con valores no válidos y caso: "
								+ "(fechaReporte = null) no lanza una excepción.");
						assertEquals("la fecha introducida es null", ex.getMessage(),
								"(CP018) La creación de la incidencia con valores no válidos y caso: "
										+ "(fechaReporte = null) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), null, "El pueblo",
									"Otros");
						}, "(CP019) La creación de la incidencia con valores no válidos y caso: "
								+ "(descripcion = null) no lanza una excepción.");
						assertEquals("la descripción introducida es null", ex.getMessage(),
								"(CP019) La creación de la incidencia con valores no válidos y caso: "
										+ "(descripcion = null) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(),
									"abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcab",
									"El pueblo", "Otros");
						}, "(CP020) La creación de la incidencia con valores no válidos y caso: "
								+ "(descripcion.length > 400) no lanza una excepción.");
						assertEquals("descripcion demasiado larga", ex.getMessage(),
								"(CP020) La creación de la incidencia con valores no válidos y caso: "
										+ "(descripcion.length > 400) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									null, "Otros");
						}, "(CP021) La creación de la incidencia con valores no válidos y caso: "
								+ "(localizacion = null) no lanza una excepción.");
						assertEquals("la localizacion introducida es null", ex.getMessage(),
								"(CP021) La creación de la incidencia con valores no válidos y caso: "
										+ "(localizacion = null) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									"abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcab",
									"Otros");
						}, "(CP022) La creación de la incidencia con valores no válidos y caso: "
								+ "(localizacion.length > 100) no lanza una excepción.");
						assertEquals("localizacion demasiado larga", ex.getMessage(),
								"(CP022) La creación de la incidencia con valores no válidos y caso: "
										+ "(localizacion.length > 100) tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "esternocleidomastoideo");
						}, "(CP023) La creación de la incidencia con valores no válidos y caso: "
								+ "(tipo = 'esternocleidomastoideo') no lanza una excepción.");
						assertEquals("tipo no válido", ex.getMessage(),
								"(CP023) La creación de la incidencia con valores no válidos y caso: "
										+ "(tipo = 'esternocleidomastoideo') tiene un mensaje de excepción erróneo.");
					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", null);
						}, "(CP024) La creación de la incidencia con valores no válidos y caso: "
								+ "(tipo = null) no lanza una excepción.");
						assertEquals("el tipo introducido es null", ex.getMessage(),
								"(CP024) La creación de la incidencia con valores no válidos y caso: "
										+ "(tipo = null) tiene un mensaje de excepción erróneo.");

					},

					() -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", "esternocleidomastoideo");
						}, "(CP023) La creación de la incidencia con valores no válidos y caso: "
								+ "(tipo = 'esternocleidomastoideo') no lanza una excepción.");
						assertEquals("tipo no válido", ex.getMessage(),
								"(CP023) La creación de la incidencia con valores no válidos y caso: "
										+ "(tipo = 'esternocleidomastoideo') tiene un mensaje de excepción erróneo.");
					}, () -> {
						Exception ex = assertThrows(Exception.class, () -> {
							GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo",
									"El pueblo", null);
						}, "(CP024) La creación de la incidencia con valores no válidos y caso: "
								+ "(tipo = null) no lanza una excepción.");
						assertEquals("el tipo introducido es null", ex.getMessage(),
								"(CP024) La creación de la incidencia con valores no válidos y caso: "
										+ "(tipo = null) tiene un mensaje de excepción erróneo.");
					});
		}

	}

	@Nested
	@DisplayName("Rendimiento")
	class Rendimiento {
		@Test
		@DisplayName("Test_P1_CrearOT_CrearIncidencia: Prueba de rendimiento (Caso válido CP001)")
		void P1_CrearOT_CrearIncidencia_CV() {
			assertTimeout(Duration.ofSeconds(2), () -> {
				GI.crearIncidencia("Antonio", "50047393Z", "934729383", new Date(), "Texto de ejemplo", "El pueblo",
						"Suministro");
			}, "(CP001) La creación del proceso supera el límite temporal de respuesta requerido.");
		}
	}
}