package com.tennisKata.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tennisKata.exceptions.MyBusinessException;
import com.tennisKata.models.Match;
import com.tennisKata.models.Player;
import com.tennisKata.services.MatchService;
import com.tennisKata.services.PlayerService;

@RestController
@RequestMapping(value = { "/api/players" })
public class PlayerController {

	private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

	@Autowired
	private PlayerService playerService;

	@Autowired
	private MatchService matchService;

	// find all
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Player>> findAll() {
		return ResponseEntity.ok().body(playerService.findAll());
	}

	// get player by its id
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Player> findPlayerById(@PathVariable Integer id) {

		Player player = playerService.findById(id);
		if (player != null) {
			return ResponseEntity.ok().body(player);
		} else {
			return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
		}

	}

	// save given player
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public ResponseEntity<Player> create(@RequestBody Player player) {
		return ResponseEntity.ok().body(playerService.save(player));
	}

	// mark a point by a player in a match
	@RequestMapping(method = RequestMethod.PUT, value = "/{playerId}/winAPoint")
	public ResponseEntity<Void> winAPointByAPlayerInAMatch(@PathVariable Integer idPlayer,
			@RequestParam("matchId") Integer matchId) {

		Player player = playerService.findById(idPlayer);
		Match match = matchService.findById(matchId);
		if (player == null || match == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			try {
				playerService.winAPoint(player, match);
				return ResponseEntity.ok().build();
			} catch (MyBusinessException e) {
				LOG.error(e.getMessage());
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
		}
	}
}
