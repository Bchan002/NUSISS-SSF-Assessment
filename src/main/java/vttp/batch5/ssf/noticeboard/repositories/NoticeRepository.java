package vttp.batch5.ssf.noticeboard.repositories;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
public class NoticeRepository {

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */

	@Autowired
	@Qualifier("notice")
	private RedisTemplate<String, Object> redisTemplate;

	// set redisKey --> object.toString()
	public void insertNotices(String redisKey, JsonObject object) {

		ValueOperations<String,Object> valueOps = redisTemplate.opsForValue();

		valueOps.set(redisKey, object.toString());

	}


	//Return a randomKey 
	// RANDOMKEY
	public String returnRandomKey() throws Exception {

		String randomKey = redisTemplate.randomKey();

		return randomKey;
	}


}
