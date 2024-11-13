package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RankeoTestCase {
	
	private Rankeo rankeo;
	private Categoria categoriaMock;
	
	@BeforeEach
	public void setUp() {
		categoriaMock = mock(Categoria.class);
	}

	@Test
	void testCuandoSeCreaUnRankeo(){
		
		rankeo = new Rankeo(categoriaMock, 3);
		
		assertEquals(rankeo.getCategoria(), categoriaMock);
		assertEquals(rankeo.getPuntaje(), 3);
	}
}