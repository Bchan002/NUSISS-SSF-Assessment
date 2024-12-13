package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers

@Controller
@RequestMapping("/")
public class NoticeController {

    @Autowired
    private NoticeService noticeSvc;

    @GetMapping("")
    public String notice(Model model){
        Notice notice = new Notice();
        
        model.addAttribute("notice", notice);
        return "notice";
    }

    @PostMapping("/notice")
    public String postNotice(@Valid @ModelAttribute("notice") Notice notice, BindingResult binding, Model model, HttpSession session) throws Exception{
        
        session.setAttribute("postEmail", notice.getPoster());

        if(binding.hasErrors()){
            return "notice";
        }

        try {
            
            String id = noticeSvc.postToNoticeServer(notice,session);

            model.addAttribute("id", id);
            return "successful";

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

            return "unsuccessful";

        }
    }

    @GetMapping(path="/status", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getStatus(){

        try {
            
            noticeSvc.getRandomKey();

            
            return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}");
              
        
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatusCode.valueOf(503))
                .header("Content-Type", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}");
        }

    }

    



    
    
}
