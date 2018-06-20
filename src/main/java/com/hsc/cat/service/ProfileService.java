package com.hsc.cat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsc.cat.TO.ProfileTO;
import com.hsc.cat.TO.ViewProfileTO;
import com.hsc.cat.VO.CreateProfileVO;
import com.hsc.cat.VO.PopulateDropdownOtherDomainVO;
import com.hsc.cat.entity.EmployeeDetails;
import com.hsc.cat.entity.ProfileEntity;
import com.hsc.cat.enums.RoleCategoryEnum;
import com.hsc.cat.enums.SkillCategoryEnum;
import com.hsc.cat.repository.EmployeeDetailRepository;
import com.hsc.cat.repository.ProfileRepository;
import com.hsc.cat.repository.SkillProfileRepository;

@Service
@Transactional
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
private SkillProfileService skillProfileService;
	
	@Autowired
	private SkillProfileRepository skillProfileRepository;
	
	@Autowired
	private EmployeeDetailRepository employeeDetailRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public ProfileTO createProfile(CreateProfileVO profileVO) {
		
		ProfileEntity exists=profileRepository.findByEmpId(profileVO.getUserName());
		
		List<String> skillsInProfile=new ArrayList<>();
		
		ProfileTO createProfileTO=new ProfileTO();
		
		if(exists==null) {
		ProfileEntity profileEntity=new ProfileEntity();
		profileEntity.setEmpId(profileVO.getUserName());
		profileEntity.setProjectRole(profileVO.getProjectRole());
		Date date=new Date();
		profileEntity.setCreationDate(date);
		profileRepository.save(profileEntity);
		
		ProfileEntity saved=profileRepository.findByEmpId(profileVO.getUserName());
		
		createProfileTO.setFirstName(profileVO.getFirstName());
		createProfileTO.setLastName(profileVO.getLastName());
		createProfileTO.setDepartment(profileVO.getDepartment());
		createProfileTO.setEmailId(profileVO.getEmailId());
		createProfileTO.setProjectRole(profileEntity.getProjectRole());
		createProfileTO.setUserName(profileVO.getUserName());
	//	createProfileTO.setSelectedSkills(profileVO.getSelectedSkills());
		createProfileTO.setSelectedDomainSkills(profileVO.getSelectedDomainSkills());
		createProfileTO.setSelectedOtherDomainSkills(profileVO.getSelectedOtherDomainSkills());
		//skillsInProfile.addAll(profileVO.getSelectedDomainSkills());
		//skillsInProfile.addAll(profileVO.getSelectedOtherDomainSkills());
		
		//createProfileTO.setSelectedSkills(skillsInProfile);
		System.out.println("\n\n\n#######################"+saved.getId());
		
		for(String skill:createProfileTO.getSelectedDomainSkills()) {
			skillProfileService.saveSkillProfile(skill, saved.getId());
		}
		
		for(String skill:createProfileTO.getSelectedOtherDomainSkills()) {
			skillProfileService.saveSkillProfile(skill, saved.getId());
		}
		
//		for(int i=0;i<createProfileTO.getSelectedSkills().size();i++) {
//			System.out.print(createProfileTO.getSelectedSkills().get(i)+" ");
//		}
		
		}
		
		
		else {
		//We need to update the profile
			
			profileRepository.updateProfile(profileVO.getUserName(), profileVO.getProjectRole());
			ProfileEntity saved=profileRepository.findByEmpId(profileVO.getUserName());
			System.out.println("\n\n\n#######################"+saved.getId());
			createProfileTO.setFirstName(profileVO.getFirstName());
			createProfileTO.setLastName(profileVO.getLastName());
			createProfileTO.setDepartment(profileVO.getDepartment());
			createProfileTO.setEmailId(profileVO.getEmailId());
			createProfileTO.setProjectRole(saved.getProjectRole());
			createProfileTO.setUserName(profileVO.getUserName());
			createProfileTO.setSelectedDomainSkills(profileVO.getSelectedDomainSkills());
			createProfileTO.setSelectedOtherDomainSkills(profileVO.getSelectedOtherDomainSkills());
			//createProfileTO.setSelectedSkills(profileVO.getSelectedSkills());
			//skillsInProfile.addAll(profileVO.getSelectedDomainSkills());
			//skillsInProfile.addAll(profileVO.getSelectedOtherDomainSkills());
			
			//createProfileTO.setSelectedSkills(skillsInProfile);
//			if(profileVO.getSelectedSkills()!=null && !profileVO.getSelectedSkills().isEmpty())
//			{
//				//delete previous skills
////				//skillProfileRepository.deleteByProfileId(saved.getId());
////				String query="Delete from SkillProfileEntity e WHERE e.profileId=:profileId";
////				Query q=entityManager.createQuery(query);
////				q.setParameter("profileId",saved.getId());
////				List<Long> list=q.getResultList();
////				System.out.println("Skills deleted from profile: "+list.size());
//				
//				skillProfileRepository.deleteByProfileId(saved.getId());
//			}
			
			
			/*for(int i=0;i<createProfileTO.getSelectedSkills().size();i++) {
				System.out.print(createProfileTO.getSelectedSkills().get(i)+" ");
			}
	*/
			
			for(String skill:createProfileTO.getSelectedDomainSkills()) {
				skillProfileService.saveSkillProfile(skill, saved.getId());
			}
			
			for(String skill:createProfileTO.getSelectedOtherDomainSkills()) {
				skillProfileService.saveSkillProfile(skill, saved.getId());
			}
			
			
			
			
			skillsInProfile.addAll(createProfileTO.getSelectedDomainSkills());
			skillsInProfile.addAll(createProfileTO.getSelectedOtherDomainSkills());
			
			Set<String> set=new HashSet<>(skillsInProfile);
			
			List<String> skillsInProfileWithoutDuplicates=new ArrayList<>(set);
			
			if(skillsInProfileWithoutDuplicates!=null && !skillsInProfileWithoutDuplicates.isEmpty())
			{
				skillProfileRepository.deleteByProfileId(saved.getId());
			}
			for(String skill:skillsInProfileWithoutDuplicates) {
				skillProfileService.saveSkillProfile(skill, saved.getId());
			}
			
		}
		return createProfileTO;
	}
	
	
	public String findCategoryFromRole(String role) {
String category=null;
		
		if(role.equalsIgnoreCase(RoleCategoryEnum.BACKEND_DEVELOPER.getName()))
			category=SkillCategoryEnum.BACKEND.getName();
		else if(role.equalsIgnoreCase(RoleCategoryEnum.FRONTEND_DEVELOPER.getName()))
			category=SkillCategoryEnum.FRONTEND.getName();
			else if(role.equalsIgnoreCase(RoleCategoryEnum.FULLSTACK_DEVELOPER.getName()))
				category=SkillCategoryEnum.FULLSTACK.getName();
			else if(role.equalsIgnoreCase(RoleCategoryEnum.MANUAL_TESTER.getName()))
				category=SkillCategoryEnum.MANUAL.getName();
			else if(role.equalsIgnoreCase(RoleCategoryEnum.AUTOMATION_TESTER.getName()))
				category=SkillCategoryEnum.AUTOMATION.getName();
			
		
		return category;
	}
	
	public ProfileTO viewProfile(String empId) {
		ProfileTO createProfileTO=new ProfileTO();
		ProfileEntity profileEntity=profileRepository.findByEmpId(empId);
		
		EmployeeDetails emp=employeeDetailRepository.findOne(empId);
		
		
		
		createProfileTO.setFirstName(emp.getFirstName());
		createProfileTO.setLastName(emp.getLastName());
		createProfileTO.setEmailId(emp.getEmail());
		createProfileTO.setProjectRole(profileEntity.getProjectRole());
		createProfileTO.setUserName(empId);
		createProfileTO.setDepartment(emp.getDepartment());
		//createProfileTO.setSelectedSkills(skillProfileService.viewSkillsInProfile(profileEntity.getId()));
		String category=findCategoryFromRole(profileEntity.getProjectRole());
		
		System.out.println("\n\n*****"+category);
		List<String> domainSkillsList=new ArrayList<>();
		List<String> otherDomainSkillsList =new ArrayList<>();
		
		if(profileEntity.getProjectRole().equalsIgnoreCase(RoleCategoryEnum.FULLSTACK_DEVELOPER.getName())) {
			domainSkillsList=skillProfileRepository.getSelectedDomainSKillsFullstack(empId);
			otherDomainSkillsList=skillProfileRepository.getSelectedOtherDomainSKillsFullstack(empId);
		}
		else {
			
			domainSkillsList=skillProfileRepository.getSelectedDomainSKills(empId, category);
			otherDomainSkillsList=skillProfileRepository.getSelectedOtherDomainSKills(empId, category);
		
		}
		
		createProfileTO.setSelectedDomainSkills(domainSkillsList);
		createProfileTO.setSelectedOtherDomainSkills(otherDomainSkillsList);
		
		List<String> selectedSkillList=new ArrayList<>();
	     selectedSkillList.addAll(domainSkillsList);
	     selectedSkillList.addAll(otherDomainSkillsList);
	     createProfileTO.setSelectedSkills(selectedSkillList);
		return createProfileTO;
	}
	
	
	
//	public List<String> populateDropdownOtherDomain(PopulateDropdownOtherDomainVO incomingVO){
//		String projectRole=RoleCategoryEnum.getProjectRoleNameFromNumber(incomingVO.getProjectRole());
//		List<String> domainSelectedSkills=incomingVO.getDomainSelectedSkills();
//		
//		
//		
//	}
}
