package com.ZenPack.ReportHeader;

import com.ZenPack.ReportHeader.Controller.ZenPackReportController;
import com.ZenPack.ReportHeader.dto.HeaderInfoDto;
import com.ZenPack.ReportHeader.model.ReportHeader;
import com.ZenPack.ReportHeader.repository.ReportHeaderRepository;
import com.ZenPack.ReportHeader.service.ReportHeaderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ZenPackReportController.class)
//@RunWith()
public class ReportControllerTest {
    @MockBean
    private ReportHeaderRepository repository;

    @MockBean
    private ReportHeaderService service;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ZenPackReportController zenPackReportController;

    @Autowired
    private ObjectMapper objectMapper;

    private ReportHeader reportHeader;
    private ReportHeader reportHeader1;

    public HeaderInfoDto headerInfoDto;

    @BeforeEach
    void init(){
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.setReportId(1L);
        reportHeader.setReportName("Zen1");
        HeaderInfoDto headerInfoDto = new HeaderInfoDto();
        headerInfoDto.setActualName("Zen_Pack");
        headerInfoDto.setHide(true);
        headerInfoDto.setDisplayName("ZenPack");
        headerInfoDto.setDataType("String");

        List<HeaderInfoDto> list = new ArrayList<>();
        list.add(headerInfoDto);

        reportHeader.setHeaderInfo(list);

        ReportHeader reportHeader1 = new ReportHeader();
        reportHeader1.setReportId(2L);
        reportHeader1.setReportName("Zen2");
        HeaderInfoDto headerInfoDto1 = new HeaderInfoDto();
        headerInfoDto1.setActualName("Zen_Pack");
        headerInfoDto1.setHide(true);
        headerInfoDto1.setDisplayName("ZenPack");
        headerInfoDto1.setDataType("String");

        List<HeaderInfoDto> list1 = new ArrayList<>();
        list.add(headerInfoDto1);

        reportHeader1.setHeaderInfo(list1);
    }

    @Test
    void create() throws Exception {
        ReportHeader reportHeader = new ReportHeader();

        reportHeader.setReportId(2L);
        reportHeader.setReportName("zen");

        HeaderInfoDto headerInfoDto = new HeaderInfoDto();
        headerInfoDto.setActualName("Zen_Pack");
        headerInfoDto.setHide(true);
        headerInfoDto.setDisplayName("ZenPack");
        headerInfoDto.setDataType("String");

        List<HeaderInfoDto> list = new ArrayList<>();
        list.add(headerInfoDto);

        reportHeader.setHeaderInfo(list);

        when(service.createReportHeader(any(ReportHeader.class))).thenReturn(reportHeader);

        this.mockMvc.perform(post("/api/v1/reportHeader/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportHeader)))
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.reportId", CoreMatchers.is(reportHeader.getReportId())))
                .andExpect(jsonPath("$.reportName", CoreMatchers.is(reportHeader.getReportName())));
//                .andExpect(jsonPath("$.HEADERINFO.actualName", CoreMatchers.is(reportHeader.getHeaderInfo())));
    }

    @Test
    void shouldGetAllReports() throws Exception {
        List<ReportHeader> reportHeaderList = new ArrayList<>();
        reportHeaderList.add(reportHeader);
        reportHeaderList.add(reportHeader1);

        when(service.getAllReportHeader()).thenReturn(reportHeaderList);

        this.mockMvc.perform(get("/api/v1/reportHeader/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",CoreMatchers.is(reportHeaderList.size())));
    }


    @Test
    void deleteReports(){
        ReportHeader reportHeader2 = new ReportHeader(1L,"Zen1");
        service.deleteReportHeaderById(1L);
        verify(repository,times(1)).delete(reportHeader2);
    }

    @Test
    void shouldDeleteReport() throws Exception {

        doNothing().when(service).deleteReportHeaderById(anyLong());

        this.mockMvc.perform(delete("/api/v1/reportHeader/delete/{reportId}", 1L))
                .andExpect(status().isNoContent());

    }

    @Test
    void shouldFetchOneReportById() throws Exception {

        when(service.getReportHeaderById(anyLong())).thenReturn(reportHeader);

        this.mockMvc.perform(get("/api/v1/reportHeader/getReportHeaderById/{reportId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportId", CoreMatchers.is(reportHeader.getReportId())))
                .andExpect(jsonPath("$.reportName", CoreMatchers.is(reportHeader.getReportName())));
    }




}
