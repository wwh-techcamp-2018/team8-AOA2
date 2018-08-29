package com.aoa.springwebservice.security;

import com.aoa.springwebservice.domain.*;
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
import org.springframework.core.io.ClassPathResource;
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
import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private StoreService storeService;
    private static boolean dataLoaded = false;
    private MockHttpServletRequestBuilder builder;

    private MockMultipartFile mockMultipartFile;
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

/*
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
*/
//        File file = new ClassPathResource("/static/images/goal.png").getFile();
//        mockMultipartFile = new MockMultipartFile("file", "goal.png", "image/png",  new FileInputStream(file));
//
//        assertThat(mockMultipartFile.isEmpty()).isFalse();
//        assertThat(mockMultipartFile.getSize()).isGreaterThan(10);
//
//        log.debug("mock File {} ", mockMultipartFile.getBytes());
        prepareDefaultUser();
        prepareDefaultStore(owner);
        prepareDefaultMenus(createdStore);
        session = new MockHttpSession();
//        builder = MockMvcRequestBuilders
////                .multipart("/api/stores/"+ createdStore.getId()+"/menus")
////                .file("file", mockMultipartFile.getBytes())
////
////                .param("name", "NAME")
////                .param("description", "DESC")
////                .param("price", "10000")
////                .characterEncoding("UTF-8");

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
                //.andExpect((rs) -> rs.getResolvedException().getClass().equals(RuntimeException.class))
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
        assertThat(result.getResolvedException().getClass()).isAssignableFrom(EntityNotFoundException.class);

    }
}
