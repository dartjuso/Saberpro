package com.example.demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AsociacionRepository asociacionRepository;
    private final CompeticionRepository competicionRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final JugadorRepository jugadorRepository;
    private final ClubRepository clubRepository;

    public DataInitializer(AsociacionRepository asociacionRepository,
                           CompeticionRepository competicionRepository,
                           EntrenadorRepository entrenadorRepository,
                           JugadorRepository jugadorRepository,
                           ClubRepository clubRepository) {
        this.asociacionRepository = asociacionRepository;
        this.competicionRepository = competicionRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.jugadorRepository = jugadorRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        Asociacion asociacion = new Asociacion();
        asociacion.setNombre("Dimayor");
        asociacion.setPais("Colombia");
        asociacion.setPresidente("Fernando Jaramillo");
        asociacionRepository.save(asociacion);

        Entrenador entrenador = new Entrenador();
        entrenador.setNombre("Alberto");
        entrenador.setApellido("Gamero");
        entrenador.setEdad(62);
        entrenador.setNacionalidad("Colombiano");
        entrenadorRepository.save(entrenador);

        Jugador j1 = new Jugador();
        j1.setNombre("Radamel");
        j1.setApellido("Falcao");
        j1.setNumero(9);
        j1.setPosicion("Delantero");
        jugadorRepository.save(j1);

        Jugador j2 = new Jugador();
        j2.setNombre("David");
        j2.setApellido("Silva");
        j2.setNumero(14);
        j2.setPosicion("Mediocampista");
        jugadorRepository.save(j2);

        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(j1);
        listaJugadores.add(j2);

        Competicion comp1 = new Competicion();
        comp1.setNombre("Liga BetPlay");
        comp1.setMontoPremio(500000);
        comp1.setFechaInicio(LocalDate.of(2026, 1, 15));
        comp1.setFechaFin(LocalDate.of(2026, 6, 20));
        competicionRepository.save(comp1);

        Competicion comp2 = new Competicion();
        comp2.setNombre("Copa Colombia");
        comp2.setMontoPremio(200000);
        comp2.setFechaInicio(LocalDate.of(2026, 2, 1));
        comp2.setFechaFin(LocalDate.of(2026, 10, 30));
        competicionRepository.save(comp2);

        List<Competicion> listaCompeticiones = new ArrayList<>();
        listaCompeticiones.add(comp1);
        listaCompeticiones.add(comp2);

        Club club = new Club();
        club.setNombre("Millonarios FC");
        club.setAsociacion(asociacion); 
        club.setEntrenador(entrenador); 
        club.setJugadores(listaJugadores); 
        club.setCompeticiones(listaCompeticiones); 
        
        clubRepository.save(club);

        System.out.println("¡Base de datos H2 inicializada con datos de prueba!");
    }
}