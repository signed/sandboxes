package samples;

import com.fasterxml.jackson.databind.node.ObjectNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ConfluenceClient {

    @GET("rest/api/content")
    Call<ObjectNode> getPage(@Query("spaceKey") String spaceKey, @Query("title") String title, @Query("expand") String expand);
}
