import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileAnalizer {
    private final static String OPEN_BRACE = "{";
    private final static String CLOSE_BRACE = "}";
    private final static String[] LINE_COUNTER = {";", OPEN_BRACE, CLOSE_BRACE, "if(", "for("};
    private final static String CLASS_COUNTER = " class ";
    private final static String METHOD_REGEX = "[a-zA-Z]\\s[a-zA-Z].*\\(.*\\).*\\{";
    private final static String JAVADOC_COMMENT_START = "/**";
    private final static char JAVADOC_COMMENT_MIDDLE = '*';
    private final static String JAVADOC_COMMENT_END = "*/";
    private final static String NORMAL_COMMENT = "//";
    private final static Integer LINES_TO_BE_GOD_METHOD = 127;
    private final static Integer LINES_TO_BE_GOD_CLASS = 800;

    public static RetornoDTO analyzeFile(File file) throws Exception {
        StringBuilder documentFullText = new StringBuilder();
        List<BodyCounterHolder> methodsAndClasses = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        int lines = 0;
        String line;

        while ((line = br.readLine()) != null) {
            documentFullText.append(line = removeCommentsIfExists(line));

            if (Util.containsOneOfThis(line, LINE_COUNTER)) {
                lines++;
                documentFullText.append("\n");
            }

            findBody(methodsAndClasses, lines, line);
        }
        String analyzableCode = documentFullText.toString().replaceAll("\".*\"", "");
        int numberOfClasses = Util.count(analyzableCode, CLASS_COUNTER);
        int numberOfMethods = Util.count(analyzableCode.replaceAll(METHOD_REGEX, "Ç"), "Ç");
        int numberOfGodMethods = countGod(returnOnlyTypeOfBody(methodsAndClasses, BodyCounterHolder.Type.METHOD), LINES_TO_BE_GOD_METHOD);
        int numberOfGodClasses = countGod(returnOnlyTypeOfBody(methodsAndClasses, BodyCounterHolder.Type.CLASS), LINES_TO_BE_GOD_CLASS);

        return new RetornoDTO(lines, numberOfClasses, numberOfMethods,numberOfGodMethods,numberOfGodClasses);
    }

    private static void findBody(List<BodyCounterHolder> methodsAndClasses, int lines, String line) {
        if (Util.containsOneOfThis(line, CLOSE_BRACE)) {
            int lineToClose = methodsAndClasses.size() - 1;
            while(!methodsAndClasses.get(lineToClose).closeBody(lines)){
                lineToClose--;
            }
        }

        if (Util.containsOneOfThis(line, OPEN_BRACE)) {
            if (Util.containsOneOfThis(line, CLASS_COUNTER)) {
                methodsAndClasses.add(new BodyCounterHolder(BodyCounterHolder.Type.CLASS, lines));
            } else if (line.matches("(.*)" + METHOD_REGEX + "(.*)")) {
                methodsAndClasses.add(new BodyCounterHolder(BodyCounterHolder.Type.METHOD, lines));
            } else {
                methodsAndClasses.add(new BodyCounterHolder(BodyCounterHolder.Type.COUNTLESS, lines));
            }
        }

    }

    private static List<BodyCounterHolder> returnOnlyTypeOfBody(List<BodyCounterHolder> bodies, BodyCounterHolder.Type bodyType){
        List<BodyCounterHolder> segregatedBodies = new ArrayList<>(bodies);
        segregatedBodies.removeIf(body -> !body.getBodyOf().equals(bodyType));
        return segregatedBodies;
    }

    private static int countGod(List<BodyCounterHolder> bodies, Integer linesToBeAGod){
        int numberOfGods = 0;

        for (int i = 0; i < bodies.size(); i++) {
           BodyCounterHolder body =  bodies.get(i);
            for (int j = i; j < bodies.size(); j++) {
                BodyCounterHolder anotherBody = bodies.get(j);
                if(anotherBody.getOpenLine() < body.getCloseLine() && body != anotherBody){
                    body.subtractSize(anotherBody.getSize());
                }
            }
            if(body.getSize() >= linesToBeAGod){
                numberOfGods++;
            }
        }
        return numberOfGods;
    }

    private static String removeCommentsIfExists(String s) {
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
}
