package SubsistemaGestionOrdenesTrabajo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import SubsistemaGestionProcesos.GestorProcesos;
import SubsistemaGestionProcesos.Proceso;
import SubsistemaIncidencias.GestorIncidencias;
import SubsistemaIncidencias.Incidencia;


class Test_P1_CrearOT_CrearOT {

	private GestorIncidencias GI;
	private GestorOT GOT;
	private GestorProcesos GP;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		GI = Mockito.mock((GestorIncidencias.class));
		GOT = new GestorOT();
		GP = Mockito.mock(GestorProcesos.class);
		GOT.setGestorProc(GP);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("Pruebas con entradas válidas")
	class EntradasValidas {
		@Test
		@DisplayName("CP048-P1-CrearOT: estado = Sin comenzar")
		void testCP48CrearOT() throws Exception {
			// Arrange
			ArrayList<Incidencia> simIncidencias = new ArrayList<>();
			ArrayList<Proceso> simProcesos= new ArrayList<>();
			simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
			simProcesos.add(new Proceso(1, null, null, null, null, null, null, simIncidencias));
			simProcesos.add(new Proceso(2, null, null, null, null, null, null, simIncidencias));
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			Mockito.when(GP.getProcesos()).thenReturn(simProcesos);
			
			ArrayList<String> personal = new ArrayList<String>();
			personal.add("50047393Z");
			personal.add("91836452M");
			Date fecha = new Date();

			// ATRIBUTOS Integer id, Float coste, String descripcion, Integer
			// duracionEstimada,String empresaResponsable,
			// String estado, Date fechaInicio, String material, ArrayList<String> personal,
			// ArrayList<Float> presupuestos
			OrdenTrabajo otEsperada = new OrdenTrabajo(1, (float) 100.25, "Texto de ejemplo", 20, "responsable",
					"sin comenzar", fecha, "ladrillos", personal, null);

			// Act
			// ATRIBUTOS: String empresaResponsable, String estado, Integer
			// duracionEstimada,
			// Date fechaInicio, String descripcion, Float coste, String material,
			// ArrayList<String> personal,
			// ArrayList<Float> presupuestos, Integer proceso
			OrdenTrabajo ot = GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
					(float) 100.25, "ladrillos", personal, null, 2);

			// Assert
			assertEquals(otEsperada, ot, "No se ha creado la OT deseada");

		}

		@Test
		@DisplayName("CP049-P1-CrearOT: valores válidos(nulls) y estado = Iniciado")
		void testCP49CrearOT() throws Exception {
			// Arrange
			ArrayList<Incidencia> simIncidencias = new ArrayList<>();
			ArrayList<Proceso> simProcesos= new ArrayList<>();
			simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
			simProcesos.add(new Proceso(1, null, null, null, null, null, null, simIncidencias));
			simProcesos.add(new Proceso(2, null, null, null, null, null, null, simIncidencias));
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			Mockito.when(GP.getProcesos()).thenReturn(simProcesos);
			
			
			ArrayList<Float> presupuestos = new ArrayList<Float>();
			presupuestos.add((float) 100.25);
			presupuestos.add((float) 200.50);
			presupuestos.add((float) 300.6);

			OrdenTrabajo otEsperada = new OrdenTrabajo(1, (float) 100.25, "Texto de ejemplo", null, null, "iniciado",
					null, null, null, presupuestos);

			// Act
			OrdenTrabajo ot = GOT.crearOrdenTrabajo(null, "iniciado", null, null, "Texto de ejemplo", null, null, null,
					presupuestos, 2);

			// Assert
			assertEquals(otEsperada, ot, "No se ha creado la OT deseada");

		}

		@Test
		@DisplayName("CP050-P1-CrearOT: valores válidos y estado = Avanzado")
		void testCP50CrearOT() throws Exception {
			// Arrange
			ArrayList<Incidencia> simIncidencias = new ArrayList<>();
			ArrayList<Proceso> simProcesos= new ArrayList<>();
			simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
			simProcesos.add(new Proceso(1, null, null, null, null, null, null, simIncidencias));
			simProcesos.add(new Proceso(2, null, null, null, null, null, null, simIncidencias));
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			Mockito.when(GP.getProcesos()).thenReturn(simProcesos);
			
			ArrayList<String> personal = new ArrayList<String>();
			personal.add("50047393Z");
			personal.add("91836452M");
			Date fecha = new Date();

			OrdenTrabajo otEsperada = new OrdenTrabajo(1, (float) 100.25, "Texto de ejemplo", 20, "responsable",
					"avanzado", fecha, "ladrillos", personal, null);

			// Act
			OrdenTrabajo ot = GOT.crearOrdenTrabajo("responsable", "avanzado", 20, fecha, "Texto de ejemplo",
					(float) 100.25, "ladrillos", personal, null, 2);

			// Assert
			assertEquals(otEsperada, ot, "No se ha creado la OT deseada");

		}

		@Test
		@DisplayName("CP051-P1-CrearOT: estado = Finalizado ")
		void testCP51CrearOT() throws Exception {
			// Arrange
			ArrayList<Incidencia> simIncidencias = new ArrayList<>();
			ArrayList<Proceso> simProcesos= new ArrayList<>();
			simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
			simProcesos.add(new Proceso(1, null, null, null, null, null, null, simIncidencias));
			simProcesos.add(new Proceso(2, null, null, null, null, null, null, simIncidencias));
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			Mockito.when(GP.getProcesos()).thenReturn(simProcesos);
			
			ArrayList<String> personal = new ArrayList<String>();
			personal.add("50047393Z");
			personal.add("91836452M");
			Date fecha = new Date();

			OrdenTrabajo otEsperada = new OrdenTrabajo(1, (float) 100.25, "Texto de ejemplo", 20, "responsable",
					"finalizado", fecha, "ladrillos", personal, null);

			// Act
			OrdenTrabajo ot = GOT.crearOrdenTrabajo("responsable", "finalizado", 20, fecha, "Texto de ejemplo",
					(float) 100.25, "ladrillos", personal, null, 2);

			// Assert
			assertEquals(otEsperada, ot, "No se ha creado la OT deseada");

		}
		
		@Test
		@DisplayName("CP052-P1-CrearOT: estado = null")
		void testCP52CrearOT() throws Exception {
			// Arrange
			ArrayList<Incidencia> simIncidencias = new ArrayList<>();
			ArrayList<Proceso> simProcesos= new ArrayList<>();
			simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
			simProcesos.add(new Proceso(1, null, null, null, null, null, null, simIncidencias));
			simProcesos.add(new Proceso(2, null, null, null, null, null, null, simIncidencias));
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			Mockito.when(GP.getProcesos()).thenReturn(simProcesos);
			
			ArrayList<String> personal = new ArrayList<String>();
			personal.add("50047393Z");
			personal.add("91836452M");
			Date fecha = new Date();

			OrdenTrabajo otEsperada = new OrdenTrabajo(1, (float) 100.25, "Texto de ejemplo", 20, "responsable",
					null, fecha, "ladrillos", personal, null);

			// Act
			OrdenTrabajo ot = GOT.crearOrdenTrabajo("responsable", null, 20, fecha, "Texto de ejemplo",
					(float) 100.25, "ladrillos", personal, null, 2);

			// Assert
			assertEquals(otEsperada, ot, "No se ha creado la OT deseada");

		}
	}
	
	@Nested
	@DisplayName("Pruebas con entradas no válidas")
	class EntradasNoValidas {
		@Test
		@DisplayName("CP053-CP066-P1-CrearOT:  Casos no válidos (CP053 - CP066)")
		void testCP53_CP66CrearOT() throws Exception {
			// Arrange
			ArrayList<Incidencia> simIncidencias = new ArrayList<>();
			ArrayList<Proceso> simProcesos= new ArrayList<>();
			simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
			simProcesos.add(new Proceso(1, null, null, null, null, null, null, simIncidencias));
			simProcesos.add(new Proceso(2, null, null, null, null, null, null, simIncidencias));
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			Mockito.when(GP.getProcesos()).thenReturn(simProcesos);
			
			ArrayList<String> personal = new ArrayList<String>();
			personal.add("50047393Z");
			personal.add("91836452M");
			Date fecha = new Date();
			//fecha anterior a la actual
			GregorianCalendar gc = new GregorianCalendar(2020, 0, 8);
			Date fecha2 = gc.getTime();
			//array.size > 10
			ArrayList<String> personalTam = new ArrayList<String>();
			personalTam.add("50047393Z");
			personalTam.add("91836452M");
			personalTam.add("74261748S");
			personalTam.add("34264938K");
			personalTam.add("72412374W");
			personalTam.add("90626757H");
			personalTam.add("46973629Q");
			personalTam.add("48068308X");
			personalTam.add("03856495Q");
			personalTam.add("33978782P");
			personalTam.add("91584709C");
			//array personal con dni no válido
			ArrayList<String> personalNoValid = new ArrayList<String>();
			personalNoValid.add("50047393Z");
			personalNoValid.add("01234567P");
			//presupuestos.size() < 3
			ArrayList<Float> presupuestosTam = new ArrayList<Float>();
			presupuestosTam.add((float)100.25);
			presupuestosTam.add((float) 200.5);
			//presupuestos con elemento negativo
			ArrayList<Float> presupuestosNeg = new ArrayList<Float>();
			presupuestosNeg.add((float)100.25);
			presupuestosNeg.add((float) 200.5);
			presupuestosNeg.add((float) -200.5);
			
			// Act
			assertAll(
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsableResponsableResponsab", "sin comenzar", 20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personal, null, 2);},
							"(CP053) La creación de la OT con valores no válidos y caso: "
							+ "(empresaResponsable.length > 30) no lanza una excepción.");
						assertEquals("empresaResponsable no valida", e.getMessage(),
								"(CP053) La creación de la OT con valores no válidos y caso: "
								+ "(empresaResponsable.length > 30) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "Realizado", 20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personal, null, 2);},
							"(CP054) La creación de la OT con valores no válidos y caso: "
							+ "(estado no válido) no lanza una excepción.");
						assertEquals("estado no valido", e.getMessage(),
								"(CP054) La creación de la OT con valores no válidos y caso: "
								+ "(estado no válido) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", -20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personal, null, 2);},
							"(CP055) La creación de la OT con valores no válidos y caso: "
							+ "(duracionEstimada <0) no lanza una excepción.");
						assertEquals("duracion estimada no valida", e.getMessage(),
								"(CP055) La creación de la OT con valores no válidos y caso: "
								+ "(duracionEstimada <0) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{
							GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha2, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personal, null, 2);},
							"(CP056) La creación de la OT con valores no válidos y caso: "
							+ "(fechaInicio anterior a la actual) no lanza una excepción.");
						assertEquals("fechaInicio no valida", e.getMessage(),
								"(CP056) La creación de la OT con valores no válidos y caso: "
								+ "(fechaInicio anterior a la actual) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha,
							"abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabca"
							+ "bcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcab"
							+ "cabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc"
							+ "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcab",
								(float) 100.25, "ladrillos", personal, null, 2);},
								"(CP057) La creación de la OT con valores no válidos y caso: "
								+ "descripcion.length > 400) no lanza una excepción.");
							assertEquals("descripcion no valida", e.getMessage(),
									"(CP057) La creación de la OT con valores no válidos y caso: "
									+ "(descripcion.length > 400) tiene un mensaje de excepción erróneo.");
							},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha,null,
							(float) 100.25, "ladrillos", personal, null, 2);},
							"(CP058) La creación de la OT con valores no válidos y caso: "
							+ "(descripcion = null) no lanza una excepción.");
						assertEquals("descripcion no valida", e.getMessage(),
								"(CP058) La creación de la OT con valores no válidos y caso: "
								+ "(descripcion = null) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
							(float) -100.25, "ladrillos", personal, null, 2);},
							"(CP059) La creación de la OT con valores no válidos y caso: "
							+ "(coste <0) no lanza una excepción.");
						assertEquals("coste no valido", e.getMessage(),
								"(CP059) La creación de la OT con valores no válidos y caso: "
								+ "(cost <0) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillosladrillosladrillosladrillosladrillosladrillosladrillosladrillosladrillosladrillosladrillosladrillosladr"
									+ "illosladrillosladrillosladrillosladrill", personal, null, 2);},
							"(CP060) La creación de la OT con valores no válidos y caso: "
							+ "(material.length > 150) no lanza una excepción.");
						assertEquals("material no valido", e.getMessage(),
								"(CP060) La creación de la OT con valores no válidos y caso: "
								+ "(material.length > 150) tiene un mensaje de excepción erróneo.");
					},
					()->{Exception e = assertThrows(Exception.class, ()->{
						GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personalTam, null, 2);},
							"(CP061) La creación de la OT con valores no válidos y caso: "
							+ "(personal.size() > 10) no lanza una excepción.");
						assertEquals("personal no valido", e.getMessage(),
								"(CP061) La creación de la OT con valores no válidos y caso: "
								+ "(personal.size() > 10) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personalNoValid, null, 2);},
							"(CP062) La creación de la OT con valores no válidos y caso: "
							+ "(array personal con algún dni no válido) no lanza una excepción.");
						assertEquals("dni del personal no valido", e.getMessage(),
								"(CP062) La creación de la OT con valores no válidos y caso: "
								+ "(array personal con algún dni no válido) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personal, presupuestosTam, 2);},
							"(CP063) La creación de la OT con valores no válidos y caso: "
							+ "(presupuestos.size() < 3) no lanza una excepción.");
						assertEquals("numero de presupuestos no valido", e.getMessage(),
								"(CP063) La creación de la OT con valores no válidos y caso: "
								+ "(presupuestos.size() < 3) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personal, presupuestosNeg, 2);},
							"(CP064) La creación de la OT con valores no válidos y caso: "
							+ "(presupuestos[i] < 0) no lanza una excepción.");
						assertEquals("presupuesto no valido", e.getMessage(),
								"(CP064) La creación de la OT con valores no válidos y caso: "
								+ "(presupuestos[i] < 0) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personal, null, null);},
							"(CP065) La creación de la OT con valores no válidos y caso: "
							+ "(proceso = null) no lanza una excepción.");
						assertEquals("proceso no existente en el sistema", e.getMessage(),
								"(CP065) La creación de la OT con valores no válidos y caso: "
								+ "(proceso = null) tiene un mensaje de excepción erróneo.");
						},
					()->{Exception e = assertThrows(Exception.class, ()->{GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
							(float) 100.25, "ladrillos", personal, null, -2);},
							"(CP066) La creación de la OT con valores no válidos y caso: "
							+ "(proceso < 0) no lanza una excepción.");
						assertEquals("proceso no existente en el sistema", e.getMessage(),
								"(CP066) La creación de la OT con valores no válidos y caso: "
								+ "(proceso < 0) tiene un mensaje de excepción erróneo.");
						}

					);			

		}
	}
	
	
	@Nested
	@DisplayName("Prueba rendimiento")
	class Rendimiento{
		@Test
		@DisplayName("Test_P1_CrearOT_CrearOT: Prueba de rendimiento (Caso válido CP048)")
		void P1_CrearOT_CrearOT_Rendimiento() {
			// Arrange
			ArrayList<Incidencia> simIncidencias = new ArrayList<>();
			ArrayList<Proceso> simProcesos= new ArrayList<>();
			simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
			simProcesos.add(new Proceso(1, null, null, null, null, null, null, simIncidencias));
			simProcesos.add(new Proceso(2, null, null, null, null, null, null, simIncidencias));
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			Mockito.when(GP.getProcesos()).thenReturn(simProcesos);
			
			ArrayList<String> personal = new ArrayList<String>();
			personal.add("50047393Z");
			personal.add("91836452M");
			Date fecha = new Date();
			
			//Act + Assert
			assertTimeoutPreemptively(Duration.ofSeconds(2), ()->{ GOT.crearOrdenTrabajo("responsable", "sin comenzar", 20, fecha, "Texto de ejemplo",
					(float) 100.25, "ladrillos", personal, null, 2);},
					"(CP048) La creación de la orden de trabajo supera el límite temporal de respuesta requerido.");
		}
	}
	

}
