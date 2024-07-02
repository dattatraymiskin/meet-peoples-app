package com.meetpeoples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meetpeoples.models.Chat;
import com.meetpeoples.models.User;

public interface ChatRepository extends JpaRepository<Chat, Long>{

	public List<Chat> findByUsersId(Long userId);
	
	
	@Query("select c from Chat c where :user Member of c.users And :reqUser Member of c.users")
	public Chat findChatByUsersId(@Param("user") User user,@Param("reqUser") User reqUser);
}
