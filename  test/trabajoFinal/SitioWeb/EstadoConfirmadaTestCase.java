package trabajoFinal.SitioWeb;

// import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoConfirmadaTestCase {

	private EstadoConfirmada estadoConfirmada;
    private Reserva reservaMock;
    private Inmueble inmuebleMock;
    private PoliticaDeCancelacion politicaMock;

    @BeforeEach
    public void setUp() {
        estadoConfirmada = new EstadoConfirmada();
        reservaMock = mock(Reserva.class);
        inmuebleMock = mock(Inmueble.class);
        politicaMock = mock(PoliticaDeCancelacion.class);

        when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
        when(inmuebleMock.getPoliticaDeCancelacion()).thenReturn(politicaMock);
	}
	 
	@Test
	void testCuandoSeCancelaUnaReservaYHayReservaCondicional() {
		Reserva reservaCondicionalMock = mock(Reserva.class);
	    when(reservaMock.obtenerReservaCondicional(inmuebleMock)).thenReturn(Optional.of(reservaCondicionalMock));
		
		estadoConfirmada.cancelar(reservaMock);
		
		verify(politicaMock).darResarcimiento(reservaMock);
		verify(inmuebleMock).eliminarReserva(reservaMock);
		verify(reservaMock).setEstadoDeReserva(any(EstadoCancelada.class));
		verify(reservaCondicionalMock).evaluarReserva();	
	} 
	
	@Test
	void testCuandoSeCancelaUnaReservaYNoHayReservaCondicional() {
		when(reservaMock.obtenerReservaCondicional(inmuebleMock)).thenReturn(Optional.empty());
		
		estadoConfirmada.cancelar(reservaMock);
		
		verify(politicaMock).darResarcimiento(reservaMock);
		verify(inmuebleMock).eliminarReserva(reservaMock); 
		verify(reservaMock).setEstadoDeReserva(any(EstadoCancelada.class));
		verify(reservaMock, never()).evaluarReserva();	
	}
	
	@Test
	void TestCuandoSeFinalizaUnaReserva() {
		
		estadoConfirmada.finalizar(reservaMock);
		
		verify(inmuebleMock).eliminarReserva(reservaMock);
		verify(reservaMock).setEstadoDeReserva(any(EstadoFinalizada.class));
	}
}
