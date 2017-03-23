package bhargavi.com.crimesyndicate;

import java.util.List;

import bhargavi.com.crimesyndicate.model.Crime;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CrimeAPI {
    @GET("/resource/ritf-b9ki.json")
    Call<List<Crime>> getCrimes();
}