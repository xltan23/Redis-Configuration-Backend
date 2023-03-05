package sg.edu.nus.iss.server.models;

import java.io.StringReader;
import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Member {
    
    // Defining Member attributes
    private String name;
    private String telegram;
    private String grade;

    // Generate getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTelegram() {
        return telegram;
    }
    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }

    // Member to JSON OBJECT
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                    .add("telegram", telegram)
                    .add("grade", grade)
                    .add("date", (new Date()).toString())
                    .build();
    }

    // Create Member object from String payload
    public static Member create(String payload) {
        StringReader sr = new StringReader(payload);
        JsonReader jr = Json.createReader(sr);
        return create(jr.readObject());
    }

    // Create Member object from JsonObject payload
    public static Member create(JsonObject payload) {
        Member member = new Member();
        member.setTelegram(payload.getString("telegram"));
        member.setGrade(payload.getString("grade"));
        return member;
    }
}
