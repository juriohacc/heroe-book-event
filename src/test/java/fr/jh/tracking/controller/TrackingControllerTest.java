package fr.jh.tracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.jh.tracking.model.TrackingVisitedBook;
import fr.jh.tracking.service.TrackingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TrackingController.class)
public class TrackingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TrackingService trackingService;

    @Test
    public void trackVisitBook_authorId_ShouldReturnError() throws Exception {
        TrackingRequest trackingVisitedBook = new TrackingRequest(null,1);

        this.mvc.perform(post("/visit-book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(trackingVisitedBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].path", is("authorId")));

        verifyZeroInteractions(trackingService);
    }

    @Test
    public void trackVisitBook_bookId_ShouldReturnError() throws Exception {
        TrackingRequest trackingVisitedBook = new TrackingRequest(1,null);

        this.mvc.perform(post("/visit-book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(trackingVisitedBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].path", is("bookId")));

        verifyZeroInteractions(trackingService);
    }

    @Test
    public void trackVisitBook_withGoodInputs_ShouldReturn_OK() throws Exception {
//        given(this.trackingService.visitBookAction(new TrackingVisitedBook(null,1)))
//                .will
        TrackingRequest trackingVisitedBook = new TrackingRequest(1,1);

        this.mvc.perform(post("/visit-book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(trackingVisitedBook)))
                .andExpect(status().isOk());

        ArgumentCaptor<TrackingVisitedBook> argumentCaptor = ArgumentCaptor.forClass(TrackingVisitedBook.class);
        verify(trackingService, times(1)).visitBookAction(argumentCaptor.capture());
        verifyNoMoreInteractions(trackingService);
    }

}
