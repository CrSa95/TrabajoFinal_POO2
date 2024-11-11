package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PrecioEspecifico {
	
	private int precioPorPeriodo;
	private LocalDate fechaInicial;
	private LocalDate fechaFinal;
	
	public PrecioEspecifico(LocalDate fechaInicial, LocalDate fechaFinal, int precio) {
		
		this.precioPorPeriodo = precio;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
	}
	
	public int getPrecioPorPeriodo() {
		return precioPorPeriodo;
	}
	
	public LocalDate getFechaInicial() {
		return fechaInicial;
	}
	
	public LocalDate getFechaFinal() {
		return fechaFinal;
	}
}
