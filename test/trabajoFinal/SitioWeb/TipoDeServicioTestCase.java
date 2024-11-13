package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TipoDeServicioTestCase {

	private TipoDeServicio tipoDeServicio;
	
	@Test
	void testCreacionDeTipoDeServicio() {
		tipoDeServicio = new TipoDeServicio("Agua");
		
		assertNotNull(tipoDeServicio);
		assertEquals(tipoDeServicio.getTipoDeServicio(), "Agua");	
	}
}
