package teamproject.lam_server.domain.inqiury.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryEdit;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryResponse;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.inqiury.entity.InquiryEditor;
import teamproject.lam_server.domain.inqiury.repository.core.InquiryRepository;
import teamproject.lam_server.domain.inqiury.repository.query.InquiryQueryRepository;
import teamproject.lam_server.exception.notfound.InquiryNotFound;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryApiServiceImpl implements InquiryApiService {

    private final InquiryRepository inquiryRepository;
    private final InquiryQueryRepository inquiryQueryRepository;
    private final SecurityContextFinder finder;

    @Override
    @Transactional
    public PostIdResponse write(InquiryCreate request) {
        return PostIdResponse.of(
                inquiryRepository.save(
                                request.toEntity(finder.getLoggedInMember())
                        )
                        .getId());
    }

    @Override
    public CustomPage<InquiryListResponse> getInquires(PageableDTO pageableDTO) {
        PageRequest pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());
        Page<InquiryListResponse> inquiries = inquiryQueryRepository.getInquiries(finder.getLoggedInMemberId(), pageable);

        return CustomPage.<InquiryListResponse>builder()
                .page(inquiries)
                .build();
    }

    @Override
    public InquiryResponse getInquiry(Long id) {
        return inquiryQueryRepository.getInquiry(id).orElseThrow(InquiryNotFound::new);
    }

    @Override
    @Transactional
    public void edit(Long id, InquiryEdit request) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(InquiryNotFound::new);

        finder.checkLegalWriterOfPost(inquiry);

        InquiryEditor editor = inquiry.toEditor()
                .title(request.getTitle())
                .content(request.getContent())
                .category(InquiryCategory.valueOf(request.getCategory()))
                .build();

        inquiry.edit(editor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(InquiryNotFound::new);

        finder.checkLegalWriterOfPost(inquiry);

        inquiryRepository.delete(inquiry);
    }
}

