package com.obss.Model.Services;

import com.obss.Model.Entities.Skill;
import com.obss.Model.Repositories.SkillRepository;
import com.obss.Model.Services.Interfaces.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arnold on 7/21/2017.
 */
@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Skill findSkill(int skillId) {
        return skillRepository.findOne(skillId);
    }


}
