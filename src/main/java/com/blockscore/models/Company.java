package com.blockscore.models;

import com.blockscore.common.CorporationType;
import com.blockscore.common.ValidityStatus;
import com.blockscore.models.base.BasicResponse;
import com.blockscore.net.BlockscoreApiClient;
import com.blockscore.net.BlockscoreRestAdapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.GregorianCalendar;

/**
 * Company model
 */
public class Company extends BasicResponse {
    private BlockscoreRestAdapter restAdapter;

    // Request fields
    @NotNull
    @JsonProperty("entity_name")
    private String entityName;

    @NotNull
    @JsonProperty("tax_id")
    private String taxId;

    @Nullable
    @JsonProperty("incorporation_state")
    private String incorporationState;

    @NotNull
    @JsonProperty("incorporation_country_code")
    private String incorporationCountryCode;

    @NotNull
    @JsonProperty("incorporation_type")
    private String incorporationType;

    @Nullable
    @JsonProperty("incorporation_day")
    private Integer incorporationDay;

    @Nullable
    @JsonProperty("incorporation_month")
    private Integer incorporationMonth;

    @Nullable
    @JsonProperty("incorporation_year")
    private Integer incorporationYear;

    @Nullable
    @JsonProperty("dbas")
    private String dbas;

    @Nullable
    @JsonProperty("registration_number")
    private String registrationNumber;

    @Nullable
    @JsonProperty("email")
    private String email;

    @Nullable
    @JsonProperty("url")
    private String url;

    @Nullable
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Nullable
    @JsonProperty("ip_address")
    private String ipAddress;

    @Nullable
    @JsonProperty("note")
    private String note;

    @NotNull
    @JsonProperty("address_street1")
    private String addressStreet1;
    
    @Nullable
    @JsonProperty("address_street2")
    private String addressStreet2;
    
    @NotNull
    @JsonProperty("address_city")
    private String addressCity;

    @NotNull
    @JsonProperty("address_subdivision")
    private String addressSubdivision;

    @NotNull
    @JsonProperty("address_postal_code")
    private String addressPostalCode;
    
    @NotNull
    @JsonProperty("address_country_code")
    private String addressCountryCode;

    // Response fields
    @NotNull
    @JsonProperty("details")
    private Details details;

    @Nullable
    @JsonProperty("status")
    private String status;

    private Company() {
        // Restricts access to end user so they must use a Company.Builder to create a Person
    }

    /**
     * Gets the name of the entity.
     *
     * @return Name of the entity.
     */
    @NotNull
    public String getEntityName() {
        return entityName;
    }

    /**
     * Gets the Tax ID associated with this entity.
     *
     * @return Tax ID
     */
    @NotNull
    public String getTaxId() {
        return taxId;
    }

    /**
     * Gets the incorporation state. Can be either of ISO code form or the full length name of the state.
     *
     * @return the incorporation state
     */
    @Nullable
    public String getIncorporationState() {
        return incorporationState;
    }
    
    /**
     * Gets the incorporation country code. Should be of the ISO alpha-2 code form.
     *
     * @return the incorporation country code
     */
    @NotNull
    public String getIncorporationCountryCode() {
        return incorporationCountryCode;
    }

    /**
     * Gets the corporation type. The type of legal business entity that this company
     * is such as a Limited Liability Company.
     *
     * @return the corporation type
     */
    @NotNull
    public CorporationType getIncorporationType() {
        return CorporationType.toEnum(incorporationType);
    }

    /**
     * Gets the date of incorporation.
     *
     * @return the incorporation date
     */
    @Nullable
    public Date getIncorporationDate() {
        if (incorporationDay == null || incorporationMonth == null || incorporationYear == null) {
            return null;
        }

        GregorianCalendar calendarDay = new GregorianCalendar(incorporationYear, incorporationMonth, incorporationDay);
        return calendarDay.getTime();
    }

    /**
     * Gets a list of "doing business as" names, which are other names this business may be known by.
     *
     * @return the list of DBA names
     */
    @Nullable
    public String[] getDbas() {
        return dbas.split(","); //TODO: Handle ensure dbas is , delimited
    }

    /**
     * Gets the registration number. This is the number typically provided by the state of
     * incorporation which is assigned to a business. Should only include the digits of the
     * registration number with no extraneous characters like dashes.
     *
     * @return the registration number
     */
    @Nullable
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Gets the email for this entity.
     *
     * @return the email address
     */
    @Nullable
    public String getEmail() {
        return email;
    }

    /**
     * Gets the URL for the entity.
     *
     * @return the URL
     */
    @Nullable
    public String getURL() {
        return url;
    }

    /**
     * Gets the company's phone number.
     *
     * @return the phone number
     */
    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets a company's IP address.
     *
     * @return the IP address
     */
    @Nullable
    public String getIPAddress() {
        return ipAddress;
    }

    /**
     * Gets any note data you have associated with the Company.
     *
     * @return the note data
     */
    @Nullable
    public String getNote() {
        return note;
    }

    /**
     * Gets the address for this company.
     *
     * @return Address
     */
    @NotNull
    public Address getAddress() {
        Address addressObject = new Address(addressStreet1,
                                            addressStreet2,
                                            addressCity, 
                                            addressSubdivision,
                                            addressPostalCode,
                                            addressCountryCode);
        return addressObject;
    }

    /**
     * Gets either valid or invalid and is the culmination of whether or not the passed
     * in information is valid against various databases and signals.
     *
     * @return true if valid.
     */
    public boolean isValid() {
        return ValidityStatus.VALID.isEqualTo(status);
    }

    /**
     * Sets the internal REST api adapter.
     */
    public void setAdapter(BlockscoreRestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    /**
     * Contains a breakdown of how the status (validity) was determined. It will let you diagnose 
     * problems like address inconsistencies.
     *
     * @return the details
     */
    @Nullable
    public Details getDetails() {
        return details;
    }

    public static class Builder {
        private transient BlockscoreRestAdapter restAdapter; // TODO: Discover if transient is neccesary
        private transient Map<String, String> queryOptions;

        public Builder(BlockscoreApiClient client) {
            this.restAdapter = client.getAdapter();
            queryOptions = new HashMap<String, String>();
        }

        /**
         * Name of entity. This should exclude any legal endings like "Co" or "Inc" for best results.
         *
         * @param entityName  Name
         * @return this
         */
        @NotNull
        public Builder setEntityName(@NotNull final String entityName) {
            queryOptions.put("entity_name", entityName);
            return this;
        }

        /**
         * Sets the Tax ID for this entity. The tax ID should only include the digits of the ID with
         * no extraneous characters like dashes.
         *
         * @param taxId  the tax ID for the company
         * @return this
         */
        @NotNull
        public Builder setTaxId(@NotNull final String taxId) {
            queryOptions.put("tax_id", taxId);
            return this;
        }

        /**
         * Sets the incorporation state. Can be either of ISO code form or the full length name of the state.
         *
         * @param incorporationState  the incorporation state
         * @return this
         */
        @NotNull
        public Builder setIncorporationState(@Nullable final String incorporationState) {
            queryOptions.put("incorporation_state", incorporationState);
            return this;
        }

        /**
         * Sets the incorporation country code. Should be of the ISO alpha-2 code form.
         *
         * @param incorporationCountryCode  the country code
         * @return this
         */
        @NotNull
        public Builder setIncorporationCountryCode(@NotNull final String incorporationCountryCode) {
            queryOptions.put("incorporation_country_code", incorporationCountryCode);
            return this;
        }

        /**
         * Sets the incorporation type.
         *
         * @param incorporationType  the corporation type
         * @return this
         */
        public Builder setIncorporationType(@NotNull final CorporationType incorporationType) {
            queryOptions.put("incorporation_type", String.valueOf(incorporationType));
            return this;
        }

        /**
         * Sets the incorporation date.
         *
         * @param incorporationDate  the incorporation date
         * @return this
         */
        @NotNull
        public Builder setIncorporationDate(@Nullable final Date incorporationDate) {
            if (incorporationDate == null) {
                return this;
            }

            queryOptions.put("birth_day", String.valueOf(incorporationDate.getDay()));
            queryOptions.put("birth_month", String.valueOf(incorporationDate.getMonth()));
            queryOptions.put("birth_year", String.valueOf(incorporationDate.getYear()));
            return this;
        }

        /**
         * Sets the "doing business as" names for the company.
         * @param dbas  the doing business as names
         * @return this.
         */
        public Builder setDbas(@Nullable final String dbas) { // TODO: Alter to string array
            queryOptions.put("dbas", dbas);
            return this;
        }

        /**
         * Sets the registration number for this entity. Should only include the digits of the
         * registration number with no extraneous characters like dashes.
         *
         * @param registrationNumber  the registration number
         * @return this
         */
        public Builder setRegistrationNumber(@Nullable final String registrationNumber) {
            queryOptions.put("registration_number", registrationNumber);
            return this;
        }

        /**
         * Sets the email for this entity. Any form of valid email is accepted.
         *
         * @param email  the email address
         * @return this
         */
        public Builder setEmail(@Nullable final String email) {
            queryOptions.put("email", email);
            return this;
        }
        
        /**
         * Sets the URL for this business. Can either contain protocol information or not
         * (ex. www.example.com and http://www.example.com).
         *
         * @param url  the URL
         * @return this
         */
        public Builder setURL(@Nullable final String url) {
            queryOptions.put("url", url);
            return this;
        }
        
        /**
         * Sets a company's phone number. Extra characters like parenthesis and dashes are
         * accepted - can either contain the country code or not.
         *
         * @param phoneNumber  the phone number
         * @return this
         */
        @NotNull
        public Builder setPhoneNumber(@Nullable final String phoneNumber) {
            queryOptions.put("phone_number", phoneNumber);
            return this;
        }

        /**
         * Sets a company's IP address. Both IPv4 and IPv6 style IP addresses are acceptable.
         *
         * @param ipAddress  the IP address
         */
        @NotNull
        public Builder setIPAddress(@Nullable final String ipAddress) {
            queryOptions.put("ip_address", ipAddress);
            return this;
        }

        /**
         * Store additional information about the candidate here such as your internal system's identifier
         * for this individual. This will allow you to keep track of them.
         *
         * @param note  the additional information
         * @return this
         */
        @NotNull
        public Builder setNote(@Nullable final String note) {
            queryOptions.put("note", note);
            return this;
        }

        /**
         * Sets the company's address.
         *
         * @param address  the address
         * @return this
         */
        public Builder setAddress(@NotNull final Address address) {
            queryOptions.put("address_street1", address.getStreet1());
            queryOptions.put("address_street2", address.getStreet2());
            queryOptions.put("address_city", address.getCity());
            queryOptions.put("address_subdivision", address.getSubdivision());
            queryOptions.put("address_postal_code", address.getPostalCode());
            queryOptions.put("address_country_code", address.getCountryCode());
            return this;
        }


        /**
         * Creates a new {@code Company}.
         *
         * @return the new company
         */
        public Company create() {
            Company company = restAdapter.createCompany(queryOptions);
            company.setAdapter(restAdapter);
            return company;
        }
    }
}