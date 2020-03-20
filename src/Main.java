import java.io.File;
import java.io.FileWriter;

public class Main {
    private static final String[] FILES_NAMES = {"DispatchQueue.txt",
            "FileLoader.txt",
            "FileLog.txt",
            "FileUploadOperation.txt",
            "UserConfig.txt",
            "Utilities.txt"};

    public static void main(String[] args) throws Exception {
        FileWriter csvWriter = new FileWriter("new.csv");
        csvWriter.append("MES");

        for (String fileName : FILES_NAMES) {
            csvWriter.append(";").append(fileName).append(";").append(";");
        }

        csvWriter.append("\n");

        for (String fileName : FILES_NAMES) {
            csvWriter.append(";");
            csvWriter.append("LOC");
            csvWriter.append(";");
            csvWriter.append("CLASSES");
            csvWriter.append(";");
            csvWriter.append("METODOS");
        }

        for (Integer i = 1; i <= 27; i++) {
            csvWriter.append("\n");
            csvWriter.append(i.toString());
            for (String fileName : FILES_NAMES) {
                File file = new File(i.toString() + "/" + fileName);
                System.out.println("\n" + i.toString() + "/" + fileName);
                RetornoDTO retorno = Util.analizarArquivo(file);
                csvWriter.append(";");
                csvWriter.append(retorno.getLoc().toString());
                csvWriter.append(";");
                csvWriter.append(retorno.getClasses().toString());
                csvWriter.append(";");
                csvWriter.append(retorno.getMetodos().toString());
            }
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
