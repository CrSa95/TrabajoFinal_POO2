package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SinCancelacionTestCase {

	private SinCancelacion sinCancelacion;
	private Inmueble inmuebleMock;
	private Usuario propietarioMock;
	private Reserva reservaMock;
	private LocalDate fechaIngreso;
	private LocalDate fechaEgreso;
	
	@BeforeEach
	public void setUp() {
		sinCancelacion = new SinCancelacion();
		inmuebleMock = mock(Inmueble.class);
		propietarioMock = mock(Usuario.class);
	    reservaMock = mock(Reserva.class);
	    
	    fechaIngreso = LocalDate.of(2024, 11, 1);
        fechaEgreso = LocalDate.of(2024, 11, 10);
	}
	
	@Test
	void testDarResarcimientoDeReserva() {
		when(inmuebleMock.getPropietario()).thenReturn(propietarioMock);
		when(reservaMock.getFechaDeIngreso()).thenReturn(fechaIngreso);
        when(reservaMock.getFechaDeEgreso()).thenReturn(fechaEgreso);
        when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
        when(inmuebleMock.calcularPrecioTotal(fechaIngreso, fechaEgreso)).thenReturn(1000.0);
		
        sinCancelacion.darResarcimiento(reservaMock);
        
		verify(propietarioMock).recibirResarcimiento(1000.0);
	}
}