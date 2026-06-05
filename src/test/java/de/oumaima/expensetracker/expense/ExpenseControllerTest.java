package de.oumaima.expensetracker.expense;

import de.oumaima.expensetracker.dto.ExpenseRequest;
import de.oumaima.expensetracker.dto.ExpenseResponse;
import de.oumaima.expensetracker.mapper.ExpenseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExpenseService expenseService;

    @MockitoBean
    private ExpenseMapper expenseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getById_returnsExpense() throws Exception {
        Expense entity = new Expense();
        ExpenseResponse response =
                new ExpenseResponse(1L, "Coffee", new BigDecimal("3.50"),
                        LocalDate.of(2026, 6, 1), null);
        when(expenseService.findById(1L)).thenReturn(Optional.of(entity));
        when(expenseMapper.toResponse(entity)).thenReturn(response);

        mockMvc.perform(get("/expenses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Coffee"))
                .andExpect(jsonPath("$.amount").value(3.50));

    }

     @Test
    void getById_returnNotFound() throws Exception {
         when(expenseService.findById(99L)).thenReturn(Optional.empty());

         mockMvc.perform(get("/expenses/99"))
                 .andExpect(status().isNotFound());



     }
    @Test
    void create_returnsBadRequestWhenDescriptionBlank() throws Exception {
        // Arrange — an invalid body: blank description violates @NotBlank
        ExpenseRequest invalid =
                new ExpenseRequest("", new BigDecimal("3.50"), LocalDate.of(2026, 6, 1),
                        1L);

        // Act + Assert
        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors[0].field").value("description"));
    }

    @Test
    void create_returnsExpense() throws Exception {
        Expense entity = new Expense();
        ExpenseRequest request =
                new ExpenseRequest("Coffee", new BigDecimal("3.50"),
                        LocalDate.of(2026, 6, 1), 1L);
        ExpenseResponse response =
                new ExpenseResponse(1L, "Coffee", new BigDecimal("3.50"),
                        LocalDate.of(2026, 6, 1), null);
        when(expenseMapper.toEntity(request)).thenReturn(entity);
        when(expenseService.create(entity)).thenReturn(entity);
        when(expenseMapper.toResponse(entity)).thenReturn(response);

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

}
