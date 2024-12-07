package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservaTestCase {

    private Reserva reserva;
    private Inmueble inmuebleMock;
    private Usuario inquilinoMock;
    private Usuario propietarioMock;
    private FormaDePago formaDePagoMock;
    private EstadoDeReserva estadoDeReservaMock;
    private Manager managerMock;

    @BeforeEach
    public void setUp() {
        inmuebleMock = mock(Inmueble.class); 
        inquilinoMock = mock(Usuario.class); 
        propietarioMock = mock(Usuario.class);
        formaDePagoMock = mock(FormaDePago.class);
        estadoDeReservaMock = mock(EstadoDeReserva.class);
        managerMock = mock(Manager.class);
 
        LocalDate fechaDeIngreso = LocalDate.of(2024, 12, 1);
        LocalDate fechaDeEgreso = LocalDate.of(2024, 12, 10);

        when(inmuebleMock.getManager()).thenReturn(managerMock);
        when(inmuebleMock.estaReservado(reserva)).thenReturn(true); 
        reserva = new Reserva(inmuebleMock, inquilinoMock, formaDePagoMock, fechaDeIngreso, fechaDeEgreso);
        
    }
	
    @Test
    void testCreacionDeReserva() {
        assertEquals(inmuebleMock, reserva.getInmueble());
        assertEquals(inquilinoMock, reserva.getInquilino());
        assertEquals(formaDePagoMock, reserva.getFormaDePago());
        assertEquals(LocalDate.of(2024, 12, 1), reserva.getFechaDeIngreso());
        assertEquals(LocalDate.of(2024, 12, 10), reserva.getFechaDeEgreso());
        assertNotNull(reserva.getManager());
    }
    
	@Test
	void testCuandoSeFinalizaLaReserva() {
		reserva.setEstadoDeReserva(estadoDeReservaMock);
		reserva.finalizarReserva();
		
		verify(estadoDeReservaMock, times(1)).finalizar(reserva);
	}
	
	@Test
	void testCuandoSeCancelaLaReserva() {
		reserva.setEstadoDeReserva(estadoDeReservaMock);
		reserva.cancelarReserva(); 
		
		verify(estadoDeReservaMock, times(1)).cancelar(reserva);;
		verify(managerMock, times(1)).cancelacionDeReserva(inmuebleMock);
	}
	
	@Test
	void testEvaluarReservaCuandoInmuebleEstaReservado() {     
	    when(inmuebleMock.estaReservado(any(Reserva.class))).thenReturn(true);

	    reserva.evaluarReserva();
	    assertTrue(reserva.getEstadoDeReserva() instanceof EstadoCondicional);

	    when(inmuebleMock.estaReservado(any(Reserva.class))).thenReturn(false);
	    Optional<Reserva> reservaCondicional = reserva.obtenerReservaCondicional(inmuebleMock);

	    assertTrue(reservaCondicional.isPresent());
	    assertEquals(reserva, reservaCondicional.get());
	}
	
	@Test
	void testEvaluarReservaCuandoInmuebleEstaNoReservado() {
        when(inmuebleMock.estaReservado(any(Reserva.class))).thenReturn(false);

        assertTrue(reserva.getEstadoDeReserva() instanceof EstadoConfirmada);
        
        verify(inmuebleMock, times(1)).agregarReserva(reserva);
        verify(inquilinoMock, times(1)).registrarReserva(reserva);
        verify(managerMock, times(1)).altaDeReserva();
	}
	
	@Test
    void testObtenerReservaCondicional() {
		when(inmuebleMock.estaReservado(any(Reserva.class))).thenReturn(true);
	    reserva.evaluarReserva();

	    when(inmuebleMock.estaReservado(any(Reserva.class))).thenReturn(false);

	    Optional<Reserva> reservaCondicional = reserva.obtenerReservaCondicional(inmuebleMock);
	    assertTrue(reservaCondicional.isPresent());
	    assertEquals(reserva, reservaCondicional.get());
    }
}