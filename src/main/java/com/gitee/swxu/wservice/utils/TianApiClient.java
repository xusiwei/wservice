package com.gitee.swxu.wservice.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gitee.swxu.wservice.bean.ChineseDate;
import com.gitee.swxu.wservice.bean.ChineseDateKeys;
import com.gitee.swxu.wservice.controller.ChineseDateController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TianApiClient {
    private static final String DEFAULT_API_KEY = "d0152290b25aa135d35813ab32cf9d5b";
    private static final String API_URI_FORMAT = "http://api.tianapi.com/lunar/index?key=%s&date=%s";
    private static final String K_NEWS_LIST = "newslist";
    private static final int READ_BUFFER_SIZE = 4096;

    private final Log log = LogFactory.getLog(ChineseDateController.class);
    private final String apiKey = getApiKey();

    private static String getApiKey() {
        String keyVar = System.getenv("TIAN_API_KEY");
        if (keyVar != null) {
            return keyVar;
        }
        return DEFAULT_API_KEY;
    }

    public ChineseDate requestChineseDateInfo(String dateStr) {
        // fetch info from upstream service provider
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String requestURI = String.format(API_URI_FORMAT, apiKey, dateStr);
        log.debug("uri = " + requestURI);
        CloseableHttpResponse response = null;
        try {
            HttpGet request = new HttpGet(requestURI);
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String contentString = EntityUtils.toString(entity);
            log.debug("contentString: " + contentString);
            return parseFromJson(contentString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                httpClient.close();
                log.debug("http connection closed!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.error("some exception raised!");
        return null;
    }

    private ChineseDate parseFromJson(String json) {
        ChineseDate chineseDate = new ChineseDate();
        JSONObject jsonObj = JSON.parseObject(json);
        log.debug(String.format("code = %d, msg = %s", jsonObj.getInteger("code"), jsonObj.getString("msg")));

        JSONArray newsList = jsonObj.getJSONArray(K_NEWS_LIST);
        if (newsList.size() == 0) {
            log.error(String.format("no data in %s of json", K_NEWS_LIST));
            return null;
        }

        JSONObject details = newsList.getJSONObject(0);
        chineseDate.setDate(details.getString(ChineseDateKeys.K_GREGORIAN_DATE));
        chineseDate.setLunarDate(details.getString(ChineseDateKeys.K_LUNAR_DATE));
//        chineseDate.setLunarFestival(details.getString(ChineseDateKeys.K_LUNAR_FESTIVAL));
//        chineseDate.setFestival(details.getString(ChineseDateKeys.K_FESTIVAL));
//        chineseDate.setLunarMonth(details.getString(ChineseDateKeys.K_LUNAR_MONTH));
//        chineseDate.setLunarDay(details.getString(ChineseDateKeys.K_LUNAR_DAY));
//        chineseDate.setJieQi(details.getString(ChineseDateKeys.K_JIE_QI));
//        chineseDate.setFitness(details.getString(ChineseDateKeys.K_FITNESS));
//        chineseDate.setTaboo(details.getString(ChineseDateKeys.K_TABOO));
        log.debug(String.format("ChineseDate object parsed %s", chineseDate.toString()));
        return chineseDate;
    }

    public static void main(String[] args) {
        TianApiClient client = new TianApiClient();
        String json = "{\"code\":200,\"msg\":\"success\",\"newslist\":[{\"gregoriandate\":\"2021-12-15\",\"lunardate\":\"2021-11-12\",\"lunar_festival\":\"????????????\",\"festival\":\"fest\",\"fitness\":\"??????.??????.??????.??????.??????\",\"taboo\":\"??????.??????.??????.??????.??????\",\"shenwei\":\"??????????????? ??????????????? ?????????????????????????????? ??????????????? \",\"taishen\":\"???????????????,???????????????,???????????????????????????????????????5???\",\"chongsha\":\"?????????(??????)???\",\"suisha\":\"?????????\",\"wuxingjiazi\":\"???\",\"wuxingnayear\":\"?????????\",\"wuxingnamonth\":\"?????????\",\"xingsu\":\"???????????????-???\",\"pengzu\":\"???????????? ????????????\",\"jianshen\":\"???\",\"tiangandizhiyear\":\"??????\",\"tiangandizhimonth\":\"??????\",\"tiangandizhiday\":\"??????\",\"lmonthname\":\"??????\",\"shengxiao\":\"???\",\"lubarmonth\":\"?????????\",\"lunarday\":\"??????\",\"jieqi\":\"???????????????\"}]}";
        ChineseDate cnDate = client.parseFromJson(json);
        System.out.println("parsing: " + json);
        System.out.println("parsed: " + cnDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        System.out.println("today's date: " + dateStr);
        cnDate = client.requestChineseDateInfo(dateStr);
        System.out.println("request from server: " + cnDate.toString());
    }
}
