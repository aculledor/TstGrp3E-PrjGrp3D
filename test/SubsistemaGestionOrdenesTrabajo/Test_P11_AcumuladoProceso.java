package SubsistemaGestionOrdenesTrabajo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import SubsistemaGestionProcesos.GestorProcesos;
import SubsistemaGestionProcesos.Proceso;
import SubsistemaIncidencias.GestorIncidencias;

class Test_P11_AcumuladoProceso {

	private ArrayList<OrdenTrabajo> ordenesTrabajo;
	private ArrayList<Proceso> procesos;
	private GestorOT GOT;
	private GestorProcesos GP;
	private GestorIncidencias GI;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ordenesTrabajo = new ArrayList<>();
		procesos=new ArrayList<>();
		GOT = new GestorOT();
		GP = Mockito.mock(GestorProcesos.class);
		
		
		procesos.add(new Proceso(0, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()));
		procesos.add(new Proceso(1, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()));
		procesos.add(new Proceso(2, null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>()));
		
		// ordenesTrabajo = {OT1(proceso = 0)}
		ordenesTrabajo.add(new OrdenTrabajo(0, 200f, null, null, null, null, null, null, null, null));
		ordenesTrabajo.get(0).setProceso(procesos.get(0));
		
		// ordenesTrabajo = {OT1(proceso = 1, coste = 200), OT2(proceso = 2, coste = 0)}
		ordenesTrabajo.add(new OrdenTrabajo(1, 200f, null, null, null, null, null, null, null, null));
		ordenesTrabajo.get(1).setProceso(procesos.get(1));

		ordenesTrabajo.add(new OrdenTrabajo(2, 0f, null, null, null, null, null, null, null, null));
		ordenesTrabajo.get(2).setProceso(procesos.get(2));
		
		//añado solo OT1(proceso=0) para caso de prueba CP133 y CP134
		GOT.getOrdenesTrabajo().add(ordenesTrabajo.get(0));

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test_P11_AcumuladoProceso: Prueba de caja blanca CP128")
	void CP128_P11_acumuladoProceso() throws Exception{
		assertThrows(Exception.class, () -> GOT.acumuladoProceso(null),
				"(CP128) No lanza excepcion al recibir un identificador de proceso nulo.");
			
	}

	@Test
	@DisplayName("Test_P11_AcumuladoProceso: Prueba de caja blanca CP129")
	void CP129_P11_acumuladoProceso() throws Exception{

		assertThrows(Exception.class, () -> GOT.acumuladoProceso(1),
				"(CP129) No lanza excepcion al introducir un número de proceso que no pertenece a ninguna OT.");

	}

	@Test
	@DisplayName("Test_P11_AcumuladoProceso: Prueba de caja blanca CP130")
	void CP130_P11_acumuladoProceso() {
		GOT.getOrdenesTrabajo().remove(0);
				
		GOT.getOrdenesTrabajo().add(ordenesTrabajo.get(1));
		GOT.getOrdenesTrabajo().add(ordenesTrabajo.get(2));

		assertDoesNotThrow(() -> {
			GOT.acumuladoProceso(1);
		}, "(CP130) Obtener el coste para un proceso existente lanza una excepción");
	}

}
