package com.obss.Model.Entities.Extras;

import com.obss.Model.Entities.Skill;

/**
 * Created by arnold on 7/18/2017.
 */
public class SkillView extends Skill {

    private static String[] colors = {"danger", "success", "info", "warning", "primary"};

    public String colorCode;

    public SkillView(int id, String name) {
        this.setSkillId(id);
        this.setSkillName(name);
        int index = this.getSkillId() % colors.length;
        this.colorCode = colors[index];

    }

    public String getColor() {
        return this.colorCode;
    }

    public void setColor(String color) {
        this.colorCode = color;
    }

}
