package e_store.dto.out;

import org.springframework.data.domain.Page;

import java.util.List;

public final class PageResponseDto<T> {

    private final List<T> content;

    private final int totalPages;
    private final int pageSize;
    private final long totalElements;

    public static <T> PageResponseDto<T> of(Page<T> pagedData) {
        return new PageResponseDto<>(
                pagedData.getContent(),
                pagedData.getTotalPages(),
                pagedData.getSize(),
                pagedData.getTotalElements());
    }







    public PageResponseDto(List<T> content,
                           int totalPages,
                           int pageSize,
                           long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }
}
