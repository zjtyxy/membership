package cn.lger.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GoodsOrders {
    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id",strategy="uuid")
    private String id;
    @ManyToOne
    private Market market;
//    @ManyToOne
//    private Commodity commodity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
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
                ", market=" + market +
                '}';
//        ", commodity=" + commodity +
    }
}
