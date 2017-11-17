package postagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Jalal Sajadi and Sina Ahmadi
 * @since 1.0 (2015-06-16)
 */
public class ItemList extends ArrayList<String> {
    private final Logger LOG = Logger.getLogger(getClass().getName());

    public ItemList(String filePath) {
        try {
            FileInputStream f = new FileInputStream(new File(filePath));
            InputStreamReader isr = new InputStreamReader(f, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            while (br.ready()) {
                add(br.readLine());
            }
            LOG.info("[" + filePath + "] contains " + size() + " item(s).");

        } catch (Exception e) {
            LOG.warning(e.getMessage());
        }
    }

}
