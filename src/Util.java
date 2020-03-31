class Util {
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