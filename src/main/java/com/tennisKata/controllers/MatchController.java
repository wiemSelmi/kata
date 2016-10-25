package com.tennisKata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tennisKata.models.Match;
import com.tennisKata.services.MatchService;
@RestController
@RequestMapping(value = { "/matchs" })
public class MatchController {
	
	@Autowired
	private MatchService matchService ;

	// get match by its id
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Match> findPlayerById(@PathVariable Integer id) {

		Match match = matchService.findById(id);
		if (match != null) {
			return ResponseEntity.ok().body(match);
		} else {
			return new ResponseEntity<Match>(HttpStatus.NOT_FOUND);
		}

	}

	// save given match
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public ResponseEntity<Match> create(@RequestBody Match match) {
		return ResponseEntity.ok().body(matchService.save(match));
	}
}
