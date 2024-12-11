package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;  

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioTestCase {
	
	private Usuario usuario;
	private Usuario otroUsuarioMock;
	private SitioWeb sitioWebMock;
	private Reserva reservaMock;
	private Reserva otraReservaMock;
    private Inmueble inmuebleMock;
    private Inmueble otroInmuebleMock;
	private Categoria categoriaMock;
	private Categoria otraCategoriaMock;
    private SolicitudDeReserva solicitudMock;
    private UsuarioInquilino usuarioInquilino;
	private UsuarioPropietario usuarioPropietario;
	
    @BeforeEach
    public void setUp() {
    	
		usuario = new Usuario("Cristian Sanabria", "crissanabria@gmail.com", 1160583214);
    	sitioWebMock = mock(SitioWeb.class);
    	otroUsuarioMock = mock(Usuario.class);
    	reservaMock = mock(Reserva.class);
    	otraReservaMock = mock(Reserva.class);
    	inmuebleMock = mock(Inmueble.class);
    	otroInmuebleMock = mock(Inmueble.class);
        usuario.registrarEnSitioWeb(sitioWebMock); 
        categoriaMock = mock(Categoria.class);
        otraCategoriaMock = mock(Categoria.class);
        solicitudMock = mock(SolicitudDeReserva.class);
        usuarioInquilino = usuario;
        usuarioPropietario = usuario;
    }
	
	//Test de funcionalidades en comun 
    
	@Test
    void testUnUsuarioPuedeRegistrarseEnSitioWeb() {
		
        assertTrue(usuario.getSitioWeb().equals(sitioWebMock));
        assertTrue(usuario.getFechaDeRegistro().isEqual(LocalDate.now()));
    }
	
	@Test
    void testUnUsuarioPuedeSaberLaCantidadDeTiempoQueEstaRegistrado() {
		
        assertTrue(usuario.cantidadTiempoRegistrado() == 0);
    } 
	 
	@Test
    void testUnUsuarioPuedeEnviarYRecibirUnMail() {
		
		when(otroUsuarioMock.getContenidoMail()).thenReturn("Hello World");
		usuario.recibirMail("Hello World");
		usuario.enviarMail(usuario.getContenidoMail(),otroUsuarioMock);
		assertTrue(otroUsuarioMock.getContenidoMail().equalsIgnoreCase("Hello World"));
		assertTrue(usuario.getContenidoMail().equalsIgnoreCase("Hello World"));
    }
 
	@Test
    void testUnUsuarioPuedeRegistrarYEliminarUnaReserva() {
		when(reservaMock.getInquilino()).thenReturn(usuario);
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getReservas().contains(reservaMock));
		usuario.eliminarReserva(reservaMock);
		assertFalse(usuario.getReservas().contains(reservaMock));
    }
	
	@Test
    void testUnUsuarioPuedeCalcularSuPromedioTotalDeRanking() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuario.rankearAPropietario(reservaMock, categoriaMock, 5));
		assertTrue(usuario.calcularPromedioTotal(usuario.getRankeosPropietario()) == 5);
	}
	
	//Test de Usuario Propietario

	@Test
    void testUnUsuarioPropietarioPuedeRankearUnInquilino() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuarioPropietario.rankearAInquilino(reservaMock, categoriaMock, 5));
		assertFalse(usuario.getRankeosInquilino().isEmpty());
	}
	
	@Test
    void testUnUsuarioPropietarioNoPuedeRankearUnInquilino() throws Exception {
		
		assertThrows(Exception.class, () -> {usuarioPropietario.rankearAInquilino(reservaMock, categoriaMock, 5);});
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(categoriaMock.nombreCategoria()).thenReturn(" ");
		doThrow(new Exception("Error: La categoria" + categoriaMock.nombreCategoria() + "es incorrecta.")).when(sitioWebMock).estaCategoriaEspecificaInquilino(categoriaMock);
		assertThrows(Exception.class, () -> {usuarioPropietario.rankearAInquilino(reservaMock, categoriaMock, 5);});
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeDejarleUnComentarioAUnInquilino() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuarioPropietario.dejarUnComentarioAlInqulino(reservaMock, "Buen inquilino"));
		assertFalse(usuario.getComentariosInquilino().isEmpty());
	}
	
	@Test
    void testUnUsuarioPropietarioNoPuedeDejarleUnComentarioAUnInquilino() throws Exception {
		
		assertThrows(Exception.class, () -> {usuarioPropietario.dejarUnComentarioAlInqulino(reservaMock, "Sucio");});
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeDarSuInformacion() {
		
		usuarioPropietario.datosDelPropietario(inmuebleMock);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeRecibirResarcimiento() {
		
		assertTrue(usuarioPropietario.recibirResarcimiento(100) == 100);
		
	}
	
	@Test
    void testUnUsuarioPropietarioConoceSusInmueblesAlquilados() {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
		when(otraReservaMock.getEstadoDeReserva()).thenReturn(new EstadoConfirmada());
		when(otraReservaMock.getInmueble()).thenReturn(otroInmuebleMock);
		
		usuario.registrarReserva(reservaMock); 
		usuario.registrarReserva(otraReservaMock);
		assertTrue(usuarioPropietario.inmueblesAlquilados().size() == 1);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeRecibirUnaSolicitudDeReserva() {
		
		usuarioPropietario.recibirSolicitudReserva(solicitudMock);
		verify(solicitudMock).aprobarSolicitud();
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeActualizarUnValorEspecificoDeSuRanking() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(categoriaMock.nombreCategoria()).thenReturn("Amable");
		
		assertDoesNotThrow(() -> usuario.rankearAPropietario(reservaMock, categoriaMock, 5));
		assertDoesNotThrow(() -> usuario.rankearAPropietario(reservaMock, categoriaMock, 3));
		assertTrue(usuario.getRankeosPropietario().stream().filter(rankeo -> rankeo.getCategoria()
																		.nombreCategoria().equals("Amable"))
																		.findFirst().get().getPuntaje() == 4);
		
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeDarSuInformacion() {
		usuarioInquilino.datosDelInquilino();
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeRankearUnPropietario() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuarioInquilino.rankearAPropietario(reservaMock, categoriaMock, 5));
		assertFalse(usuario.getRankeosPropietario().isEmpty());
	}
	
	@Test
    void testUnUsuarioInquilinoNoPuedeRankearUnPropietario() throws Exception {
		
		assertThrows(Exception.class, () -> {usuarioInquilino.rankearAPropietario(reservaMock, categoriaMock, 5);});
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(categoriaMock.nombreCategoria()).thenReturn(" ");
		doThrow(new Exception("Error: La categoria" + categoriaMock.nombreCategoria() + "es incorrecta.")).when(sitioWebMock).estaCategoriaEspecificaPropietario(categoriaMock);   
		assertThrows(Exception.class, () -> {usuarioInquilino.rankearAPropietario(reservaMock, categoriaMock, 5);});
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeDejarleUnComentarioAUnPropietario() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuarioInquilino.dejarUnComentarioAlPropietario(reservaMock, "Hermoso"));
		assertFalse(usuario.getComentariosPropietario().isEmpty());
	}
	
	@Test
    void testUnUsuarioInquilinoNoPuedeDejarleUnComentarioAUnPropietario() throws Exception {
		
		assertThrows(Exception.class, () -> {usuarioInquilino.dejarUnComentarioAlPropietario(reservaMock, "Hermoso");});
		
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeActualizarUnValorEspecificoDeSuRanking() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(categoriaMock.nombreCategoria()).thenReturn("Amable");
		when(otraCategoriaMock.nombreCategoria()).thenReturn("Amable");
		
		assertDoesNotThrow(() -> usuario.rankearAInquilino(reservaMock, categoriaMock, 5));
		assertDoesNotThrow(() -> usuario.rankearAInquilino(reservaMock, otraCategoriaMock, 3));
		
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeVerTodasSusReservas() {
		
		usuario.registrarReserva(reservaMock);
		otroUsuarioMock.registrarReserva(otraReservaMock);
		assertTrue(usuario.getReservas().contains(reservaMock));
		assertFalse(usuario.getReservas().contains(otraReservaMock));
    }
	
	@Test
    void testUnUsuarioInquilinoPuedeCancelarUnaReserva() throws Exception {
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getReservas().contains(reservaMock));
		assertDoesNotThrow(() -> usuarioInquilino.cancelarReserva(reservaMock));
		assertFalse(usuario.getReservas().contains(reservaMock));
		assertThrows(Exception.class, () -> {usuarioInquilino.cancelarReserva(otraReservaMock);});
    }
	
}
