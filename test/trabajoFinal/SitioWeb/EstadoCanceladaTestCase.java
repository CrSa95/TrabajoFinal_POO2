package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
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
	void testCancelar() throws Exception {
		Exception exception = assertThrows(Exception.class, () -> estadoCancelada.cancelar(reservaMock));
	    assertEquals("Error: La Reserva se encuentra cancelada.", exception.getMessage());
	}
	
	@Test
	void testFinalizar() throws Exception {
		Exception exception = assertThrows(Exception.class, () -> estadoCancelada.finalizar(reservaMock));
	    assertEquals("Error: La Reserva se encuentra cancelada.", exception.getMessage());
	}
	
	// Test para mostrar lo pedido en la corrección. 
	@Test
	void testCuandoSeQuiereRealizarUnRankeoLaReservaNoEstaFinalizada() {
		Usuario usuarioMock = mock(Usuario.class);
		Categoria categoriaMock = mock(Categoria.class);
		
		Exception exception = assertThrows(Exception.class, () -> estadoCancelada.rankearInquilino(usuarioMock, categoriaMock, 3));
	    assertEquals("Error: La Reserva aun no finalizo.", exception.getMessage());
	}   
} 
