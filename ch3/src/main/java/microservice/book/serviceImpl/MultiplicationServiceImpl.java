package microservice.book.serviceImpl;

import microservice.book.domain.Multiplication;
import microservice.book.domain.MultiplicationResultAttempt;
import microservice.book.service.MultiplicationService;
import microservice.book.service.RandomGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomGeneratorService randomGeneratorService;

    @Autowired
    public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService) {
        this.randomGeneratorService = randomGeneratorService;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Override
    public boolean checkAttempt(MultiplicationResultAttempt attempt) {
        boolean correct =  attempt.getResultAttempt() ==
                    attempt.getMultiplication().getFactorA() *
                    attempt.getMultiplication().getFactorB();

        Assert.isTrue(!attempt.isCorrect(), "채점한 상태로 보낼수 없습니다.");

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(attempt.getUser(),
                attempt.getMultiplication(), attempt.getResultAttempt(), correct );

        return correct;
    }
}
