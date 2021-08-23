package cn.ecomb.park.state.machine.pattern;

/**
 * 状态模式
 *
 * @author brian.zhou
 * @date 2021/8/20
 */
public class StatePattern {

    public static void main(String[] args) {
        Context context = new Context();
        context.doAction();
        context.doAction();
        context.doAction();
        context.doAction();
    }
}
