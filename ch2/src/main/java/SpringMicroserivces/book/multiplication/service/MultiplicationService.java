package SpringMicroserivces.book.multiplication.service;

import SpringMicroserivces.book.multiplication.domain.Multiplication;

public interface MultiplicationService {

    /*
        두 개의 무작위 인수를 담는 객체를 생성한다.
        무작위로 생성되는 숫자는 범위는 11 ~ 19

        @return 무작위 인수를 담는 객체
     */
    Multiplication createRandomMultiplication();
}
