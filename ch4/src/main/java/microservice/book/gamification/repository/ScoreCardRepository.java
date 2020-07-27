package microservice.book.gamification.repository;

import microservice.book.gamification.domain.LeaderBoardRow;
import microservice.book.gamification.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

    /**
     * ScoreCard 의 점수를 합해서 주어진 사용자의 총 점수를 조회
     *
     * @param userId 총 점수를 조회하고자 하는 사용자의 ID
     * @return 주어진 사용자의 총 점수
     */
    @Query(value = "SELECT SUM(s.score) FROM SCORE_CARD s WHERE s.user_id = :userId GROUP BY s.user_id", nativeQuery = true)
    int getTotalScoreForUser(@Param("userId") final int userId);

    /**
     * 사용자와 사용자의 총 점수를 나타내는 {@link LeaderBoardRow} 리스트를 조회
     *
     * @return 높은 점수 순으로 정렬된 리더 보드
     */
    /*@Query(value = "SELECT NEW microservices.book.gamification.domain.LeaderBoard(s.userId, SUM(s.score))"
            + "FROM microservices.book.gamification.domain.ScoreCard s "
            + "GROUP BY s.userId ORDER BY SUM(s.score) DESC", nativeQuery = true)*/
    @Query(value = " SELECT s.USER_ID, SUM(s.score) " +
            "      FROM SCORE_CARD s  " +
            "         , LEADER_BOARD_ROW  l " +
            "     where s.user_id = l.user_id " +
            "     GROUP BY s.user_id " +
            "     ORDER BY SUM(s.score) DESC ;", nativeQuery = true)
    List<LeaderBoardRow> findFirst10();

    /**
     * 주어진 사용자의 모든 ScoreCard 를 조회
     *
     * @param userId 사용자 ID
     * @return 주어진 사용자의 최근 순으로 정렬된 ScoreCard 리스트
     */
    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);

}
