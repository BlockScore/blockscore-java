package com.blockscore.net;

import com.blockscore.common.BlockscoreErrorType;
import com.blockscore.exceptions.ApiException;
import com.blockscore.exceptions.InvalidRequestException;
import com.blockscore.models.error.BlockscoreError;
import com.blockscore.models.error.RequestError;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

class BlockscoreErrorHandler implements ErrorHandler {
  @Override
  public Throwable handleError(RetrofitError cause) {
    Object rawError = cause.getBodyAs(BlockscoreError.class);
    
    if (rawError instanceof BlockscoreError) {
      BlockscoreError error = (BlockscoreError) rawError;
      RequestError requestError = error.getError();

      if (requestError.getErrorType() == BlockscoreErrorType.INVALID) {
        return new InvalidRequestException(error);
      } else if (requestError.getErrorType() == BlockscoreErrorType.API) {
        return new ApiException(error);
      } else {
        //Theoretically, this should never happen, unless the API has changed to break something.
        String msg = String.format("An unknown error has occurred. Please contact support. Error type: %s",
                       requestError.getErrorType().toString());
        return new RuntimeException(msg);
      }
    }
    return cause;
  }
}
