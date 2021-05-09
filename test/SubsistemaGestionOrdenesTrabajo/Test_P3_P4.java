package SubsistemaGestionOrdenesTrabajo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import SubsistemaGestionProcesos.GestorProcesos;
import SubsistemaGestionProcesos.Proceso;
import SubsistemaIncidencias.Incidencia;

class Test_P3_P4 {

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
		GOT = new GestorOT();
		GP = Mockito.mock(GestorProcesos.class);
		GOT.setGestorProc(GP);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("P3 Visualizar OOTT")
	class p3{
		private HashMap<String, Object> filtro;
		private ArrayList<OrdenTrabajo> resultadoEsperado;
		private ArrayList<Proceso> simProcesos;
		private ArrayList<String> personal;
		private ArrayList<Float> presupuestos;

		OrdenTrabajo OOTT1=new OrdenTrabajo(1, 300f, "OOTT 1", 15, "Bloques Cando", "iniciado", new GregorianCalendar(2020, 0, 10).getTime(), "Material 1", personal, presupuestos);
		OrdenTrabajo OOTT2=new OrdenTrabajo(2, 300f, "OOTT 2", 1, "Otra Empresa", "iniciado", new GregorianCalendar(2020, 0, 10).getTime(), "Material 2", personal, presupuestos);
		OrdenTrabajo OOTT3=new OrdenTrabajo(3, 5f, "OOTT 3", 20, "Bloques Cando", "iniciado", new GregorianCalendar(2020, 0, 10).getTime(), "Material 2", personal, presupuestos);
		OrdenTrabajo OOTT4=new OrdenTrabajo(4, 1500f, "OOTT 4", 15, "Bloques Cando Sexa", "iniciado", new GregorianCalendar(2020, 0, 10).getTime(), "Material 2", personal, presupuestos);
		OrdenTrabajo OOTT5=new OrdenTrabajo(5,  3.5f, "OOTT 5", 3, "Cando Bloques", "iniciado", new GregorianCalendar(2020, 0, 10).getTime(), "Material 1", personal, presupuestos);
		
		
		@BeforeEach
		void setUp() throws Exception {
			simProcesos = new ArrayList<>();
			personal=new ArrayList<>();
			presupuestos=new ArrayList<>();

			personal.add("50047393Z");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			presupuestos.add(2000f);
			presupuestos.add(500f);
			presupuestos.add(1050f);
			
			Proceso proceso=new Proceso(1,3f,"","","","","",new GregorianCalendar(2020, 0, 10).getTime());
			simProcesos.add(proceso);
			Mockito.when(GP.getProcesos()).thenReturn(simProcesos);


			GOT.crearOrdenTrabajo("Bloques Cando", "iniciado", 15, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 1", 300f, "Material 1", personal, presupuestos, 1);
			GOT.crearOrdenTrabajo("Otra Empresa", "iniciado", 1, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 2", 300f, "Material 2", personal, presupuestos, 1);
			GOT.crearOrdenTrabajo("Bloques Cando", "iniciado", 20, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 3", 5f, "Material 2", personal, presupuestos, 1);
			GOT.crearOrdenTrabajo("Bloques Cando Sexa", "iniciado", 15, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 4", 1500f, "Material 2", personal, presupuestos, 1);
			GOT.crearOrdenTrabajo("Cando Bloques", "iniciado", 3, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 5", 3.5f, "Material 1", personal, presupuestos, 1);
			filtro = new HashMap<String, Object>();
			
			resultadoEsperado =new ArrayList<>();
		}
		
		@Nested
		@DisplayName("Entradas Válidas ")
		class EntradasValidas{
			
			@DisplayName("CP070-P3: entrada vacía")
			@Test
			void testCP070VisualizarOOTT() throws Exception{
				//Arrange
				resultadoEsperado.add(OOTT1);
				resultadoEsperado.add(OOTT2);
				resultadoEsperado.add(OOTT3);
				resultadoEsperado.add(OOTT4);
				resultadoEsperado.add(OOTT5);
				
				//Act
				
				//Assert
				assertEquals(resultadoEsperado,GOT.filtrarOT(filtro),"(CP70) El filtado devuelve valores inesperados");
			}
			
			@DisplayName("CP071-P3: valores correctos")
			@Test
			void testCP071VisualizarOOTT() throws Exception{
				//Arrange
				filtro.put("empresa", "Bloques Cando");
				filtro.put("fechaInicio", new GregorianCalendar(2020, 0, 10).getTime());
				filtro.put("duracion", 15);
				filtro.put("estado", "iniciado");

				resultadoEsperado.add(OOTT1);
				
				//Act
				
				//Assert
				assertEquals(resultadoEsperado,GOT.filtrarOT(filtro),"(CP71) El filtado devuelve valores inesperados");
			}
		}

		@Nested
		@DisplayName("Entradas No Válidas")
		class EntradasNoValidas{
			
			@DisplayName("CP072-P3: filtro null")
			@Test
			void testCP072VisualizarOOTT() throws Exception{
				//Arrange
				
				//Act
				
				//Assert
				assertNull(GOT.filtrarOT(null),"(CP72) El filtado devuelve un valor no nulo");
			}
			
			@DisplayName("CP073-P3: filtro con clave no válida")
			@Test
			void testCP073VisualizarOOTT() throws Exception{
				//Arrange
				filtro.put("empresa", "Bloques Cando");
				filtro.put("AGAGAGAG", new GregorianCalendar(2020, 0, 10).getTime());
				
				//Act
				
				//Assert
				assertNull(GOT.filtrarOT(filtro),"(CP73) El filtado devuelve un valor no nulo");
			}
			
			@DisplayName("CP074-P3: filtro con valor no válido")
			@Test
			void testCP074VisualizarOOTT() throws Exception{
				//Arrange
				filtro.put("empresa", "Bloques Cando");
				filtro.put("estado", new GregorianCalendar(2020, 0, 10).getTime());
				
				//Act
				
				//Assert
				assertNull(GOT.filtrarOT(filtro),"(CP74) El filtado devuelve un valor no nulo");
			}
		}

		@Nested
		@DisplayName("Rendimiento")
		class Rendimiento{
			@Test
			@DisplayName("Prueba de rendimiento (Caso válido CP70)")
			void P1_CrearOT_CrearProceso_CV() {
				//Arrange
				resultadoEsperado.add(OOTT1);
				resultadoEsperado.add(OOTT2);
				resultadoEsperado.add(OOTT3);
				resultadoEsperado.add(OOTT4);
				resultadoEsperado.add(OOTT5);
				
				//Act + Assert
				
				assertTimeoutPreemptively(Duration.ofSeconds(2), ()->{GOT.filtrarOT(filtro);},
						"(CP70 - Rendimiento) El filtrado supera el límite temporal de respuesta requerido.");
			}
		}
	}

	@Nested
	@DisplayName("P4 Modificar OOTT")
	class p4{
		private HashMap<String, Object> filtro;
		private ArrayList<OrdenTrabajo> resultadoEsperado;
		private ArrayList<Proceso> simProcesos;
		private ArrayList<String> personal;
		private ArrayList<Float> presupuestos;
		
		@BeforeEach
		void setUp() throws Exception {
			simProcesos = new ArrayList<>();
			personal=new ArrayList<>();
			presupuestos=new ArrayList<>();

			personal.add("50047393Z");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			personal.add("91836452M");
			presupuestos.add(2000f);
			presupuestos.add(500f);
			presupuestos.add(1050f);
			
			Proceso proceso=new Proceso(1,3f,"","","","","",new GregorianCalendar(2020, 0, 10).getTime());
			simProcesos.add(proceso);
			Mockito.when(GP.getProcesos()).thenReturn(simProcesos);


			GOT.crearOrdenTrabajo("Bloques Cando", "iniciado", 15, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 1", 300f, "Material 1", personal, presupuestos, 1);
			GOT.crearOrdenTrabajo("Otra Empresa", "iniciado", 1, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 2", 300f, "Material 2", personal, presupuestos, 1);
			GOT.crearOrdenTrabajo("Bloques Cando", "iniciado", 20, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 3", 5f, "Material 2", personal, presupuestos, 1);
			GOT.crearOrdenTrabajo("Bloques Cando Sexa", "iniciado", 15, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 4", 1500f, "Material 2", personal, presupuestos, 1);
			GOT.crearOrdenTrabajo("Cando Bloques", "iniciado", 3, new GregorianCalendar(2020, 0, 10).getTime(), "OOTT 5", 3.5f, "Material 1", personal, presupuestos, 1);
			filtro = new HashMap<String, Object>();
			
			resultadoEsperado =new ArrayList<>();
		}
		
		@Nested
		@DisplayName("Entradas Válidas ")
		class EntradasValidas{
			
			@DisplayName("CP075-P4: valores correctos")
			@Test
			void testCP075VisualizarOOTT() throws Exception{
				//Arrange
				OrdenTrabajo ordenTrabajo=new OrdenTrabajo(1, 78.45f, "Texto de ejemplo", 30, "NombreEmpresa", "iniciado", new GregorianCalendar(2020, 0, 10).getTime(), "Material necesario", personal, presupuestos);
				resultadoEsperado.add(ordenTrabajo);
				filtro.put("fechaInicio", new GregorianCalendar(2020, 0, 10).getTime());
				filtro.put("duracion", 30);
				filtro.put("estado", "iniciado");
				
				//Act
				
				//Assert
				assertAll(
						()->{assertDoesNotThrow(()->GOT.modificarOT(ordenTrabajo),"(CP75) La modifacación falla y devuelve una excepción");},
						()->{assertEquals(resultadoEsperado,GOT.filtrarOT(filtro), "(CP75) La orden de trabajo no se ha modificado correctamente");}
						);
			}
		}
		
		@Nested
		@DisplayName("Entradas No Válidas")
		class EntradasNoValidas{

			@DisplayName("CP076-P4: ordenTrabajo = null")
			@Test
			void testCP076VisualizarOOTT() throws Exception{
				//Arrange
				
				//Act
				
				//Assert
				assertThrows(Exception.class, ()->GOT.modificarOT(null),"(CP76) La modificación incorrecta no devuelve una excepción");
			}
			
			@DisplayName("CP077-P4: coste negativo")
			@Test
			void testCP077VisualizarOOTT() throws Exception{
				//Arrange
				OrdenTrabajo ordenTrabajo=new OrdenTrabajo(1, -78.45f, "Texto de ejemplo", 30, "NombreEmpresa", "iniciado", new GregorianCalendar(2020, 0, 10).getTime(), "Material necesario", personal, presupuestos);
				
				//Act
				
				//Assert
				assertThrows(Exception.class, ()->GOT.modificarOT(ordenTrabajo),"(CP77) La modificación incorrecta no devuelve una excepción");
			}
			
		}

		@Nested
		@DisplayName("Rendimiento")
		class Rendimiento{
			@Test
			@DisplayName("Prueba de rendimiento (Caso válido CP75)")
			void P1_CrearOT_CrearProceso_CV() {
				// Arrange

				OrdenTrabajo ordenTrabajo=new OrdenTrabajo(1, 78.45f, "Texto de ejemplo", 30, "NombreEmpresa", "iniciado", new GregorianCalendar(2020, 0, 10).getTime(), "Material necesario", personal, presupuestos);
				resultadoEsperado.add(ordenTrabajo);
				filtro.put("fechaInicio", new GregorianCalendar(2020, 0, 10).getTime());
				filtro.put("duracion", 30);
				filtro.put("estado", "iniciado");
				
				//Act + Assert
				
				assertTimeoutPreemptively(Duration.ofSeconds(2), ()->{GOT.modificarOT(ordenTrabajo);},
						"(CP75 - Rendimiento) La modificación de la orden de trabajo supera el límite temporal de respuesta requerido.");
			}
		}
	}
	
	
}
