package com.example.domain;

public class AttachVo {
	
	private int num;
	private String filename;   // ���� ���ε�� ���ϸ�
	private String uploadpath; // ���� ���ε�� ���� ���
	private String image;      // ������ �̹������� ����. "I"�� �̹���, "O"�� �̹��� �ƴ�
	private int noNum;    // notice ���̺��� �۹�ȣ num
	
	public AttachVo() {
	}

	public int getNum() {
		return num;
	}

	public String getFilename() {
		return filename;
	}

	public String getUploadpath() {
		return uploadpath;
	}

	public String getImage() {
		return image;
	}

	public int getNoNum() {
		return noNum;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setUploadpath(String uploadpath) {
		this.uploadpath = uploadpath;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setNoNum(int noNum) {
		this.noNum = noNum;
	}

	@Override
	public String toString() {
		return "AttachVo [num=" + num + ", filename=" + filename + ", uploadpath=" + uploadpath + ", image=" + image
				+ ", noNum=" + noNum + "]";
	}
	
}
