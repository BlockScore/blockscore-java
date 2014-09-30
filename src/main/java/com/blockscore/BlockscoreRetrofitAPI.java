package com.blockscore;

import com.blockscore.models.Person;
import com.blockscore.models.Verification;
import org.jetbrains.annotations.NotNull;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

import java.util.List;

/**
 * Blockscore Java API interface.
 * Created by tealocean on 9/29/14.
 */
public interface BlockscoreRetrofitAPI {
    /**
     * Creates a new verification. The information for the person will be run through the
     * verification process and returned with additional information that will help you
     * determine the authenticity of the information given. <br />
     * Thread: Asynchronous <br />
     * @param person Person to verify.
     * @param callback Callback to use.
     */
    @POST("/verifications")
    void createVerification(@NotNull @Body final Person person, @NotNull final Callback<Verification> callback);

    /**
     * Creates a new verification. The information for the person will be run through the
     * verification process and returned with additional information that will help you
     * determine the authenticity of the information given. <br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param person Person to verify.
     * @return Observable containing the verification results.
     */
    @NotNull
    @POST("/verifications")
    Observable<Verification> createVerification(@NotNull @Body final Person person);

    /**
     * Pulls up a single verification. This will return a record exactly as it was when you created it.
     * This route is useful for auditing purposes as you can provide proof that a verification
     * took place along with all of its associated data. <br />
     * Thread: Asynchronous <br />
     * @param id ID of verification to verify.
     * @param callback Callback to use.
     */
    @GET("/verifications/{id}")
    void getVerification(@NotNull @Path("id") String id, @NotNull final Callback<Verification> callback);

    /**
     * Pulls up a single verification. This will return a record exactly as it was when you created it.
     * This route is useful for auditing purposes as you can provide proof that a verification
     * took place along with all of its associated data. <br />
     * Thread: Any [Determined by settings on Observable] <br />
     * @param id ID of verification to verify.
     */
    @NotNull
    @GET("/verifications/{id}")
    Observable<Verification> getVerification(@NotNull @Path("id") String id);

    /**
     * Gets a list of historical records for all verifications you have completed. Sorted
     * newest to oldest. <br />
     * Thread: Asynchronous <br />
     * @param callback Callback to use.
     */
    @GET("/verifications")
    void listVerifications(@NotNull final Callback<List<Verification>> callback);

    /**
     * Gets a list of historical records for all verifications you have completed. Sorted
     * newest to oldest. <br />
     * Thread: Any [Determined by settings on Observable] <br />
     */
    @NotNull
    @GET("/verifications")
    Observable<Verification> listVerifications();
}
