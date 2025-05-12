package gorae.backend.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PaymentSource(PaypalPaymentSource paypal) {
    public record PaypalPaymentSource(
            @JsonProperty("experience_context") ExperienceContext experienceContext
    ) {
        @Builder
        public record ExperienceContext(
                @JsonProperty("brand_name") String brandName,
                @JsonProperty("shipping_preference") ShippingPreference shippingPreference,
                @JsonProperty("landing_page") LandingPage landingPage,
                @JsonProperty("return_url") String returnUrl,
                @JsonProperty("cancel_url") String cancelUrl,
                @JsonProperty("user_action") UserAction userAction
        ) {
            public enum ShippingPreference {
                GET_FROM_FILE,
                NO_SHIPPING,
                SET_PROVIDED, ADDRESS
            }

            public enum LandingPage {
                LOGIN,
                GUEST_CHECKOUT,
                NO_PREFERENCE
            }

            public enum UserAction {
                CONTINUE,
                PAY_NOW
            }
        }
    }
}