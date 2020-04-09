import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final String[] FILES_NAMES = {"DispatchQueue.txt",
            "FileLoader.txt",
            "FileLog.txt",
            "FileUploadOperation.txt",
            "UserConfig.txt",
            "Utilities.txt"};
    private static final String SEPARADOR = ",";
    private static List<RetornoDTO> retornoDTOList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        FileWriter csvWriter = new FileWriter("new.csv");
        csvWriter.append("MES");
        csvWriter.append(SEPARADOR);
        csvWriter.append("LOC");
        csvWriter.append(SEPARADOR);
        csvWriter.append("CLASSES");
        csvWriter.append(SEPARADOR);
        csvWriter.append("METODOS");
        csvWriter.append(SEPARADOR);
        csvWriter.append("CLASSES_DEUS");
        csvWriter.append(SEPARADOR);
        csvWriter.append("METODOS_DEUS");
        List<Integer> meses = new ArrayList<>();
        for (Integer i = 1; i <= 27; i++) {
            RetornoDTO retornoMes = new RetornoDTO(0,0,0, 0, 0);
            File file;
            for (String fileName : FILES_NAMES) {
                file = new File(i.toString() + "/" + fileName);
                retornoMes.sumWithAnother(FileAnalizer.analyzeFile(file));
            }
            meses.add(i);
            retornoDTOList.add(retornoMes);
            csvWriter.append("\n");
            csvWriter.append(i.toString());
            csvWriter.append(SEPARADOR);
            csvWriter.append(retornoMes.getLoc().toString());
            csvWriter.append(SEPARADOR);
            csvWriter.append(retornoMes.getClasses().toString());
            csvWriter.append(SEPARADOR);
            csvWriter.append(retornoMes.getMethods().toString());
            csvWriter.append(SEPARADOR);
            csvWriter.append(retornoMes.getNumberOfGodClasses().toString());
            csvWriter.append(SEPARADOR);
            csvWriter.append(retornoMes.getNumberOfGodMethods().toString());
        }
        final Integer MES_28 = 28;
        csvWriter.append("\n");
        csvWriter.append(MES_28.toString());
        csvWriter.append(SEPARADOR);
        csvWriter.append(new RegressaoLinear(meses,retornoDTOList.stream().map(RetornoDTO::getLoc).collect(Collectors.toList())).prever(MES_28).toString());
        csvWriter.append(SEPARADOR);
        csvWriter.append(new RegressaoLinear(meses,retornoDTOList.stream().map(RetornoDTO::getClasses).collect(Collectors.toList())).prever(MES_28).toString());
        csvWriter.append(SEPARADOR);
        csvWriter.append(new RegressaoLinear(meses,retornoDTOList.stream().map(RetornoDTO::getMethods).collect(Collectors.toList())).prever(MES_28).toString());
        csvWriter.append(SEPARADOR);
        csvWriter.append(new RegressaoLinear(meses,retornoDTOList.stream().map(RetornoDTO::getNumberOfGodClasses).collect(Collectors.toList())).prever(MES_28).toString());
        csvWriter.append(SEPARADOR);
        csvWriter.append(new RegressaoLinear(meses,retornoDTOList.stream().map(RetornoDTO::getNumberOfGodMethods).collect(Collectors.toList())).prever(MES_28).toString());
        csvWriter.flush();
        csvWriter.close();
    }
}
