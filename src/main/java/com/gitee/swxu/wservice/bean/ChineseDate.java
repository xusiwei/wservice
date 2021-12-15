package com.gitee.swxu.wservice.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChineseDate {

    private String date;

    @JsonProperty(ChineseDateKeys.K_LUNAR_DATE)
    private String lunarDate;

    @JsonProperty(ChineseDateKeys.K_LUNAR_FESTIVAL)
    private String lunarFestival;

    @JsonProperty(ChineseDateKeys.K_FESTIVAL)
    private String festival;

    @JsonProperty(ChineseDateKeys.K_LUNAR_MONTH)
    private String lunarMonth;

    @JsonProperty(ChineseDateKeys.K_LUNAR_DAY)
    private String lunarDay;

    @JsonProperty(ChineseDateKeys.K_JIE_QI)
    private String jieQi;

    private String fitness;

    private String taboo;

    public String getFitness() {
        return fitness;
    }

    public void setFitness(String fitness) {
        this.fitness = fitness;
    }

    public String getTaboo() {
        return taboo;
    }

    public void setTaboo(String taboo) {
        this.taboo = taboo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLunarDate() {
        return lunarDate;
    }

    public void setLunarDate(String lunarDate) {
        this.lunarDate = lunarDate;
    }

    public String getLunarFestival() {
        return lunarFestival;
    }

    public void setLunarFestival(String lunarFestival) {
        this.lunarFestival = lunarFestival;
    }

    public String getFestival() {
        return festival;
    }

    public void setFestival(String festival) {
        this.festival = festival;
    }

    public String getLunarMonth() {
        return lunarMonth;
    }

    public void setLunarMonth(String lunarMonth) {
        this.lunarMonth = lunarMonth;
    }

    public String getLunarDay() {
        return lunarDay;
    }

    public void setLunarDay(String lunarDay) {
        this.lunarDay = lunarDay;
    }

    public String getJieQi() {
        return jieQi;
    }

    public void setJieQi(String jieQi) {
        this.jieQi = jieQi;
    }

    @Override
    public String toString() {
        return "ChineseDate{" +
                "date='" + date + '\'' +
                ", lunarDate='" + lunarDate + '\'' +
                ", lunarFestival='" + lunarFestival + '\'' +
                ", festival='" + festival + '\'' +
                ", lunarMonth='" + lunarMonth + '\'' +
                ", lunarDay='" + lunarDay + '\'' +
                ", jieQi='" + jieQi + '\'' +
                ", fitness='" + fitness + '\'' +
                ", taboo='" + taboo + '\'' +
                '}';
    }
}
