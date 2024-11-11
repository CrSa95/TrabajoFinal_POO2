package trabajoFinal.SitioWeb;

public class Rankeo {
	
	private Categoria categoria;
	private int puntaje; 
	
	public Rankeo(Categoria categoria, int puntaje) {
		this.categoria = categoria;
		this.puntaje = puntaje;
	}
	
	public Categoria getCategoria() {
		return this.categoria;
	}
	
	public int getPuntaje() {
		return this.puntaje;
	}
	
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
}
