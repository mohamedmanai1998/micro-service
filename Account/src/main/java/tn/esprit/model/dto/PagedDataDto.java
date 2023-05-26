package tn.esprit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagedDataDto<T> {

	private List<T> listData;
	
	private Long totalElements;
	
	private Integer totalPages;
	

	public PagedDataDto(Page<T> pageData) {
		this.listData = pageData.getContent();
		this.totalElements = pageData.getTotalElements();
		this.totalPages = pageData.getTotalPages();
	}

	
	
}