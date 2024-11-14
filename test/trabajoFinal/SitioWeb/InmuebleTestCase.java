package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class InmuebleTestCase {
	
	private Inmueble inmueble;
	private Usuario usuarioMock;
	private Reserva reservaMock;
	private Reserva otraReservaMock;
	private Manager managerMock;
	private Rankeo rankeoMock;
	private Rankeo otroRankeoMock;
	private Categoria categoriaMock;
	private Optional<TipoDeInmueble> tipoDeInmuebleMock;
	private TipoDeInmueble tipoConcretoMock;
	private SolicitudDeReserva solicitudMock;
	
	@BeforeEach
    public void setUp() {
		
		usuarioMock = mock(Usuario.class);
		inmueble = new Inmueble(usuarioMock, 40, "Argentina", "Buenos Aires", "Calle 43 N° 898", 3,  LocalTime.now(),  LocalTime.now(), 1000);
		reservaMock = mock(Reserva.class);
		otraReservaMock = mock(Reserva.class);
		managerMock = mock(Manager.class);
		rankeoMock = mock(Rankeo.class);
		categoriaMock = mock(Categoria.class);
		otroRankeoMock = mock(Rankeo.class);
		tipoDeInmuebleMock = Optional.empty();
		tipoConcretoMock = mock(TipoDeInmueble.class);
		solicitudMock = mock(SolicitudDeReserva.class);
		
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
		
		inmueble.agregarReserva(reservaMock);
		assertTrue(inmueble.getReservas().contains(reservaMock));
		inmueble.eliminarReserva(reservaMock);
		assertFalse(inmueble.getReservas().contains(reservaMock));
    }
	
	@Test
    void testUnInmueblePuedeSaberSiEstaReservadoEnLaFechaDeUnaReservaEntrante() {
		
		when(reservaMock.getFechaDeEgreso()).thenReturn(LocalDate.now());
		when(reservaMock.getFechaDeIngreso()).thenReturn(LocalDate.now());
		when(otraReservaMock.getFechaDeEgreso()).thenReturn(LocalDate.now());
		when(otraReservaMock.getFechaDeIngreso()).thenReturn(LocalDate.now());
		
		inmueble.agregarReserva(reservaMock);
		assertTrue(inmueble.estaReservado(otraReservaMock));
    }
	
	@Test
    void testUnInmueblePuedeModificarSuPrecioBase() {
		
		inmueble.setManager(managerMock);
		
		assertTrue(inmueble.getPrecioBase() == 1000);
		inmueble.modificarPrecioBase(2000);
		assertTrue(inmueble.getPrecioBase() == 2000);
		inmueble.modificarPrecioBase(500);
		assertTrue(inmueble.getPrecioBase() == 500);
		
		verify(managerMock).bajaDePrecio(inmueble);
    }
	
	@Test
    void testUnInmueblePuedeSetearSuDisponiblidad() {
		
		assertTrue(inmueble.getFechaInicial() == null);
		assertTrue(inmueble.getFechaFinal() == null);
		inmueble.setDisponibilidad(LocalDate.now(), LocalDate.now());
		assertTrue(inmueble.getFechaInicial().isEqual(LocalDate.now()));
		assertTrue(inmueble.getFechaFinal().isEqual(LocalDate.now()));
    }
	
	@Test
    void testUnInmueblePuedeModificarSusPreciosEspecialesl() throws Exception {
		
		inmueble.modificarPreciosEspecificos(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 10), 200);
		assertTrue(inmueble.estaFechaEspecifica(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 10)));
		assertThrows(Exception.class, () -> {inmueble.modificarPreciosEspecificos(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 10), 200);});
    }
	
	@Test
    void testUnInmueblePuedeCalcularElCostoTotalParaUnRangoDeFechas() throws Exception {
		
		inmueble.modificarPreciosEspecificos(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 10), 200);
		assertTrue(inmueble.calcularPrecioTotal(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 11)) == 3000);
		
    }
	
	@Test
    void testUnInmueblePuedeSaberQuienEsSuPropietario() {
		
		assertTrue(inmueble.getPropietario().equals(usuarioMock));
    }
	
	@Test
    void testUnInmueblePuedeActualizarSuListaDeRanking() throws Exception {
		
		when(rankeoMock.getCategoria()).thenReturn(categoriaMock);
		when(otroRankeoMock.getCategoria()).thenReturn(categoriaMock);
		when(categoriaMock.nombreCategoria()).thenReturn("Amplio");
		when(rankeoMock.getPuntaje()).thenReturn(3);
		when(otroRankeoMock.getPuntaje()).thenReturn(5);
		
		inmueble.actualizarListaDeRaneko(rankeoMock);
		assertTrue(inmueble.getRankeosInmueble().contains(rankeoMock));
		inmueble.actualizarListaDeRaneko(rankeoMock);	
		assertTrue(inmueble.calcularPromedioTotal() == 3);
	}
	
	@Test
    void testUnInmueblePuedeSetearseUnComentario() {
		
		assertTrue(inmueble.getComentarios().isEmpty());
		inmueble.setComentario("Bello");
		assertTrue(inmueble.getComentarios().contains("Bello"));
	}
	
	@Test
    void testUnInmueblePuedeSetearSuTipoDeInmueble() throws Exception {
		
		assertThrows(Exception.class, () -> {inmueble.setTipoDeInmueble(tipoDeInmuebleMock);});
		
		inmueble.setTipoDeInmueble(Optional.of(tipoConcretoMock));
		assertTrue(inmueble.getTipoInmueble().equals(tipoConcretoMock));
	}
	
	@Test
    void testUnInmueblePuedeSetearSusTiposDeServicios() {
		
		TipoDeServicio gasMock = mock(TipoDeServicio.class);
		List<TipoDeServicio> listaServicios = new ArrayList<TipoDeServicio>();
		listaServicios.add(gasMock);
		
		inmueble.setTipoDeServicios(listaServicios);
		assertTrue(inmueble.getTiposDeServicios().contains(gasMock));
		
	}
	
	@Test
    void testUnInmueblePuedeSetearSuAlbumDeFotos() throws Exception {
		
		Foto foto = mock(Foto.class);
		List<Foto> listaFotos = new ArrayList<Foto>();
		listaFotos.add(foto);
		
		inmueble.setFotos(listaFotos);
		
		assertTrue(inmueble.getFotos().contains(foto));
		
		listaFotos.add(foto);
		listaFotos.add(foto);
		listaFotos.add(foto);
		listaFotos.add(foto);
		listaFotos.add(foto);
		
		assertThrows(Exception.class, () -> {inmueble.setFotos(listaFotos);});
	}
	
	@Test
    void testUnInmuebleConoceQuienEsSuManager() {
		
		inmueble.setManager(managerMock);
		assertTrue(inmueble.getManager().equals(managerMock));
	}
	
	@Test
    void testUnInmuebleConoceSuPoliticaDeCancelacion() {
		
		PoliticaDeCancelacion politicaMock = mock(PoliticaDeCancelacion.class);
		
		inmueble.setPoliticaDeCancelacion(politicaMock);
		assertTrue(inmueble.getPoliticaDeCancelacion().equals(politicaMock));
	}
	
	@Test
    void testUnInmueblePudeModificarSuFormaDePago() {
		
		inmueble.modificarFormasDePago(FormaDePago.DEBITO);
		assertTrue(inmueble.getFormasDePago().contains(FormaDePago.DEBITO));
	}
	
	@Test
    void testUnInmueblePudeRealizarUnaReserva() {
		
		assertTrue(inmueble.realizarReservaDelInmueble(usuarioMock, FormaDePago.DEBITO, LocalDate.now(), LocalDate.now())instanceof SolicitudDeReserva);
		
		inmueble.solicitarReserva(solicitudMock);
		
		verify(solicitudMock).solicitarReserva();
	}
	
	@Test
    void testUnInmueblePudeSaberSiActualmenteEstaAlquilado() {
		
		assertFalse(inmueble.estaAlquiladoActualmente());
		inmueble.agregarReserva(otraReservaMock);
		assertTrue(inmueble.estaAlquiladoActualmente());
	}
	
}
