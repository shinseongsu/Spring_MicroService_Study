package microservice.book.serviceImpl;


import microservice.book.domain.Multiplication;
import microservice.book.domain.MultiplicationResultAttempt;
import microservice.book.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiplicationServiceImplTest {

    @Autowired
    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Test
    public void checkCorrectAttemptTest() {

        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("John_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user,
                multiplication, 3000);

        boolean attempResult = multiplicationServiceImpl.checkAttempt(attempt);

        assertThat(attempResult).isTrue();

    }

    @Test
    public void checkWrongAttempTest() {

        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("Jhon_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user,
                multiplication, 3010);

        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

        assertThat(attemptResult).isFalse();

    }

}

