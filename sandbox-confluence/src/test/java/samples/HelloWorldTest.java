package samples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Base64;

public class HelloWorldTest {

    private final String baseUrl = "https://example.org:8090/";
    private final String credentials = "user:password";

    @Test
    public void fetchFromServer() throws Exception {
        Call<ObjectNode> demo = client().getPage("DEMO", "First Page", "body.storage");

        Response<ObjectNode> response = demo.execute();
        System.out.println(response.code());
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response.body()));
    }

    @Test
    public void createNewPage() throws Exception {
        Call<Void> call = client().createPage(new CreatePageTO.Builder()
                .space("DEMO")
                .title("Automation for the win")
                .content("<p>I like the storage format.</p>").build());

        Response response = call.execute();
        System.out.println(response.code());

    }

    private ConfluenceClient client() {
        final String basic = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", basic)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);

        Retrofit confluence = new Retrofit.Builder()
                .client(client.build())
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return confluence.create(ConfluenceClient.class);
    }

}
