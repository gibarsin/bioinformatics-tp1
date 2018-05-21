package ar.edu.itba.bio;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum Main {
    ;

    public static void main(final String[] args) throws Exception {
        final var exercise = Integer.parseInt(args[0]);
        final var filePath = getFile(exercise, args[1]);
        switch (exercise) {
            case 1:
                Exercise1.solve(filePath);
                break;
            case 2:
                final var isLocal = Boolean.parseBoolean(args[2]);
                Exercise2.solve(filePath, isLocal);
                break;
            default:
                throw new IllegalArgumentException("Exercise number must be in range [1,2].");
        }
    }

    private static File getFile(final int exercise, final String pathString) {
        final Path path = Paths.get(pathString);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("[Exercise " + exercise + "] file \"" + path + "\" does not exist.");
        }
        return path.toFile();
    }
}
