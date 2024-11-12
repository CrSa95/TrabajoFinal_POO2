package trabajoFinal.SitioWeb;

import java.time.LocalDate;

public class PrecioEspecifico {

	private double precioPorPeriodo;
	private LocalDate fechaInicial;
	private LocalDate fechaFinal;

	public PrecioEspecifico(LocalDate fechaInicial, LocalDate fechaFinal, double precio) {

		this.precioPorPeriodo = precio;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
	}

	public double getPrecioPorPeriodo() {
		return precioPorPeriodo;
	}

	public LocalDate getFechaInicial() {
		return fechaInicial;
	}

	public LocalDate getFechaFinal() {
		return fechaFinal;
	}
}
