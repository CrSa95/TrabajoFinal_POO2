package trabajoFinal.SitioWeb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
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
	private Boolean esInquilino;
	private Boolean esPropietario;
	private SitioWeb sitioWeb;
	
	public Usuario (String nombreCompleto, String mail, int telefono, Boolean esInquilino, Boolean esPropietario, SitioWeb sitioWeb) {
		
		this.nombreCompleto = nombreCompleto;
		this.mail = mail;
		this.telefono = telefono;
		this.esInquilino = esInquilino;
		this.esPropietario = esPropietario;
		this.sitioWeb = sitioWeb;
		this.fechaRegistro = LocalDate.now();
		
	}
	
	//FUNCIONES QUE COMPARTEN AMBOS
	
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
	
	//FUNCIONES DE INQUILINO
	
	public Inmueble seleccionarInmueble(Inmueble inmueble) {
		return inmueble;
	}
	
	public void realizarReservar(Inmueble inmueble, Usuario usuario, FormaDePago formaDePago, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso) {
		Optional<FormaDePago> formaDePagoSeleccionada = inmueble.seleccionarFormaDePago(formaDePago);
		
	}
	
	/* Falta implementacion de la busqueda de inmuebles
	public List<Inmueble> buescarInmuebles(String ciudad, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso,int cantidadDeHuespedes, int precioMinimo, int precioMaximo){
		
	}
	*/
	
	public List<Reserva> reservasHechas() throws AccesoDenegadoException {
		if (this.esInquilino) {
			return this.sitioWeb.getReservas(this);
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public List<Reserva> reservasFuturas() throws AccesoDenegadoException {
		if (this.esInquilino) {
			return this.sitioWeb.getReservasFuturas(this, LocalDate.now());
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public List<Reserva> reservasEnCiudad(String ciudad) throws AccesoDenegadoException{
		if (this.esInquilino) {
			return this.sitioWeb.getReservasEnCiudad(this, ciudad);
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public List<String> ciudadesReservadas() throws AccesoDenegadoException{
		if (this.esInquilino) {
			return this.sitioWeb.getCiudadesReservadas(this);
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public void cancelarReserva(Reserva reserva) throws AccesoDenegadoException{
		if (this.esInquilino) {
			reserva.cancelarReserva();
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public void setComentarioInmueble(Inmueble inmueble, String comentario) throws AccesoDenegadoException{
		if (this.esInquilino) {
			inmueble.setComentario(comentario);;
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public void setComentarioPropietario(Usuario usuario, String comentario) throws AccesoDenegadoException {
		if (this.esInquilino && usuario.esPropietario) {
			usuario.setComentarioPropietario(comentario);
		}
		else {
			throw new AccesoDenegadoException("Error: Los inquilinos solo pueden rankear propietarios.");
		}
	}
	
	public void rankPropietario(Usuario usuario, Categoria categoria, int puntaje) throws AccesoDenegadoException, CategoriaIncorrectaException {
		if (this.esInquilino && usuario.esPropietario) {
			if (this.sitioWeb.getCategoriaEspecifica(categoria)){
				usuario.setRankeosPropietario(new Rankeo(usuario, categoria, puntaje));
			}
			else {
				throw new CategoriaIncorrectaException("Error: La categoria seleccionada es incorrecta.");
			}
		}
		else {
			throw new AccesoDenegadoException("Error: Los inquilinos solo pueden rankear propietarios.");
		}
	}
	
	public void rankInmueble(Inmueble inmueble, Categoria categoria, int puntaje) throws AccesoDenegadoException, CategoriaIncorrectaException  {
		
		if (this.esInquilino) {
			if (this.sitioWeb.getCategoriaEspecifica(categoria)){
				inmueble.setRankeosInmueble(new Rankeo(inmueble, categoria, puntaje));
			}
			else {
				throw new CategoriaIncorrectaException("Error: La categoria seleccionada es incorrecta.");
			}
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public Optional<FormaDePago> elegirFormaDePago(List<FormaDePago> formasDePago, FormaDePago formaDePago) throws AccesoDenegadoException {
		if (this.esInquilino) {
			return formasDePago.stream()
					.filter(formaPago -> formaPago == formaDePago)
	                .findFirst();
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	//FUNCIONES DE PROPIETARIO
	
	public void altaInmueble(String tipoDeInmueble, int superficie, String pais, String ciudad, 
							 String direccion, int capacidad, List<String> servicios,
							 String fotos, LocalDate checkIn, LocalDate checkOut, List<FormaDePago> formasDePago, 
							 int precioPorDia) throws AccesoDenegadoException {
		
		if (this.esPropietario) {
			
			//Obtengo la lista de servicios que quiero aplicar a mi inmueble
			this.tiposDeServicios = this.sitioWeb.seleccionarTiposDeServicio(servicios);
			
			//Obtengo las formas de pago que quiero aplicar a mi inmueble
			this.formasDePago = this.sitioWeb.seleccionarFormasDePago(formasDePago);
			
			//Obtengo el tipo de inmueble que quiero aplicar a mi inmueble
			this.tipoDeInmueble = this.sitioWeb.seleccionarTipoDeInmueble(tipoDeInmueble);
			
			//Creo el inmueble
			Inmueble inmueble = new Inmueble(this.tipoDeInmueble, superficie, pais, ciudad, 
					 direccion, this.tiposDeServicios, capacidad, 
					 fotos, checkIn, checkOut, this.formasDePago, precioPorDia); 
			
			//Lo doy de alta en el sitio
			this.sitioWeb.altaInmueble(inmueble);
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los propietarios pueden realizar esta acción.");
		}		
		
	}
	
	/*
	  
	public void aceptarReserva(Reserva) throws AccesoDenegadoException {
		if (this.esPropietario) {
			
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los propietarios pueden realizar esta acción.");
		}		
	}
	
	public void rankearInquilino(Usuario usuario, Categoria categoria, int puntaje) throws AccesoDenegadoException, CategoriaIncorrectaException {
		if (this.esPropietario && usuario.esInquilino) {
			
		}
		else {
			throw new AccesoDenegadoException("Error: Los propietarios solo pueden rankear inquilinos.");
		}		
	}
	
	public int cantidadTotalDeInmueblesAlquialdos() throws AccesoDenegadoException {
		if (this.esPropietario) {
			
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los propietarios pueden realizar esta acción.");
		}		
	}
	
	*/
}
