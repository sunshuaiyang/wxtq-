package ys.tq.wechat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ys.tq.wechat.utils.Pusher;

@SpringBootApplication
@EnableScheduling
public class WechatpushApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatpushApplication.class, args);
    }

    /**
     * 定时每天早上七点半
     * 从左到右分别代表秒 分 时 日 月 星期 年(可不填)
     * *表示任意
     */
    @Scheduled(cron = "0 30 7 * * ?")
    public void goodMorning(){
        Pusher.push();
    }

}
