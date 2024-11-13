package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TipoDeInmuebleTestCase {

	private TipoDeInmueble tipoDeInmueble;
	
	@Test
	void testCreacionDeTipoDeServicio() {
		tipoDeInmueble = new TipoDeInmueble("Departamento");
		
		assertNotNull(tipoDeInmueble);
		assertEquals(tipoDeInmueble.getTipoDeInmueble(), "Departamento");	
	}
}