package piuk.blockchain.android.ui.buy.api;


import retrofit2.Retrofit;

// TODO: 03/03/2017 This will move to the jar. Temporarily placed here while not in sync with refactor branch.
public final class BuyFrameWork {

    public interface ExchangePartnerInterface {
        Retrofit getRetrofitInstance();
    }

    private static ExchangePartnerInterface buyPartner;

    public static void init(ExchangePartnerInterface frameworkBuyPartner) {
        buyPartner = frameworkBuyPartner;
    }

    public static Retrofit getRetrofitInstance() {
        return buyPartner.getRetrofitInstance();
    }
}
