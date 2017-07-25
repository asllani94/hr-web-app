package com.obss.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss.Model.Entities.Skill;
import com.obss.Model.Repositories.SkillRepository;
import com.obss.Model.Services.SkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by arnold on 7/25/2017.
 * This class is used to fill database with mock data when app starts
 * Mainly for testing purposes
 */
@Component
public class DatabaseDataGenerator {

    @Autowired
    private SkillServiceImpl skillService;

    private ObjectMapper mapper;


    public void generateSkills() {
        List<Skill> myObjects = null;
        try {
            myObjects = mapper.readValue(new File("src/main/resources/skills.json"),
                    new TypeReference<List<Skill>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Skill skill : myObjects) {
            skillService.createSkill(skill);
        }
    }


}
