package com.peche3000.service;

import com.peche3000.entity.Produit;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaiementService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;
    // Stockage  des sessions Stripe associées à leurs produits
    private Map<String, List<Long>> sessions = new HashMap<>();


    public String createPaymentSession(List<Produit> produits, String email) throws Exception {
        Stripe.apiKey = stripeApiKey;

        List<SessionCreateParams.LineItem> lineItems = produits.stream()
                .map(produit -> SessionCreateParams.LineItem.builder()
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("eur")
                                        .setUnitAmount((long) (produit.getPrix() * 100)) // Conversion en centimes
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(produit.getNom())
                                                        .build()
                                        )
                                        .build()
                        )
                        .setQuantity(1L)
                        .build()
                ).collect(Collectors.toList());

        // Paramètres de création de la session Stripe
        SessionCreateParams params = SessionCreateParams.builder()
                .setCustomerEmail(email)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success?sessionId={CHECKOUT_SESSION_ID}") // URL de succès
                .setCancelUrl("http://localhost:8080/cancel") // URL en cas d'annulation
                .addAllLineItem(lineItems)
                .build();

        // Création de la session Stripe
        Session session = Session.create(params);

        // Stock les produits associés à cette session
        sessions.put(session.getId(), produits.stream().map(Produit::getId).collect(Collectors.toList()));

        return session.getUrl();
    }

    public boolean verifierSession(String sessionId) {
        try {
            Stripe.apiKey = stripeApiKey;
            Session session = Session.retrieve(sessionId);
            return "complete".equals(session.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Long> recupererProduitsDeSession(String sessionId) {
        return sessions.getOrDefault(sessionId, new ArrayList<>());
    }
}
