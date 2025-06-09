package e_store.unit_tests.rest;

import e_store.dto.in.UserCreateUpdateDto;
import e_store.dto.out.UserReadDto;
import e_store.rest.UserRestController;
import e_store.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService mockUserService;

    @Test
    void findAll_WhenEntitiesExistThenReturnsEntityList() throws Exception {
        UserReadDto userReadDtoOne = new UserReadDto(
                1L, "Fname",
                "Lname",
                "e@mail.com",
                List.of());
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<UserReadDto> page = new PageImpl<>(
                List.of(userReadDtoOne),
                pageRequest,
                1);
        Mockito.when(mockUserService.findAll(PageRequest.of(0, 5))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].firstName").value("Fname"))
                .andExpect(jsonPath("$.content[0].lastName").value("Lname"))
                .andExpect(jsonPath("$.content[0].email").value("e@mail.com"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.pageSize").value(5))
                .andExpect(jsonPath("$.totalElements").value(1));

        verify(mockUserService, times(1)).findAll(pageRequest);
    }

    @Test
    void create_WhenValidEntityThenReturnsSavedEntity() throws Exception {
        UserReadDto outDto = new UserReadDto(
                1L,
                "Fname",
                "Lname",
                "a@a.a",
                List.of());
        Mockito.when(mockUserService.create(any(UserCreateUpdateDto.class))).thenReturn(outDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/users")
                        .content("""
                                { "firstName": "Fname",
                                  "lastName": "Lname",
                                  "email": "a@a.a"
                                }""")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(outDto.id()))
                .andExpect(jsonPath("$.firstName").value(outDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(outDto.lastName()))
                .andExpect(jsonPath("$.email").value(outDto.email()))
                .andExpect(jsonPath("$.ordersDtoLst").value(Matchers.hasSize(0))); //TODO по какой-то причине возвращается null, а не [] если сравнивать с List.of()

        verify(mockUserService, times(1)).create(
                new UserCreateUpdateDto("Fname", "Lname", "a@a.a"));
    }
}