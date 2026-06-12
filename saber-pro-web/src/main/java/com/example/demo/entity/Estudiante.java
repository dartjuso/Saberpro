package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_documento", length = 5)
    private String tipoDocumento;

    @Column(unique = true, nullable = false)
    private String documento;

    @Column(nullable = false)
    private String password; 

    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    private String correo;
    private String telefono;

    @Column(name = "numero_registro", unique = true, nullable = false)
    private String numeroRegistro;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resultado_id") 
    private Resultado resultado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

    @Column(name = "aprobado")
    private Boolean aprobado = false;

    private String urlPago;
    
    @Column(name = "pago_aprobado")
    private Boolean pagoAprobado = false; 

    private String beneficio;
    
    private boolean pruebaPresentada = false;
    
    // --- CONSTRUCTOR VACÍO (Obligatorio para Hibernate) ---
    public Estudiante() {}

    // --- "SAFETY NET": Evita error de NULL en base de datos ---
    @PrePersist
    protected void onCreate() {
        if (this.password == null || this.password.isEmpty()) {
            this.password = "12345"; // Contraseña por defecto si algo falla
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}

	public Boolean getAprobado() {
		return aprobado;
	}

	public void setAprobado(Boolean aprobado) {
		this.aprobado = aprobado;
	}

	public String getUrlPago() {
		return urlPago;
	}

	public void setUrlPago(String urlPago) {
		this.urlPago = urlPago;
	}

	public Boolean getPagoAprobado() {
		return pagoAprobado;
	}

	public void setPagoAprobado(Boolean pagoAprobado) {
		this.pagoAprobado = pagoAprobado;
	}

	public String getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}

	public boolean isPruebaPresentada() {
		return pruebaPresentada;
	}

	public void setPruebaPresentada(boolean pruebaPresentada) {
		this.pruebaPresentada = pruebaPresentada;
	}

}