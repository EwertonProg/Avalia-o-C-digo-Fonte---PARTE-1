import java.util.List;

public class RegressaoLinear {
    private List<Integer> variaveisIndepen;
    private List<Integer> variaveisDepen;
    private Double coeficienteDependente;
    private Double coeficienteIndependente;
    private int listSize;

    public RegressaoLinear(List<Integer> variaveisIndepen, List<Integer> variaveisDepen) {
        if (variaveisDepen.size() != variaveisIndepen.size()) {
            throw new IllegalArgumentException("As duas listas devem, obrigatoramente, ter o mesmo tamanho");
        }
        this.listSize = variaveisDepen.size();
        this.variaveisIndepen = variaveisIndepen;
        this.variaveisDepen = variaveisDepen;
        calcularCoeficientes();
    }

    private void calcularCoeficientes() {
        Long somatorioDepen = 0L;
        Long somatorioIndepen = 0L;
        double indepenQuadrado = 0D;
        long somatorioCoeficiente = 0L;

        for (int i = 0; i < listSize; i++) {
            Integer variavelIndepen = variaveisIndepen.get(i);
            Integer variavelDepen = variaveisDepen.get(i);

            somatorioIndepen += variavelIndepen;
            somatorioDepen += variavelDepen;

            somatorioCoeficiente += variavelIndepen * variavelDepen;

            indepenQuadrado = indepenQuadrado + Math.pow(variavelIndepen.doubleValue(), 2);
        }

        coeficienteDependente = ((listSize * somatorioCoeficiente) - (somatorioDepen * somatorioIndepen)) / ((listSize * indepenQuadrado) - (Math.pow(somatorioIndepen.doubleValue(), 2)));
        coeficienteIndependente = (somatorioDepen / listSize) - (coeficienteDependente * (somatorioIndepen / listSize));
    }

    public Long prever(Integer valor) {
        return Math.round(coeficienteDependente * valor + coeficienteIndependente);
    }
}
