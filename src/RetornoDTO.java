public class RetornoDTO {
    private Integer loc;
    private Integer classes;
    private Integer metodos;

    RetornoDTO(Integer loc, Integer classes, Integer metodos){
        this.classes = classes;
        this.loc = loc;
        this.metodos = metodos;
    }

    public Integer getLoc() {
        return loc;
    }

    public Integer getClasses() {
        return classes;
    }

    public Integer getMetodos() {
        return metodos;
    }
}
