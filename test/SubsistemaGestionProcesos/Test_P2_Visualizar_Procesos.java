package SubsistemaGestionProcesos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import SubsistemaAnalisisEstadisticas.GeneradorEstadisticas;
import SubsistemaGestionOrdenesTrabajo.GestorOT;
import SubsistemaGestionOrdenesTrabajo.OrdenTrabajo;
import SubsistemaIncidencias.GestorIncidencias;
import SubsistemaIncidencias.Incidencia;

class Test_P2_Visualizar_Procesos {
	
	private HashMap<String, Object> filtros;
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
		GI = Mockito.mock(GestorIncidencias.class);
		GOT = Mockito.mock(GestorOT.class);
		GP = new GestorProcesos(GI, GOT);
		filtros = new HashMap<>();
		
		// Creamos las OOTT de prueba
		ArrayList<OrdenTrabajo> simOOTT = new ArrayList<>();
		simOOTT.add(new OrdenTrabajo(1, null, null, null, null, null, null, null, null, null));
		simOOTT.add(new OrdenTrabajo(2, null, null, null, null, null, null, null, null, null));

		// Creamos las incidencias de prueba
		ArrayList<Incidencia> simIncidencias = new ArrayList<>();
		simIncidencias.add(new Incidencia(35, null, null, null, null, null, null, null));
		simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
		
		// Creamos los Mocks necesarios para el funcionamiento de crearProceso() y desvincularOT()
		Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
		Mockito.when(GOT.getOrdenesTrabajo()).thenReturn(simOOTT);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("P2_Pruebas con entradas válidas")
	class EntradasValidas {

		@Test
		@DisplayName("CP065_P2_VisualizarProcesos: Filtros vacíos")
		void testCP65_P2_VisualizarProceso() throws Exception{
			//Arrange
			ArrayList<Integer> auxList = new ArrayList<>();
			auxList.add(35);
			
			ArrayList<Integer> auxList_2 = new ArrayList<>();
			auxList_2.add(1);
			GP.crearProceso("proceso_1", "sin comenzar", "Bloques Cando", "Suministro", "Texto de ejemplo", 100.87f, auxList, auxList_2);

			auxList = new ArrayList<>();
			auxList.add(2);
			GP.crearProceso("proceso_2", "planificado", "USC", "Limpieza", "Texto de ejemplo", 500f, auxList, new ArrayList<>());
			
			//Expected result
			// First process
			ArrayList<Proceso> expectedResult = new ArrayList<Proceso>();
			ArrayList<Incidencia> auxInc = new ArrayList<Incidencia>();
			ArrayList<OrdenTrabajo> auxOT = new ArrayList<OrdenTrabajo>();
			auxInc.add(new Incidencia(35, null, null, null, null, null, null, null));
			auxOT.add(new OrdenTrabajo(1, null, null, null, null, null, null, null, null, null));
			expectedResult.add(new Proceso(1, 100.87f, "Texto de ejemplo", "sin comenzar", "proceso_1", "Bloques Cando", "Suministro", auxInc, auxOT));
			
			//Second process
			auxInc = new ArrayList<Incidencia>();
			auxInc.add(new Incidencia(2, null, null, null, null, null, null, null));
			expectedResult.add(new Proceso(2, 500f, "Texto de ejemplo", "planificado", "proceso_2", "USC", "Limpieza", auxInc, new ArrayList<>()));
			
			
			// Act + Assert
			assertEquals(expectedResult, GP.filtrarProcesos(filtros), "(CP065) No devuelve el set de datos correctamente");
		}

		@Test
		@DisplayName("CP066_P2_VisualizarProcesos: valores válidos")
		void testCP66VisualizarProceso() throws Exception{
			//Arrange
			ArrayList<Integer> auxList = new ArrayList<>();
			auxList.add(35);
			
			ArrayList<Integer> auxList_2 = new ArrayList<>();
			auxList_2.add(1);
			GP.crearProceso("proceso_1", "sin comenzar", "Bloques Cando", "Suministro", "Texto de ejemplo", 100.87f, auxList, auxList_2);

			auxList = new ArrayList<>();
			auxList.add(2);
			GP.crearProceso("proceso_2", "planificado", "USC", "Limpieza", "Texto de ejemplo", 500f, auxList, new ArrayList<>());
			
			//Expected result
			// First process
			ArrayList<Proceso> expectedResult = new ArrayList<Proceso>();
			ArrayList<Incidencia> auxInc = new ArrayList<Incidencia>();
			ArrayList<OrdenTrabajo> auxOT = new ArrayList<OrdenTrabajo>();
			auxInc.add(new Incidencia(35, null, null, null, null, null, null, null));
			auxOT.add(new OrdenTrabajo(1, null, null, null, null, null, null, null, null, null));
			expectedResult.add(new Proceso(1, 100.87f, "Texto de ejemplo", "sin comenzar", "proceso_1", "Bloques Cando", "Suministro", auxInc, auxOT));
			
			// Set filtros
			filtros.put("estado", "sin comenzar");
			filtros.put("responsable", "Bloques Cando"); 
			filtros.put("incidencia", 35);
			filtros.put("fecha", new GregorianCalendar(2021, 5, 9).getTime()); 
			filtros.put("orden de trabajo", 1); 
			
			// Act + Assert
			assertEquals(expectedResult, GP.filtrarProcesos(filtros), "(CP066) No devuelve el set de datos correctamente");
		}

	}
	

	
	@Nested
	@DisplayName("P2_Pruebas con entradas no válidas")
	class EntradasNoValidas {

		@Test
		@DisplayName("CP067_P2_VisualizarProcesos: Filtros null")
		void testCP67_P2_VisualizarProceso() throws Exception{
			// Arrange
			filtros = null;
			
			// Act + Assert
			assertEquals(null, GP.filtrarProcesos(filtros), "(CP067) No devuelve null");
		}

		@Test
		@DisplayName("CP068_P2_VisualizarProcesos: Filtro no válido")
		void testCP68_P2_VisualizarProceso() throws Exception{
			// Arrange
			filtros.put("responsable", "Bloques Cando");
			filtros.put("AGAGAGAG", new GregorianCalendar(2021, 5, 9).getTime());
			
			// Act + Assert
			assertEquals(null, GP.filtrarProcesos(filtros), "(CP068) No devuelve null");
		}

		@Test
		@DisplayName("CP069_P2_VisualizarProcesos: Filtro con clase no válida")
		void testCP69_P2_VisualizarProceso() throws Exception{
			// Arrange
			filtros.put("responsable", "Bloques Cando");
			filtros.put("estado", new GregorianCalendar(2021, 5, 9).getTime());
			
			// Act + Assert
			assertEquals(null, GP.filtrarProcesos(filtros), "(CP069) No devuelve null");
		}
	
	}

}
