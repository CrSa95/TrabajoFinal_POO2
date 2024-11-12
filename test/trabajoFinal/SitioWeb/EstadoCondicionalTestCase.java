package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoCondicionalTestCase {

	private EstadoCondicional estadoCondicional;
	private Reserva reservaMock;
	
	@BeforeEach
    public void setUp() {
		estadoCondicional = new EstadoCondicional();
		reservaMock = mock(Reserva.class);
	}
	
	@Test
	void testCancelar() {
		estadoCondicional.cancelar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
	
	@Test
	void testFinalizar() {
		estadoCondicional.finalizar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
} 