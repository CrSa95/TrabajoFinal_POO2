package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CategoriaTestCase {

	private Categoria categoria;
	
	@Test
	void testCreacionDeTipoDeServicio() {
		categoria = new Categoria("Limpieza");
		
		assertNotNull(categoria);
		assertEquals(categoria.nombreCategoria(), "Limpieza");	
	}
}