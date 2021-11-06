package com.anodiam.StudentSignup.serviceRepository.Message;

abstract class MessageServiceImpl implements MessageService{
    @Override
    public String showMessage(Integer language_id,String message_code)
    {
        return new MessageServiceDal().showMessage(language_id, message_code);
    }
}
