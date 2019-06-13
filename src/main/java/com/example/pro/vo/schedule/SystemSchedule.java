package com.example.pro.vo.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @Auther: ccz
 * @Date: 2019/5/9 14:47
 * @Description:
 */
public class SystemSchedule implements SchedulingConfigurer {

    //任务调度周期
    @Value("${task.auto-promoted.cron:0 0 5 ? * *}")
    private String autoPromotedCron;
    @Value("${task.auto-promoted.enable:true}")
    private Boolean enable;



    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addCronTask(() -> {
            if (enable) {
                //执行内容
            }
        },autoPromotedCron);

    }
}
