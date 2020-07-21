package microservice.book.serviceImpl;

import microservice.book.domain.Multiplication;
import microservice.book.domain.MultiplicationResultAttempt;
import microservice.book.service.MultiplicationService;
import org.springframework.stereotype.Service;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    @Override
    public Multiplication createRandomMultiplication() {
        return null;
    }

    @Override
    public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
        return resultAttempt.getResultAttempt() ==
                resultAttempt.getMultiplication().getFactorA() *
                        resultAttempt.getMultiplication().getFactorB();
    }
}
