package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "resultados")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Puntaje Global
    @Column(name = "puntaje_global")
    private Integer puntajeGlobal;
    @Column(name = "nivel_global")
    private String nivelGlobal;

    // Comunicación Escrita
    @Column(name = "puntaje_comunicacion")
    private Integer puntajeComunicacion;
    @Column(name = "nivel_comunicacion")
    private String nivelComunicacion;

    // Razonamiento Cuantitativo
    @Column(name = "puntaje_razonamiento")
    private Integer puntajeRazonamiento;
    @Column(name = "nivel_razonamiento")
    private String nivelRazonamiento;

    // Lectura Crítica
    @Column(name = "puntaje_lectura")
    private Integer puntajeLectura;
    @Column(name = "nivel_lectura")
    private String nivelLectura;

    // Competencias Ciudadanas
    @Column(name = "puntaje_ciudadanas")
    private Integer puntajeCiudadanas;
    @Column(name = "nivel_ciudadanas")
    private String nivelCiudadanas;

    // Inglés
    @Column(name = "puntaje_ingles")
    private Integer puntajeIngles;
    @Column(name = "nivel_ingles") // Ej: A1, B1, B2
    private String nivelIngles;

    // Módulos Específicos de Ingeniería
    @Column(name = "puntaje_proyectos")
    private Integer puntajeProyectos;
    @Column(name = "nivel_proyectos")
    private String nivelProyectos;

    @Column(name = "puntaje_pensamiento")
    private Integer puntajePensamiento;
    @Column(name = "nivel_pensamiento")
    private String nivelPensamiento;

    @Column(name = "puntaje_diseno_software")
    private Integer puntajeDisenoSoftware;
    @Column(name = "nivel_diseno_software")
    private String nivelDisenoSoftware;

    // Relación: Este resultado pertenece a un estudiante específico
    @OneToOne
    @JoinColumn(name = "estudiante_id", referencedColumnName = "id")
    private Estudiante estudiante;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPuntajeGlobal() {
		return puntajeGlobal;
	}

	public void setPuntajeGlobal(Integer puntajeGlobal) {
		this.puntajeGlobal = puntajeGlobal;
	}

	public String getNivelGlobal() {
		return nivelGlobal;
	}

	public void setNivelGlobal(String nivelGlobal) {
		this.nivelGlobal = nivelGlobal;
	}

	public Integer getPuntajeComunicacion() {
		return puntajeComunicacion;
	}

	public void setPuntajeComunicacion(Integer puntajeComunicacion) {
		this.puntajeComunicacion = puntajeComunicacion;
	}

	public String getNivelComunicacion() {
		return nivelComunicacion;
	}

	public void setNivelComunicacion(String nivelComunicacion) {
		this.nivelComunicacion = nivelComunicacion;
	}

	public Integer getPuntajeRazonamiento() {
		return puntajeRazonamiento;
	}

	public void setPuntajeRazonamiento(Integer puntajeRazonamiento) {
		this.puntajeRazonamiento = puntajeRazonamiento;
	}

	public String getNivelRazonamiento() {
		return nivelRazonamiento;
	}

	public void setNivelRazonamiento(String nivelRazonamiento) {
		this.nivelRazonamiento = nivelRazonamiento;
	}

	public Integer getPuntajeLectura() {
		return puntajeLectura;
	}

	public void setPuntajeLectura(Integer puntajeLectura) {
		this.puntajeLectura = puntajeLectura;
	}

	public String getNivelLectura() {
		return nivelLectura;
	}

	public void setNivelLectura(String nivelLectura) {
		this.nivelLectura = nivelLectura;
	}

	public Integer getPuntajeCiudadanas() {
		return puntajeCiudadanas;
	}

	public void setPuntajeCiudadanas(Integer puntajeCiudadanas) {
		this.puntajeCiudadanas = puntajeCiudadanas;
	}

	public String getNivelCiudadanas() {
		return nivelCiudadanas;
	}

	public void setNivelCiudadanas(String nivelCiudadanas) {
		this.nivelCiudadanas = nivelCiudadanas;
	}

	public Integer getPuntajeIngles() {
		return puntajeIngles;
	}

	public void setPuntajeIngles(Integer puntajeIngles) {
		this.puntajeIngles = puntajeIngles;
	}

	public String getNivelIngles() {
		return nivelIngles;
	}

	public void setNivelIngles(String nivelIngles) {
		this.nivelIngles = nivelIngles;
	}

	public Integer getPuntajeProyectos() {
		return puntajeProyectos;
	}

	public void setPuntajeProyectos(Integer puntajeProyectos) {
		this.puntajeProyectos = puntajeProyectos;
	}

	public String getNivelProyectos() {
		return nivelProyectos;
	}

	public void setNivelProyectos(String nivelProyectos) {
		this.nivelProyectos = nivelProyectos;
	}

	public Integer getPuntajePensamiento() {
		return puntajePensamiento;
	}

	public void setPuntajePensamiento(Integer puntajePensamiento) {
		this.puntajePensamiento = puntajePensamiento;
	}

	public String getNivelPensamiento() {
		return nivelPensamiento;
	}

	public void setNivelPensamiento(String nivelPensamiento) {
		this.nivelPensamiento = nivelPensamiento;
	}

	public Integer getPuntajeDisenoSoftware() {
		return puntajeDisenoSoftware;
	}

	public void setPuntajeDisenoSoftware(Integer puntajeDisenoSoftware) {
		this.puntajeDisenoSoftware = puntajeDisenoSoftware;
	}

	public String getNivelDisenoSoftware() {
		return nivelDisenoSoftware;
	}

	public void setNivelDisenoSoftware(String nivelDisenoSoftware) {
		this.nivelDisenoSoftware = nivelDisenoSoftware;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}
}