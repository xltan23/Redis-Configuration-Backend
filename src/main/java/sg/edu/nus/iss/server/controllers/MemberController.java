package sg.edu.nus.iss.server.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.server.models.Member;
import sg.edu.nus.iss.server.repositories.MemberRepository;

@Controller
// configredis-production.up.railway.app/member
@RequestMapping(path = "/member")
@CrossOrigin(origins = "*")
public class MemberController {
    
    @Autowired
    private MemberRepository memberRepo;

    // configredis-production.up.railway.app/member/add
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/add")
    public ResponseEntity<String> saveMember(@RequestPart String name, @RequestPart String telegram, @RequestPart String grade) {
        System.out.println(name);
        System.out.println(telegram);
        System.out.println(grade);
        Member member = new Member();
        member.setName(name);
        member.setTelegram(telegram);
        member.setGrade(grade);
        JsonObject memberJson = member.toJSON();
        // Save member to Redis Database
        memberRepo.save(name, memberJson.toString());
        return ResponseEntity.ok(memberJson.toString());
    }

    // configredis-production.up.railway.app/member/{name}
    @GetMapping(path = "{name}")
    public ResponseEntity<String> getMember(@PathVariable String name) {
        System.out.println(name);
        // Get member from Redis Database
        Optional<String> optMember = memberRepo.get(name);
        Member member = new Member();
        if (optMember.isEmpty()) {
            member.setName("Not set");
            member.setTelegram("Not set");
            member.setGrade("Not set");
        } else {
            String memberString = optMember.get();
            member = Member.create(memberString);
        }
        return ResponseEntity.ok(member.toJSON().toString());
    }
}
