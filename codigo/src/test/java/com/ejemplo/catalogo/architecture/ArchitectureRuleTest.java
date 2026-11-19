package com.ejemplo.catalogo.architecture;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Verificación formal de las reglas de dependencias de la Arquitectura Hexagonal:
 * 1. Nada bajo domain importa org.springframework ni jakarta.
 * 2. Nada bajo application importa adaptadores (com.ejemplo.catalogo.adapter).
 */
public class ArchitectureRuleTest {

    private static final Path MAIN_JAVA = Paths.get("src", "main", "java", "com", "ejemplo", "catalogo");

    @Test
    @DisplayName("Regla Hexagonal: domain no debe importar Spring ni Jakarta")
    public void domainNoImportaSpringNiJakarta() throws IOException {
        Path domainDir = MAIN_JAVA.resolve("domain");
        List<String> violaciones = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(domainDir)) {
            stream.filter(p -> p.toString().endsWith(".java")).forEach(file -> {
                try {
                    List<String> lines = Files.readAllLines(file);
                    for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i).trim();
                        if (line.startsWith("import org.springframework") || line.startsWith("import jakarta")) {
                            violaciones.add(file.getFileName() + ":" + (i + 1) + " -> " + line);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        if (!violaciones.isEmpty()) {
            System.err.println("[VIOLACION ARQUITECTURA EN DOMAIN]:");
            violaciones.forEach(v -> System.err.println("  " + v));
        }

        assertTrue(violaciones.isEmpty(),
                "Violación de arquitectura en domain (no debe importar Spring ni Jakarta):\n"
                        + String.join("\n", violaciones));
    }

    @Test
    @DisplayName("Regla Hexagonal: application no debe importar adapters")
    public void applicationNoImportaAdapters() throws IOException {
        Path appDir = MAIN_JAVA.resolve("application");
        List<String> violaciones = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(appDir)) {
            stream.filter(p -> p.toString().endsWith(".java")).forEach(file -> {
                try {
                    List<String> lines = Files.readAllLines(file);
                    for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i).trim();
                        if (line.startsWith("import com.ejemplo.catalogo.adapter")) {
                            violaciones.add(file.getFileName() + ":" + (i + 1) + " -> " + line);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        if (!violaciones.isEmpty()) {
            System.err.println("[VIOLACION ARQUITECTURA EN APPLICATION]:");
            violaciones.forEach(v -> System.err.println("  " + v));
        }

        assertTrue(violaciones.isEmpty(),
                "Violación de arquitectura en application (no debe importar ningún adapter):\n"
                        + String.join("\n", violaciones));
    }
}
