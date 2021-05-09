package SubsistemaAnalisisEstadisticas;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
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

import SubsistemaGestionOrdenesTrabajo.GestorOT;
import SubsistemaGestionOrdenesTrabajo.OrdenTrabajo;
import SubsistemaGestionProcesos.GestorProcesos;
import SubsistemaIncidencias.GestorIncidencias;
import SubsistemaIncidencias.Incidencia;

class Test_P10_DistribucionMediaCosteOT {

	private GestorIncidencias GI;
	private GestorOT GOT;
	private GestorProcesos GP;
	private GeneradorEstadisticas GE;

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

	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP118)")
	void P10_CP118() throws Exception {

		// Arrange

		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(null);

		// Act + Assert

		Exception e = assertThrows(Exception.class, () -> {
			GE.distribucionMediaCosteOT(null, null, null);
		}, "(CP118) La distribución de la media de coste de las OOTT con valores no válidos y caso: "
				+ "(dias = null, semanas = null, empresaResponsable = null, ordenes = null) "
				+ "no lanza una excepción.");
		assertEquals("Los valores de dias y semanas no puede ser null.", e.getMessage(),
				"(CP118) La distribución de la media de coste de las OOTT con valores no válidos y caso: "
						+ "(dias = null, semanas = null, empresaResponsable = null, ordenes = null) "
						+ "tiene un mensaje de excepción erróneo.");
	}
	
	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP119)")
	void P10_CP119() throws Exception {

		// Arrange

		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(null);

		// Act + Assert

		Exception e = assertThrows(Exception.class, () -> {
			GE.distribucionMediaCosteOT(false, false, "CentralLecheraAsturiana2.0Super");
		}, "(CP119) La distribución de la media de coste de las OOTT con valores no válidos y caso: "
				+ "(dias = false, semanas = false, empresaResponsable = 'CentralLecheraAsturiana2.0Super', ordenes = null) "
				+ "no lanza una excepción.");
		assertEquals("empresa responsable no valida", e.getMessage(),
				"(CP119) La distribución de la media de coste de las OOTT con valores no válidos y caso: "
						+ "(dias = false, semanas = false, empresaResponsable = 'CentralLecheraAsturiana2.0Super', ordenes = null) "
						+ "tiene un mensaje de excepción erróneo.");
	}
	
	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP120)")
	void P10_CP120() throws Exception {

		// Arrange

		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(null);

		// Act + Assert

		Exception e = assertThrows(Exception.class, () -> {
			GE.distribucionMediaCosteOT(true, true, "USC");
		}, "(CP120) La distribución de la media de coste de las OOTT con valores no válidos y caso: "
				+ "(dias = true, semanas = true, empresaResponsable = 'USC', ordenes = null) "
				+ "no lanza una excepción.");
		assertEquals("No se puede dar al mismo tiempo la distribucion por dias y semanas", e.getMessage(),
				"(CP120) La distribución de la media de coste de las OOTT con valores no válidos y caso: "
						+ "(dias = true, semanas = true, empresaResponsable = 'USC', ordenes = null) "
						+ "tiene un mensaje de excepción erróneo.");
	}
	
	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP121)")
	void P10_CP121() throws Exception {

		// Arrange

		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(null);

		// Act + Assert

		Exception e = assertThrows(Exception.class, () -> {
			GE.distribucionMediaCosteOT(false, false, null);
		}, "(CP121) La distribución de la media de coste de las OOTT con valores no válidos y caso: "
				+ "(dias = false, semanas = false, empresaResponsable = null, ordenes = null) "
				+ "no lanza una excepción.");
		assertEquals("Es necesario indicar si la distribucion es por dias o por semanas", e.getMessage(),
				"(CP121) La distribución de la media de coste de las OOTT con valores no válidos y caso: "
						+ "(dias = false, semanas = false, empresaResponsable = null, ordenes = null) "
						+ "tiene un mensaje de excepción erróneo.");
	}
	
	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP122)")
	void P10_CP122() throws Exception {

		// Arrange
		
		ArrayList<OrdenTrabajo> ordenes = new ArrayList<>();
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 7, 12).getTime(), null, null, null));
		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(ordenes);

		// Act
		
		HashMap<Date, Float> resultado = GE.distribucionMediaCosteOT(true, false, null);
		HashMap<Date, Float> resultadoEsperado = new HashMap<>();
		resultadoEsperado.put(new GregorianCalendar(2021, 7, 12).getTime(), 10f);
		
		// Assert

		assertEquals(resultado, resultadoEsperado, 
				"(CP122) La distribución de la media de coste de las OOTT con valores válidos y caso: "
				+ "(dias = true, semanas = false, empresaResponsable = null, ordenes = {OT1 (fechaInicio=12/08/2021)}) "
				+ "no da el resultado esperado.");
	}
	
	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP123)")
	void P10_CP123() throws Exception {

		// Arrange
		
		ArrayList<OrdenTrabajo> ordenes = new ArrayList<>();
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 7, 12).getTime(), null, null, null));
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 7, 12).getTime(), null, null, null));
		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(ordenes);

		// Act
		
		HashMap<Date, Float> resultado = GE.distribucionMediaCosteOT(true, false, null);
		HashMap<Date, Float> resultadoEsperado = new HashMap<>();
		resultadoEsperado.put(new GregorianCalendar(2021, 7, 12).getTime(), 10f);
		
		// Assert

		assertEquals(resultado, resultadoEsperado, 
				"(CP123) La distribución de la media de coste de las OOTT con valores válidos y caso: "
				+ "(dias = true, semanas = false, empresaResponsable = null,ordenes = {OT1 (fechaInicio=12/08/2021), OT2(fechaInicio=12/08/2021)}) "
				+ "no da el resultado esperado.");
	}
	
	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP124)")
	void P10_CP124() throws Exception {

		// Arrange
		
		ArrayList<OrdenTrabajo> ordenes = new ArrayList<>();
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 7, 12).getTime(), null, null, null));
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 8, 27).getTime(), null, null, null));
		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(ordenes);

		// Act
		
		HashMap<Date, Float> resultado = GE.distribucionMediaCosteOT(true, false, null);
		HashMap<Date, Float> resultadoEsperado = new HashMap<>();
		resultadoEsperado.put(new GregorianCalendar(2021, 7, 12).getTime(), 10f);
		resultadoEsperado.put(new GregorianCalendar(2021, 8, 27).getTime(), 10f);
		
		// Assert

		assertEquals(resultado, resultadoEsperado, 
				"(CP124) La distribución de la media de coste de las OOTT con valores válidos y caso: "
				+ "(dias = true, semanas = false, empresaResponsable = null, ordenes = {OT1 (fechaInicio=12/08/2021), OT2(fechaInicio=27/09/2021)}) "
				+ "no da el resultado esperado.");
	}
	
	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP125)")
	void P10_CP125() throws Exception {

		// Arrange
		
		ArrayList<OrdenTrabajo> ordenes = new ArrayList<>();
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 7, 12).getTime(), null, null, null));
		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(ordenes);

		// Act
		
		HashMap<Date, Float> resultado = GE.distribucionMediaCosteOT(false, true, null);
		HashMap<Date, Float> resultadoEsperado = new HashMap<>();
		resultadoEsperado.put(new GregorianCalendar(2021, 7, 12).getTime(), 10f);
		
		// Assert

		assertEquals(resultado, resultadoEsperado, 
				"(CP125) La distribución de la media de coste de las OOTT con valores válidos y caso: "
				+ "(dias = false, semanas = true, empresaResponsable = null, ordenes = {OT1 (fechaInicio=12/08/2021)}) "
				+ "no da el resultado esperado.");
	}
	
	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP126)")
	void P10_CP126() throws Exception {

		// Arrange
		
		ArrayList<OrdenTrabajo> ordenes = new ArrayList<>();
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 7, 12).getTime(), null, null, null));
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 8, 27).getTime(), null, null, null));
		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(ordenes);

		// Act
		
		HashMap<Date, Float> resultado = GE.distribucionMediaCosteOT(false, true, null);
		HashMap<Date, Float> resultadoEsperado = new HashMap<>();
		resultadoEsperado.put(new GregorianCalendar(2021, 7, 12).getTime(), 10f);
		resultadoEsperado.put(new GregorianCalendar(2021, 8, 27).getTime(), 10f);
		
		// Assert

		assertEquals(resultado, resultadoEsperado, 
				"(CP132) La distribución de la media de coste de las OOTT con valores válidos y caso: "
				+ "(dias = false, semanas = true, empresaResponsable = null, ordenes = {OT1 (fechaInicio=12/08/2021), OT2(fechaInicio=27/09/2021)}) "
				+ "no da el resultado esperado.");
	}
	
	@Test
	@DisplayName("Test_P10_DistribucionMediaCosteOT (CP127)")
	void P10_CP127() throws Exception {

		// Arrange
		
		ArrayList<OrdenTrabajo> ordenes = new ArrayList<>();
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 7, 12).getTime(), null, null, null));
		ordenes.add(new OrdenTrabajo(1, 10f, null, null, null, null, new GregorianCalendar(2021, 7, 13).getTime(), null, null, null));
		Mockito.when(GOT.filtrarOT(Mockito.any())).thenReturn(ordenes);

		// Act
		
		HashMap<Date, Float> resultado = GE.distribucionMediaCosteOT(false, true, null);
		HashMap<Date, Float> resultadoEsperado = new HashMap<>();
		resultadoEsperado.put(new GregorianCalendar(2021, 7, 12).getTime(), 10f);
		
		// Assert

		assertEquals(resultado, resultadoEsperado, 
				"(CP127) La distribución de la media de coste de las OOTT con valores válidos y caso: "
				+ "(dias = false, semanas = true, empresaResponsable = null, ordenes = {OT1 (fechaInicio=12/08/2021), OT2(fechaInicio=13/08/2021)}) "
				+ "no da el resultado esperado.");
	}

}
