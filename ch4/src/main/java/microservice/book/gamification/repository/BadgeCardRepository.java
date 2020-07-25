package microservice.book.gamification.repository;

import microservice.book.gamification.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {

    List<BadgeCard> findByUserOrderByBadgeTimestampDesc(final Long userId);
}
