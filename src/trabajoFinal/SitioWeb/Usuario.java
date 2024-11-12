package trabajoFinal.SitioWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

public class Usuario {
	
	private String nombreCompleto;
	private String mail;
	private int telefono;
	private LocalDate fechaRegistro;
	private List<String> tiposDeServicios;
	private List<FormaDePago> formasDePago;
	private Optional<String> tipoDeInmueble;
	private List<String> comentariosInquilino = new ArrayList<String>();
	private List<String> comentariosPropietario = new ArrayList<String>();
	private List<Rankeo> rankeosInquilino = new ArrayList<Rankeo>();
	private List<Rankeo> rankeosPropietario = new ArrayList<Rankeo>();
	private List<Reserva> reservas = new ArrayList<Reserva>();
	private SitioWeb sitioWeb;
	private int cantidadDeVecesQueAlquile = 0;
	private double dineroResarcido;
	private List<Inmueble> inmueblesBuscados;
	private int puntajePromedio;
	
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
	
	public LocalDate getFechaDeRegistro() {
		return this.fechaRegistro;
	}
	
	public String getNombre() {
		return this.nombreCompleto;
	}
	
	public void setPuntajePromedio(int promedio) {
		this.puntajePromedio = promedio;
	}
	
	public int getPuntajePromedio() {
		return this.puntajePromedio;
	}
	
	public void registrarReserva(Reserva reserva) {
		this.reservas.add(reserva);
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
		Period periodo = Period.between(fechaRegistro, LocalDate.now());
		return periodo.getYears();
	}
	
	public String getMail() {
		return mail;
	}
	
	public String recibirMail(String mail){
		return "Mail Recibido";
	}
	
	public void enviarMail(String mail, Usuario usuario){
		usuario.recibirMail(mail);
	}
	
	public void setComentarioInquilino(String comentario){
		comentariosInquilino.add(comentario);
	}
	
	public List<String> getComentariosInquilino(){
		return comentariosInquilino;
	}
	
	public void setComentarioPropietario(String comentario){
		comentariosPropietario.add(comentario);
	}
	
	public List<String> getComentariosPropietario(){
		return comentariosPropietario;
	}
	
	public void setRankeosPropietario(Rankeo rankeo){
		this.rankeosPropietario.add(rankeo);
	}
	
	public List<Rankeo> getRankeosPropietario(){
		return this.rankeosPropietario;
	}
	
	public void setRankeosInquilino(Rankeo rankeo){
		this.rankeosInquilino.add(rankeo);
	}
	
	public List<Rankeo> getRankeosInquilino(){
		return this.rankeosInquilino;
	}
	
	//METODOS DE INQUILINO
	
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
	
	public void realizarReservar(Inmueble inmueble, Usuario usuario, FormaDePago formaDePago, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso) {
		inmueble.seleccionarFormaDePago(formaDePago);
		inmueble.realizarReservaDelInmueble(usuario, formaDePago, fechaDeIngreso, fechaDeEgreso);
	}
	
	public void buscarInmuebles(String ciudad, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso,int cantidadDeHuespedes, double precioMinimo, double precioMaximo){
		this.inmueblesBuscados = this.sitioWeb.buscarInmuebles(ciudad, fechaDeIngreso, fechaDeEgreso, cantidadDeHuespedes, precioMinimo, precioMaximo);
	}
	
	public void visualizarInmueble(Inmueble inmueble, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso) {
		Optional<Inmueble> inmuebleSeleccionado = inmueblesBuscados.stream().filter(unInmueble -> unInmueble.equals(inmueble)).findFirst();
		inmuebleSeleccionado.get().getCapacidad();
		inmuebleSeleccionado.get().getCiudad();
		inmuebleSeleccionado.get().getComentarios();
		inmuebleSeleccionado.get().getRankeosInmueble();
		inmuebleSeleccionado.get().getPromedioPuntajeTotal();
		inmuebleSeleccionado.get().setDisponibilidad(fechaDeIngreso, fechaDeEgreso);
		inmuebleSeleccionado.get().calcularPrecio(fechaDeIngreso, fechaDeEgreso);
		inmuebleSeleccionado.get().getPrecioTotalCalculado();
		inmuebleSeleccionado.get().getPropietario().getComentariosPropietario();
		inmuebleSeleccionado.get().getPropietario().getPuntajePromedio();
		inmuebleSeleccionado.get().getPropietario().cantidadTiempoRegistrado();
		//cantidad de veces que alquilo
		//y cuales han sido, se necesita una lista de inmuebles alquilados??
	}
	
	public Optional<Inmueble> seleccionarInmueble(Inmueble inmueble) {
		return this.inmueblesBuscados.stream().filter(unInmueble -> unInmueble.equals(inmueble)).findFirst();
	}
	
	public void cancelarReserva(Reserva reserva){
		
		reserva.cancelarReserva();

	}
	
	//Funciones que se deben realizar luego de finalizar la reserva
	
	public void setComentarioInmueble(Reserva reserva, Inmueble inmueble, String comentario) throws ReservaNoFinalizadaException{
		
		if (reserva.getEstadoDeReserva().equals(new EstadoFinalizada())) {
			inmueble.setComentario(comentario);
		}
		else {
			throw new ReservaNoFinalizadaException("Error: La Reserva aun no finalizo.");
		}
		
	}
	
	public void setComentarioPropietario(Reserva reserva, Usuario usuario, String comentario) throws ReservaNoFinalizadaException{
		
		if (reserva.getEstadoDeReserva().equals(new EstadoFinalizada())) {
			usuario.setComentarioPropietario(comentario);
		}
		else {
			throw new ReservaNoFinalizadaException("Error: La Reserva aun no finalizo.");
		}
			
	}
	
	public void rankPropietario(Reserva reserva, Usuario usuario, Categoria categoria, int puntaje) throws CategoriaIncorrectaException, ReservaNoFinalizadaException {
		if (reserva.getEstadoDeReserva().equals(new EstadoFinalizada())) {
			
			if (this.sitioWeb.getCategoriaEspecificaPropietario(categoria)){
				usuario.setRankeosPropietario(new Rankeo(categoria, puntaje));
			}
			else {
					throw new CategoriaIncorrectaException("Error: La categoria seleccionada es incorrecta.");
			}
		}
		else {
			throw new ReservaNoFinalizadaException("Error: La Reserva aun no finalizo.");
		}
	}
	
	public void rankInmueble(Reserva reserva, Inmueble inmueble, Categoria categoria, int puntaje) throws CategoriaIncorrectaException, ReservaNoFinalizadaException  {
		
		if (reserva.getEstadoDeReserva().equals(new EstadoFinalizada())) {
			if (this.sitioWeb.getCategoriaEspecificaInmueble(categoria)){
				inmueble.setRankeosInmueble(new Rankeo(categoria, puntaje));
			}
			else {
				throw new CategoriaIncorrectaException("Error: La categoria seleccionada es incorrecta.");
			}	
		}
		else {
			throw new ReservaNoFinalizadaException("Error: La Reserva aun no finalizo.");
		}
		
	}
	
	//METODOS DE PROPIETARIO
	
	public void altaInmueble(TipoDeInmueble tipoDeInmueble, int superficie, String pais, String ciudad, 
							 String direccion, List<TipoDeServicio> servicios, int capacidad, 
							 List<Foto> fotos, LocalTime checkIn, LocalTime checkOut, List<FormaDePago> formasDePago, 
							 int precioPorDia, PoliticaDeCancelacion politicaDeCancelacion, double precioBase,
							 List<PrecioEspecifico> preciosEspecificos, LocalDate fechaInicial, LocalDate fechaFinal) 
							 throws TipoDeInmuebleIncorrectoException, CantidadDeFotosIncorrectaException{
		
		//Creo el inmueble	
		Inmueble inmueble = new Inmueble(this,superficie, pais, ciudad, 
				 direccion, capacidad, checkIn, checkOut, preciosEspecificos, precioBase); 
			
		//Obtengo la lista de servicios que quiero aplicar a mi inmueble
		inmueble.setTipoDeServicios(sitioWeb.seleccionarTiposDeServicio(servicios));
			
		//Obtengo las formas de pago que quiero aplicar a mi inmueble
		inmueble.setFormasDePago(formasDePago);
		
		inmueble.setFotos(fotos);
		
		inmueble.setPoliticaDeCancelacion(politicaDeCancelacion);
		
		inmueble.setDisponibilidad(fechaInicial, fechaFinal);
		
		//Obtengo el tipo de inmueble que quiero aplicar a mi inmueble
		if (sitioWeb.seleccionarTipoDeInmueble(tipoDeInmueble)) {
			inmueble.setTipoDeInmueble(tipoDeInmueble);
		}
		else {
			throw new TipoDeInmuebleIncorrectoException("Error: El tipo de inmueble seleccionado es incorrecto.");
		}
		
		//Lo doy de alta en el sitio
		this.sitioWeb.altaInmueble(inmueble);
	}
	  
	public void recibirSolicitudReserva(SolicitudDeReserva solicitudDeReserva) {
		
		solicitudDeReserva.aprobarSolicitud();
	
	}
	
	public void rankearInquilino(Reserva reserva, Usuario usuario, Categoria categoria, int puntaje) throws CategoriaIncorrectaException, ReservaNoFinalizadaException {
		
		if (reserva.getEstadoDeReserva().equals(new EstadoFinalizada())) {
			
			if (this.sitioWeb.getCategoriaEspecificaInquilino(categoria)){
				
				this.actualizarListaDeRaneko(usuario, new Rankeo(categoria, puntaje));
			}
			else {
				throw new CategoriaIncorrectaException("Error: La categoria seleccionada es incorrecta.");
			}
		}
		
		else {
			throw new ReservaNoFinalizadaException("Error: La Reserva aun no finalizo.");
		}
		
	}
	
	public void actualizarListaDeRaneko(Usuario usuario, Rankeo rankeo) {
		
		if (this.estaElRank(rankeo)) {
			
			this.actualizarPuntajeDeRankeo(rankeo);
		}
		else {
			
			usuario.setRankeosInquilino(rankeo);
		}
	}
	
	public Boolean estaElRank(Rankeo rankeo) {
		return rankeosInquilino.stream()
				.anyMatch(rank -> rank.getCategoria().nombreCategoria().equals
						 (rankeo.getCategoria().nombreCategoria()));
	}
	
	public void actualizarPuntajeDeRankeo(Rankeo rankeo) {
		
		Optional<Rankeo> rankeoViejo = rankeosInquilino.stream()
			    									   .filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria())).findFirst();
				
		rankeosInquilino.stream()
	    				.filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria())) 
	    				.forEach(rank -> rank.setPuntaje((rankeoViejo.get().getPuntaje() + rankeo.getPuntaje()) / 2));
	}
	
	public void setComentarioInqulino(Reserva reserva, Usuario usuario, String comentario) throws ReservaNoFinalizadaException{
		
		if (reserva.getEstadoDeReserva().equals(new EstadoFinalizada())) {
			usuario.setComentarioInquilino(comentario);
		}
		else {
			throw new ReservaNoFinalizadaException("Error: La Reserva aun no finalizo.");
		}
			
	}
	
	public void setCantidadDeVecesQueAlquile() {
		
		this.cantidadDeVecesQueAlquile += 1;
	}
	
	public int getCantidadDeVecesQueAlquile() {	
		
		return this.cantidadDeVecesQueAlquile;
	}
	
	public void recibirResarcimiento(double dineroResarcido) {
		this.dineroResarcido = dineroResarcido;
	}

	public void restarCantidadDeVecesQueAlquile() {
		// TODO Auto-generated method stub
		
	}
	
}
