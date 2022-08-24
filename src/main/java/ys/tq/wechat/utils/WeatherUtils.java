package ys.tq.wechat.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;
import ys.tq.wechat.entity.Weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 天气工具接口
 */
public class WeatherUtils {
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getWeather());
    }

    /**
     * 获取天气
     * @return
     */
    public static Weather getWeather(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap<String,String>();
        map.put("district_id","130100"); // 石家庄行政代码
        map.put("data_type","all");//这个是数据类型
        map.put("ak","xPgOmbGWpEEhp0DHF1VB3Pz63G3N60Uv");//百度天气ak码
        String res = restTemplate.getForObject(
                "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
                String.class,
                map);
        JSONObject json = JSONObject.parseObject(res);
        JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");
        List<Weather> weathers = forecasts.toJavaList(Weather.class);
        JSONObject now = json.getJSONObject("result").getJSONObject("now");
        Weather weather = weathers.get(0);
        weather.setText_now(now.getString("text"));
        weather.setTemp(now.getString("temp"));
        weather.setWind_class(now.getString("wind_class"));
        weather.setWind_dir(now.getString("wind_dir"));
        return weather;
    }
}
