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
	private List<String> comentariosInquilino = new ArrayList<String>();
	private List<String> comentariosPropietario = new ArrayList<String>();
	private Boolean esInquilino;
	private Boolean esUsuario;
	private SitioWeb sitioWeb;
	
	public Usuario (String nombreCompleto, String mail, int telefono, Boolean esInquilino, Boolean esUsuario, SitioWeb sitioWeb) {
		
		this.nombreCompleto = nombreCompleto;
		this.mail = mail;
		this.telefono = telefono;
		this.esInquilino = esInquilino;
		this.esUsuario = esUsuario;
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
	
	//FUNCIONES DE INQUILINO
	
	public Inmueble seleccionarInmueble(Inmueble inmueble) {
		return inmueble;
	}
	
	public void realizarReservar(Inmueble inmueble, Usuario usuario, FormaDePago formaDePago, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso) {
		
	}
	
	public List<Inmueble> buescarInmuebles(String ciudad, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso,int cantidadDeHuespedes, int precioMinimo, int precioMaximo){
		
	}
	
	public List<Reserva> reservasHechas() throws AccesoDenegadoException {
		if (esInquilino) {
			return this.sitioWeb.getReservas(this);
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public List<Reserva> reservasFuturas() throws AccesoDenegadoException {
		if (esInquilino) {
			return this.sitioWeb.getReservasFuturas(this, LocalDate.now());
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public List<Reserva> reservasEnCiudad(String ciudad) throws AccesoDenegadoException{
		if (esInquilino) {
			return this.sitioWeb.getReservasEnCiudad(this, ciudad);
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public List<String> ciudadesReservadas() throws AccesoDenegadoException{
		if (esInquilino) {
			return this.sitioWeb.getCiudadesReservadas(this);
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public void cancelarReserva(Reserva reserva) throws AccesoDenegadoException{
		if (esInquilino) {
			reserva.cancelarReserva();
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public void setComentarioInmueble(Inmueble inmueble, String comentario) throws AccesoDenegadoException{
		if (esInquilino) {
			inmueble.setComentario(comentario);;
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public void setComentarioPropietario(Usuario usuario, String comentario) throws AccesoDenegadoException {
		if (esInquilino) {
			usuario.setComentarioPropietario(comentario);
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public void rankPropietario(Usuario usuario, List<Categoria> categorias, Categoria categoria, int puntaje) throws AccesoDenegadoException {
		if (esInquilino) {
			
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public void rankInmueble(Inmueble inmueble, List<Categoria> categorias, Categoria categoria, int puntaje) throws AccesoDenegadoException {
		if (esInquilino) {
			
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}
	
	public Optional<FormaDePago> elegirFormaDePago(List<FormaDePago> formasDePago, FormaDePago formaDePago) throws AccesoDenegadoException {
		if (esInquilino) {
			return formasDePago.stream()
					.filter(formaPago -> formaPago == formaDePago)
	                .findFirst();
		}
		else {
			throw new AccesoDenegadoException("Error: Solo los inquilinos pueden realizar esta acción.");
		}
	}

}
