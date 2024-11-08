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
	private Boolean esInquilino;
	private Boolean esPropietario;
	private SitioWeb sitioWeb;
	private int cantidadDeVecesQueAlquile = 0;
	private int dineroResarcido;
	
	public Usuario (String nombreCompleto, String mail, int telefono, Boolean esInquilino, Boolean esPropietario, SitioWeb sitioWeb) {
		
		this.nombreCompleto = nombreCompleto;
		this.mail = mail;
		this.telefono = telefono;
		this.esInquilino = esInquilino;
		this.esPropietario = esPropietario;
		this.sitioWeb = sitioWeb;
		this.fechaRegistro = LocalDate.now();
		
	}
	
	//METODOS QUE COMPARTEN AMBOS TIPOS DE USUARIOS
	
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
	
	public Inmueble seleccionarInmueble(Inmueble inmueble) {
		return inmueble;
	}
	
	public void realizarReservar(Inmueble inmueble, Usuario usuario, FormaDePago formaDePago, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso) {
		inmueble.seleccionarFormaDePago(formaDePago);
		
	}
	
	/* Falta implementacion de la busqueda de inmuebles
	public List<Inmueble> buescarInmuebles(String ciudad, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso,int cantidadDeHuespedes, int precioMinimo, int precioMaximo){
		
	}
	*/
	
	public void cancelarReserva(Reserva reserva){
		
		reserva.cancelarReserva();

	}
	
	public void setComentarioInmueble(Inmueble inmueble, String comentario){
		
		inmueble.setComentario(comentario);
	}
	
	public void setComentarioPropietario(Usuario usuario, String comentario) {
		
		usuario.setComentarioPropietario(comentario);
			
	}
	
	public void rankPropietario(Usuario usuario, Categoria categoria, int puntaje) throws CategoriaIncorrectaException {

		if (this.sitioWeb.getCategoriaEspecificaPropietario(categoria)){
			usuario.setRankeosPropietario(new Rankeo(categoria, puntaje));
		}
		else {
				throw new CategoriaIncorrectaException("Error: La categoria seleccionada es incorrecta.");
		}
		
	}
	
	public void rankInmueble(Inmueble inmueble, Categoria categoria, int puntaje) throws CategoriaIncorrectaException  {
		
		if (this.sitioWeb.getCategoriaEspecificaInmueble(categoria)){
			inmueble.setRankeosInmueble(new Rankeo(categoria, puntaje));
		}
		else {
			throw new CategoriaIncorrectaException("Error: La categoria seleccionada es incorrecta.");
		}
	}
	
	//METODOS DE PROPIETARIO
	
	public void altaInmueble(TipoDeInmueble tipoDeInmueble, int superficie, String pais, String ciudad, 
							 String direccion, int capacidad, List<TipoDeServicio> servicios, 
							 List<Foto> fotos, LocalTime checkIn, LocalTime checkOut, List<FormaDePago> formasDePago, 
							 int precioPorDia, PoliticaDeCancelacion politicaDeCancelacion) 
							 throws TipoDeInmuebleIncorrectoException, CantidadDeFotosIncorrectaException{
		
		//Creo el inmueble	
		Inmueble inmueble = new Inmueble(this,superficie, pais, ciudad, 
				 direccion, capacidad, checkIn, checkOut, precioPorDia); 
			
		//Obtengo la lista de servicios que quiero aplicar a mi inmueble
		inmueble.setTipoDeServicios(sitioWeb.seleccionarTiposDeServicio(servicios));
			
		//Obtengo las formas de pago que quiero aplicar a mi inmueble
		inmueble.setFormasDePago(formasDePago);
		
		inmueble.setFotos(fotos);
		
		inmueble.setPoliticaDeCancelacion(politicaDeCancelacion);
		
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
	  
	public void aceptarReserva(SolicitudDeReserva solicitudDeReserva) {
		
		solicitudDeReserva.aceptarReserva();
	
	}
	
	public void rankearInquilino(Usuario usuario, Categoria categoria, int puntaje) throws CategoriaIncorrectaException {
		
		if (this.sitioWeb.getCategoriaEspecificaInquilino(categoria)){
			usuario.setRankeosInquilino(new Rankeo(categoria, puntaje));
		}
		else {
				throw new CategoriaIncorrectaException("Error: La categoria seleccionada es incorrecta.");
		}
		
	}
	
	public void setCantidadDeVecesQueAlquile() {
		
		this.cantidadDeVecesQueAlquile += 1;
	}
	
	public int getCantidadDeVecesQueAlquile() {	
		
		return this.cantidadDeVecesQueAlquile;
	}
	
	public void recibirResarcimiento(int dineroResarcido) {
		this.dineroResarcido = dineroResarcido;
	}
	
}
