package com.ZenPack.ReportHeader;

import com.ZenPack.ReportHeader.dto.HeaderInfoDto;
import com.ZenPack.ReportHeader.model.ReportHeader;
import com.ZenPack.ReportHeader.repository.ReportHeaderRepository;
import com.ZenPack.ReportHeader.service.ReportHeaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ReportHeaderServiceTest {
    @Mock
    private ReportHeaderRepository repository;

    @InjectMocks
    private ReportHeaderService service;

    private ReportHeader reportHeader;

    private ReportHeader reportHeader1;

    public HeaderInfoDto headerInfoDto;

    @BeforeEach
    void init(){
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.setReportId(1L);
        reportHeader.setReportName("Zen1");
//        HeaderInfoDto headerInfoDto = new HeaderInfoDto();
//        headerInfoDto.setActualName("Zen_Pack");
//        headerInfoDto.setHide(true);
//        headerInfoDto.setDisplayName("ZenPack");
//        headerInfoDto.setDataType("String");
//
//        List<HeaderInfoDto> list = new ArrayList<>();
//        list.add(headerInfoDto);

//        reportHeader.setHeaderInfo(list);

        ReportHeader reportHeader1 = new ReportHeader();
        reportHeader1.setReportId(2L);
        reportHeader1.setReportName("Zen2");
        HeaderInfoDto headerInfoDto1 = new HeaderInfoDto();
        headerInfoDto1.setActualName("Zen_Pack");
        headerInfoDto1.setHide(true);
        headerInfoDto1.setDisplayName("ZenPack");
        headerInfoDto1.setDataType("String");

        List<HeaderInfoDto> list1 = new ArrayList<>();
        list1.add(headerInfoDto1);

        reportHeader1.setHeaderInfo(list1);
    }

    @Test
    @DisplayName("Junit Test for Save List")
    void saveList(){
        given(repository.findById(reportHeader.getReportId()))
                .willReturn(Optional.empty());

        given(repository.save(reportHeader)).willReturn(reportHeader);

        System.out.println(repository);
        System.out.println(service);

        ReportHeader savedReports = service.createReportHeader(reportHeader);

        System.out.println(savedReports);
        assertThat(savedReports).isNotNull();
    }
}
