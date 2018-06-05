package ar.edu.itba.bio;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.RNASequence;
import org.biojava.nbio.core.sequence.io.FastaWriterHelper;
import org.biojava.nbio.core.sequence.io.GenbankReaderHelper;
import org.biojava.nbio.core.sequence.transcription.Frame;

public enum Exercise1 {
  ;

  public static void solve(final File genBankFile) throws Exception {
    final var dnaSequences = GenbankReaderHelper.readGenbankDNASequence(genBankFile);
    final var rnaSequences = toRNASequences(dnaSequences);
    final var proteinSequences = toProteinSequences(rnaSequences);
    saveToFileProteinSequences(proteinSequences, genBankFile.getParent(),
        filenameWithoutExtension(genBankFile));
  }

  private static String filenameWithoutExtension(final File path) {
    final String filename = path.getName();
    return filename.substring(0, filename.lastIndexOf('.'));
  }

  private static List<RNASequence> toRNASequences(final Map<String, DNASequence> dnaSequences) {
    final var rnaSequences = new ArrayList<RNASequence>();
    dnaSequences.forEach((ignored, dnaSequence) -> rnaSequences.addAll(toRNASequence(dnaSequence)));
    return rnaSequences;
  }

  private static List<RNASequence> toRNASequence(final DNASequence dnaSequence) {
    return Arrays.stream(Frame.getAllFrames()).map(dnaSequence::getRNASequence)
        .collect(Collectors.toList());
  }

  private static List<ProteinSequence> toProteinSequences(
      final Iterable<RNASequence> rnaSequences) {
    final var proteinSequences = new ArrayList<ProteinSequence>();
    rnaSequences.forEach(v -> proteinSequences.add(v.getProteinSequence()));
    setHeaderToProteinSequences(proteinSequences);
    return proteinSequences;
  }

  private static void setHeaderToProteinSequences(
      final Iterable<ProteinSequence> proteinSequences) {
    int i = 0;
    for (final ProteinSequence proteinSequence : proteinSequences) {
      proteinSequence.setOriginalHeader(String.valueOf(i));
      i++;
    }
  }

  private static void saveToFileProteinSequences(final Collection<ProteinSequence> proteinSequences,
      final String parent, final String filename) throws Exception {
    final File fastaFile = Paths.get(parent, filename + ".fas").toFile();
    if (!fastaFile.createNewFile()) {
      throw new IllegalStateException("[Exercise 1] \"" + filename
          + ".fas\"\" exists in the same dir as the input file. Will not overwrite file.");
    }
    FastaWriterHelper.writeProteinSequence(fastaFile, proteinSequences);
  }
}
