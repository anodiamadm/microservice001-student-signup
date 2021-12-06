package com.anodiam.StudentSignup.serviceRepository.Message;

import com.anodiam.StudentSignup.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
class MessageServiceDal extends MessageServiceImpl {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public String showMessage(Integer languageId,String messageCode)
    {
        try
        {
            List<Message> allMessages = messageRepository.findAll()
                    .stream().filter(a->a.getLanguage().getLanguageId() == languageId
                            && a.getMessageCode().equals(messageCode))
                    .collect(Collectors.toList());

            if (allMessages == null) { return ""; }
            if (allMessages.size() == 0) { return ""; }

            return allMessages.get(0).getMessageDesc();
        }catch(Exception ex) { return ""; }
    }
}
