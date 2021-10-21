package com.anodiam.StudentSignup.serviceRepository.errorHandling;

import com.anodiam.StudentSignup.model.common.MessageResponse;

abstract class ErrorHandlingImpl implements ErrorHandlingService {

    @Override
    public MessageResponse GetErrorMessage(String errorMessage) {
        return new ErrorHandlingDal().GetErrorMessage(errorMessage);
    }
}