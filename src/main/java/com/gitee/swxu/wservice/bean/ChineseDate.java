package com.gitee.swxu.wservice.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChineseDate {

    private String date;

    @JsonProperty(ChineseDateKeys.K_LUNAR_DATE)
    private String lunarDate;

//    @JsonProperty(ChineseDateKeys.K_LUNAR_FESTIVAL)
//    private String lunarFestival;
//
//    @JsonProperty(ChineseDateKeys.K_FESTIVAL)
//    private String festival;
//
//    @JsonProperty(ChineseDateKeys.K_LUNAR_MONTH)
//    private String lunarMonth;
//
//    @JsonProperty(ChineseDateKeys.K_LUNAR_DAY)
//    private String lunarDay;
//
//    @JsonProperty(ChineseDateKeys.K_JIE_QI)
//    private String jieQi;
//
//    private String fitness;
//
//    private String taboo;

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

    @Override
    public String toString() {
        return "ChineseDate{" +
                "date='" + date + '\'' +
                ", lunarDate='" + lunarDate + '\'' +
                '}';
    }
}
