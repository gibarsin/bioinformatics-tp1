package ar.edu.itba.bio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.ws.alignment.qblast.BlastOutputFormatEnum;
import org.biojava.nbio.ws.alignment.qblast.BlastProgramEnum;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastAlignmentProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastOutputProperties;
import org.biojava.nbio.ws.alignment.qblast.NCBIQBlastService;

public enum Exercise2 {
  ;

  public static void solve(final File fastaFile, final boolean isLocal) throws Exception {
    if (isLocal) {
      solveLocal(fastaFile);
    } else {
      solveRemote(fastaFile);
    }
  }

  private static void solveLocal(final File ignored) {
    throw new UnsupportedOperationException(
        "[Exercise 2] Implementation for local database does not exist");
  }

  private static void solveRemote(final File fastaFile) throws Exception {
    final var proteinSequences = FastaReaderHelper.readFastaProteinSequence(fastaFile);
    if (proteinSequences.size() != 1) {
      throw new IllegalArgumentException(
          "[Exercise 2] Only one protein sequence is accepted and it is assumed " +
              "it was translated using the correct Reading Frame.");
    }
    for (final ProteinSequence onlyProteinSequence : proteinSequences.values()) {
      try (final var result = blastRequest(onlyProteinSequence.getSequenceAsString(),
          alignmentProperties(), outputProperties())) {
        saveToFileBlastResult(fastaFile.getParent(), result);
      }
    }
  }

  private static NCBIQBlastAlignmentProperties alignmentProperties() {
    final var properties = new NCBIQBlastAlignmentProperties();
    properties.setBlastProgram(BlastProgramEnum.blastp);
    properties.setBlastDatabase("swissprot");
    return properties;
  }

  private static NCBIQBlastOutputProperties outputProperties() {
    final var properties = new NCBIQBlastOutputProperties();
    properties.setOutputFormat(BlastOutputFormatEnum.Text);
    return properties;
  }

  private static InputStream blastRequest(final String sequence,
      final NCBIQBlastAlignmentProperties alignmentProperties,
      final NCBIQBlastOutputProperties outputProperties) throws Exception {
    final NCBIQBlastService service = new NCBIQBlastService();
    final var requestId = service.sendAlignmentRequest(sequence, alignmentProperties);
    final InputStream results = service.getAlignmentResults(requestId, outputProperties);
    service.sendDeleteRequest(requestId);
    return results;
  }

  private static void saveToFileBlastResult(final String parent, final InputStream result)
      throws IOException {
    final File blastReport = Paths.get(parent, "blast.out").toFile();
    if (!blastReport.createNewFile()) {
      throw new IllegalStateException(
          "[Exercise 2] \"blast.out\" exists in the same dir as the input file. Will not overwrite file.");
    }
    try (final var outputStream = new BufferedOutputStream(new FileOutputStream(blastReport))) {
      outputStream.write(result.readAllBytes());
    }
  }
}
