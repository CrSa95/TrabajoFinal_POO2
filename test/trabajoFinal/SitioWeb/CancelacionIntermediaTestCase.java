package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;

class CancelacionIntermediaTestCase {

	private CancelacionIntermedia cancelacionIntermedia;
    private Inmueble inmuebleMock;
    private Usuario propietarioMock;
    private Reserva reservaMock;

    @BeforeEach
    public void setUp() {
    	cancelacionIntermedia = new CancelacionIntermedia();
    	
    	inmuebleMock = mock(Inmueble.class);
       	propietarioMock = mock(Usuario.class);
       	reservaMock = mock(Reserva.class);
       
       	when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
       	when(inmuebleMock.getPropietario()).thenReturn(propietarioMock);
    }
    
    @Test
    void testCancelacionConMasDe20DiasAnticipacion() {
    	LocalDate fechaIngreso = LocalDate.now().plusDays(25);
    	when(reservaMock.getFechaDeIngreso()).thenReturn(fechaIngreso);
    	
    	cancelacionIntermedia.darResarcimiento(reservaMock);
    	
    	verify(propietarioMock).recibirResarcimiento(0);
    }
    
    @Test
    void testCancelacionEntre10Y19DiasAntes() {
    	LocalDate fechaIngreso = LocalDate.now().plusDays(13);
    	LocalDate fechaEgreso = LocalDate.now().plusDays(16);
    	when(reservaMock.getFechaDeIngreso()).thenReturn(fechaIngreso);
    	when(reservaMock.getFechaDeEgreso()).thenReturn(fechaEgreso);
    	when(inmuebleMock.calcularPrecioTotal(fechaIngreso, fechaEgreso)).thenReturn(700.0);
    	
    	cancelacionIntermedia.darResarcimiento(reservaMock);
    	
    	verify(inmuebleMock).calcularPrecioTotal(fechaIngreso, fechaEgreso);
    	verify(propietarioMock).recibirResarcimiento(700.0 * 0.5);
    }
    
    @Test
    void testCancelacionConMenosDe10DiasAnticipacion() {
    	LocalDate fechaIngreso = LocalDate.now().plusDays(5);
    	LocalDate fechaEgreso = LocalDate.now().plusDays(16);
    	when(reservaMock.getFechaDeIngreso()).thenReturn(fechaIngreso);
    	when(reservaMock.getFechaDeEgreso()).thenReturn(fechaEgreso);
    	when(inmuebleMock.calcularPrecioTotal(fechaIngreso, fechaEgreso)).thenReturn(700.0);
    	
    	cancelacionIntermedia.darResarcimiento(reservaMock);
    	
    	verify(inmuebleMock).calcularPrecioTotal(fechaIngreso, fechaEgreso);
    	verify(propietarioMock).recibirResarcimiento(700.0);
    } 
}