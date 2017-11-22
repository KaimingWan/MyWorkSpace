package design;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Vector;

/**
 * 事件监听模式： 可以理解为观察者模式的升级版。注意观察者模式相比事件监听模式的缺点：
 * 观察者模式本质是发布订阅模式，缺点是观察者被通知后都只能用一个update()方法来响应并且处理
 *
 * 事件监听模式和观察者模式选择依据：
 *  如果多个事件源需要被监听（观察），并且针对不同事件需要不同的处理，那么就要使用事件监听模式；
 *  如果只有一个单一的事件需要通知，符合发布订阅模型就用观察者模式
 *
 * Created by Kaiming Wan on 2017/1/13.
 */
public class EventListenerExample {


    /**
     * 例子：
     *
     * 事件对象（event object）：start事件和stop事件
     * 事件监听器（event listener）： start listener和stop listener
     * 事件源(event source):  负责监听器的注册删除，以及触发事件通知
     *
     * @param args
     */

    public static void main(String[] args) {
        //定义一个事件源用来接收管理事件和事件监听器
        EventSource eventSource = new EventSource();
        //增加Start和Stop两个事件的监听器
        eventSource.addListener(new StartEventListener());
        eventSource.addListener(new StopEventListener());

        //尝试把Start和Stop事件扔给事件源，触发监听器里面的处理方法
        eventSource.notifyListenerEvents(new StartEvent("start"));
        eventSource.notifyListenerEvents(new StopEvent("stop"));

    }



}





/**
 * 定义开始事件
 *
 * 重点是理解父类中source对象的含义。source对象是触发该事件的源头。即通过source对象来触发这个StartEvent
 */
class StartEvent extends EventObject{

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public StartEvent(Object source) {
        super(source);
    }



}

/**
 * 定义结束事件
 */
class StopEvent extends EventObject{

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public StopEvent(Object source) {
        super(source);
    }


}


/**
 * 定义一个监听器接口，方便以后实现别的监听器。
 *  JDK提供的EventListener接口里面没有任何抽象方法，不方便以后实现使用。
 *  所以这里重新实现一个更加详细的监听器接口（包含一个handler方法）
 *
 */

interface MyEventListener extends EventListener{

    /**
     * 在监听器类里面进行事件的处理
     * @param eventObject 触发事件的对象
     */
    public void handleEvent(EventObject eventObject);
}


/**
 * 具体的监听器类
 * PS：在具体的监听器类里面判断事件类型，然后进行处理
 */
class StartEventListener implements MyEventListener{


    @Override
    public void handleEvent(EventObject eventObject) {
//        判断下事件源的类型，然后符合则进行相关的处理
        if (eventObject instanceof StartEvent) {
            System.out.println("StartEvent is being handled...");
        }
    }
}

class StopEventListener implements MyEventListener{


    @Override
    public void handleEvent(EventObject eventObject) {
        if (eventObject instanceof StopEvent) {
            System.out.println("StopEvent is being handled...");
        }
    }
}



class EventSource {
    //监听器列表，监听器的注册则加入此列表
    private Vector<MyEventListener> ListenerList = new Vector<MyEventListener>();
    //注册监听器
    public void addListener(MyEventListener eventListener){
        ListenerList.add(eventListener);
    }
    //撤销注册
    public void removeListener(MyEventListener eventListener){
        ListenerList.remove(eventListener);
    }

    //接受外部事件。外部函数内可以将事件扔给事件源然后让监听器去触发
    public void notifyListenerEvents(EventObject event){
        for(MyEventListener eventListener:ListenerList){
            eventListener.handleEvent(event);
        }
    }

}