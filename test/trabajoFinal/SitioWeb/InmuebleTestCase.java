package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class InmuebleTestCase {
	
	private Inmueble inmueble;
	private Usuario usuarioMock;
	private Reserva reservaMock;

	@BeforeEach
    public void setUp() {
		
		usuarioMock = mock(Usuario.class);
		inmueble = new Inmueble(usuarioMock, 40, "Argentina", "Buenos Aires", "Calle 43 N° 898", 3,  LocalTime.now(),  LocalTime.now(), 1000);
		reservaMock = mock(Reserva.class);
    }
	
	@Test
    void testUnInmuebleConoceSusDatos() {
		
		inmueble.datosDelInmueble();
    }
	
	@Test
    void testUnInmueblePuedeSumaryRestarLaCantidadDeVecesQueFueAlquilado() {
		
		assertTrue(inmueble.getCantidadDeVecesAlquilado() == 0);
		inmueble.sumarCantidadDeVecesAlquilado();
		assertTrue(inmueble.getCantidadDeVecesAlquilado() == 1);
		inmueble.restarCantidadDeVecesAlquilado();
		assertTrue(inmueble.getCantidadDeVecesAlquilado() == 0);
    }

	@Test
    void testUnInmueblePuedeRegistrarYEliminarUnaReserva() {
		
		inmueble.agregarReserva(reservaMock);
		assertTrue(inmueble.getReservas().contains(reservaMock));
		inmueble.eliminarReserva(reservaMock);
		assertFalse(inmueble.getReservas().contains(reservaMock));
    }
	
	@Test
    void testUnInmueblePuedeSaberSiEstaRegistrado() {
		
		//when(reservaMock.getFechaDeIngreso()).thenReturn();
		
		inmueble.agregarReserva(reservaMock);
		assertTrue(inmueble.getReservas().contains(reservaMock));
		inmueble.eliminarReserva(reservaMock);
		assertFalse(inmueble.getReservas().contains(reservaMock));
    }
}
