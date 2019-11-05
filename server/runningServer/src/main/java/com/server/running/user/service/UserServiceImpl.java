package com.server.running.user.service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.running.running.dto.FriendRunning;
import com.server.running.running.dto.Running;
import com.server.running.user.dto.Friend;
import com.server.running.user.dto.TotalFriend;
import com.server.running.user.dto.User;
import com.server.running.user.repository.UserRepository;
import com.server.running.util.SecurityUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	// 회원가입
	@Override
	public boolean signup(User user) {
		Optional<User> maybeUser = userRepository.findByEmail(user.getEmail());
		if(maybeUser.isPresent()) {
			// 중복된 아이디가 있음
			return false;
		} else {
			// 회원가입 성공
			user.setPassword(new SecurityUtil().encodeSHA256(user.getPassword()));
			userRepository.save(user);
			return true;
		}
	}

	// 로그인
	@Override
	public User login(User user) {
		Optional<User> maybeUser = userRepository.findByEmail(user.getEmail());
		if(maybeUser.isPresent() && 
				new SecurityUtil().encodeSHA256(user.getPassword()).equals(maybeUser.get().getPassword())) {
			// 아이디가 있음
			return maybeUser.get();
		} else {
			// 아이디가 없음
			user = new User();
			user.setUid(0);
			return user;
		}
	}
	
	// 회원 정보 수정
	@Override
	public User updateUser(User user) {
		userRepository.save(user);
		Optional<User> maybeUser = userRepository.findByEmail(user.getEmail());
		return maybeUser.get();
	}
	
	// 회원 탈퇴
	@Override
	public boolean deleteUser(User user) {
		userRepository.delete(user);
		return true;
	}

	// 러닝 데이터 조회
	@Override
	public User findRunning(int uid) {
		User user = userRepository.findById(uid).get();
		for (int i = 0; i < user.getRunningData().size(); i++) {
			if(user.getRunningData().get(i).getStarttime().equals(user.getRunningData().get(i).getEndtime())) {
				user.getRunningData().remove(i);
				i--;
			}
		}
		return user;
	}

	// 아이디 중복체크 요청
	@Override
	public boolean overlap(User user) {
		Optional<User> maybeUser = userRepository.findByEmail(user.getEmail());
		if(maybeUser.isPresent()) {
			return false;
		} else {
			return true;
		}
	}

	// 친구 검색
	@Override
	public List<String> findFriends(String email) {
		List<User> friends = userRepository.findByEmailContaining(email);
		List<String> list = new ArrayList<String>();
		for (User user : friends) {
			list.add(user.getEmail());
		}
		return list;
	}

	// 친구 추가
	@Override
	public Boolean addFriend(Friend friend) {
		User user = userRepository.findByEmail(friend.getUser()).get();
		Optional<User> fri = userRepository.findByEmail(friend.getEmail());
		user.addFriends(fri.get());
		fri.get().addFriends(user);
		userRepository.save(user);
		userRepository.save(fri.get());
		return true;
	}

	// 친구 러닝 데이터 조회
	@Override
	public List<FriendRunning> findMyFriends(Integer uid) {
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.with(TemporalAdjusters.firstDayOfMonth());
		List<FriendRunning> list = new ArrayList<>();
		Optional<User> maybeUser = userRepository.findById(uid);
		if(maybeUser.isPresent()) {
			User user = maybeUser.get();
			for (User temp : user.getFriends()) {
				for (Running running : temp.getRunningData()) {
					if(running.getEndtime().isAfter(ldt)) {
						FriendRunning fr = new FriendRunning();
						fr.setRunning(running);
						fr.setUserEmail(temp.getEmail());
						fr.setUserName(temp.getName());
						list.add(fr);
					}
				}
			}
		}
		return list;
	}

	// 친구 랭킹
	@Override
	public List<TotalFriend> selectMyFriends(Integer uid) {
		List<TotalFriend> list = new ArrayList<TotalFriend>();
		Optional<User> maybeUser = userRepository.findById(uid);
		if(maybeUser.isPresent()) {
			User user = maybeUser.get();
			TotalFriend temp = new TotalFriend();
			double dir = 0;
			for (Running running : user.getRunningData()) {
				dir += running.getDistance();
			}
			temp.setUserEmail(user.getEmail());
			temp.setUserName(user.getName());
			temp.setWholeDistance(dir);
			list.add(temp);
			for (User friend : user.getFriends()) {
				TotalFriend tf = new TotalFriend();
				dir = 0;
				for (Running running : friend.getRunningData()) {
					dir += running.getDistance();
				}
				tf.setUserEmail(friend.getEmail());
				tf.setUserName(friend.getName());
				tf.setWholeDistance(dir);
				list.add(tf);
			}
		}
		Collections.sort(list);
		return list;
	}
}
