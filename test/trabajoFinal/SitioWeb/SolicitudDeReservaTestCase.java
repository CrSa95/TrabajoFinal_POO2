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
    // No lo puedo hacer andar, ya que dentro del metodo se hace un nuevo new de Reserva
    public void testRealizarReserva() {
        Reserva reservaMock = mock(Reserva.class);
        Usuario propietarioMock = mock(Usuario.class);
        Manager managerMock = mock(Manager.class);
        
        reservaMock.setManager(managerMock);
        when(reservaMock.getManager()).thenReturn(managerMock);
        when(inmuebleMock.getPropietario()).thenReturn(propietarioMock);
        
        solicitud.evaluarReserva(reservaMock); 

        verify(reservaMock).evaluarReserva();
    }
 
    public void testNotificarAInquilino() {
        solicitud.notificarAInquilino();

        verify(inquilinoMock).recibirMail("Solicitud de reserva aceptada");
    }
    
    @Test
    public void testCuandoSeCreaUnaReserva() {
        Reserva reserva = solicitud.crearReserva();

        assertNotNull(reserva);

        assertEquals(inmuebleMock, reserva.getInmueble());
        assertEquals(inquilinoMock, reserva.getUsuario());
        assertEquals(formaDePagoMock, reserva.getFormaDePago());
        assertEquals(LocalDate.of(2024, 12, 1), reserva.getFechaDeIngreso());
        assertEquals(LocalDate.of(2024, 12, 10), reserva.getFechaDeEgreso());
    }
    
    @Test
    public void testRealizarReserva() {  
    	Reserva reservaMock = mock(Reserva.class);
    	Usuario propietarioMock = mock(Usuario.class);
    	Manager managerMock = mock(Manager.class);

    	reservaMock.setManager(managerMock);
    	when(reservaMock.getManager()).thenReturn(managerMock);
    	when(inmuebleMock.getPropietario()).thenReturn(propietarioMock);
    	
        solicitud.realizarReserva(reservaMock); // Llamamos al método que debería evaluar la reserva

        verify(reservaMock).evaluarReserva(); // Verificamos la interacción con el mock
    }
}