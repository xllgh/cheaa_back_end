package com.gizwits.bsh.enums;

/**
 * Created by zhl on 2016/12/6.
 */
public enum TranslateRule {
    ONE_TO_MANY("OneToMany"),
    ONE_TO_ONE("OneToOne"),
    MANY_TO_ONE("ManyToOne"),
    MANY_TO_MANY("ManyToMany");

    TranslateRule(String rule) {
        this.rule = rule;
    }
    private String rule;

    public String getRule() {
        return rule;
    }
}
