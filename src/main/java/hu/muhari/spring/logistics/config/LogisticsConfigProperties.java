package hu.muhari.spring.logistics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "logistics")
@Component
public class LogisticsConfigProperties {

    private RevenueDropPercentage revenueDropPercentage = new RevenueDropPercentage();


    public static class RevenueDropPercentage {

        private int delayOf30Minutes;
        private int delayOf60Minutes;
        private int delayOf120Minutes;

        public int getDelayOf30Minutes() {
            return delayOf30Minutes;
        }

        public void setDelayOf30Minutes(int delayOf30Minutes) {
            this.delayOf30Minutes = delayOf30Minutes;
        }

        public int getDelayOf60Minutes() {
            return delayOf60Minutes;
        }

        public void setDelayOf60Minutes(int delayOf60Minutes) {
            this.delayOf60Minutes = delayOf60Minutes;
        }

        public int getDelayOf120Minutes() {
            return delayOf120Minutes;
        }

        public void setDelayOf120Minutes(int delayOf120Minutes) {
            this.delayOf120Minutes = delayOf120Minutes;
        }
    }

    public RevenueDropPercentage getRevenueDropPercentage() {
        return revenueDropPercentage;
    }

    public void setRevenueDropPercentage(RevenueDropPercentage revenueDropPercentage) {
        this.revenueDropPercentage = revenueDropPercentage;
    }

}
