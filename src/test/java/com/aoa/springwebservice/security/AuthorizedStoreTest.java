package com.aoa.springwebservice.security;

import com.aoa.springwebservice.domain.*;
import com.aoa.springwebservice.dto.*;
import com.aoa.springwebservice.exception.CustomerOrderException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
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
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;
    private MockHttpServletRequestBuilder builder;

    private MockMultipartFile mockMultipartFile;
    @Before
    public void setUp() throws Exception {

        prepareDefaultUser();
        prepareDefaultStore(owner);
        prepareDefaultMenus(createdStore);
        session = new MockHttpSession();

        builder = MockMvcRequestBuilders
                .get("/api/stores/"+createdStore.getId()+"/menus");

        assertThat(createdStore.hasSameOwner(owner)).isTrue();
        assertThat(createdStore.hasSameOwner(notOwner)).isFalse();

    }
  
    private void prepareDefaultUser() throws IOException {
        UserInputDTO userInputDTO = UserInputDTO.builder().email("owner@example.com").name("주인").phoneNumber_1("010").phoneNumber_2("1234").phoneNumber_3("1234").uuid("12345").build();//new User("12345","주인장", "owner@example.com", "01012341234");
        owner = userInputDTO.toEntity();
        owner = userRepository.save(owner);

        userInputDTO.setUuid("1234567");
        userInputDTO.setEmail("notOwner@example.com");
        userInputDTO.setName("주인아님");
        notOwner = userInputDTO.toEntity();
        notOwner = userRepository.save(notOwner);
    }

    private void prepareDefaultStore(User user) {
        createdStore = Store.builder()
                .user(user)
                .description("DESC")
                .imgURL("img")
                .ownerName("주인")
                .phoneNumber("1234512345")
                .postCode("12345")
                .serviceDescription("create menu 가게관점")
                .storeName("storeName")
                .address("ADDRESS")
                .build();
        storeRepository.save(createdStore);
    }
    private void prepareDefaultMenus(Store store) {
        Menu defaultMenu = Menu.builder()
                .store(store)
                .name("test1")
                .description("test1")
                .price(1)
                .imageUrl("/path")
                .build();
        defaultMenu = Menu.builder().store(store).name("test2").description("test2").price(2).imageUrl("/path").build();
        store.addMenu(defaultMenu);
        storeRepository.save(store);
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
                //todo exception 수정
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        assertThat(result.getResolvedException().getClass()).isAssignableFrom(RuntimeException.class);

    }
    @Test
    public void createMenu_로그인안함_세션없음() throws Exception{
        MvcResult result = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        assertThat(result.getResolvedException().getClass()).isAssignableFrom(RuntimeException.class);

    }
    @Test
    public void createMenu_가게없음() throws Exception{
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, owner);
        builder = MockMvcRequestBuilders
                .get("/api/stores/"+ (Integer.MAX_VALUE - 1)+"/menus");

        MvcResult result = this.mockMvc.perform(builder.session(session))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        assertThat(result.getResolvedException().getClass()).isAssignableFrom(CustomerOrderException.class);

    }
}
