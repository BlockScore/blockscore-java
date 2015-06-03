package com.blockscore.models.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.GregorianCalendar;

public class BirthRange {
  @NotNull
  @JsonProperty("birth_day")
  private Integer birthDay;

  @NotNull
  @JsonProperty("birth_month")
  private Integer birthMonth;

  @NotNull
  @JsonProperty("birth_year")
  private Integer birthYear;

  @Nullable
  @JsonProperty("birth_day_end")
  private Integer birthDayEnd;

  @Nullable
  @JsonProperty("birth_month_end")
  private Integer birthMonthEnd;

  @Nullable
  @JsonProperty("birth_year_end")
  private Integer birthYearEnd;

  /**
   * Returns the start of the date of birth range.
   *
   * @return the start of the date of birth range
   */
  @NotNull
  public Date getDateOfBirth() {
    GregorianCalendar calendar = new GregorianCalendar(birthYear, birthMonth, birthDay);
    return calendar.getTime();
  }

  /**
   * Returns the end of the date of birth range.
   *
   * @return the end of the date of birth range, may be null
   */
  @Nullable
  public Date getDateOfBirthEnd() {
    if (birthDayEnd == null || birthMonthEnd == null || birthYearEnd == null) {
      return null;
    }

    GregorianCalendar calendar = new GregorianCalendar(birthYearEnd, birthMonthEnd, birthDayEnd);
    return calendar.getTime();
  }
}
