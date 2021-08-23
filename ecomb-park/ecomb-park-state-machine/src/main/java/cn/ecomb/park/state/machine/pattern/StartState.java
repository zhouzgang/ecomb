package cn.ecomb.park.state.machine.pattern;

/**
 * @author brian.zhou
 * @date 2021/8/20
 */
public class StartState extends AbstractState{

    public StartState(Context context) {
        super(context);
    }

    @Override
    public void doAction() {
        System.out.println("start do");
        context.setState(new BeginState(context));
    }
}
