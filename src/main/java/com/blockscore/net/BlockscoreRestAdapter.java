package com.blockscore.net;

import com.blockscore.models.AnswerSet;
import com.blockscore.models.Candidate;
import com.blockscore.models.Company;
import com.blockscore.models.Person;
import com.blockscore.models.QuestionSet;
import com.blockscore.models.results.PaginatedResult;
import com.blockscore.models.results.WatchlistHit;
import com.blockscore.models.results.WatchlistSearchResults;

import org.jetbrains.annotations.NotNull;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * Blockscore API REST adapter; relies on Retrofit.
 */
public interface BlockscoreRestAdapter {
  // Person operations
  @NotNull
  @POST("/people")
  Person createPerson(@NotNull @QueryMap final Map<String, String> options);

  @NotNull
  @GET("/people/{id}")
  Person retrievePerson(@NotNull @Path("id") String id);

  @NotNull
  @GET("/people")
  PaginatedResult<Person> listPeople();

  @NotNull
  @POST("/question_sets")
  QuestionSet createQuestionSet(@NotNull @QueryMap final Map<String, String> options);

  @NotNull
  @POST("/question_sets/{id}/score")
  QuestionSet scoreQuestionSet(@Path("id") @NotNull final String questionSetId,
                 @NotNull @Body final AnswerSet answers);

  @NotNull
  @GET("/question_sets/{id}")
  QuestionSet retrieveQuestionSet(@Path("id") @NotNull final String questionSetId);
 
  @NotNull
  @GET("/question_sets")
  PaginatedResult<QuestionSet> listQuestionSets();


  // Company operations
  @POST("/companies")
  Company createCompany(@NotNull @QueryMap final Map<String, String> options);

  @NotNull
  @GET("/companies/{id}")
  Company retrieveCompany(@Path("id") @NotNull final String companyId);

  @NotNull
  @GET("/companies")
  PaginatedResult<Company> listCompanies();


  // Candidate operations
  @NotNull
  @POST("/candidates")
  Candidate createCandidate(@NotNull @Body final Candidate candidate);

  @NotNull
  @GET("/candidates/{id}")
  Candidate retrieveCandidate(@NotNull @Path("id") final String id);

  @NotNull
  @PATCH("/candidates/{id}")
  Candidate updateCandidate(@NotNull @Path("id") final String id, @NotNull @Body final Candidate candidate);

  @NotNull
  @DELETE("/candidates/{id}")
  Candidate deleteCandidate(@NotNull @Path("id") final String id);

  @NotNull
  @GET("/candidates")
  PaginatedResult<Candidate> listCandidates();

  @NotNull
  @GET("/candidates/{id}/history")
  List<Candidate> getCandidateHistory(@NotNull @Path("id") final String id);

  @NotNull
  @GET("/candidates/{id}/hits")
  PaginatedResult<WatchlistHit> getCandidateHits(@NotNull @Path("id") final String id);

  @NotNull
  @POST("/watchlists")
  WatchlistSearchResults searchWatchlists(@NotNull @QueryMap final Map<String, String> options);
}
