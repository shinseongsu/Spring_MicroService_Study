package microservice.book.gamification.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import microservice.book.gamification.domain.Badge;
import microservice.book.gamification.domain.BadgeCard;
import microservice.book.gamification.domain.GameStats;
import microservice.book.gamification.domain.ScoreCard;
import microservice.book.gamification.repository.BadgeCardRepository;
import microservice.book.gamification.repository.ScoreCardRepository;
import microservice.book.gamification.service.GameService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

    private ScoreCardRepository scoreCardRepository;
    private BadgeCardRepository badgeCardRepository;

    GameServiceImpl(ScoreCardRepository scoreCardRepository,
                    BadgeCardRepository badgeCardRepository ) {
        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
    }

    @Override
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {

        if(correct) {
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("사용자 ID {}, 점수 {} 점, 답안 ID {} ",
                     userId, scoreCard.getScore(), attemptId);
            List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
            return new GameStats(userId, scoreCard.getScore(),
                    badgeCards.stream()
                            .map(BadgeCard::getBadge)
                            .collect(Collectors.toList()));
        }

        return GameStats.emptyStats(userId);
    }

    private List<BadgeCard> processForBadges(final Long userId,
                                             final Long attemptId) {
        List<BadgeCard> badgeCards = new ArrayList<>();

        int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        log.info("사용자 ID {} 의 새로운 점수 {}", userId, totalScore);

        List<ScoreCard> scoreCardList = scoreCardRepository
                .findByUserIdOrderByScoreTimestampDesc(userId);
        List<BadgeCard> badgeCardList = badgeCardRepository
                .findByUserOrderByBadgeTimestampDesc(userId);

        checkAndGiveBadgeBasedOnScore(badgeCardList,
                Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
                .ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList,
                Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
                .ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList,
                Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
                .ifPresent(badgeCards::add);

        if(scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_MON)) {
            BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_MON, userId);
            badgeCards.add(firstWonBadge);
        }

        return badgeCards;

    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) {
        return null;
    }

    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(
                    final List<BadgeCard> badgeCards,
                    final Badge badge,
                    final int score,
                    final int scoreThreshold,
                    final Long userId) {

        if(score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
            return Optional.of(giveBadgeToUser(badge, userId));
        }
        return Optional.empty();
    }

    private boolean containsBadge(final List<BadgeCard> badgeCards,
                                  final Badge badge) {
        return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
    }

    private BadgeCard giveBadgeToUser(final Badge badge,
                                      final Long userId) {
        BadgeCard badgeCard = new BadgeCard(userId, badge);
        badgeCardRepository.save(badgeCard);
        log.info("사용자 ID {} 새로운 배지 획득 : {}", userId, badge);
        return badgeCard;
    }

}
