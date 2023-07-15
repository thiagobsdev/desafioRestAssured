package com.devsuperior.dsmovie.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static  org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;

@SuppressWarnings("unused")
public class ScoreControllerRA {
	
	private String adminUsername, adminPassword;
	private String adminToken;
	private Long nonExistingMovieID;
	
	private Map<String, Object> putScoreInstance;
	
	
	@BeforeEach
	public void setup() throws JSONException {
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
	
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		nonExistingMovieID = 1000l;
		
		putScoreInstance = new HashMap<>();
		
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {	
		
		putScoreInstance.put("movieId", nonExistingMovieID);
		putScoreInstance.put("score", 5);
		
		JSONObject newScore = new JSONObject(putScoreInstance);
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
		.then()
			.statusCode(404);
			
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		putScoreInstance.put("movieId", "");
		putScoreInstance.put("score", 5);
		
		JSONObject newScore = new JSONObject(putScoreInstance);
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
		.then()
			.statusCode(422);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {	
		
		putScoreInstance.put("movieId", "");
		putScoreInstance.put("score", -5);
		
		JSONObject newScore = new JSONObject(putScoreInstance);
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
		.then()
			.statusCode(422);
	}
}
