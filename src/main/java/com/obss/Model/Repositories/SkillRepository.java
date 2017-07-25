package com.obss.Model.Repositories;

import com.obss.Model.Entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by arnold on 7/17/2017.
 */

public interface SkillRepository extends JpaRepository<Skill,Integer> {
    public Skill findBySkillId(int id);
}
