package SpringMicroserivces.book.multiplication.domain;

/*
    애플리케이션에서 곱셈을 나타내는 클래스
 */

public class Multiplication {

    // 인수
    private int factorA;
    private int factorB;

    // A * B 의 결과
    private int result;

    // 생성자
    public Multiplication(int factorA, int factorB) {
        this.factorA = factorA;
        this.factorB = factorB;
        this.result = factorA * factorB;
    }

    // getter
    public int getFactorA() {
        return factorA;
    }

    // getter
    public int getFactorB() {
        return factorB;
    }

    // getter
    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Multiplication{" +
                "factorA = " + factorA +
                ", factorB = " + factorB +
                ", result(A*B) = " + result +
                "}";

    }

}
