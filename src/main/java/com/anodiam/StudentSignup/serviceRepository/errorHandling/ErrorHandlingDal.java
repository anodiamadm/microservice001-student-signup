package com.anodiam.StudentSignup.serviceRepository.errorHandling;

import com.anodiam.StudentSignup.model.common.MessageResponse;

import com.anodiam.StudentSignup.model.common.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class ErrorHandlingDal extends ErrorHandlingImpl {

    class ErrorHandling
    {
        Integer errorNumber;
        String errorKey;
        String errorMessage;

        public ErrorHandling(){}

        public ErrorHandling(Integer errorNumber,String errorKey,String errorMessage)
        {
            this.errorNumber=errorNumber;
            this.errorKey=errorKey;
            this.errorMessage=errorMessage;
        }
    }

    @Override
    public MessageResponse GetErrorMessage(String errorMessage)
    {
        System.out.println("Error Message Is: " + errorMessage);
        List<ErrorHandling> lstErrorHandle=populateErrorHandlers();
        for(int i=0;i<lstErrorHandle.size();i++)
        {
            ErrorHandling objErrorHandling=lstErrorHandle.get(i);
            if (errorMessage.contains(objErrorHandling.errorKey)) {
                return new MessageResponse(objErrorHandling.errorNumber,
                        objErrorHandling.errorMessage);
            }
        }
        System.out.println("Passed Errors");
        return new MessageResponse(ResponseCode.FAILURE.getID(),
                "Failed to retrieve/update details");
    }

    private List<ErrorHandling> populateErrorHandlers()
    {
        List<ErrorHandling> lstErrorHandle=new ArrayList<ErrorHandling>();
        //Handling of User Errors
        lstErrorHandle.add(new ErrorHandling(ResponseCode.DUPLICATE.getID(),
       "uk_username","User name already exist!"));

        lstErrorHandle.add(new ErrorHandling(ResponseCode.DUPLICATE.getID(),
                "uk_email","Email already exists!"));

        return lstErrorHandle;
    }
}
