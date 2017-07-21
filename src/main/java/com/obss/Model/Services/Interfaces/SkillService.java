package com.obss.Model.Services.Interfaces;

import com.obss.Model.Entities.Skill;

import java.util.List;

/**
 * Created by arnold on 7/21/2017.
 */
public interface SkillService {


    public List<Skill> getAllSkills();

    public Skill findSkill(int skillId);

    public void createSkill(Skill skill);
}
