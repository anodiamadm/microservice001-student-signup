package com.anodiam.StudentSignup.serviceRepository.errorHandling;

import com.anodiam.StudentSignup.model.common.MessageResponse;

public interface ErrorHandlingService
{
    MessageResponse GetErrorMessage(String errorMessage);
}
