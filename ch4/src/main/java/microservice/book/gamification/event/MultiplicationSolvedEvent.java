package microservice.book.gamification.event;

import lombok.*;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable {

    private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;

    MultiplicationSolvedEvent() {
        multiplicationResultAttemptId = 0L;
        userId = 0L;
        correct = false;
    }

}