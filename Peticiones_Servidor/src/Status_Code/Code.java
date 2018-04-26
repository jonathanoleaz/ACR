package Status_Code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Code {
    HashMap<String, String> Status_Code = new HashMap<String, String>();
    
    public Code(){
        Status_Code.put("Continue", "100 Continue");
        Status_Code.put("OK", "200 OK");
        Status_Code.put("Accepted", "202 Accepted");
        Status_Code.put("Found", "302 Found");
        Status_Code.put("Not Modified", "304 Not Modified");
        Status_Code.put("Unauthorized", "401 Unauthorized");
        Status_Code.put("Not Found", "404 Not Found");
        Status_Code.put("Unsupported Media Type", "415 Unsupported Media Type");
        Status_Code.put("Internal Server Error", "500 Internal Server Error");
        Status_Code.put("Not Implemented", "501 Not Implemented");
    }
    public String getCode(String codeName){
        return Status_Code.get(codeName);
    }
}
