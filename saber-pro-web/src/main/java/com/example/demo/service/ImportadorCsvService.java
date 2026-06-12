package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entity.Estudiante;
import com.example.demo.entity.Resultado;


@Service
public class ImportadorCsvService {

    @Autowired
    private EstudianteService estudianteService;

    public void procesarArchivoCsv(MultipartFile archivo) throws Exception {
        System.out.println("Iniciando importación..."); // LOG DE INICIO
        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            boolean primeraLinea = true;
            int contador = 0;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                // Intentamos parsear la línea
                try {
                    String lineaLimpia = linea.replace("\"", "");
                    String[] datos = lineaLimpia.split(",", -1);

                    if (datos.length >= 28) {
                        crearYGuardarEstudiante(datos);
                        contador++;
                    } else {
                        System.out.println("Línea ignorada por formato incorrecto: " + linea);
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando línea: " + linea);
                    e.printStackTrace(); // Esto te dirá EXACTAMENTE qué falló
                }
            }
            System.out.println("Importación finalizada. Estudiantes procesados: " + contador);
        }
    }

    private void crearYGuardarEstudiante(String[] datos) {
        String numeroRegistro = datos[8].trim();

        if (estudianteService.buscarPorNumeroRegistro(numeroRegistro).isPresent()) {
            System.out.println("Estudiante ya existe, saltando: " + numeroRegistro);
            return;
        }

        try {
            Estudiante est = new Estudiante();
            est.setTipoDocumento(datos[0].trim());
            
            // Si el documento es vacío, usa el número de registro
            String documento = datos[1].trim();
            est.setDocumento(documento.isEmpty() ? numeroRegistro : documento);

            est.setPrimerApellido(datos[2].trim());
            // ... (resto de tus sets) ...
            est.setNumeroRegistro(numeroRegistro);

            Resultado res = new Resultado();
            res.setPuntajeGlobal(parsearEntero(datos[9]));
            // ... (resto de tus sets de Resultado) ...

            res.setEstudiante(est);
            est.setResultado(res);

            estudianteService.guardar(est);
            System.out.println("Guardado exitosamente: " + numeroRegistro);
        } catch (Exception e) {
            System.err.println("Fallo al guardar estudiante con registro " + numeroRegistro + ": " + e.getMessage());
            throw e; // Relanzamos para verlo en el log de la línea
        }
    }

    private Integer parsearEntero(String valor) {
        try {
            return Integer.parseInt(valor.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}