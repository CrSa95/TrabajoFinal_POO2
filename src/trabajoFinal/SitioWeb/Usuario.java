package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Usuario {

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
	private int cantidadDeVecesQueAlquilo = 0;
	private List<Inmueble> inmueblesDadosDeAlta = new ArrayList<>();
	private String contenidoMail;

	public Usuario (String nombreCompleto, String mail, int telefono) {

		this.nombreCompleto = nombreCompleto;
		this.mail = mail;
		this.telefono = telefono;

	}

	//METODOS QUE COMPARTEN AMBOS TIPOS DE USUARIOS
 
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
	
	//METODOS DE INQUILINO

	public long cantidadDeVecesQueAlquiloUnInquilino() {
		return this.reservas.stream()
                			.filter(reserva -> reserva.getEstadoDeReserva()  instanceof EstadoFinalizada ) 
                			.count();
	}
	
	public List<Inmueble> buscarInmuebles(String ciudad, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso,int cantidadDeHuespedes, double precioMinimo, double precioMaximo){

		return this.sitioWeb.buscarInmuebles(ciudad, fechaDeIngreso, fechaDeEgreso, cantidadDeHuespedes, precioMinimo, precioMaximo);
	}
	
	public void visualizarInmueble(Inmueble inmueble){

		inmueble.datosDelInmueble();
		inmueble.getPropietario().datosDelPropietario(inmueble);
	}
	
	public void realizarReservar(Inmueble inmueble, Usuario usuario, FormaDePago formaDePago, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso) {
		inmueble.realizarReservaDelInmueble(usuario, formaDePago, fechaDeIngreso, fechaDeEgreso);
	}
	
	public List<Rankeo> getRankeosInquilino(){
		return this.rankeosInquilino;
	}

	public List<String> getComentariosInquilino(){
		return comentariosInquilino; 
	}

	public List<Reserva> getReservas(){
		return reservas.stream()
		        		.filter(reserva -> reserva.getUsuario().equals(this))
		        		.collect(Collectors.toList());
	}

	public List<Reserva> getReservasFuturas(LocalDate fechaActual){
		return reservas.stream()
		        .filter(reserva -> reserva.getUsuario().equals(this)
		        				   &&
		        				   reserva.getFechaDeIngreso().isAfter(fechaActual))
		        .collect(Collectors.toList());
	}

	public List<Reserva> getReservasEnCiudad(String ciudad){
		return reservas.stream()
		        .filter(reserva -> reserva.getUsuario().equals(this)
		        				   &&
		        				   reserva.getInmueble().getCiudad().equalsIgnoreCase(ciudad))
		        .collect(Collectors.toList());
	}

	public List<String> getCiudadesReservadas(){
		return reservas.stream()
					.filter(reserva -> reserva.getUsuario().equals(this))
	            	.map(reserva -> reserva.getInmueble().getCiudad())
	            	.distinct()
	            	.collect(Collectors.toList());
	}

	public void cancelarReserva(Reserva reserva) {

		if (reservas.stream().anyMatch(reser -> reser.equals(reserva))){
			reserva.cancelarReserva();
			this.eliminarReserva(reserva);
		}
	}

	//Funciones que se deben realizar luego de finalizar la reserva

	public void setComentarioInmueble(Reserva reserva, Inmueble inmueble, String comentario) throws Exception{

		if (reserva.getEstadoDeReserva() instanceof EstadoFinalizada) {
			inmueble.setComentario(comentario);
		}
		else {
			throw new Exception("Error: La Reserva aun no finalizo.");
		}

	}

	public void setComentarioPropietario(Reserva reserva, String comentario) throws Exception{

		if (reserva.getEstadoDeReserva() instanceof EstadoFinalizada) {
			this.comentariosPropietario.add(comentario);
		}
		else {
			throw new Exception("Error: La Reserva aun no finalizo.");
		}
	}

	public void rankPropietario(Reserva reserva, Categoria categoria, int puntaje) throws Exception {
		if (reserva.getEstadoDeReserva() instanceof EstadoFinalizada) {

			if (this.sitioWeb.getCategoriaEspecificaPropietario(categoria)){
				this.actualizarListaDeRankeoPropietario(new Rankeo(categoria, puntaje));
			}
			else {
					throw new Exception("Error: La categoria seleccionada es incorrecta.");
			}
		}
		else {
			throw new Exception("Error: La Reserva aun no finalizo.");
		}
	}
	
	public void actualizarListaDeRankeoPropietario(Rankeo rankeo) {

		if (this.estaElRank(rankeo,this.rankeosPropietario)) {

			this.actualizarPuntajeDeRankeo(rankeo, this.rankeosPropietario);
		}
		else {

			this.rankeosPropietario.add(rankeo);
		}
	}

	public void rankInmueble(Reserva reserva, Inmueble inmueble, Categoria categoria, int puntaje) throws Exception  {

		if (reserva.getEstadoDeReserva() instanceof EstadoFinalizada) {
			if (this.sitioWeb.getCategoriaEspecificaInmueble(categoria)){
				inmueble.actualizarListaDeRaneko(new Rankeo(categoria, puntaje));
			}
			else {
				throw new Exception("Error: La categoria seleccionada es incorrecta.");
			}
		}
		else {
			throw new Exception("Error: La Reserva aun no finalizo.");
		}

	}
	
	public void datosDelInquilino() {
		this.getNombre();
		this.getMail();
		this.getTelefono();
		this.getComentariosInquilino();
		this.getRankeosInquilino();
		this.calcularPromedioTotal(this.rankeosInquilino);
		this.cantidadTiempoRegistrado();
	}

	//METODOS DE PROPIETARIO
	public void altaInmueble(TipoDeInmueble tipoDeInmueble, int superficie, String pais, String ciudad,
			 				 String direccion, List<TipoDeServicio> servicios, int capacidad,
			 				 List<Foto> fotos, LocalTime checkIn, LocalTime checkOut,
			 				 PoliticaDeCancelacion politicaDeCancelacion, double precioBase,
			 				 LocalDate fechaInicial, LocalDate fechaFinal) throws Exception{
		//Creo el inmueble
		Inmueble inmueble = new Inmueble(this,superficie, pais, ciudad,
		direccion, capacidad, checkIn, checkOut, precioBase);
			
		//Obtengo el tipo de inmueble que quiero aplicar a mi inmueble
		inmueble.setTipoDeInmueble(sitioWeb.seleccionarTipoDeInmueble(tipoDeInmueble));
		
		//Obtengo la lista de servicios que quiero aplicar a mi inmueble
		inmueble.setTipoDeServicios(sitioWeb.seleccionarTiposDeServicio(servicios));
		
		inmueble.setFotos(fotos);
			
		inmueble.setPoliticaDeCancelacion(politicaDeCancelacion);
			
		inmueble.setDisponibilidad(fechaInicial, fechaFinal);
			
		//Lo doy de alta en el sitio
		this.sitioWeb.altaInmueble(inmueble);
			
		//Lo agrego a la lista de inmuebles dados de alta
		this.inmueblesDadosDeAlta.add(inmueble);
	}
	
	public void actualizarPrecioBase(Inmueble inmueble, int precioNuevo) {
		inmueble.modificarPrecioBase(precioNuevo);
	}
	
	public void modificarPreciosEspecificos(Inmueble inmueble, LocalDate fechaInicial, LocalDate fechaFinal, int precio) throws Exception {
		inmueble.modificarPreciosEspecificos(fechaInicial, fechaFinal, precio);
	}
	
	public void modificarFormasDePago(Inmueble inmueble,FormaDePago formaDePago) {
		inmueble.modificarFormasDePago(formaDePago);
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

	public List<Inmueble> inmueblesAlquilados() {
		return this.inmueblesDadosDeAlta.stream().filter(inmueble -> inmueble.getCantidadDeVecesAlquilado() > 0 ).toList();
	}

	public void recibirSolicitudReserva(SolicitudDeReserva solicitudDeReserva) {
		solicitudDeReserva.aprobarSolicitud();

	}

	public void rankearInquilino(Reserva reserva, Categoria categoria, int puntaje) throws Exception {
		
		if (reserva.getEstadoDeReserva() instanceof EstadoFinalizada) {
			
			if (this.sitioWeb.getCategoriaEspecificaInquilino(categoria)){

				this.actualizarListaDeRankeoInquilino(new Rankeo(categoria, puntaje));
			}
			else {
				throw new Exception("Error: La categoria seleccionada es incorrecta.");
			}
		}
		else {
			throw new Exception("Error: La Reserva aun no finalizo.");
		}

	}

	public void actualizarListaDeRankeoInquilino(Rankeo rankeo) {

		if (this.estaElRank(rankeo,this.rankeosInquilino)) {

			this.actualizarPuntajeDeRankeo(rankeo, this.rankeosInquilino);
		}
		else {

			this.rankeosInquilino.add(rankeo);
		}
	}
	
	public void setComentarioInqulino(Reserva reserva, String comentario) throws Exception{

		if (reserva.getEstadoDeReserva()instanceof EstadoFinalizada) {
			this.comentariosInquilino.add(comentario);
		}
		else {
			throw new Exception("Error: La Reserva aun no finalizo.");
		}

	}
	
	public double recibirResarcimiento(double dineroResarcido) {
		return dineroResarcido;
	}

	public int getCantidadDeVecesQueAlquilo() {	
		
		return this.cantidadDeVecesQueAlquilo;
	}
	
	public void sumarCantidadDeVecesQueAlquilo() {

        this.cantidadDeVecesQueAlquilo += 1;
    }

    public void restarCantidadDeVecesQueAlquilo() {

        this.cantidadDeVecesQueAlquilo -= 1;
    }

	public void datosDelPropietario(Inmueble inmueble) {
		this.getNombre();
		this.getMail();
		this.getTelefono();
		this.getComentariosPropietario();
		this.getRankeosPropietario();
		this.calcularPromedioTotal(this.rankeosPropietario);
		this.cantidadTiempoRegistrado();
		inmueble.getCantidadDeVecesAlquilado();
		this.getCantidadDeVecesQueAlquilo();
		this.inmueblesAlquilados();
	}
}
