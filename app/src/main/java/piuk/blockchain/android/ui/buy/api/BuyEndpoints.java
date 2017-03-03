package piuk.blockchain.android.ui.buy.api;

import okhttp3.ResponseBody;
import piuk.blockchain.android.ui.buy.api.data.BuyResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

// TODO: 03/03/2017 This will move to the jar. Temporarily placed here while not in sync with refactor branch.
public interface BuyEndpoints {

    @GET("buy_something")
    Call<BuyResponse> getSomething(
        @Query("method") String method,
        @Query("guid") String guid);

}
