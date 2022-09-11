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
    void registration_success() throws Exception {
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
    void info_success() throws Exception {
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
    void info_notFound() throws Exception {
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
    void info_badRequest() throws Exception {
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
    void  changeRateById_success() throws Exception {
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
    void changeRateById_failWithUserNotFoundException() throws Exception {
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
    void changeRateById_failWithException() throws Exception {
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