package cn.ecomb.park.state.machine.pattern;

/**
 * @author brian.zhou
 * @date 2021/8/20
 */
public class PlayState extends AbstractState{


    public PlayState(Context context) {
        super(context);
    }

    @Override
    public void doAction() {
        System.out.println("play state");
        context.setState(new StopState(context));
    }
}
