package design;

import java.util.Observable;
import java.util.Observer;

/**
 * 实现个观察者模式（发布订阅模式），复习下。这个模式还算比较常用
 *
 * 观察者模式： 1个被观察者，被N个观察者观察。如果被观察者触发了事件则通知所有观察者。
 *
 * 例子：古董的价钱是作为被观察的，如果发生事件波动就告知所有观察者。
 *
 * Created by Kaiming Wan on 2017/1/13.
 */
public class ObserverExample {

    public static void main(String[] args) {
        Antique house = new Antique(1222f);
        People p1 = new People("p1");
        People p2 = new People("p2");
        People p3 = new People("p3");
        house.addObserver(p1);
        house.addObserver(p2);
        house.addObserver(p3);

        System.out.println(house);// 输出波动前的价格
        house.setPrice(111f);
        System.out.println(house); // 输出波动后的价格

    }



}

/**
 * 可以继承Observable，说明是被观察的。关键调用父类的setChanged方法和notifyObservers方法来触发通知
 */
class Antique extends Observable {
    private float mPrice;// 价钱

    public Antique(float price) {
        this.mPrice = price;
    }

    public float getPrice() {
        return this.mPrice;
    }

    public void setPrice(float price) {
        super.setChanged();
        super.notifyObservers(price);// 价格被改变
        this.mPrice = price;
    }

    public String toString() {
        return "古董价格为：" + this.mPrice;
    }
}

/**
 * 观察者
 */
class People implements Observer {

    private String name;

    public People(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable observable, Object data) {

        System.out.println("People update() -> update name:"+ this.name + ",price:"+ ((Float)data).floatValue());

    }

}
