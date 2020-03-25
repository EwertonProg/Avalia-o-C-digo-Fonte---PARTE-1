import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Util {
    final static String JAVADOC_COMMENT_START = "/**";
    final static char JAVADOC_COMMENT_MIDDLE = '*';
    final static String JAVADOC_COMMENT_END = "*/";
    final static String NORMAL_COMMENT = "//";
    final static String[] LINE_COUNTER = {";", "{", "}","if(","for("};
    final static String CLASS_COUNTER = " class ";


    public static RetornoDTO analizarArquivo(File file) throws Exception {
        StringBuilder documentFullText = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(file));
        int lines = 0;
        String st;
        while ((st = br.readLine()) != null) {
            documentFullText.append(st = removeCommentsIfExists(st));

            if(containsOneOfThis(st, LINE_COUNTER)){
                lines++;
                documentFullText.append("\n");
            }
        }
        String analyzableCode = documentFullText.toString().replaceAll("\".*\"", "");
        int numberOfClasses = count(analyzableCode, CLASS_COUNTER);
        int numberOfMethods = count(analyzableCode.replaceAll("[a-zA-Z]\\s[a-zA-Z].*\\(.*\\).*\\{","Ç"),"Ç");
        return new RetornoDTO(lines,numberOfClasses,numberOfMethods);
    }

    public static String removeCommentsIfExists(String s) {
        s = s.trim();
        if (!s.isEmpty()) {
            int startPointCleaner;
            boolean aux = false;
            if ((startPointCleaner = s.indexOf(JAVADOC_COMMENT_START)) >= 0 || (aux = s.charAt(0) == JAVADOC_COMMENT_MIDDLE)) {
                if (aux)
                    startPointCleaner = 0;

                int endPointCleaner;

                if ((endPointCleaner = s.indexOf(JAVADOC_COMMENT_END)) > 0) {
                    return s.substring(0, startPointCleaner) + s.substring(endPointCleaner);
                } else {
                    return s.substring(0, startPointCleaner);
                }

            } else if ((startPointCleaner = s.indexOf(NORMAL_COMMENT)) >= 0) {
                return s.substring(0, startPointCleaner);
            }
        }

        return s;
    }

    public static boolean containsOneOfThis(String string, String... comparableValues){
        for (String aux : comparableValues) {
            if (string.contains(aux))
                return true;
        }
        return false;
    }

    public static int count(String s, String analyzableString){
        return (s.length() - s.replace(analyzableString,"").length())/analyzableString.length();
    }

}