public class RetornoDTO {
    private Integer loc;
    private Integer classes;
    private Integer methods;
    private Integer numberOfGodMethods;
    private Integer numberOfGodClasses;

    public RetornoDTO(Integer loc, Integer classes, Integer methods, Integer numberOfGodMethods, Integer numberOfGodClasses) {
        this.loc = loc;
        this.classes = classes;
        this.methods = methods;
        this.numberOfGodMethods = numberOfGodMethods;
        this.numberOfGodClasses = numberOfGodClasses;
    }

    public void sumWithAnother(RetornoDTO another) {
        this.loc += another.loc;
        this.classes += another.classes;
        this.methods += another.methods;
        this.numberOfGodMethods += another.numberOfGodMethods;
        this.numberOfGodClasses += another.numberOfGodClasses;
    }

    public Integer getLoc() {
        return loc;
    }

    public Integer getClasses() {
        return classes;
    }

    public Integer getMethods() {
        return methods;
    }


    public Integer getNumberOfGodMethods() {
        return numberOfGodMethods;
    }

    public Integer getNumberOfGodClasses() {
        return numberOfGodClasses;
    }
}
