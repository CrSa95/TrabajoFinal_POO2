package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoCanceladaTestCase {

	private EstadoCancelada estadoCancelada;
	private Reserva reservaMock;
	
	@BeforeEach
    public void setUp() {
		estadoCancelada = new EstadoCancelada();
		reservaMock = mock(Reserva.class);
	}
	
	@Test
	void testCancelar() {
		estadoCancelada.cancelar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
	
	@Test
	void testFinalizar() {
		estadoCancelada.finalizar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
} 
