package microservice.book.service;

import microservice.book.domain.Multiplication;
import microservice.book.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {

    /*
    *
    * @return 무작위 인수를 담은 객체
    *
    */
    Multiplication createRandomMultiplication();


    /*
     *
     * @return 곱셈 게산 결과가 맞으면 true, 아니면 false
     *
     */
    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

    List<MultiplicationResultAttempt> getStatsForUser(final String userAlias);

}
