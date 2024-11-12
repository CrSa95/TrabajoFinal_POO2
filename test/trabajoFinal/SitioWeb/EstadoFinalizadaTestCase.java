package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoFinalizadaTestCase {

	private EstadoFinalizada estadoFinalizada;
	private Reserva reservaMock;
	
	@BeforeEach
    public void setUp() {
		estadoFinalizada = new EstadoFinalizada();
		reservaMock = mock(Reserva.class);
	}
	
	@Test
	void testCancelar() {
		estadoFinalizada.cancelar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
	
	@Test
	void testFinalizar() {
		estadoFinalizada.finalizar(reservaMock);
		
		verifyNoInteractions(reservaMock);
	}
} 
