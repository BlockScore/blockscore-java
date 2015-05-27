package com.blockscore.models;

import com.blockscore.common.CorporationType;
import com.blockscore.common.ValidityStatus;
import com.blockscore.models.base.BasicResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Company model
 */
public class Company extends BasicResponse {
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

    /**
     * Name of entity. This should exclude any legal endings like "Co" or "Inc" for best results.
     * @param entityName Name
     * @return this.
     */
    @NotNull
    public Company setEntityName(@NotNull final String entityName) {
        this.entityName = entityName;
        return this;
    }

    /**
     * Sets the Tax ID for this entity. The tax ID should only include the digits of the ID with
     * no extraneous characters like dashes.
     * @param taxId Tax ID
     * @return this.
     */
    @NotNull
    public Company setTaxId(@NotNull final String taxId) {
        this.taxId = taxId;
        return this;
    }

    /**
     * Sets the incorporation state. Can be either of ISO code form or the full length name of the state.
     * @param incorporationState Incorporation state.
     * @return this.
     */
    @NotNull
    public Company setIncorporationState(@Nullable final String incorporationState) {
        this.incorporationState = incorporationState;
        return this;
    }

    /**
     * Sets the incorporation country code. Should be of the ISO alpha-2 code form.
     * @param incorporationCountryCode Country code.
     * @return this.
     */
    @NotNull
    public Company setIncorporationCountryCode(@NotNull final String incorporationCountryCode) {
        this.incorporationCountryCode = incorporationCountryCode;
        return this;
    }

    /**
     * Sets the incorporation type.
     * @param incorporationType Corporation type.
     * @return this.
     */
    public Company setIncorporationType(@NotNull final CorporationType incorporationType) {
        this.incorporationType = incorporationType.toString();
        return this;
    }

    /**
     * Sets the incorporation date.
     * @param incorporationDate Incorporation date.
     * @return this.
     */
    @NotNull
    public Company setIncorporationDate(@Nullable final Date incorporationDate) {
        if (incorporationDate == null) {
            this.incorporationDay = null;
            this.incorporationMonth = null;
            this.incorporationYear = null;
            return this;
        }

        this.incorporationDay = incorporationDate.getDay();
        this.incorporationMonth = incorporationDate.getMonth();
        this.incorporationYear = incorporationDate.getYear();
        return this;
    }

    /**
     * Sets the "doing business as" names.
     * @param dbas Doing business as names.
     * @return this.
     */
    public Company setDbas(@Nullable final String dbas) { // TODO: Alter to string array
        this.dbas = dbas;
        return this;
    }

    /**
     * Sets the registration number for this entity. Should only include the digits of the
     * registration number with no extraneous characters like dashes.
     * @param regNumber Registration number.
     * @return this.
     */
    public Company setRegistrationNumber(@Nullable final String registrationNumber) {
        this.registrationNumber = registrationNumber;
        return this;
    }

    /**
     * Sets the email for this entity. Any form of valid email is accepted.
     * @param email Email for the entity.
     * @return this.
     */
    public Company setEmail(@Nullable final String email) {
        this.email = email;
        return this;
    }
    
    /**
     * Sets the URL for this business. Can either contain protocol information or not
     * (ex. www.example.com and http://www.example.com).
     * @param url URL for the business
     * @return this.
     */
    public Company setURL(@Nullable final String url) {
        this.url = url;
        return this;
    }
    
    /**
     * Sets a company's phone number. Extra characters like parenthesis and dashes are
     * accepted - can either contain the country code or not.
     * @param phoneNumber Phone number for this individual.
     * @return this.
     */
    @NotNull
    public Company setPhoneNumber(@Nullable final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Sets a company's IP address. Both IPv4 and IPv6 style IP addresses are acceptable.
     * @param ipAddress IP address to associate with this individual.
     * @return this.
     */
    @NotNull
    public Company setIPAddress(@Nullable final String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    @NotNull
    public Company setNote(@Nullable final String note) {
        this.note = note;
        return this;
    }

    /**
     * Sets the comapny's address.
     * @param address The address.
     */
    public Company setAddress(@NotNull final Address address) {
        this.addressStreet1 = address.getStreet1();
        this.addressStreet2 = address.getStreet2();
        this.addressCity = address.getCity();
        this.addressSubdivision = address.getSubdivision();
        this.addressPostalCode = address.getPostalCode();
        this.addressCountryCode = address.getCountryCode();
        return this;
    }

    /**
     * Gets the name of the entity.
     * @return Name of the entity.
     */
    @NotNull
    public String getEntityName() {
        return entityName;
    }

    /**
     * Gets the Tax ID associated with this entity. 
     * @return Tax ID
     */
    @NotNull
    public String getTaxId() {
        return taxId;
    }

    /**
     * Gets the incorporation state. Can be either of ISO code form or the full length name of the state.
     * @return Incorporation state.
     */
    @Nullable
    public String getIncorporationState() {
        return incorporationState;
    }
    
    /**
     * Gets the incorporation country code. Should be of the ISO alpha-2 code form.
     * @return Incorporation country code.
     */
    @NotNull
    public String getIncorporationCountryCode() {
        return incorporationCountryCode;
    }

    /**
     * Gets the corporation type. The type of legal business entity that this company
     * is such as a Limited Liability Company.
     * @return Corporation type.
     */
    @NotNull
    public CorporationType getIncorporationType() {
        return CorporationType.toEnum(incorporationType);
    }

    /**
     * Gets the date of incorporation.
     * @return Incorporation date.
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
     * @return List of DBA names.
     */
    @Nullable
    public String[] getDbas() {
        return dbas.split(","); //TODO: Handle ensure dbas is , delimited
    }

    /**
     * Gets the registration number. This is the number typically provided by the state of
     * incorporation which is assigned to a business. Should only include the digits of the
     * registration number with no extraneous characters like dashes.
     * @return Registration number.
     */
    @Nullable
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Gets the email for this entity.
     * @return Email.
     */
    @Nullable
    public String getEmail() {
        return email;
    }

    /**
     * Gets the URL for the entity.
     * @return URL.
     */
    @Nullable
    public String getURL() {
        return url;
    }

    /**
     * Gets the company's phone number.
     * @return Phone number.
     */
    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets a company's IP address.
     * @return IP address.
     */
    @Nullable
    public String getIPAddress() {
        return ipAddress;
    }

    /**
     * Gets any note data you have associated with the Company.
     * @return IP address.
     */
    @Nullable
    public String getNote() {
        return note;
    }

    /**
     * Gets the address for this company.
     * @return Address.
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
     * @return True if valid.
     */
    public boolean isValid() {
        return ValidityStatus.VALID.isEqualTo(status);
    }

    /**
     * Contains a breakdown of how the status (validity) was determined. It will let you diagnose 
     * problems like address inconsistencies.
     * @return Details breakdown.
     */
    @Nullable
    public Details getDetails() {
        return details;
    }
}
