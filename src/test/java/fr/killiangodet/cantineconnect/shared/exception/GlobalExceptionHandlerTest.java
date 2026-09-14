package fr.killiangodet.cantineconnect.shared.exception;

import fr.killiangodet.cantineconnect.billing.infrastructure.rest.BillingControllerTest;
import fr.killiangodet.cantineconnect.shared.application.exception.ApplicationException;
import fr.killiangodet.cantineconnect.shared.domain.exception.DomainException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Initialisation manuelle du validateur pour le mode standalone
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        this.mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
            .setControllerAdvice(new GlobalExceptionHandler())
            .setValidator(validator) // Activateur de l'annotation @Valid
            .build();
    }

    @Test
    @DisplayName("Should return HTTP 404 on ApplicationException")
    void shouldReturn404OnApplicationException() throws Exception {
        mockMvc.perform(get("/test/application-error"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title").value("Resource Not Found"))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.detail").value("Resource not found"))
            .andExpect(jsonPath("$.module").value("shared"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Should return HTTP 422 on DomainException")
    void shouldReturn422OnDomainException() throws Exception {
        mockMvc.perform(get("/test/domain-error"))
            .andExpect(status().is(HttpStatus.UNPROCESSABLE_CONTENT.value()))
            .andExpect(jsonPath("$.title").value("Business Rule Violation"))
            .andExpect(jsonPath("$.status").value(422))
            .andExpect(jsonPath("$.detail").value("Business rule violated"))
            .andExpect(jsonPath("$.module").value("shared"));
    }

    @Test
    @DisplayName("Should return HTTP 400 with field violations on validation error")
    void shouldReturn400OnValidationError() throws Exception {
        String invalidJsonPayload = """
                {
                  "name": ""
                }
                """;

        mockMvc.perform(post("/test/validation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonPayload))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Validation Failed"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.module").value("shared"))
            .andExpect(jsonPath("$.violations[0].field").value("name"));
    }

    @Test
    @DisplayName("Should return HTTP 500 on unhandled exception")
    void shouldReturn500OnUnhandledException() throws Exception {
        mockMvc.perform(get("/test/crash"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.title").value("Internal Server Error"))
            .andExpect(jsonPath("$.status").value(500))
            .andExpect(jsonPath("$.detail").value("An unexpected internal error occurred."))
            .andExpect(jsonPath("$.module").value("shared"));
    }

    @RestController
    static class TestController {

        @GetMapping("/test/application-error")
        void throwApplication() {
            throw new DummyApplicationException("Resource not found");
        }

        @GetMapping("/test/domain-error")
        void throwDomain() {
            throw new DummyDomainException("Business rule violated");
        }

        @GetMapping("/test/crash")
        void throwCrash() {
            throw new RuntimeException("Unexpected DB crash");
        }

        @PostMapping("/test/validation")
        void validate(@Valid @RequestBody DummyDto dto) {
        }
    }

    static class DummyApplicationException extends ApplicationException {
        public DummyApplicationException(String message) {
            super(message);
        }
    }

    static class DummyDomainException extends DomainException {
        public DummyDomainException(String message) {
            super(message);
        }
    }

    record DummyDto(
        @NotBlank(message = "Name is required")
        String name
    ) {}
}
