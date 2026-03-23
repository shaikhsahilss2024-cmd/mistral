import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

public class MistralUtil {

    static String API_KEY = System.getenv("MISTRAL_API_KEY");

    public static String getXPath(String dom, String failedLocator) {

        // 🔥 Limit DOM size
        if (dom.length() > 5000) {
            dom = dom.substring(0, 5000);
        }

        JSONObject body = new JSONObject();

        body.put("model", "mistral-small-latest");

        String prompt = "You are a Selenium expert.\n"
                + "Find XPath for username input field.\n"
                + "Return ONLY XPath starting with //.\n\n"
                + "Failed locator: " + failedLocator + "\n\n"
                + "HTML:\n" + dom;

        body.put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                        .put("role", "user")
                        .put("content", prompt)));

        String API_KEY = System.getenv("MISTRAL_API_KEY");

        if (API_KEY == null) {
            throw new RuntimeException("❌ API Key not found. Check environment variable.");
        }

        Response res = RestAssured.given()
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .post("https://api.mistral.ai/v1/chat/completions");
        // 🔍 Debug
        String responseBody = res.asString();
        System.out.println("🔍 Mistral Response: " + responseBody);

        String xpath = res.jsonPath().getString("choices[0].message.content");

        if (xpath == null || xpath.isEmpty()) {
            throw new RuntimeException("❌ AI failed to return XPath");
        }

        return xpath.trim();
    }
}