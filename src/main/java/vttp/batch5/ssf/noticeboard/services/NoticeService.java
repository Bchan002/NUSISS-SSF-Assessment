package vttp.batch5.ssf.noticeboard.services;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.http.HttpSession;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepo;



	private String POST_URL = "https://publishing-production-d35a.up.railway.app/notice";

	// TODO: Task 3
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type
	public String postToNoticeServer(Notice notice, HttpSession session) throws Exception {

		// Create the Json object
		JsonObject noticeJsonObject = convertToJson(notice);

		// Request Url
		RequestEntity<String> req = RequestEntity
				.post(POST_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(noticeJsonObject.toString(), String.class);

		// Step 2: Create a RestTemplate
		RestTemplate template = new RestTemplate();

		// Step 3: Configure the response
		ResponseEntity<String> resp;

		resp = template.exchange(req, String.class);

		/// If required can check for status code 
		// Check for errors
		if (resp.getStatusCode().is4xxClientError()) {
			throw new Exception("Authentication failed");
		}

		// Step 5: Extract the payload (JSON String format)
		String payload = resp.getBody();

		// Step 6: Read the JSON format payload
		Reader reader = new StringReader(payload);
		JsonReader jsonReader = Json.createReader(reader);

		JsonObject object = jsonReader.readObject();
		String id = object.getString("id");
		String postEmail = (String) session.getAttribute("postEmail");

		noticeRepo.insertNotices(postEmail,object);

		return id;
		
	}

	public JsonObject convertToJson(Notice notice) throws Exception {

		// Convert notice to JsonString

		String title = notice.getTitle();
		String poster = notice.getPoster();

		// Changing postdate to LONG
		Long postDate = notice.getPostDate().getTime();
		// String postDateString = postDate.toString();

		// public-notice,sport,meeting
		String categories = notice.getCategories();
		String[] arrayCategories = categories.split(",");

		String text = notice.getText();

		JsonArrayBuilder builder = Json.createArrayBuilder();

		// Create an array first for the Categories
		for (String category : arrayCategories) {
			builder.add(category);
		}

		JsonArray categoriesArray = builder.build();

		// Create a Json Object
		JsonObject noticeObject = Json.createObjectBuilder()
				.add("title", title)
				.add("poster", poster)
				.add("postDate", postDate)
				.add("categories", categoriesArray)
				.add("text", text)
				.build();

		return noticeObject;

	}


	public void getRandomKey() throws Exception{
		
		String randomKey = noticeRepo.returnRandomKey();

		if(randomKey.isEmpty()){
			throw new Exception("No key!");
		}
	}
}
