package com.tennisKata.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tennisKata.dao.PlayerDAO;
import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Match;
import com.tennisKata.models.Player;
import com.tennisKata.services.MatchService;
import com.tennisKata.services.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PlayerServiceImpl.class);

	@Autowired
	private PlayerDAO playerDao;

	@Autowired
	private MatchService matchService;

	@Override
	public List<Player> findAll() {
		return playerDao.findAll();
	}

	@Override
	public void winAPoint(Player player) {
		Integer oldPointScore = player.getPointsScore();
		if (oldPointScore != null) {
			player.setPointsScore(oldPointScore + 1);
			playerDao.save(player);
		}
	}

	@Override
	public void winAGame(Player player) {
		Integer oldGamesScore = player.getGamesScore();
		if (oldGamesScore != null) {
			player.setGamesScore(oldGamesScore + 1);
			playerDao.save(player);
		}

	}

	@Override
	public void winASet(Player player) {
		Integer oldSetsScore = player.getSetsScore();
		if (oldSetsScore != null) {
			player.setSetsScore(oldSetsScore + 1);
			playerDao.save(player);
		}

	}

	@Override
	public Player save(Player player) {
		return playerDao.save(player);
	}

	@Override
	public Player findById(Integer id) {
		return playerDao.findById(id);

	}

	@Override
	public void winAPoint(Player player, Match match) throws MyBusinessException {
		checkIfMatchHasDifferentPlayers(match);
		checkIfThereIsAConflictedData(player, match);
		winAPoint(player);
		matchService.calculateScore(match);
	}

	private void checkIfMatchHasDifferentPlayers(Match match) throws MyBusinessException {
		if (match.getFirstPlayer() == null || match.getSecondPlayer() == null) {
			LOG.error("Match must have two players");
			throw new MyBusinessException("Match must have two players");
		} else if(match.getFirstPlayer().getId() == match.getSecondPlayer().getId()){
			LOG.error("Match can not have similar players");
			throw new MyBusinessException("Match can not have similar players");
		}
	}

	private void checkIfThereIsAConflictedData(Player player , Match match) throws MyBusinessException{

		if(match.getFirstPlayer().getId() != player.getId() && match.getSecondPlayer().getId() != player.getId()){
			LOG.error("Conflict data : Player "+player.getName()+" doesn't belong to this match");
			throw new MyBusinessException("Conflict data : Player "+player.getName()+" doesn't belong to this match");
		}
		
	}
}
