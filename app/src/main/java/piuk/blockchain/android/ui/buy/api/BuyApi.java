package piuk.blockchain.android.ui.buy.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import piuk.blockchain.android.ui.buy.api.data.BuyResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

// TODO: 03/03/2017 This will move to the jar. Temporarily placed here while not in sync with refactor branch.
public class BuyApi{

    private static BuyEndpoints buyEndpoints;

    private static BuyEndpoints getBuyApiInstance() {
        if (buyEndpoints == null) {
            buyEndpoints = BuyFrameWork.getRetrofitInstance().create(BuyEndpoints.class);
        }
        return buyEndpoints;
    }

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(Level.BASIC);

        return new OkHttpClient.Builder()
//            .addInterceptor(mockInterceptor)//Mock responses
//            .addInterceptor(loggingInterceptor)//Extensive logging
            .build();
    }

    private static Retrofit getRetrofit(String url, OkHttpClient client) {
        return new Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    }

    public static Call<BuyResponse> getSomething(String a, String b) {
        return getBuyApiInstance().getSomething(a, b);
    }
}
