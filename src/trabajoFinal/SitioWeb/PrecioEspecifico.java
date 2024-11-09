package trabajoFinal.SitioWeb;

import java.time.LocalDateTime;

public class PrecioEspecifico {
	
	private int precioPorPeriodo;
	private LocalDateTime fechaInicial;
	private LocalDateTime fechaFinal;
	
	public PrecioEspecifico(LocalDateTime fechaInicial, LocalDateTime fechaFinal, int precio) {
		
		this.precioPorPeriodo = precio;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
	}
	
	public int getPrecioPorPeriodo() {
		return precioPorPeriodo;
	}
	
	public LocalDateTime getFechaInicial() {
		return fechaInicial;
	}
	
	public LocalDateTime getFechaFinal() {
		return fechaFinal;
	}
}
