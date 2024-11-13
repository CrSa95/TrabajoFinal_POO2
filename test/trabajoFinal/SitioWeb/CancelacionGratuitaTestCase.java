package trabajoFinal.SitioWeb;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;

class CancelacionGratuitaTestCase {

	private CancelacionGratuita cancelacionGratuita;
    private Inmueble inmuebleMock;
    private Usuario propietarioMock;
    private Reserva reservaMock;

    @BeforeEach
    public void setUp() {
    	cancelacionGratuita = new CancelacionGratuita();
    	
    	inmuebleMock = mock(Inmueble.class);
       	propietarioMock = mock(Usuario.class);
       	reservaMock = mock(Reserva.class);
       
       	when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
       	when(inmuebleMock.getPropietario()).thenReturn(propietarioMock);
    }
    
    @Test
    void testDarResarcimientoConCancelacionAnticipada() {
    	 LocalDate fechaIngreso = LocalDate.now().plusDays(15);
         when(reservaMock.getFechaDeIngreso()).thenReturn(fechaIngreso);
         
         cancelacionGratuita.darResarcimiento(reservaMock);
         
         verify(propietarioMock).recibirResarcimiento(0);
    }
    
    @Test
    void testDarResarcimientoConCancelacionTardia() {
    	 LocalDate fechaIngreso = LocalDate.now().plusDays(5);
         when(reservaMock.getFechaDeIngreso()).thenReturn(fechaIngreso);
         when(inmuebleMock.getPrecioBase()).thenReturn(100.0);
         
         cancelacionGratuita.darResarcimiento(reservaMock);
         
         verify(propietarioMock).recibirResarcimiento(200.0);
    }
}