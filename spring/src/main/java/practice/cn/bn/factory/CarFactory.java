package practice.cn.bn.factory;

import org.springframework.beans.factory.FactoryBean;
import practice.cn.bn.entity.Car;

public class CarFactory implements FactoryBean<Car> {
    private String carName;
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setName(carName);
        return car;
    }

    public Class<?> getObjectType() {
        return Car.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
}
