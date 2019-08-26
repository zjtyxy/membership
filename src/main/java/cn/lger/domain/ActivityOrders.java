package cn.lger.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ActivityOrders {
    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id",strategy="uuid")
    private String id;
    @ManyToOne
    private Activity activity;
//    @ManyToOne
//    private Commodity commodity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

//    public Commodity getCommodity() {
//        return commodity;
//    }
//
//    public void setCommodity(Commodity commodity) {
//        this.commodity = commodity;
//    }

    @Override
    public String toString() {
        return "GoodsOrders{" +
                "id='" + id + '\'' +
                ", activity=" + activity +
                '}';
//        ", commodity=" + commodity +
    }
}
