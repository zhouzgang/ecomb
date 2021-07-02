package cn.ecomb.park.pattern.behavior;

import cn.ecomb.park.pattern.Pattern;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 策略模式
 * 意图：策略模式是一种行为设计模式， 它能让你定义一系列算法， 并将每种算法分别放入独立的类中， 以使算法的对象能够相互替换。
 *
 * @author brian.zhou
 * @date 2021/7/2
 */
@Slf4j
public class Strategy implements Pattern {

    /**
     * 计算接口
     */
    interface Computation {
        boolean match(String strategyCode);
        String compute(String param);
    }

    class Add implements Computation{
        @Override
        public boolean match(String strategyCode) {
            return "+".equals(strategyCode);
        }

        @Override
        public String compute(String param) {
            log.info("执行加法计算：{}", param);
            return param;
        }
    }

    class Subtract implements Computation{
        @Override
        public boolean match(String strategyCode) {
            return "-".equals(strategyCode);
        }

        @Override
        public String compute(String param) {
            log.info("执行减法计算：{}", param);
            return param;
        }
    }

    class Multiply implements Computation{
        @Override
        public boolean match(String strategyCode) {
            return "*".equals(strategyCode);
        }

        @Override
        public String compute(String param) {
            log.info("执行乘法计算：{}", param);
            return param;
        }
    }


    class Context {
        private List<Computation> computations = new LinkedList<>();

        public Context() {
            /*
             这里可以结合工厂模式

             */
            log.info("初始化基础策略");
            this.computations = Stream.of(new Add(), new Subtract()).collect(Collectors.toList());
        }

        /**
         * 在 Spring 里面使用自动注入的方式注册整个实现
         * @param computation
         */
        public void register(Computation computation) {
            log.info("注册一个执行策略");
            computations.add(computation);
        }

        public Computation match(String strategyCode) {
            for (Computation computation: computations) {
                if (computation.match(strategyCode)) {
                    return computation;
                }
            }
            return null;
        }

        public String execute(String strategyCode, String param) {
            return match(strategyCode).compute(param);
        }
    }

    @Override
    public void execute(String param) {
        log.info("策略模式 demo");
        Context context = new Context();
        String result = context.match("+").compute(param);
        log.info("执行结果：{}", result);
        context.register(new Multiply());
        result = context.execute("*", param);
        log.info("执行结果：{}", result);
    }

    private static final Strategy strategy = new Strategy();

    private Strategy() {
    }

    public static Pattern getInstance() {
        return strategy;
    }

}
