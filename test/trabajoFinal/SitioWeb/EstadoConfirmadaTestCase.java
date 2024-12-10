package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoConfirmadaTestCase {

	private EstadoConfirmada estadoConfirmada;
    private Reserva reservaMock;
    private Inmueble inmuebleMock;
    private Usuario usuarioMock;
    private Usuario propietarioMock;
    private PoliticaDeCancelacion politicaMock;

    @BeforeEach
    public void setUp() {
        estadoConfirmada = new EstadoConfirmada();
        reservaMock = mock(Reserva.class);
        inmuebleMock = mock(Inmueble.class);
        usuarioMock = mock(Usuario.class);
        propietarioMock = mock(Usuario.class);
        politicaMock = mock(PoliticaDeCancelacion.class);

        when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
        when(reservaMock.getInquilino()).thenReturn(usuarioMock);
        when(inmuebleMock.getPoliticaDeCancelacion()).thenReturn(politicaMock);
        when(inmuebleMock.getPropietario()).thenReturn(propietarioMock);
	}
	 
	@Test
	void testCuandoSeCancelaUnaReservaYHayReservaCondicional() {
		Reserva reservaCondicionalMock = mock(Reserva.class);
	    when(reservaMock.obtenerReservaCondicional(inmuebleMock)).thenReturn(Optional.of(reservaCondicionalMock));
		
		estadoConfirmada.cancelar(reservaMock); 
		
		verify(politicaMock).darResarcimiento(reservaMock);
		verify(inmuebleMock, times(1)).eliminarReserva(reservaMock);
		verify(propietarioMock).recibirMail("La reserva fue cancelada");
		verify(usuarioMock, times(1)).eliminarReserva(reservaMock);
		verify(reservaMock).setEstadoDeReserva(any(EstadoCancelada.class));
		verify(reservaCondicionalMock).evaluarReserva();	 
	} 
	
	@Test
	void testCuandoSeCancelaUnaReservaYNoHayReservaCondicional() {
		when(reservaMock.obtenerReservaCondicional(inmuebleMock)).thenReturn(Optional.empty());
		
		estadoConfirmada.cancelar(reservaMock);
		
		verify(politicaMock).darResarcimiento(reservaMock);
		verify(inmuebleMock).eliminarReserva(reservaMock);
		verify(propietarioMock).recibirMail("La reserva fue cancelada");
		verify(usuarioMock, times(1)).eliminarReserva(reservaMock);
		verify(reservaMock).setEstadoDeReserva(any(EstadoCancelada.class));
		verify(reservaMock, never()).evaluarReserva();	
	}
	
	@Test
	void TestCuandoSeFinalizaUnaReserva() {
		
		estadoConfirmada.finalizar(reservaMock);
		
		verify(inmuebleMock).eliminarReserva(reservaMock);
		verify(reservaMock).setEstadoDeReserva(any(EstadoFinalizada.class));
	}

	@Test
	void testElEstadoEsFinalizado() throws Exception {
		assertThrows(Exception.class, () -> {estadoConfirmada.finalizoLaReserva();});
		assertThrows(Exception.class,() -> estadoConfirmada.finalizoLaReserva(), "Error: La Reserva aun no finalizo."); 
	}
	
	@Test
	void testRankearAUnPropietarioLanzaExcepcion() {
	    Categoria categoriaMock = mock(Categoria.class);

	    assertThrows(Exception.class, () -> {estadoConfirmada.rankearAUnPropietario(usuarioMock, categoriaMock, 5);});
	    assertThrows(Exception.class, () -> estadoConfirmada.rankearAUnPropietario(usuarioMock, categoriaMock, 5), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRankearAUnInquilinoLanzaExcepcion() {
	    Categoria categoriaMock = mock(Categoria.class);

	    assertThrows(Exception.class, () -> {estadoConfirmada.rankearAUnInquilino(usuarioMock, categoriaMock, 5);});
	    assertThrows(Exception.class, () -> estadoConfirmada.rankearAUnInquilino(usuarioMock, categoriaMock, 5), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRankearUnInmuebleLanzaExcepcion() {
	    Categoria categoriaMock = mock(Categoria.class);

	    assertThrows(Exception.class, () -> {estadoConfirmada.rankearUnInmueble(inmuebleMock, categoriaMock, 5);});
	    assertThrows(Exception.class, () -> estadoConfirmada.rankearUnInmueble(inmuebleMock, categoriaMock, 5), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRegistrarComentarioParaElPropietarioLanzaExcepcion() {
  
	    assertThrows(Exception.class, () -> {estadoConfirmada.registrarComentarioParaElPropietario(usuarioMock, "Excelente propietario");});
	    assertThrows(Exception.class, () -> estadoConfirmada.registrarComentarioParaElPropietario(usuarioMock, "Excelente propietario"), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRegistrarComentarioParaElInquilinoLanzaExcepcion() {
	    
	    assertThrows(Exception.class, () -> {estadoConfirmada.registrarComentarioParaElInquilino(usuarioMock, "Muy buen inquilino");});
	    assertThrows(Exception.class, () -> estadoConfirmada.registrarComentarioParaElInquilino(usuarioMock, "Muy buen inquilino"), "Error: La Reserva aun no finalizo.");
	}

	@Test
	void testRegistrarComentarioParaElInmuebleLanzaExcepcion() {
	    
	    assertThrows(Exception.class, () -> {estadoConfirmada.registrarComentarioParaElInmueble(inmuebleMock, "Excelente ubicación");});
	    assertThrows(Exception.class, () -> estadoConfirmada.registrarComentarioParaElInmueble(inmuebleMock, "Excelente ubicación"), "Error: La Reserva aun no finalizo.");
	}
}
