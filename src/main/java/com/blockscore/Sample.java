package com.blockscore;

import com.blockscore.models.*;
import com.blockscore.net.BlockscoreApiClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example class on how to use the Blockscore API client.
 * Created by tealocean on 9/29/14.
 */
public class Sample {
    public static void main(final String[] args) {
        BlockscoreApiClient.init("sk_test_3380b53cc2ae5b78910344c49f334c2e");
        BlockscoreApiClient.useVerboseLogs(true);
        BlockscoreApiClient apiClient = new BlockscoreApiClient();

        Person person = new Person();
        Name name = new Name("John", "Pearce", "Doe");
        Identification identification = new Identification();
        identification.setSSN("0000");
        Address address = new Address("1 Infinite Loop", "Apt 6", "Cupertino", "CA", "95014", "US");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse("1980-08-23");
        } catch (ParseException e) {
            //Do nothing.
        }

        person.setName(name).setAddress(address).setDateOfBirth(date)
                .setIdentification(identification);
        apiClient.createVerification(person, new Callback<Verification>() {
            @Override
            public void success(Verification verification, Response response) {
                System.out.println("FUCK YEA!");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.getMessage());
            }
        });
        apiClient.getVerification("542a39c159756e8609090000", new Callback<Verification>() {
            @Override
            public void success(Verification verification, Response response) {
                System.out.println("We did it!");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("WHAT");
            }
        });
    }
}
