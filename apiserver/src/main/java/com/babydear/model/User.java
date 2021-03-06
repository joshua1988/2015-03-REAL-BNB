package com.babydear.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.babydear.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class User {

	public User(UserDTO userDTO, Long fId) {
		this.email = userDTO.getEmail();
		this.password = userDTO.getPassword();
		this.fId = fId;
		this.userImg = userDTO.getUserImg();
	}
	public User(UserDTO loginDTO) {
		this(loginDTO, null);
	}
	public User(){}
	public enum SignIn {
		NAVER,
		KAKAO,
		FACEBOOK,
		NORMAL
	}
	
	public enum State {
		MANAGER,
		NORMAL,
		WITHDRAW,
		PRISON
	}
	
//	public enum Sex {
//		WOMAN,
//		MAN
//	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uId;
	
	private Long fId;
	
	private String userImg;
	
	private String email;
	private String password;
	private String nickname;
	
	private State state;
	private SignIn signIn;
	public boolean checkPW(String password) {
		if(this.password.equals(password)){
			return true;
		}
		return false;
	}
	
}
