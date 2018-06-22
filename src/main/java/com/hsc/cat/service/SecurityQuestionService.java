package com.hsc.cat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsc.cat.entity.SecurityQuestions;
import com.hsc.cat.repository.SecurityQuestionRepository;

@Service
public class SecurityQuestionService {

	@Autowired
	private SecurityQuestionRepository securityQuestionRepository;
	
	
	public List<SecurityQuestions> getSecurityQuestions(){
		List<SecurityQuestions> list=securityQuestionRepository.findAll();
		return list;
	}
}
