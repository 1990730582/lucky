import cn.smbms.tools.ProperManager_;
import org.junit.Test;

import java.util.Properties;

public class test {


    @Test
    public void getValueByKey() {

        System.out.println( ProperManager_.getInstance().getValueByKey("url"));
    }
}
