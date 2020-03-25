import java.io.File;
import java.io.FileWriter;

public class Main {
    private static final String[] FILES_NAMES = {"DispatchQueue.txt",
            "FileLoader.txt",
            "FileLog.txt",
            "FileUploadOperation.txt",
            "UserConfig.txt",
            "Utilities.txt"};
    private static final String SEPARADOR = ",";
    
    public static void main(String[] args) throws Exception {
        FileWriter csvWriter = new FileWriter("new.csv");
        csvWriter.append("MES");
        csvWriter.append(SEPARADOR);
        csvWriter.append("LOC");
        csvWriter.append(SEPARADOR);
        csvWriter.append("CLASSES");
        csvWriter.append(SEPARADOR);
        csvWriter.append("METODOS");

        for (Integer i = 1; i <= 27; i++) {
            RetornoDTO retorno = new RetornoDTO(0,0,0);
            File file;
            for (String fileName : FILES_NAMES) {
                file = new File(i.toString() + "/" + fileName);
                retorno.somarComOutro(Util.analizarArquivo(file));
            }
            csvWriter.append("\n");
            csvWriter.append(i.toString());
            csvWriter.append(SEPARADOR);
            csvWriter.append(retorno.getLoc().toString());
            csvWriter.append(SEPARADOR);
            csvWriter.append(retorno.getClasses().toString());
            csvWriter.append(SEPARADOR);
            csvWriter.append(retorno.getMetodos().toString());
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
