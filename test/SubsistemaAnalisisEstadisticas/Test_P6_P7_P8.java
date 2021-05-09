package SubsistemaAnalisisEstadisticas;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
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

import SubsistemaGestionOrdenesTrabajo.GestorOT;
import SubsistemaGestionOrdenesTrabajo.OrdenTrabajo;
import SubsistemaGestionProcesos.GestorProcesos;
import SubsistemaIncidencias.GestorIncidencias;
import SubsistemaIncidencias.Incidencia;

class Test_P6_P7_P8 {
	
	private GestorIncidencias GI;
	private GestorOT GOT;
	private GestorProcesos GP;
	private GeneradorEstadisticas GE;

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
		GP = Mockito.mock(GestorProcesos.class);
		GE = new GeneradorEstadisticas(GOT, GP, GI);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("P6_ContarTotalIncidencias")
	class P6_ContarTotalIncidencias{
		@Test
		@DisplayName("CP084_P6_ContarTotalIncidencias: sin argumentos")
		void testCP84ContarTotalIncidencias() throws Exception{
			// Arrange
			ArrayList<Incidencia> simIncidencias = new ArrayList<>();
			for(int i = 1; i <= 10; i++) {
				simIncidencias.add(new Incidencia(i, null, null, null, null, null, null, null));
			}
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			
			// Act + Assert
			assertSame(simIncidencias.size(), GE.contarIncidencias(), "(CP84) El tamaño devuelto no se corresponde al tamaño de los datos almacenados");
			
		}
		
		@Test
		@DisplayName("CP084_P6_Rendimiento: sin argumentos y tiempo < 2s")
		void testCP84Rendimiento() throws Exception{
			// Arrange
			ArrayList<Incidencia> simIncidencias = new ArrayList<>();
			for(int i = 1; i <= 10; i++) {
				simIncidencias.add(new Incidencia(i, null, null, null, null, null, null, null));
			}
			Mockito.when(GI.getIncidencias()).thenReturn(simIncidencias);
			
			// Act + Assert
			assertTimeoutPreemptively(Duration.ofSeconds(2), ()->GE.contarIncidencias(), "(CP84) La realización del proceso supera el límite temporal de respuesta requerido");
			
		}
	}

	@Nested
	@DisplayName("P7_ObtenerMediaCosteOOTT")
	class P7_ObtenerMediaCosteOOTT{

		private ArrayList<OrdenTrabajo> simOOTT;
		private Float total;
		private HashMap<String, Object> filtro;
		
		@BeforeEach
		void setUp() throws Exception {
			simOOTT = new ArrayList<>();
			total = 0f;
			for(int i = 1; i <= 10; i++) {
				total += i;
				simOOTT.add(new OrdenTrabajo(i, i+0f, null, null, "USC", null, null, null, null, null));
			}
			total = total / 10;
			filtro = new HashMap<String, Object>();
			filtro.put("empresa", "USC");
			Mockito.when(GOT.filtrarOT(filtro)).thenReturn(simOOTT);
		}
		
		@Nested
		@DisplayName("P7_Pruebas con entradas válidas")
		class EntradasValidas {

			@Test
			@DisplayName("CP085_P7_ObtenerMediaCosteOOTT: entradas válidas")
			void testCP85ObtenerMediaCosteOOTT() throws Exception{
				// Act + Assert
				assertEquals(total, GE.mediaCosteOT("USC"), "(CP85) El sumatorio del coste de las OTs no es correcto");
				
			}

			@Test
			@DisplayName("CP085_P7_Rendimiento: entradas válidas y tiempo < 2s")
			void testP7Rendimiento() throws Exception{
				// Act + Assert
				assertTimeoutPreemptively(Duration.ofSeconds(2), ()->GE.mediaCosteOT("USC"), "(CP85) La realización del proceso supera el límite temporal de respuesta requerido");
				
			}
		}
		

		
		@Nested
		@DisplayName("P7_Pruebas con entradas no válidas")
		class EntradasNoValidas {

			@Test
			@DisplayName("CP086_P7_ObtenerMediaCosteOOTT: String de size > 30")
			void testCP86ObtenerMediaCosteOOTT() throws Exception{
				// Act + Assert
				assertThrows(Exception.class , ()->GE.mediaCosteOT("CentralLecheraAsturiana2.0Súper"), "(CP86) No lanza excepcion con una entrada de un String con tamaño > 30");
				
			}

			@Test
			@DisplayName("CP087_P7_ObtenerMediaCosteOOTT: String null")
			void testCP87ObtenerMediaCosteOOTT() throws Exception{
				// Act + Assert
				assertThrows(Exception.class, ()->GE.mediaCosteOT(null), "(CP87) No lanza excepcion con una entrada de un valor nulo");
				
			}
		}
		
	}

	@Nested
	@DisplayName("P8_ObtenerDistribucionOOTT")
	class P8_ObtenerDistribucionOOTT{

		private ArrayList<OrdenTrabajo> simOOTT;
		private HashMap<String, Object> filtro;
        private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        private HashMap<Date, Integer> distribucion;
		
		@BeforeEach
		void setUp() throws Exception {
			simOOTT = new ArrayList<>();
			distribucion = new HashMap<>();
			Date date;
			for(int i = 1; i <= 7; i++) {
				date = format.parse(i+"/05/2021");
				distribucion.put(date, 2);
				simOOTT.add(new OrdenTrabajo(i, i+0f, null, null, "USC", null, date, null, null, null));
				simOOTT.add(new OrdenTrabajo(7+i, i+0f, null, null, "USC", null, date, null, null, null));
			}
			
			filtro = new HashMap<String, Object>();
			filtro.put("empresa", "USC");
			
			Mockito.when(GOT.filtrarOT(filtro)).thenReturn(simOOTT);
		}
		
		@Nested
		@DisplayName("P8_Pruebas con entradas válidas")
		class EntradasValidas {

			@Test
			@DisplayName("CP088_P8_ObtenerDistribucionOOTT: valores válidos")
			void testCP88ObtenerDistribucionOOTT() throws Exception{
				// Assert
				assertEquals(distribucion, GE.distribucionOT(true, false, "USC"), "(CP88) No devuelve el set de datos correctamente");
			}

			@Test
			@DisplayName("CP088_P8_Rendimiento: valores válidos y tiempo < 2s")
			void testCP88Rendimiento() throws Exception{
				// Assert
				assertTimeoutPreemptively(Duration.ofSeconds(2), ()->GE.distribucionOT(true, false, "USC"), "(CP88) La realziación del proceso supera el límite temporal de respuesta requerido");
			}
			
		}
		

		
		@Nested
		@DisplayName("P8_Pruebas con entradas no válidas")
		class EntradasNoValidas {

			@Test
			@DisplayName("CP089_P8_ObtenerDistribucionOOTT: Días y Semanas false")
			void testCP89ObtenerDistribucionOOTT() throws Exception{
				// Act + Assert
				assertThrows(Exception.class, ()->GE.distribucionOT(false, false, "USC"), "(CP89) No lanza excepcion siendo tanto días como semanas false");
				
			}

			@Test
			@DisplayName("CP090_P8_ObtenerDistribucionOOTT: Días y Semanas true")
			void testCP90ObtenerDistribucionOOTT() throws Exception{
				// Act + Assert
				assertThrows(Exception.class, ()->GE.distribucionOT(true, true, "USC"), "(CP90) No lanza excepcion siendo tanto días como semanas true");
				
			}

			@Test
			@DisplayName("CP091_P8_ObtenerDistribucionOOTT: Días null")
			void testCP91ObtenerDistribucionOOTT() throws Exception{
				// Act + Assert
				assertThrows(Exception.class, ()->GE.distribucionOT(null, false, "USC"), "(CP91) No lanza excepcion siendo tanto días null");
				
			}

			@Test
			@DisplayName("CP092_P8_ObtenerDistribucionOOTT: Semanas null")
			void testCP92ObtenerDistribucionOOTT() throws Exception{
				// Act + Assert
				assertThrows(Exception.class, ()->GE.distribucionOT(false, null, "USC"), "(CP92) No lanza excepcion siendo semanas null");
				
			}

			@Test
			@DisplayName("CP093_P8_ObtenerDistribucionOOTT: EmpresaResponsable size > 30")
			void testCP93ObtenerDistribucionOOTT() throws Exception{
				// Act + Assert
				assertThrows(Exception.class, ()->GE.distribucionOT(true, false, "CentralLecheraAsturiana2.0Super"), "(CP93) No lanza excepcion siendo el tamaño de empresaResponsable > 30");
				
			}

			@Test
			@DisplayName("CP094_P8_ObtenerDistribucionOOTT: EmpresaResponsable size > 30")
			void testCP94ObtenerDistribucionOOTT() throws Exception{
				// Act + Assert
				assertThrows(Exception.class, ()->GE.distribucionOT(true, false, null), "(CP94) No lanza excepcion siendo empresaResponsable null");
				
			}
			
		}
	}

}
