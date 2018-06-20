package com.hsc.cat.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hsc.cat.entity.ProfileEntity;
@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer>{

	
	
	ProfileEntity findByEmpId(String empId);
	@Query("select id from ProfileEntity where empId=:empId")
	int findIdByEmpId(@Param("empId")String empId);
	
	@Modifying
	@Transactional
	@Query("UPDATE ProfileEntity e SET  e.projectRole=:projectRole WHERE e.empId=:empId") 
	int updateProfile(@Param("empId")String empId,@Param("projectRole")String projectRole);
	
	
}
