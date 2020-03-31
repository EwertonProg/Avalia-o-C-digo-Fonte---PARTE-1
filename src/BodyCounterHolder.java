public class BodyCounterHolder {
    private Type bodyOf;
    private Integer openLine;
    private Integer closeLine;
    private Integer size;

    public BodyCounterHolder(Type bodyOf, Integer openLine) {
        this.bodyOf = bodyOf;
        this.openLine = openLine;
    }

    public Integer getCloseLine() {
        return closeLine;
    }

    public Boolean closeBody(Integer closeLine) {
        if (this.closeLine == null) {
            this.closeLine = closeLine;
            this.size = closeLine - openLine;
            return true;
        }
        return false;
    }

    public Type getBodyOf() {
        return bodyOf;
    }

    public Integer getOpenLine() {
        return openLine;
    }

    public Integer getSize() {
        return size;
    }

    public void subtractSize(Integer value) {
        this.size = this.size - value;
    }

    enum Type {
        CLASS, METHOD, COUNTLESS
    }
}