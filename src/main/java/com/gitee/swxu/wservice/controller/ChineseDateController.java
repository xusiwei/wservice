package com.gitee.swxu.wservice.controller;

import com.gitee.swxu.wservice.bean.ChineseDate;
import com.gitee.swxu.wservice.service.ChineseDateService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
public class ChineseDateController {
    private static final Log log = LogFactory.getLog(ChineseDateController.class);

    @Autowired
    private ChineseDateService service;

    @GetMapping("/cn_date")
    public ChineseDate query(@RequestParam(value = "d", defaultValue = "") String date) {
        String dateStr = date;
        if (Objects.isNull(date) || StringUtils.isEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = sdf.format(new Date());
        }

//        ChineseDate cnDate = new ChineseDate();
//        cnDate.setDate(dateStr);
//        cnDate.setFestival("test");
//        return cnDate;
        return service.query(dateStr);
    }
}
