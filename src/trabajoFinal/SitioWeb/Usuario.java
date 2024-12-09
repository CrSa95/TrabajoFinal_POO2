package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Usuario implements UsuarioInquilino,UsuarioPropietario {

	private String nombreCompleto;
	private String mail;
	private int telefono;
	private LocalDate fechaRegistro;
	private List<String> comentariosInquilino = new ArrayList<>();
	private List<String> comentariosPropietario = new ArrayList<>();
	private List<Rankeo> rankeosInquilino = new ArrayList<>();
	private List<Rankeo> rankeosPropietario = new ArrayList<>();
	private List<Reserva> reservas = new ArrayList<>();
	private SitioWeb sitioWeb;
	private List<Inmueble> inmueblesDadosDeAlta = new ArrayList<>();
	private String contenidoMail;

	public Usuario (String nombreCompleto, String mail, int telefono) {

		this.nombreCompleto = nombreCompleto;
		this.mail = mail;
		this.telefono = telefono;

	}
	
	public void registrarEnSitioWeb(SitioWeb sitioWeb) {
		this.sitioWeb = sitioWeb;
		this.fechaRegistro = LocalDate.now();
	}
	
	public SitioWeb getSitioWeb() { 
		return this.sitioWeb;
	}

	public LocalDate getFechaDeRegistro() {
		return this.fechaRegistro;
	}

	public String getNombre() {
		return this.nombreCompleto; 
	}
	
	public void actualizarNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public void actualizarMail(String mail) {
		this.mail = mail;
	}

	public void actualizarNumeroDeTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int cantidadTiempoRegistrado(){
		//return Period.between(fechaRegistro, LocalDate.now()).getYears();
		return (int) ChronoUnit.DAYS.between(fechaRegistro, LocalDate.now());
	}

	public String getMail() {
		return mail;
	}

	public int getTelefono() {
		return telefono;
	}

	public void recibirMail(String contenidoMail){
		this.contenidoMail = contenidoMail;
	}
	
	public String getContenidoMail() {
		return this.contenidoMail; 
	}

	public void enviarMail(String contenidoDelMail, Usuario usuario){
		usuario.recibirMail(contenidoDelMail);
	}
	
	public void registrarReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	public void eliminarReserva(Reserva reserva) {
		this.reservas.remove(reserva);
	}

	public int calcularPromedioTotal(List<Rankeo> rankeos) {

		double sumaTotal = 0;
        for (Rankeo rank : rankeos) {
            sumaTotal += rank.getPuntaje();
        }

        return (int) (sumaTotal / rankeos.size());
	}

	public Boolean estaElRank(Rankeo rankeo, List<Rankeo> listaDeRankeo) {
		return listaDeRankeo.stream()
				.anyMatch(rank -> rank.getCategoria().nombreCategoria().equals
						 (rankeo.getCategoria().nombreCategoria()));
	}

	public void actualizarPuntajeDeRankeo(Rankeo rankeo, List<Rankeo> listaDeRankeo) {

		Optional<Rankeo> rankeoViejo = listaDeRankeo.stream()
			    									   .filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria())).findFirst();

		listaDeRankeo.stream()
	    				.filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria()))
	    				.forEach(rank -> rank.setPuntaje((int)((rankeoViejo.get().getPuntaje() + rankeo.getPuntaje()) / 2)));
	}
	
	public List<Rankeo> getRankeosInquilino(){
		return this.rankeosInquilino;
	}

	public List<String> getComentariosInquilino(){
		return comentariosInquilino; 
	}

	public List<Reserva> getReservas(){
		return this.reservas;
	}
	
	public List<Inmueble> getInmueblesDadosDeAlta(){
		return this.inmueblesDadosDeAlta;
	}
	
	public List<String> getComentariosPropietario(){
		return this.comentariosPropietario;
	}

	public List<Rankeo> getRankeosPropietario(){
		return this.rankeosPropietario;
	}

	@Override
	public void altaInmueble(TipoDeInmueble tipoDeInmueble, int superficie, String pais, String ciudad,
			String direccion, List<TipoDeServicio> servicios, int capacidad, List<Foto> fotos, LocalTime checkIn,
			LocalTime checkOut, PoliticaDeCancelacion politicaDeCancelacion, double precioBase, LocalDate fechaInicial,
			LocalDate fechaFinal) throws Exception {
		
		//Creo el inmueble
		Inmueble inmueble = new Inmueble(this,superficie, pais, ciudad,
		direccion, capacidad, checkIn, checkOut, precioBase);
			
		//Obtengo el tipo de inmueble que quiero aplicar a mi inmueble
		inmueble.setTipoDeInmueble(this.getSitioWeb().seleccionarTipoDeInmueble(tipoDeInmueble));
		
		//Obtengo la lista de servicios que quiero aplicar a mi inmueble
		inmueble.setTipoDeServicios(this.getSitioWeb().seleccionarTiposDeServicio(servicios));
		
		inmueble.setFotos(fotos);
			
		inmueble.setPoliticaDeCancelacion(politicaDeCancelacion);
			
		inmueble.setDisponibilidad(fechaInicial, fechaFinal);
			
		//Lo doy de alta en el sitio
		this.getSitioWeb().altaInmueble(inmueble);
			
		//Lo agrego a la lista de inmuebles dados de alta
		this.getInmueblesDadosDeAlta().add(inmueble);
		
	}

	@Override
	public void actualizarPrecioBase(Inmueble inmueble, int precioNuevo) {
		inmueble.modificarPrecioBase(precioNuevo);	
	}

	@Override
	public void modificarPreciosEspecificos(Inmueble inmueble, LocalDate fechaInicial, LocalDate fechaFinal, int precio)
			throws Exception {
		inmueble.modificarPreciosEspecificos(fechaInicial, fechaFinal, precio);
	}

	@Override
	public void modificarFormasDePago(Inmueble inmueble, FormaDePago formaDePago) {
		inmueble.modificarFormasDePago(formaDePago);	
	}

	@Override
	public List<Inmueble> inmueblesAlquilados() {
		return this.getReservas().stream()
    			.filter(reserva -> {
										try {
											reserva.getEstadoDeReserva().finalizoLaReserva();
											return true;
										} catch (Exception e) {
											return false;
										}
									} ).map(Reserva::getInmueble) 
    								   .distinct()
    								   .collect(Collectors.toList());
	}

	@Override
	public void recibirSolicitudReserva(SolicitudDeReserva solicitudDeReserva) {
		solicitudDeReserva.aprobarSolicitud();
	}
	
	public void laReservaFinalizo(Reserva reserva) throws Exception {
		reserva.getEstadoDeReserva().finalizoLaReserva();
	}
	
	@Override
	public void rankearUnInquilino(Reserva reserva, Categoria categoria, int puntaje) throws Exception {
		reserva.getEstadoDeReserva().finalizoLaReserva();
		this.getSitioWeb().estaCategoriaEspecificaInquilino(categoria);
		this.actualizarListaDeRankeoInquilino(new Rankeo(categoria, puntaje));
	}

	@Override
	public void actualizarListaDeRankeoInquilino(Rankeo rankeo) {
		if (this.estaElRank(rankeo,this.getRankeosInquilino())) {

			this.actualizarPuntajeDeRankeo(rankeo, this.getRankeosInquilino());
		}
		else {

			this.getRankeosInquilino().add(rankeo);
		}
		
	}

	@Override
	public void dejarUnComentarioAlInqulino(Reserva reserva, String comentario) throws Exception {
		reserva.getEstadoDeReserva().finalizoLaReserva();
		this.getComentariosInquilino().add(comentario);
	}

	@Override
	public double recibirResarcimiento(double dineroResarcido) {
		return dineroResarcido;
	}

	@Override
	public long cantidadDeVecesQueAlquiloUnPropietarioSusInmuebles() {
		return this.getReservas().stream()
    			.filter(reserva -> {
										try {
											reserva.getEstadoDeReserva().finalizoLaReserva();
											return true;
										} catch (Exception e) {
											return false;
										}
									} ) 
    								.count();
	}

	@Override
	public long cantidadDeVecesQueUnPropietarioAlquiloUnInmueble(Inmueble inmueble) {
		return this.getReservas().stream()
    			.filter(reserva -> {
										try {
											reserva.getEstadoDeReserva().finalizoLaReserva();
											return reserva.getInmueble() == inmueble;
										} catch (Exception e) {
											return false;
										}
									} ) 
    								.count();
	}


	@Override
	public void datosDelPropietario(Inmueble inmueble) {
		this.getNombre();
		this.getMail();
		this.getTelefono();
		this.getComentariosPropietario();
		this.getRankeosPropietario();
		this.calcularPromedioTotal(this.getRankeosPropietario());
		this.cantidadTiempoRegistrado();
		this.cantidadDeVecesQueUnPropietarioAlquiloUnInmueble(inmueble);
		this.cantidadDeVecesQueAlquiloUnPropietarioSusInmuebles();
		this.inmueblesAlquilados();
	}

	@Override
	public long cantidadDeVecesQueAlquiloUnInquilino() {
		
		return this.getReservas().stream()
                			.filter(reserva -> {
													try {
														reserva.getEstadoDeReserva().finalizoLaReserva();
														return true;
													} catch (Exception e) {
														return false;
													}
												} ).count();
	}

	@Override
	public List<Reserva> getReservasFuturas(LocalDate fechaActual){
		return this.getReservas().stream()
		        .filter(reserva -> reserva.getFechaDeIngreso().isAfter(fechaActual))
		        .collect(Collectors.toList());
	}

	@Override
	public List<Reserva> getReservasEnCiudad(String ciudad){
		return this.getReservas().stream()
		        .filter(reserva -> reserva.getInmueble().getCiudad().equalsIgnoreCase(ciudad))
		        .collect(Collectors.toList());
	}

	@Override
	public List<String> getCiudadesReservadas(){
		return this.getReservas().stream()
					.filter(reserva -> reserva.getInquilino().equals(this))
	            	.map(reserva -> reserva.getInmueble().getCiudad())
	            	.distinct()
	            	.collect(Collectors.toList());
	}

	@Override
	public void cancelarReserva(Reserva reserva) throws Exception{

		if (this.getReservas().stream().anyMatch(reser -> reser.equals(reserva))){
			reserva.cancelarReserva();
			this.eliminarReserva(reserva);
		}
		else {
			throw new Exception("La Reserva no le pertenece");
		}
	}

	@Override
	public void dejarUnComentarioAlPropietario(Reserva reserva, String comentario) throws Exception{

		reserva.getEstadoDeReserva().finalizoLaReserva();
		this.getComentariosPropietario().add(comentario);
	}

	@Override
	public void rankearUnPropietario(Reserva reserva, Categoria categoria, int puntaje) throws Exception {
		
		reserva.getEstadoDeReserva().finalizoLaReserva();
		this.getSitioWeb().estaCategoriaEspecificaPropietario(categoria);
		this.actualizarListaDeRankeoPropietario(new Rankeo(categoria, puntaje));
	}

	@Override
	public void actualizarListaDeRankeoPropietario(Rankeo rankeo) {

		if (this.estaElRank(rankeo,this.getRankeosPropietario())) {

			this.actualizarPuntajeDeRankeo(rankeo,this.getRankeosPropietario());
		}
		else {

			this.getRankeosPropietario().add(rankeo);
		}
	}

	@Override
	public void datosDelInquilino() {
		this.getNombre();
		this.getMail();
		this.getTelefono();
		this.getComentariosInquilino();
		this.getRankeosInquilino();
		this.calcularPromedioTotal(this.getRankeosInquilino());
		this.cantidadTiempoRegistrado();
	}
}