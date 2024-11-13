package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FotoTestCase {

	private Foto foto;
	
	@Test
	void testCuandoSeCrearUnaFotoConUnTamaño() {
		foto = new Foto(10);
		
		assertNotNull(foto);
		assertEquals(foto.getTamaño(), 10);
	}
}