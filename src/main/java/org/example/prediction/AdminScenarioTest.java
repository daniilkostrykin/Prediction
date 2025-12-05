/*package org.example.prediction;

import org.example.prediction.dto.form.AddEventDto;
import org.example.prediction.services.AdminService;
import org.example.prediction.services.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminScenarioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private AdminService adminService; // Нужно замокать, так как AdminController его использует

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAdminCanAccessDashboard() throws Exception {
        // Админ должен иметь доступ к /admin
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAdminCanCreateEvent() throws Exception {
        // Эмулируем заполнение формы создания события
        mockMvc.perform(post("/events/add")
                        .param("title", "Тестовое событие 2026")
                        .param("description", "Описание теста")
                        // Передаем дату в формате, который ждет @DateTimeFormat (yyyy-MM-dd'T'HH:mm)
                        .param("closesAt", "2026-12-31T23:59")
                        .param("options[0]", "Вариант А")
                        .param("options[1]", "Вариант Б")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/events/all"))
                .andExpect(flash().attributeExists("successMessage"));

        // Проверяем, что метод создания вызвался в сервисе
        Mockito.verify(eventService, Mockito.times(1))
                .createEvent(Mockito.any(AddEventDto.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAdminCanFinishEvent() throws Exception {
        // Эмулируем завершение события
        mockMvc.perform(post("/events/1/finish")
                        .param("winningOptionId", "5")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/events/details/1"))
                .andExpect(flash().attributeExists("successMessage"));

        Mockito.verify(eventService).finishEvent(1L, 5L);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAdminCanDeleteEvent() throws Exception {
        // Эмулируем удаление
        mockMvc.perform(get("/events/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/events/all"));

        Mockito.verify(eventService).deleteEvent(1L);
    }

    @Test
    @WithMockUser(username = "hacker", roles = "USER") // Пробуем под юзером
    void testUserCannotCreateEvent() throws Exception {
        // Обычный юзер не должен мочь создать событие
        mockMvc.perform(post("/events/add")
                        .param("title", "Hacked Event")
                        .with(csrf()))
                .andExpect(status().isForbidden()); // 403
    }
}*/