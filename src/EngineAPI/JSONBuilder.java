package EngineAPI;

import org.json.JSONObject;

public class JSONBuilder {
    public JSONObject getDocList() {
        return new JSONObject("{\n    \"method\": \"GetDocList\",\n    \"handle\": -1,\n    \"params\": {}\n}");
    }
    public JSONObject openDoc(String documentId) {
        return new JSONObject("{\n    \"method\": \"OpenDoc\",\n    \"handle\": -1,\n    \"params\": [\n        \"" + documentId + "\"\n],\n    \"outKey\": -1,\n    \"id\": 2\n}");
    }
    public JSONObject getAllInfos(Integer handleId) {
        return new JSONObject("{\n    \"method\": \"GetAllInfos\",\n    \"handle\": " + handleId + ",\n    \"params\": {}\n}");
    }
    public JSONObject getObject(String qObjectId) {
        return new JSONObject("{\n    \"method\": \"GetObject\",\n    \"handle\": 1,\n    \"params\": {\n        \"qId\": \""+ qObjectId +"\"\n},\n    \"outKey\": -1\n}");
    }
    public JSONObject exportData(Integer handleId) {
        return new JSONObject("{\n    \"method\": \"ExportData\",\n    \"handle\": " + handleId + ",\n    \"params\": [\n        \"CSV_C\",\n        \"/qHyperCubeDef\",\n        \"CsvUTF8.csv\"\n       ]\n}");
    }
}
