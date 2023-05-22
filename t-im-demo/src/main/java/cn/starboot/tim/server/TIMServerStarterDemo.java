package cn.starboot.tim.server;

import cn.starboot.tim.common.util.SetWithLimitedCapacity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DELL(mxd) on 2021/12/25 16:36
 */
public class TIMServerStarterDemo {

    public static void main(String[] args) {

//    	Test();

        TIMServerStarter instance = TIMServerStarter.getInstance();

        instance.start();
    }


    private static void Test() {
		Set<Integer> integers = new SetWithLimitedCapacity<>(10);

		System.out.println(integers.size());

		integers.add(10);
		integers.add(2);

		System.out.println(integers.size());

		integers.add(110);
		integers.add(22);
		integers.add(310);
		integers.add(42);
		integers.add(510);
		integers.add(62);
		integers.add(710);
		integers.add(82);
		integers.add(910);
		integers.add(772);

		System.out.println(integers.size());
	}


}
