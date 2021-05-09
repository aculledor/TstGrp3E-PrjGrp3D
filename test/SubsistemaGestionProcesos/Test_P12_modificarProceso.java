package SubsistemaGestionProcesos;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import SubsistemaAnalisisEstadisticas.GeneradorEstadisticas;
import SubsistemaGestionOrdenesTrabajo.GestorOT;
import SubsistemaGestionOrdenesTrabajo.OrdenTrabajo;
import SubsistemaIncidencias.GestorIncidencias;
import SubsistemaIncidencias.Incidencia;

class Test_P12_modificarProceso {

	private GestorIncidencias GI;
	private GestorOT GOT;
	private GestorProcesos GP;
	private ArrayList<Integer> incidencias;


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
		incidencias=new ArrayList<>();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("P12 Modificar Procesos")
	class P12{
		private ArrayList<Integer> incidenciasProceso1;
		private ArrayList<Integer> incidenciasProceso2;
		private ArrayList<Integer> ordenesTrabajo;
		private ArrayList<Incidencia> simIncidencias;
		private Proceso proc;


		
		@BeforeEach
		void setUp() throws Exception {
			incidenciasProceso1=new ArrayList<>();
			incidenciasProceso2=new ArrayList<>();
			ordenesTrabajo=new ArrayList<>();
			simIncidencias=new ArrayList<>();
			
			simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));

			incidenciasProceso1.add(1);
			incidenciasProceso2.add(2);
			
			
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			
			GP.crearProceso("Juan", "en construcción", "Empresa  de exemplo", "Limpieza", "Bien", 10f, incidenciasProceso1, ordenesTrabajo);
			GP.crearProceso("Juan", "en construcción", "Empresa exemplo", "Limpieza", "Bien", 10f, incidenciasProceso2, ordenesTrabajo);
		
			proc= new Proceso(1,400f,"Descripción de proba","en construcción","Pepe","Empresa exemplo","Urbanística",simIncidencias);
		}
		

		@DisplayName("CP131-P12")
		@Test
		void testCP131ModificarProceso() throws Exception{
			//Arrange
			proc.setId(2);
			proc.setNombre(null);
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP131) No se lanza una excepción al enviar un proceso con campos nulos");			
		}
		
		@DisplayName("CP132-P12")
		@Test
		void testCP132ModificarProceso() throws Exception{
			//Arrange
	
			//Act
			GP.modificarProceso(proc);
			//Assert
			assertEquals(proc, GP.getProcesos().get(0), "(CP132) No se ha modificado correctamente el proceso");			
		}
		
		@DisplayName("CP133-P12")
		@Test
		void testCP133ModificarProceso() throws Exception{
			//Arrange
			proc.setId(3);
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP133) No se lanza una excepción al enviar un proceso con id erroneo");			
		}
		
		@DisplayName("CP134-P12")
		@Test
		void testCP134ModificarProceso() throws Exception{
			//Arrange
			proc.setNombre("PepeManuelDeTodosLosSantos");
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP134) No se lanza una excepción al enviar un proceso con un nombre no válido");			
		}
		
		@DisplayName("CP135-P12")
		@Test
		void testCP135ModificarProceso() throws Exception{
			//Arrange
			proc.setEstado("Estado erróneo");
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP135) No se lanza una excepción al enviar un proceso con un estado no válido");		
		}
		
		@DisplayName("CP136-P12")
		@Test
		void testCP136ModificarProceso() throws Exception{
			//Arrange
			proc.setEstado("sin comenzar");
			proc.setResponsable("Empresa de exemplo con un nome excesivamente grande");
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP136) No se lanza una excepción al enviar un proceso con un responsable no válido");			
		}

		
		@DisplayName("CP137-P12")
		@Test
		void testCP137ModificarProceso() throws Exception{
			//Arrange
			proc.setEstado("planificado");
			proc.setServicio("Servicio erróneo");
			
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP137) No se lanza una excepción al enviar un proceso con un servicio no válido");			
		}

		
		@DisplayName("CP138-P12")
		@Test
		void testCP138ModificarProceso() throws Exception{
			//Arrange
			proc.setEstado("finalizado");
			proc.setServicio("Suministro");
			proc.setDescripcion("Descripción de proba que excede o tamaño máximo de palabras aceptado. Asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasda sdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdas dasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd");
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP138) No se lanza una excepción al enviar un proceso con una descripción no válida");			
		}

		
		@DisplayName("CP139-P12")
		@Test
		void testCP139ModificarProceso() throws Exception{
			//Arrange
			proc.setServicio("Limpieza");
			proc.setCosteEstimado(-400f);
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP139) No se lanza una excepción al enviar un proceso con un coste no válido");			
		}

		@DisplayName("CP140-P12")
		@Test
		void testCP140ModificarProceso() throws Exception{
			//Arrange
			proc.setServicio("Legalidad");
			//Act
			GP.modificarProceso(proc);

			//Assert
			assertEquals(proc, GP.getProcesos().get(0), "(CP140) No se ha modificado correctamente el proceso");			
		}

		@DisplayName("CP141-P12")
		@Test
		void testCP141ModificarProceso() throws Exception{
			//Arrange
			proc.setServicio("Otros");
			//Act
			GP.modificarProceso(proc);
			//Assert
			assertEquals(proc, GP.getProcesos().get(0), "(CP141) No se ha modificado correctamente el proceso");			
		}

		@DisplayName("CP142-P12")
		@Test
		void testCP142ModificarProceso() throws Exception{
			//Arrange
			proc.setEstado(null);
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP142) No se lanza una excepción al enviar un proceso con campos nulos");			
		}

		@DisplayName("CP143-P12")
		@Test
		void testCP143ModificarProceso() throws Exception{
			//Arrange
			proc.setResponsable(null);
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP143) No se lanza una excepción al enviar un proceso con campos nulos");			
		}

		@DisplayName("CP144-P12")
		@Test
		void testCP144ModificarProceso() throws Exception{
			//Arrange
			proc.setServicio(null);
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP144) No se lanza una excepción al enviar un proceso con campos nulos");			
		}

		@DisplayName("CP145-P12")
		@Test
		void testCP145ModificarProceso() throws Exception{
			//Arrange
			proc.setDescripcion(null);
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP145) No se lanza una excepción al enviar un proceso con campos nulos");			
		}

		@DisplayName("CP146-P12")
		@Test
		void testCP146ModificarProceso() throws Exception{
			//Arrange
			proc.setCosteEstimado(null);
	
			//Act
			
			//Assert
			assertThrows(Exception.class,()->GP.modificarProceso(proc), "(CP146) No se lanza una excepción al enviar un proceso con campos nulos");			
		}
	}
}
