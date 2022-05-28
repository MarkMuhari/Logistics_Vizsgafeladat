package hu.muhari.spring.logistics.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import hu.muhari.spring.logistics.config.LogisticsConfigProperties;
import hu.muhari.spring.logistics.dto.DelayDto;
import hu.muhari.spring.logistics.dto.LoginDto;
import hu.muhari.spring.logistics.model.Milestone;
import hu.muhari.spring.logistics.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TransportPlanControllerIT {

    private static final String TRANSPORTPLANS_BASE_URI = "/api/transportPlans";
    private static final String LOGIN_URI = "/api/login";

    private long transportPlanId;
    private String jwtToken;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    AddressService addressService;

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    SectionService sectionService;

    @Autowired
    TransportPlanService transportPlanService;

    @Autowired
    InitDbService initDbService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    LogisticsConfigProperties config;


    @BeforeEach
    public void initDb() {
        transportPlanId = initDbService.init().getId();
    }

    @Test
    void testThatWeCanLogin() {
        loginAdmin();
    }

    @Test
    void testSecurityConfig_BadUserNameAndPassword_NotValid() {
        loginWithWrongJwt("admin", "dummy");
        loginWithWrongJwt("user", "passAdmin");
        loginWithWrongJwt("transport", "passss");
    }

    @Test
    void testSecurityConfig() {
        loginWithCorrectJwt("admin", "pass");
        loginWithCorrectJwt("transport", "pass");
        loginWithCorrectJwt("address", "pass");

    }

    @Test
    void testTransportPlanController_WeCannotAddDelayTheWrongAuthorities_NotValid() {
        jwtToken = loginAddress();
        addDelayToATransportPlanForbidden(0, 0, 1, jwtToken);
    }

    @Test
    void testTransportPlan_NotFoundByNotConnectingTheCorrectTransportPlan_Not_Found() {
        jwtToken = loginTransport();
        List<Milestone> milestones = milestoneService.getAllMilestones();
        long milestoneId = milestones.get(milestones.size() - 1).getId();
        transportPlanId = 0;
        addDelayToATransportPlanBadRequest(transportPlanId, milestoneId, 1, jwtToken);
    }

    @Test
    void testDelay_WhenTheDelayIsUnder30MinutesWeGetTheOriginalRevenue_Valid() {
        jwtToken = loginTransport();
        List<Milestone> milestones = milestoneService.getAllMilestones();
        long milestoneId = milestones.get(0).getId();
        long originalRevenue = transportPlanService.findById(transportPlanId).get().getExpectedRevenue();
        int delayInMinutes = 15;

        addDelayToATransportPlanOk(transportPlanId, milestoneId, delayInMinutes, jwtToken);
        long modifiedRevenue = transportPlanService.findById(transportPlanId).get().getExpectedRevenue();
        assertThat(modifiedRevenue).isEqualTo(originalRevenue);
    }

    @Test
    void testDelay_WhenTheDelayIsUnder60MinutesButAbove30Minutes_Valid() {
        jwtToken = loginTransport();
        List<Milestone> milestones = milestoneService.getAllMilestones();
        long milestoneId = milestones.get(0).getId();
        long originalRevenue = transportPlanService.findById(transportPlanId).get().getExpectedRevenue();
        int delayInMinutes = 35;

        addDelayToATransportPlanOk(transportPlanId, milestoneId, delayInMinutes, jwtToken);
        long modifiedRevenue = transportPlanService.findById(transportPlanId).get().getExpectedRevenue();
        assertThat(modifiedRevenue).isEqualTo(
                (long) (originalRevenue * (100 - config.getRevenueDropPercentage().getDelayOf30Minutes()) * 0.01));
    }

    @Test
    void testDelay_WhenTheDelayIsUnder120MinutesButAbove60Minutes_Valid() {
        jwtToken = loginTransport();
        List<Milestone> milestones = milestoneService.getAllMilestones();
        long milestoneId = milestones.get(0).getId();
        long originalRevenue = transportPlanService.findById(transportPlanId).get().getExpectedRevenue();
        int delayInMinutes = 65;

        addDelayToATransportPlanOk(transportPlanId, milestoneId, delayInMinutes, jwtToken);
        long modifiedRevenue = transportPlanService.findById(transportPlanId).get().getExpectedRevenue();
        assertThat(modifiedRevenue).isEqualTo(
                (long) (originalRevenue * (100 - config.getRevenueDropPercentage().getDelayOf60Minutes()) * 0.01));
    }

    @Test
    void testDelay_WhenTheDelayIsAbove120Minutes_Valid() {
        jwtToken = loginTransport();
        List<Milestone> milestones = milestoneService.getAllMilestones();
        long milestoneId = milestones.get(0).getId();
        long originalRevenue = transportPlanService.findById(transportPlanId).get().getExpectedRevenue();
        int delayInMinutes = 135;

        addDelayToATransportPlanOk(transportPlanId, milestoneId, delayInMinutes, jwtToken);
        long modifiedRevenue = transportPlanService.findById(transportPlanId).get().getExpectedRevenue();
        assertThat(modifiedRevenue).isEqualTo(
                (long) (originalRevenue * (100 - config.getRevenueDropPercentage().getDelayOf120Minutes()) * 0.01));
    }

    @Test
    void testModifyDelay_AddDelayToTheMilestoneAndCheckTheDelayForTheNextMilestone_HTTPOK() {
        jwtToken = loginTransport();
        List<Milestone> originalMilestones = milestoneService.getAllMilestones();
        long milestoneId = originalMilestones.get(0).getId();
        int delayInMinutes = 10;
        addDelayToATransportPlanOk(transportPlanId, milestoneId, delayInMinutes, jwtToken);
        List<Milestone> modifiedMilestones = milestoneService.getAllMilestones();
        assertThat(originalMilestones.get(0).getPlannedTime().plusMinutes(delayInMinutes))
                .isEqualTo(modifiedMilestones.get(0).getPlannedTime());
        assertThat(originalMilestones.get(1).getPlannedTime().plusMinutes(delayInMinutes))
                .isEqualTo(modifiedMilestones.get(1).getPlannedTime());
    }


    private String loginAddress() {
        return loginWithCorrectJwt("address", "pass");
    }

    private String loginTransport() {
        return loginWithCorrectJwt("transport", "pass");
    }

    private String loginAdmin() {
        return loginWithCorrectJwt("admin", "pass");
    }

    private String loginWithCorrectJwt(String username, String password) {
        LoginDto loginDto = new LoginDto(username, password);
        return webTestClient.post().uri(LOGIN_URI).bodyValue(loginDto).exchange().expectStatus().isOk()
                .expectBody(String.class).returnResult().getResponseBody();
    }

    private void loginWithWrongJwt(String username, String password) {
        LoginDto loginDto = new LoginDto(username, password);
        webTestClient.post().uri(LOGIN_URI).bodyValue(loginDto).exchange().expectStatus().isForbidden();
    }

    private void addDelayToATransportPlanOk(long transportPlanId, long milestoneId, int delayInMinutes,
                                            String jwtToken) {
        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);
        webTestClient.post().uri(TRANSPORTPLANS_BASE_URI + "/" + transportPlanId + "/delay")
                .headers(headers -> headers.setBearerAuth(jwtToken)).bodyValue(delayDto).exchange().expectStatus()
                .isOk();
    }

    private void addDelayToATransportPlanBadRequest(long transportPlanId, long milestoneId, int delayInMinutes,
                                                    String jwtToken) {
        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);
        webTestClient.post().uri(TRANSPORTPLANS_BASE_URI + "/" + transportPlanId + "/delay")
                .headers(headers -> headers.setBearerAuth(jwtToken)).bodyValue(delayDto).exchange().expectStatus()
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void addDelayToATransportPlanForbidden(long transportPlanId, long milestoneId, int delayInMinutes,
                                                   String jwtToken) {
        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);
        webTestClient.post().uri(TRANSPORTPLANS_BASE_URI + "/" + transportPlanId + "/delay")
                .headers(headers -> headers.setBearerAuth(jwtToken)).bodyValue(delayDto).exchange().expectStatus()
                .isEqualTo(HttpStatus.FORBIDDEN);
    }
}