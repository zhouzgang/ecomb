package cn.ecomb.park.state.machine.pattern;

/**
 * @author brian.zhou
 * @date 2021/8/20
 */
public class BeginState extends AbstractState{


    public BeginState(Context context) {
        super(context);
    }

    @Override
    public void doAction() {
        System.out.println("begin state");
        context.setState(new PlayState(context));
    }
}
