package com.obss.Model.Services.Interfaces;

import com.obss.Model.Entities.Skill;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arnold on 7/21/2017.
 */
@Service
public interface SkillService {


    public List<Skill> getAllSkills();

    public Skill findSkill(int skillId);

}
