package ys.tq.wechat.utils;



import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import ys.tq.wechat.entity.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 推送类
 */
public class Pusher {

    public static void main(String[] args) {
        push();
    }
    private static String appId = "wx43804192c15a56d9";
    private static String secret = "8f36c0cf3c9d34facd22166399e719ef";

    private static List<WxMpTemplateMessage> list;

    public static void push(){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        //她
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("oHked53f23-eYh3Aq79i9XlZdoQs")
                .templateId("SpMf97CxxFbohs9o8duUWfWPH3vFDuAFWHSu5esW58M")
                .build();
        //我
        WxMpTemplateMessage templateMessage2 = WxMpTemplateMessage.builder()
                .toUser("oHked58Lt5McEvUdCpNBODu_ANT0")
                .templateId("SpMf97CxxFbohs9o8duUWfWPH3vFDuAFWHSu5esW58M")
                .build();
        list=new ArrayList<WxMpTemplateMessage>();//简便代码，下面用for循环
        list.add(templateMessage);
        list.add(templateMessage2);
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        Weather weather = WeatherUtils.getWeather();
        Map<String, String> map = CaiHongPiUtils.getEnsentence();
        for (WxMpTemplateMessage w:list) {
            w.addData(new WxMpTemplateData("riqi",weather.getDate() + "  "+ weather.getWeek(),"#00BFFF"));
            w.addData(new WxMpTemplateData("tianqi",weather.getText_now(),"#00FFFF"));
            w.addData(new WxMpTemplateData("low",weather.getLow() + "","#173177"));
            w.addData(new WxMpTemplateData("temp",weather.getTemp() + "","#EE212D"));
            w.addData(new WxMpTemplateData("high",weather.getHigh()+ "","#FF6347" ));
            w.addData(new WxMpTemplateData("windclass",weather.getWind_class()+ "","#42B857" ));
            w.addData(new WxMpTemplateData("winddir",weather.getWind_dir()+ "","#B95EA3" ));
            w.addData(new WxMpTemplateData("caihongpi",CaiHongPiUtils.getCaiHongPi(),"#FF69B4"));
            w.addData(new WxMpTemplateData("lianai",JiNianRiUtils.getLianAi()+"","#FF1493"));
            w.addData(new WxMpTemplateData("shengri1",JiNianRiUtils.getBirthday_Shuai()+"","#FFA500"));
            w.addData(new WxMpTemplateData("shengri2",JiNianRiUtils.getBirthday_RR()+"","#FFA500"));
            w.addData(new WxMpTemplateData("en",map.get("en") +"","#C71585"));
            w.addData(new WxMpTemplateData("zh",map.get("zh") +"","#C71585"));
            w.addData(new WxMpTemplateData("type",CaiHongPiUtils.getXZ().get("type") +"","#C71585"));
        }

        String beizhu = "帅帅❤冉冉";
        if(JiNianRiUtils.getLianAi() % 365 == 0){
            beizhu = "今天是恋爱" + (JiNianRiUtils.getLianAi() / 365) + "周年纪念日！";
        }
        if(JiNianRiUtils.getBirthday_Shuai()  == 0){
            beizhu = "今天是帅帅生日，生日快乐呀！";
        }
        if(JiNianRiUtils.getBirthday_RR()  == 0){
            beizhu = "今天是冉冉生日，生日快乐呀！";
        }
        templateMessage.addData(new WxMpTemplateData("beizhu",beizhu,"#FF0000"));
        templateMessage2.addData(new WxMpTemplateData("beizhu",beizhu,"#FF0000"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(templateMessage2.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage2));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
