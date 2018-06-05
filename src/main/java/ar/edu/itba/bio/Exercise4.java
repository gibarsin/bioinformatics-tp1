package ar.edu.itba.bio;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.StreamSupport;
import org.biojava.nbio.core.search.io.Hit;
import org.biojava.nbio.core.search.io.Result;
import org.biojava.nbio.core.search.io.blast.BlastXMLParser;

public enum Exercise4 {
  ;

  public static void solveA(final File blastFile, final String pattern) throws Exception {
    final var blastHits = blastHits(blastFile);
    final var patternHits = patternHits(blastHits, pattern);
    System.out.println("Total Hits = " + blastHits.size());
    printHits(patternHits);
  }

  public static void solveB(final File blastFile) throws Exception {
    final var blastHits = blastHits(blastFile);
    final var uniquePatternHits = uniquePatterns(blastHits);
    uniquePatternHits.stream()
        .map(h -> patternHits(blastHits, h))
        .forEach(Exercise4::printHits);
  }

  private static void printHits(final List<Hit> patternHits) {
    System.out.println("Pattern Hits = " + patternHits.size());
    System.out.println("Hits IDs:");
    patternHits.stream().map(Hit::getHitId).forEach(System.out::println);
    System.out.println();
  }

  private static List<Hit> blastHits(final File blastFile) throws IOException, ParseException {
    final var parser = new BlastXMLParser();
    parser.setFile(blastFile);
    final List<Result> uniqueResultedExpected = parser.createObjects(Double.MAX_VALUE - 1);
    if (uniqueResultedExpected.size() > 1) {
      throw new IllegalStateException("[Exercise 4] More than one query result was found but was"
          + " not expected.");
    }
    final var result = uniqueResultedExpected.get(0);
    return StreamSupport.stream(result.spliterator(), false).collect(toList());
  }

  private static List<Hit> patternHits(final List<Hit> hits, final String pattern) {
    final var lowerCasePattern = pattern.toLowerCase();
    return hits.stream().filter(h -> h.getHitId().toLowerCase().contains(lowerCasePattern))
        .collect(toList());
  }

  private static List<String> uniquePatterns(final List<Hit> blastHits) {
    return blastHits.stream()
        .map(Hit::getHitId)
        .map(h -> h.substring(h.lastIndexOf('_')))
        .distinct()
        .collect(toList());
  }
}
