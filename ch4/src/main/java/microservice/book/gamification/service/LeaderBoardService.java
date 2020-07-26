package microservice.book.gamification.service;

import microservice.book.gamification.domain.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {

    List<LeaderBoardRow> getCurrentLeaderBoard();

}
