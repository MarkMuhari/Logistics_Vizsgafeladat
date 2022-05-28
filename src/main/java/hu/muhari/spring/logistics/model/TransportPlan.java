package hu.muhari.spring.logistics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TransportPlan {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "transportPlan")
    private List<Section> sections;
    private long expectedRevenue;

    public TransportPlan() {
    }

    public TransportPlan(long id, List<Section> sections, long expectedRevenue) {
        this.id = id;
        this.sections = sections;
        this.expectedRevenue = expectedRevenue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public long getExpectedRevenue() {
        return expectedRevenue;
    }

    public void setExpectedRevenue(long expectedRevenue) {
        this.expectedRevenue = expectedRevenue;
    }

    @Override
    public String toString() {
        return "TransportPlan{" +
                "id=" + id +
                ", sections=" + sections +
                ", expectedRevenue=" + expectedRevenue +
                '}';
    }
}
