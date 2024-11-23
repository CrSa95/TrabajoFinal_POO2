package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioTestCase {
	
	private Usuario usuario;
	private Usuario otroUsuarioMock;
	private SitioWeb sitioWebMock;
	private Reserva reservaMock;
	private Reserva unaReservaMock;
	private Reserva otraReservaMock;
    private Inmueble inmuebleMock;
    private Inmueble otroInmuebleMock;
    private String ciudad;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private int capacidad;
    private double precioMaximo;
	private Categoria categoriaMock;
	private Categoria otraCategoriaMock;
	private TipoDeInmueble tipoDeInmuebleMock;
	private TipoDeServicio gas;
    private Foto foto;
    private PoliticaDeCancelacion politicaMock;
    private SolicitudDeReserva solicitudMock;
    @BeforeEach
    public void setUp() {
    	
		usuario = new Usuario("Cristian Sanabria", "crissanabria@gmail.com", 1160583214);
    	sitioWebMock = mock(SitioWeb.class);
    	otroUsuarioMock = mock(Usuario.class);
    	reservaMock = mock(Reserva.class);
    	unaReservaMock = mock(Reserva.class);
    	otraReservaMock = mock(Reserva.class);
    	inmuebleMock = mock(Inmueble.class);
    	otroInmuebleMock = mock(Inmueble.class);
    	ciudad = "Buenos Aires";
        fechaEntrada = LocalDate.of(2024, 11, 1);
        fechaSalida = LocalDate.of(2024, 11, 10);
        capacidad = 3;
        precioMaximo = 50;
        usuario.registrarEnSitioWeb(sitioWebMock);
        categoriaMock = mock(Categoria.class);
        otraCategoriaMock = mock(Categoria.class);
        tipoDeInmuebleMock = mock(TipoDeInmueble.class);
        gas = mock(TipoDeServicio.class);
        foto = mock(Foto.class);
        politicaMock = mock(PoliticaDeCancelacion.class);
        solicitudMock = mock(SolicitudDeReserva.class);
        new ArrayList<Inmueble>();
        
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
		
		assertDoesNotThrow(() -> usuario.rankearUnPropietario(reservaMock, categoriaMock, 5));
		assertTrue(usuario.calcularPromedioTotal(usuario.getRankeosPropietario()) == 5);
	}
	
	//Test de Usuario Propietario

	@Test
    void testUnUsuarioPropietarioPuedeRankearUnInquilino() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuario.rankearUnInquilino(reservaMock, categoriaMock, 5));
		assertFalse(usuario.getRankeosInquilino().isEmpty());
	}
	
	@Test
    void testUnUsuarioPropietarioNoPuedeRankearUnInquilino() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.rankearUnInquilino(reservaMock, categoriaMock, 5);});
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(categoriaMock.nombreCategoria()).thenReturn(" ");
		doThrow(new Exception("Error: La categoria" + categoriaMock.nombreCategoria() + "es incorrecta.")).when(sitioWebMock).estaCategoriaEspecificaInquilino(categoriaMock);
		assertThrows(Exception.class, () -> {usuario.rankearUnInquilino(reservaMock, categoriaMock, 5);});
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeDejarleUnComentarioAUnInquilino() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuario.dejarUnComentarioAlInqulino(reservaMock, "Buen inquilino"));
		assertFalse(usuario.getComentariosInquilino().isEmpty());
	}
	
	@Test
    void testUnUsuarioPropietarioNoPuedeDejarleUnComentarioAUnInquilino() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.dejarUnComentarioAlInqulino(reservaMock, "Sucio");});
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeConocerLaInformacionDeUnInquilino() {
		
		usuario.datosDelInquilino();

	}
	
	@Test
    void testUnUsuarioPropietarioPuedeRecibirResarcimiento() {
		
		assertTrue(usuario.recibirResarcimiento(100) == 100);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeModificarElPrecioBase() {
		
		usuario.actualizarPrecioBase(inmuebleMock, 100);
		verify(inmuebleMock).modificarPrecioBase(100);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeModificarFormasDePago() {
		
		usuario.modificarFormasDePago(inmuebleMock,FormaDePago.EFECTIVO);
		verify(inmuebleMock).modificarFormasDePago(FormaDePago.EFECTIVO);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeModificarLosPreciosEspecificos() throws Exception {
		
		usuario.modificarPreciosEspecificos(inmuebleMock, fechaEntrada, fechaSalida, 100);
		verify(inmuebleMock).modificarPreciosEspecificos(fechaEntrada, fechaSalida, 100);
		
	}
	
	
	@Test
    void testUnUsuarioPropietarioConoceSusInmueblesAlquilados() {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
		when(otraReservaMock.getEstadoDeReserva()).thenReturn(new EstadoConfirmada());
		when(otraReservaMock.getInmueble()).thenReturn(otroInmuebleMock);
		
		usuario.registrarReserva(reservaMock); 
		usuario.registrarReserva(otraReservaMock);
		assertTrue(usuario.inmueblesAlquilados().size() == 1);
		
	}
	
	@Test
    void testUnUsuarioPropietarioConoceLaCantidadDeVecesQueAlquiloSusInmuebles() {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(otraReservaMock.getEstadoDeReserva()).thenReturn(new EstadoConfirmada());
		
		usuario.registrarReserva(reservaMock);  
		usuario.registrarReserva(otraReservaMock);
		assertTrue(usuario.cantidadDeVecesQueAlquiloUnPropietarioSusInmuebles() == 1);
		
	}
	
	@Test
    void testUnUsuarioPropietarioConoceLaCantidadDeVecesQueAlquiloUnInmueble() {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
		when(unaReservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(unaReservaMock.getInmueble()).thenReturn(otroInmuebleMock);
		when(otraReservaMock.getEstadoDeReserva()).thenReturn(new EstadoConfirmada());
		when(otraReservaMock.getInmueble()).thenReturn(inmuebleMock);
		
		usuario.registrarReserva(reservaMock);  
		usuario.registrarReserva(otraReservaMock);
		usuario.registrarReserva(unaReservaMock);
		assertTrue(usuario.cantidadDeVecesQueUnPropietarioAlquiloUnInmueble(inmuebleMock) == 1);
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeRecibirUnaSolicitudDeReserva() {
		
		usuario.recibirSolicitudReserva(solicitudMock);
		verify(solicitudMock).aprobarSolicitud();
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeActualizarUnValorEspecificoDeSuRanking() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(categoriaMock.nombreCategoria()).thenReturn("Amable");
		when(otraCategoriaMock.nombreCategoria()).thenReturn("Amable");
		
		assertDoesNotThrow(() -> usuario.rankearUnPropietario(reservaMock, categoriaMock, 5));
		assertDoesNotThrow(() -> usuario.rankearUnPropietario(reservaMock, otraCategoriaMock, 3));
		
	}
	
	@Test
    void testUnUsuarioPropietarioPuedeDarDeAltaUnInmueble() throws Exception {
		List<TipoDeServicio> listaServicios = new ArrayList<TipoDeServicio>();
		listaServicios.add(gas);
		
		List<Foto> listaFotos = new ArrayList<Foto>();
		listaFotos.add(foto);
		
		when(sitioWebMock.seleccionarTipoDeInmueble(tipoDeInmuebleMock)).thenReturn(java.util.Optional.of(tipoDeInmuebleMock));
		
		usuario.altaInmueble(tipoDeInmuebleMock, 2332, "Argentina", ciudad, "calle 43 N° 898", 
							 listaServicios, capacidad, listaFotos, LocalTime.now(), LocalTime.now(), 
							 politicaMock, precioMaximo, fechaEntrada, fechaSalida);
		assertFalse(usuario.getInmueblesDadosDeAlta().isEmpty());
	}
	
	//Test de Usuario Inquilino
	
	@Test
    void testUnUsuarioInquilinoPuedeConocerLaInformacionDeUnPropietario() {
		
		usuario.datosDelPropietario(inmuebleMock);
		
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeRankearUnPropietario() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuario.rankearUnPropietario(reservaMock, categoriaMock, 5));
		assertFalse(usuario.getRankeosPropietario().isEmpty());
	}
	
	@Test
    void testUnUsuarioInquilinoNoPuedeRankearUnPropietario() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.rankearUnPropietario(reservaMock, categoriaMock, 5);});
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(categoriaMock.nombreCategoria()).thenReturn(" ");
		doThrow(new Exception("Error: La categoria" + categoriaMock.nombreCategoria() + "es incorrecta.")).when(sitioWebMock).estaCategoriaEspecificaPropietario(categoriaMock);   
		assertThrows(Exception.class, () -> {usuario.rankearUnPropietario(reservaMock, categoriaMock, 5);});
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeDejarleUnComentarioAUnPropietario() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		
		assertDoesNotThrow(() -> usuario.dejarUnComentarioAlPropietario(reservaMock, "Hermoso"));
		assertFalse(usuario.getComentariosPropietario().isEmpty());
	}
	
	@Test
    void testUnUsuarioInquilinoNoPuedeDejarleUnComentarioAUnPropietario() throws Exception {
		
		assertThrows(Exception.class, () -> {usuario.dejarUnComentarioAlPropietario(reservaMock, "Hermoso");});
		
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeActualizarUnValorEspecificoDeSuRanking() throws Exception {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(categoriaMock.nombreCategoria()).thenReturn("Amable");
		when(otraCategoriaMock.nombreCategoria()).thenReturn("Amable");
		
		assertDoesNotThrow(() -> usuario.rankearUnInquilino(reservaMock, categoriaMock, 5));
		assertDoesNotThrow(() -> usuario.rankearUnInquilino(reservaMock, otraCategoriaMock, 3));
		
	}
	
	@Test
    void testUnUsuarioInquilinoPuedeVerTodasSusReservas() {
		
		usuario.registrarReserva(reservaMock);
		otroUsuarioMock.registrarReserva(otraReservaMock);
		assertTrue(usuario.getReservas().contains(reservaMock));
		assertFalse(usuario.getReservas().contains(otraReservaMock));
    }
	
	@Test
    void testUnUsuarioInquilinoPuedeVerSusReservasFuturas() {
		when(reservaMock.getFechaDeIngreso()).thenReturn(LocalDate.of(2025, 1, 23));
		when(otraReservaMock.getFechaDeIngreso()).thenReturn(LocalDate.of(2024, 1, 23));	
		
		usuario.registrarReserva(reservaMock);
		usuario.registrarReserva(otraReservaMock);
		assertTrue(usuario.getReservasFuturas(LocalDate.now()).contains(reservaMock));
		assertFalse(usuario.getReservasFuturas(LocalDate.now()).contains(otraReservaMock));
    }
	
	@Test
    void testUnUsuarioInquilinoPuedeVerSusReservasEnCiudad() {
		when(reservaMock.getInquilino()).thenReturn(usuario);
		when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
		when(inmuebleMock.getCiudad()).thenReturn(ciudad);
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getReservasEnCiudad(ciudad).contains(reservaMock));
    }
	
	@Test
    void testUnUsuarioInquilinoPuedeVerLasCiudadesReservadas() {
		when(reservaMock.getInquilino()).thenReturn(usuario);
		when(reservaMock.getInmueble()).thenReturn(inmuebleMock);
		when(inmuebleMock.getCiudad()).thenReturn(ciudad);
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getCiudadesReservadas().contains(ciudad));
    }
	
	@Test
    void testUnUsuarioInquilinoPuedeCancelarUnaReserva() throws Exception {
		
		usuario.registrarReserva(reservaMock);
		assertTrue(usuario.getReservas().contains(reservaMock));
		assertDoesNotThrow(() -> usuario.cancelarReserva(reservaMock));
		assertFalse(usuario.getReservas().contains(reservaMock));
		assertThrows(Exception.class, () -> {usuario.cancelarReserva(otraReservaMock);});
    }
	
	@Test
    void testUnUsuarioInquilinoConoceLaCantidadDeVecesQueAlquilo() {
		
		when(reservaMock.getEstadoDeReserva()).thenReturn(new EstadoFinalizada());
		when(otraReservaMock.getEstadoDeReserva()).thenReturn(new EstadoConfirmada());
		
		usuario.registrarReserva(reservaMock);
		usuario.registrarReserva(otraReservaMock);
		
		assertTrue(usuario.cantidadDeVecesQueAlquiloUnInquilino() == 1);
    }
	
}
