package com.anodiam.StudentSignup.serviceRepository.Message;

import com.anodiam.StudentSignup.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer>{
}

