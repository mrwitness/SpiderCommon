package wuxian.me.spidercommon;

import wuxian.me.spidercommon.util.FileUtil;

/**
 * Created by wuxian on 11/6/2017.
 */
public class Main {

    public static void main(String[] args) {

        FileUtil.initWithClass(Main.class);

        FileUtil.writeToFile(FileUtil.getCurrentPath()+"/test.txt","hello_world");
    }
}
