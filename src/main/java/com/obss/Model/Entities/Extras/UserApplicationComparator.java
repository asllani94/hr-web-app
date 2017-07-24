package com.obss.Model.Entities.Extras;

import com.google.common.collect.Sets;
import com.obss.Model.Entities.Skill;

import java.util.Comparator;
import java.util.Set;

/**
 * Created by arnold on 7/21/2017.
 * Comparator class used to sort candidates according to the most suitable first
 * Takes  a Set of required skills and it will find the intersection between
 * required skills and skills owned by candidate
 */
public class UserApplicationComparator implements Comparator<UserApplication> {

    private Set<Skill> requiredSkills;


    public UserApplicationComparator(Set<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }


    private int calculateIntersection(Set<Skill> ownedSkills, Set<Skill> requiredSkills) {
        return Sets.intersection(ownedSkills, requiredSkills).size();
    }


    @Override
    public int compare(UserApplication app1, UserApplication app2) {
        int intersectionCandidate1 = calculateIntersection(app1.getSkills(), this.requiredSkills);
        int intersectionCandidate2 = calculateIntersection(app2.getSkills(), this.requiredSkills);

        if (intersectionCandidate1 > intersectionCandidate2)
            return -1;
        else if (intersectionCandidate1 == intersectionCandidate2)
            return 0;
        else
            return 1;

    }
}
