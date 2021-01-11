package com.example.domain;

// VO(Value Object) : �����ͺ��̽� ���̺�� ������ ����ü
// DTO(Data Tranfer Object) : �����ͺ��̽� ���̺�� ������ ������ ���ۿ뵵�� ����ü
public class PageDto {
	
	private String category; // �˻�����
	private String search;  // �˻���
	private int count; // ��ü�۰���
	private int pageCount;  // �� �ʿ��� ������(������ ��������ȣ)
	private int pageBlock;  // ��������� �� ������ ����
	private int startPage;  // ��������� �� ���������� ��ȣ
	private int endPage;    // ��������� �� �������� ��ȣ
	
	public String getCategory() {
		return category;
	}
	public String getSearch() {
		return search;
	}
	public int getCount() {
		return count;
	}
	public int getPageCount() {
		return pageCount;
	}
	public int getPageBlock() {
		return pageBlock;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	@Override
	public String toString() {
		return "PageDto [category=" + category + ", search=" + search + ", count=" + count + ", pageCount=" + pageCount
				+ ", pageBlock=" + pageBlock + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
}
