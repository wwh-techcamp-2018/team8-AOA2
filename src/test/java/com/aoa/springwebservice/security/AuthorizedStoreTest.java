package com.aoa.springwebservice.security;

import com.aoa.springwebservice.domain.Store;
import com.aoa.springwebservice.domain.StoreRepository;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.domain.UserRepository;
import com.aoa.springwebservice.dto.*;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import com.aoa.springwebservice.service.StoreService;
import com.aoa.springwebservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@AutoConfigureMockMvc
@Slf4j
public class AuthorizedStoreTest {
    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession session;

    private User owner;
    private User notOwner;
    private Store createdStore;

    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;
    private static boolean dataLoaded = false;
    private MockHttpServletRequestBuilder builder;

    private MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());

    @Before
    public void setUp() throws Exception {

//        if (!dataLoaded) {
//            init();
//            session = new MockHttpSession();
//            builder = MockMvcRequestBuilders
//                    .multipart("/api/stores/"+ createdStore.getId()+"/menus/test")
//                    .file(mockMultipartFile)
//                    .param("name", "NAME")
//                    .param("description", "DESC")
//                    .param("price", "10000");
//
//
//            assertThat(createdStore.hasSameOwner(owner)).isTrue();
//            assertThat(createdStore.hasSameOwner(notOwner)).isFalse();
//
//            dataLoaded = true;
//        }

        UserInputDTO userInputDTO = UserInputDTO.builder().email("owner@example.com").name("주인").phoneNumber_1("010").phoneNumber_2("1234").phoneNumber_3("1234").uuid("12345").build();//new User("12345","주인장", "owner@example.com", "01012341234");

        owner = userService.create(userInputDTO);

        userInputDTO.setUuid("1234567");
        userInputDTO.setEmail("notOwner@example.com");
        userInputDTO.setName("주인아님");

        notOwner = userService.create(userInputDTO);


        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());

        StoreInputDTO dto = StoreInputDTO.builder().address("address").addressDetail("detail").description("desc").ownerName("주인장").phoneNumber_1("010").phoneNumber_2("1234").phoneNumber_3("1234")
                .postCode("12345").serviceDescription("홍보문구").storeName("storeName")
                .imageFile(mockMultipartFile)
                .build();
        createdStore = storeService.createStore(dto, owner);

        session = new MockHttpSession();
        builder = MockMvcRequestBuilders
                .multipart("/api/stores/"+ createdStore.getId()+"/menus/test")
                .file(mockMultipartFile)
                .param("name", "NAME")
                .param("description", "DESC")
                .param("price", "10000");


        assertThat(createdStore.hasSameOwner(owner)).isTrue();
        assertThat(createdStore.hasSameOwner(notOwner)).isFalse();

    }

    public void init() throws IOException {
        UserInputDTO userInputDTO = UserInputDTO.builder().email("owner@example.com").name("주인").phoneNumber_1("010").phoneNumber_2("1234").phoneNumber_3("1234").uuid("12345").build();//new User("12345","주인장", "owner@example.com", "01012341234");

        owner = userService.create(userInputDTO);

        userInputDTO.setUuid("1234567");
        userInputDTO.setEmail("notOwner@example.com");
        userInputDTO.setName("주인아님");

        notOwner = userService.create(userInputDTO);


        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());

        StoreInputDTO dto = StoreInputDTO.builder().address("address").addressDetail("detail").description("desc").ownerName("주인장").phoneNumber_1("010").phoneNumber_2("1234").phoneNumber_3("1234")
                .postCode("12345").serviceDescription("홍보문구").storeName("storeName")
                .imageFile(mockMultipartFile)
                .build();
        createdStore = storeService.createStore(dto, owner);


    }

    @Test
    public void createMenu_주인권한있음() throws Exception{
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, owner);
        this.mockMvc.perform(builder.session(session))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

    }
    @Test
    public void createMenu_주인권한없음() throws Exception{
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, notOwner);
        MvcResult result = this.mockMvc.perform(builder.session(session))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect((rs) -> rs.getResolvedException().getClass().equals(RuntimeException.class))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        log.debug("result error msg {}", result.getResponse().getErrorMessage());

        log.debug("result content {}", result.getResponse().getContentAsString());

    }
    @Test
    public void createMenu_로그인안함_세션없음() throws Exception{
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }
    @Test
    public void createMenu_가게없음() throws Exception{
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, null);
        builder = MockMvcRequestBuilders//.post("/api/stores/"+ createdStore.getId()+"/reservations")
                .multipart("/api/stores/"+1000+"/menus/test")
                .file(mockMultipartFile)
                .param("name", "NAME")
                .param("description", "DESC")
                .param("price", "10000");
        this.mockMvc.perform(builder.session(session))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }
}
