package ru.leroy.screenerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.AuthenticationException;
import ru.leroy.screenerapi.exception.EmailNotFoundException;
import ru.leroy.screenerapi.exception.UserNotFoundException;
import ru.leroy.screenerapi.service.UserService;
import ru.leroy.screenerapi.util.UsersUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@AutoConfigureJsonTesters
class UserControllerTest {

    @Mock
    private UserService service;

    private MockMvc mvc;

    @InjectMocks
    private UserController underTest;

    private JacksonTester<UserEntity> userJson;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        this.userEntity = new UsersUtil().buildRandomUser();
        final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
        JacksonTester.initFields(this, mapper);
        this.mvc = MockMvcBuilders
            .standaloneSetup(this.underTest).build();
    }

    @Test
    void registrationSuccess() throws Exception {
        given(this.service.registration(this.userEntity))
            .willReturn(this.userEntity);
        final MockHttpServletResponse response = this.mvc.perform(
            post("/user/registration")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.userJson.write(this.userEntity).getJson())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(
            this.userJson.write(this.userEntity).getJson()
        );
    }

    @Test
    void authenticationSuccess() throws Exception {
        given(this.service.authentication(this.userEntity))
                .willReturn(this.userEntity);
        final MockHttpServletResponse response = this.mvc.perform(
                        post("/user/authentication")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.userJson.write(this.userEntity).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(this.userJson.write(this.userEntity).getJson());
    }

    @Test
    void authenticationFailWithAuthenticationException() throws Exception {
        given(this.service.authentication(this.userEntity))
                .willThrow(AuthenticationException.class);
        final MockHttpServletResponse response = this.mvc.perform(
                        post("/user/authentication")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.userJson.write(this.userEntity).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void authenticationFailWithEmailNotFoundException() throws Exception {
        given(this.service.authentication(this.userEntity))
                .willThrow(EmailNotFoundException.class);
        final MockHttpServletResponse response = this.mvc.perform(
                        post("/user/authentication")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.userJson.write(this.userEntity).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void authenticationFailWithException() throws Exception {
        given(this.service.authentication(this.userEntity))
                .willThrow(IllegalStateException.class);
        final MockHttpServletResponse response = this.mvc.perform(
                        post("/user/authentication")
                                .content(this.userJson.write(this.userEntity).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse();
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void infoSuccess() throws Exception {
        given(this.service.userById(this.userEntity.getId()))
            .willReturn(this.userEntity);
        final MockHttpServletResponse response = this.mvc.perform(
            get("/user/info/".concat(String.valueOf(this.userEntity.getId())))
                .content(this.userJson.write(this.userEntity).getJson())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentAsString())
            .isEqualTo(this.userJson.write(this.userEntity).getJson());
    }

    @Test
    void infoFailWithUserNotFoundException() throws Exception {
        given(this.service.userById(this.userEntity.getId()))
            .willThrow(UserNotFoundException.class);
        final MockHttpServletResponse response = this.mvc.perform(
                get("/user/info/".concat(String.valueOf(this.userEntity.getId())))
                    .content(this.userJson.write(this.userEntity).getJson())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();
        assertThat(response.getStatus())
            .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void infoFailWithException() throws Exception {
        given(this.service.userById(this.userEntity.getId()))
            .willThrow(IllegalStateException.class);
        final MockHttpServletResponse response = this.mvc.perform(
                get("/user/info/".concat(String.valueOf(this.userEntity.getId())))
                    .content(this.userJson.write(this.userEntity).getJson())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();
        assertThat(response.getStatus())
            .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void changeRateByIdSuccess() throws Exception {
        this.userEntity.setRate("pro");
        given(this.service.updateRateById(this.userEntity.getId(), "pro"))
            .willReturn(this.userEntity);
        final MockHttpServletResponse response = this.mvc.perform(
            put("/user/change-rate/".concat(String.valueOf(this.userEntity.getId())))
                .content(this.userJson.write(this.userEntity).getJson())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();
        assertThat(response.getStatus())
            .isEqualTo(HttpStatus.ACCEPTED.value());
    }

    @Test
    void changeRateByIdFailWithUserNotFoundException() throws Exception {
        this.userEntity.setRate("pro");
        given(this.service.updateRateById(this.userEntity.getId(), "pro"))
            .willThrow(UserNotFoundException.class);
        final MockHttpServletResponse response = this.mvc.perform(
            put("/user/change-rate/".concat(String.valueOf(this.userEntity.getId())))
                .content(this.userJson.write(this.userEntity).getJson())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();
        assertThat(response.getStatus())
            .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void changeRateByIdFailWithException() throws Exception {
        this.userEntity.setRate("pro");
        given(this.service.updateRateById(this.userEntity.getId(), "pro"))
            .willThrow(IllegalStateException.class);
        final MockHttpServletResponse response = this.mvc.perform(
            put("/user/change-rate/".concat(String.valueOf(this.userEntity.getId())))
                .content(this.userJson.write(this.userEntity).getJson())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andReturn()
            .getResponse();
        assertThat(response.getStatus())
            .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}