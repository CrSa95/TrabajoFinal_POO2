package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioTestCase {
	
	private Usuario usuario;
	private Usuario otroUsuarioMock;
	private SitioWeb sitioWebMock;
	private Reserva reservaMock;
    private Inmueble inmuebleMock;
    private String ciudad;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int capacidad;
    private double precioMinimo;
	private double precioMaximo;
	private Categoria categoriaMock;
	
	@BeforeEach
    public void setUp() {
    	
		usuario = new Usuario("Cristian Sanabria", "crissanabria@gmail.com", 1160583214);
    	sitioWebMock = mock(SitioWeb.class);
    	otroUsuarioMock = mock(Usuario.class);
    	reservaMock = mock(Reserva.class);
    	inmuebleMock = mock(Inmueble.class);
    	ciudad = "Buenos Aires";
        fechaEntrada = LocalDate.of(2024, 11, 1);
        fechaSalida = LocalDate.of(2024, 11, 10);
        capacidad = 3;
        precioMinimo = 30;
        precioMaximo = 50;
        usuario.registrarEnSitioWeb(sitioWebMock);
        categoriaMock = mock(Categoria.class);
    }
	
	@Test
    void testUnUsuarioPuedeRegistrarseEnSitioWeb() {
		
        assertTrue(usuario.getSitioWeb().equals(sitioWebMock));
        assertTrue(usuario.getFechaDeRegistro().isEqual(LocalDate.now()));
    }
	
	@Test
    void testUnUsuarioPuedeActualizarSuNombreCompleto() {
		
		usuario.actualizarNombreCompleto("Cristian Pablo Sanabria");
        assertTrue(usuario.getNombre().equals("Cristian Pablo Sanabria"));
    }
	 
	@Test
    void testUnUsuarioPuedeActualizarSuMail() {
		
		usuario.actualizarMail("crisss@gmail.com");
        assertTrue(usuario.getMail().equals("crisss@gmail.com"));
    }
	
	@Test
    void testUnUsuarioPuedeActualizarSuTelefono() {
		
		usuario.actualizarNumeroDeTelefono(42230149);
        assertTrue(usuario.getTelefono() == 42230149);
    }
	 
	@Test
    void testUnUsuarioPuedeEnviarYRecibirUnMail() {
		
		when(otroUsuarioMock.getContenidoMail()).thenReturn("Hello World");
		usuario.enviarMail("Hello World",otroUsuarioMock);
		assertTrue(otroUsuarioMock.getContenidoMail().equalsIgnoreCase("Hello World"));
    }
	
	@Test
    void testUnUsuarioPuedeRegistrarYEliminarUnaReserva() {
		when(reservaMock.getUsuario()).thenReturn(usuario);
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getReservas().contains(reservaMock));
		usuario.eliminarReserva(reservaMock);
		assertFalse(usuario.getReservas().contains(reservaMock));
    }
	
	@Test
    void testUnUsuarioPuedeBuscarInmuebles() {
		
		List<Inmueble> inmuebles = new ArrayList<Inmueble>();
		inmuebles.add(inmuebleMock);
		when(sitioWebMock.buscarInmuebles(ciudad, fechaEntrada, fechaSalida, capacidad, precioMinimo, precioMaximo)).thenReturn(inmuebles);
        
        assertTrue(usuario.buscarInmuebles(ciudad, fechaEntrada, fechaSalida, capacidad, precioMinimo, precioMaximo).contains(inmuebleMock));
    }
	
	@Test
    void testUnUsuarioPuedeVisualizarUnInmueble() {
		
		when(inmuebleMock.getPropietario()).thenReturn(otroUsuarioMock);
		
		usuario.visualizarInmueble(inmuebleMock);
		
        verify(inmuebleMock).datosDelInmueble();
        verify(inmuebleMock.getPropietario()).datosDelPropietario(inmuebleMock);
    }
	
	@Test
    void testUnUsuarioPuedeRealizarUnaReserva() {
		
		usuario.realizarReservar(inmuebleMock, usuario, FormaDePago.EFECTIVO, fechaSalida, fechaEntrada);
        verify(inmuebleMock).realizarReservaDelInmueble(usuario, FormaDePago.EFECTIVO, fechaSalida, fechaEntrada);
    }

	@Test
    void testUnUsuarioPropietarioPuedeRankearUnInquilino() throws Exception {
		
		usuario.rankearInquilino(reservaMock, otroUsuarioMock, categoriaMock, 5);
        //verify(otroUsuarioMock)
    }
	
}
