package dk.sdu.mmmi.cbse.main;

import org.springframework.web.client.RestTemplate;

public class ScoringClient {
    private final RestTemplate rest;
    private final String base;

    public ScoringClient(RestTemplate rest, String base) {
        this.rest = rest; this.base = base;
    }
    public int add(int points) {
        return rest.postForObject(base + "/score/add?points=" + points, null, Integer.class);
    }
    public int get() {
        Integer v = rest.getForObject(base + "/score", Integer.class);
        return v == null ? 0 : v;
    }
    public void reset() {
        rest.postForLocation(base + "/score/reset", null);
    }
}
