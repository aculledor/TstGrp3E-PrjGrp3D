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

class Test_P5 {

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
	@DisplayName("P5 Desvincular Incidencias")
	class p5{
		private ArrayList<Integer> incidenciasProceso;
		private ArrayList<Integer> incidenciasDesvincular;
		private ArrayList<Integer> ordenesTrabajo;
		private ArrayList<Incidencia> resultadoEsperado;
		private ArrayList<Incidencia> simIncidencias;


		
		@BeforeEach
		void setUp() throws Exception {
			incidenciasProceso=new ArrayList<>();
			incidenciasDesvincular=new ArrayList<>();
			ordenesTrabajo=new ArrayList<>();
			resultadoEsperado=new ArrayList<>();
			simIncidencias=new ArrayList<>();
			
			simIncidencias.add(new Incidencia(1, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(2, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(3, null, null, null, null, null, null, null));
			simIncidencias.add(new Incidencia(4, null, null, null, null, null, null, null));
			
			incidenciasProceso.add(1);
			incidenciasProceso.add(2);
			incidenciasProceso.add(4);
			
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			
			GP.crearProceso("Pepe", "en construcción", "Empresa exemplo", "Limpieza", "Bien", 10f, incidenciasProceso, ordenesTrabajo);
		}
		
		@Nested
		@DisplayName("Entradas Válidas ")
		class EntradasValidas{
			
			@DisplayName("CP078-P5: datos correctos")
			@Test
			void testCP078DesvincularIncidencias() throws Exception{
				//Arrange
				incidenciasDesvincular.add(1);
				incidenciasDesvincular.add(2);
				resultadoEsperado.add(simIncidencias.get(3));
				//Act
				
				//Assert
				assertAll(
						()->{assertDoesNotThrow(()->GP.desvincularIncidencias(1,incidenciasDesvincular),"(CP78) La desvinculación falla y devuelve una excepción");},
						()->{assertEquals(resultadoEsperado,GP.getProcesos().get(0).getIncidencias(), "(CP78) La incidencia no se ha desvinculado correctamente");}
						);			
				}
		}

		@Nested
		@DisplayName("Entradas No Válidas")
		class EntradasNoValidas{
			
			@DisplayName("CP079-P5: idProceso = null")
			@Test
			void testCP079DesvincularIncidencias() throws Exception{
				//Arrange
				incidenciasDesvincular.add(1);
				incidenciasDesvincular.add(2);
				//Act
				
				//Assert
				assertThrows(Exception.class, ()->GP.desvincularIncidencias(null,incidenciasDesvincular), "(CP79) La llamada a la función con datos erroneos no lanza una excepción");
					
				}
			
			@DisplayName("CP080-P5: idProceso negativo")
			@Test
			void testCP080DesvincularIncidencias() throws Exception{
				//Arrange
				incidenciasDesvincular.add(1);
				incidenciasDesvincular.add(2);
				//Act
				
				//Assert
				assertThrows(Exception.class, ()->GP.desvincularIncidencias(-1,incidenciasDesvincular), "(CP80) La llamada a la función con datos erroneos no lanza una excepción");
					
				}
			
			@DisplayName("CP081-P5: incidenciasDesvincular vacío")
			@Test
			void testCP081DesvincularIncidencias() throws Exception{
				//Arrange
				
				//Act
				
				//Assert
				assertThrows(Exception.class, ()->GP.desvincularIncidencias(1,incidenciasDesvincular), "(CP81) La llamada a la función con datos erroneos no lanza una excepción");
					
				}
			
			@DisplayName("CP082-P5: incidenciasDesvincular = null")
			@Test
			void testCP082DesvincularIncidencias() throws Exception{
				//Arrange

				//Act
				
				//Assert
				assertThrows(Exception.class, ()->GP.desvincularIncidencias(1,null), "(CP82) La llamada a la función con datos erroneos no lanza una excepción");
					
				}
			
			@DisplayName("CP083-P5: incidenciasDesvincular con algún elemento negativo")
			@Test
			void testCP083DesvincularIncidencias() throws Exception{
				//Arrange
				incidenciasDesvincular.add(1);
				incidenciasDesvincular.add(-2);
				//Act
				
				//Assert
				assertThrows(Exception.class, ()->GP.desvincularIncidencias(1,incidenciasDesvincular), "(CP83) La llamada a la función con datos erroneos no lanza una excepción");
					
				}
		}


		@Nested
		@DisplayName("Rendimiento")
		class Rendimiento{
			@Test
			@DisplayName("Prueba de rendimiento (Caso válido CP78)")
			void P1_CrearOT_CrearProceso_CV() {
				//Arrange
				incidenciasDesvincular.add(1);
				incidenciasDesvincular.add(2);
				resultadoEsperado.add(simIncidencias.get(3));
				
				//Act + Assert
				
				assertTimeoutPreemptively(Duration.ofSeconds(2), ()->{GP.desvincularIncidencias(1,incidenciasDesvincular);},
						"(CP78 - Rendimiento) La desvinculación de la incidencia supera el límite temporal de respuesta requerido.");
			}
		}
	}
}
