package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;



import static org.mockito.Mockito.*;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolicitudDeReservaTestCase {

    private SolicitudDeReserva solicitud;
    private Inmueble inmuebleMock;
    private Usuario inquilinoMock;
    private FormaDePago formaDePagoMock;
    private EstadoDeSolicitud estadoMock;
    
    @BeforeEach
    public void setUp() {
        inmuebleMock = mock(Inmueble.class); 
        inquilinoMock = mock(Usuario.class);
        formaDePagoMock = mock(FormaDePago.class);
        estadoMock = mock(EstadoDeSolicitud.class);


        LocalDate fechaDeIngreso = LocalDate.of(2024, 12, 1);
        LocalDate fechaDeEgreso = LocalDate.of(2024, 12, 10);
        
         
        solicitud = new SolicitudDeReserva(inmuebleMock, inquilinoMock, formaDePagoMock, fechaDeIngreso, fechaDeEgreso);
    }

    @Test
    public void testSolicitarReserva() {
        Usuario propietarioMock = mock(Usuario.class);
        when(inmuebleMock.getPropietario()).thenReturn(propietarioMock);

        solicitud.solicitarReserva();

        verify(propietarioMock).recibirSolicitudReserva(solicitud);
    }

    @Test
    public void testAprobarSolicitud() {
        solicitud.setEstadoDeSolicitud(estadoMock);
        solicitud.aprobarSolicitud();

        verify(estadoMock).aprobar(solicitud);
    } 

    @Test
    public void testGetEstadoInicial() {
    	assertEquals("EstadoDeSolicitudPendiente", solicitud.getEstado().getClass().getSimpleName());
    }
    
    @Test
    public void testNotificarAInquilino() {
        solicitud.notificarAInquilino();

        verify(inquilinoMock).recibirMail("Solicitud de reserva aceptada"); 
    }
    
    @Test
    public void testCuandoSeCreaUnaReserva() {
    	Manager managerMock = mock(Manager.class);
    	when(inmuebleMock.getManager()).thenReturn(managerMock);
        Reserva reserva = solicitud.crearReserva();

        assertNotNull(reserva);

        assertEquals(inmuebleMock, reserva.getInmueble());
        assertEquals(inquilinoMock, reserva.getInquilino());
        assertEquals(formaDePagoMock, reserva.getFormaDePago());
        assertEquals(LocalDate.of(2024, 12, 1), reserva.getFechaDeIngreso());
        assertEquals(LocalDate.of(2024, 12, 10), reserva.getFechaDeEgreso()); 
        assertNotNull(reserva.getManager());
    }
}