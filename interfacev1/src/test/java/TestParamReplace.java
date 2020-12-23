/**
 * Author:   tyx
 * Date:     2020/12/21 11:21
 * Description:
 */
public class TestParamReplace {
    public static void main(String[] args) {
        String para="ids=$param&labelType=VERIFY";
        para=para.replace("$param","25l8lmaumpdg");
        System.out.println(para);
    }
}
 
