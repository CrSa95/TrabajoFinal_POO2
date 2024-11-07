package trabajoFinal.SitioWeb;

public class Rankeo {
	
	private Categoria categoria;
	private int puntaje; 
	private Inmueble inmueble;
	private Usuario usuario;
	
	public Rankeo(Inmueble inmueble, Categoria categoria, int puntaje) {
		this.categoria = categoria;
		this.inmueble = inmueble;
		this.puntaje = puntaje;
	}
	
	public Rankeo(Usuario usuario, Categoria categoria, int puntaje) {
		this.categoria = categoria;
		this.usuario = usuario;
		this.puntaje = puntaje;
	}
	
	public Categoria getCategoria() {
		return this.categoria;
	}
	
	public int getPuntaje() {
		return this.puntaje;
	}
	
	public Inmueble getInmueble() {
		return this.inmueble;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
}
