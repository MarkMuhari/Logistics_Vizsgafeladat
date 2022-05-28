package hu.muhari.spring.logistics.dto;

public class DelayDto {

    private long milestoneId;
    private int delayInMinutes;

    public DelayDto() {
    }

    public DelayDto(long milestoneId, int delayInMinutes) {
        this.milestoneId = milestoneId;
        this.delayInMinutes = delayInMinutes;
    }

    public long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public int getDelayInMinutes() {
        return delayInMinutes;
    }

    public void setDelayInMinutes(int delayInMinutes) {
        this.delayInMinutes = delayInMinutes;
    }
}
