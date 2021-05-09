package SubsistemaGestionProcesos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import SubsistemaGestionOrdenesTrabajo.GestorOT;
import SubsistemaGestionOrdenesTrabajo.OrdenTrabajo;
import SubsistemaIncidencias.GestorIncidencias;
import SubsistemaIncidencias.Incidencia;

class Test_P13_desvincularOT {
	
	private ArrayList<Integer> ordenesTrabajo;
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
		// Definimos la variables necesarias para GP y los parametros de desvincularOT
		ordenesTrabajo = new ArrayList<>();
		GI = Mockito.mock(GestorIncidencias.class);
		GOT = Mockito.mock(GestorOT.class);
		GP = new GestorProcesos(GI, GOT);
		
		// Creamos las OOTT de prueba
		ArrayList<OrdenTrabajo> simOOTT = new ArrayList<>();
		simOOTT.add(new OrdenTrabajo(1, null, null, null, null, null, null, null, null, null));
		simOOTT.add(new OrdenTrabajo(2, null, null, null, null, null, null, null, null, null));
		simOOTT.add(new OrdenTrabajo(3, null, null, null, null, null, null, null, null, null));
		simOOTT.add(new OrdenTrabajo(4, null, null, null, null, null, null, null, null, null));
		simOOTT.add(new OrdenTrabajo(10, null, null, null, null, null, null, null, null, null));

		// Creamos las incidencias de prueba
		ArrayList<Incidencia> simIncidencias = new ArrayList<>();
		simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
		simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
		
		// Creamos los Mocks necesarios para el funcionamiento de crearProceso() y desvincularOT()
		Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
		Mockito.when(GOT.getOrdenesTrabajo()).thenReturn(simOOTT);
		
		// Creamos los arrays con los ids de las incidencias y OTs necesarios para crear los dos procesos
		ArrayList<Integer> incidenciasP1 = new ArrayList<>(); 	incidenciasP1.add(1);
		ArrayList<Integer> incidenciasP2 = new ArrayList<>(); 	incidenciasP2.add(2);
		ArrayList<Integer> oottP1 = new ArrayList<>();		 	oottP1.add(1); oottP1.add(2);
		ArrayList<Integer> oottP2 = new ArrayList<>();		 	oottP2.add(3); oottP2.add(4);
		
		// Creamos P1 y P2
		GP.crearProceso("Pepe", "en construcción", "Empresa exemplo", "Limpieza", "Bien", 10f, incidenciasP1, oottP1);
		GP.crearProceso("Lara", "en construcción", "Empresa exemplo 2", "Limpieza", "Bien", 10f, incidenciasP2, oottP2);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test_P13_desvincularOT: Prueba de caja blanca CP147")
	void CP147_P13_desvincularOT() {
		// Arrange
		ordenesTrabajo.add(1);
		ordenesTrabajo.add(2);
		
		// Act + Assert
		assertThrows(Exception.class, ()->GP.desvincularOT(10, ordenesTrabajo),"(CP147) No lanza excepción al recibir un proceso no almacenado");
	}

	@Test
	@DisplayName("Test_P13_desvincularOT: Prueba de caja blanca CP148")
	void CP148_P13_desvincularOT() throws Exception{
		// Arrange
		ordenesTrabajo.add(1);
		ordenesTrabajo.add(2);
		
		// Act
		GP.desvincularOT(1, ordenesTrabajo);
		
		// Assert
		assertTrue(GP.getProcesos().get(0).getOrdenesTrabajo().isEmpty(), "(CP148) No se eliminan las dos OT del proceso");
	}

	@Test
	@DisplayName("Test_P13_desvincularOT: Prueba de caja blanca CP149")
	void CP149_P13_desvincularOT(){
		// Arrange
		ordenesTrabajo.add(10);
		
		// Act + Assert
		assertThrows(Exception.class, ()->GP.desvincularOT(1, ordenesTrabajo),"(CP149) No lanza excepción al recibir OOTT que no pertenecen al proceso");
		
	}

	@Test
	@DisplayName("Test_P13_desvincularOT: Prueba de caja blanca CP150")
	void CP150_P13_desvincularOT() throws Exception{
		// Arrange
		ordenesTrabajo.add(1);
		
		// Act
		GP.desvincularOT(1, ordenesTrabajo);
		
		// Assert
		assertTrue(GP.getProcesos().get(0).getOrdenesTrabajo().get(0).getId() == 2, "(CP150) El proceso no contiene únicamente OT2");
	}

}
