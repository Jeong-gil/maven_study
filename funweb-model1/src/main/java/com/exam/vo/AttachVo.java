package com.exam.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachVo {
	
	private int num;
	private String filename;   // ���� ���ε�� ���ϸ�
	private String uploadpath; // ���� ���ε�� ���� ���
	private String image;      // ������ �̹������� ����. "I"�� �̹���, "O"�� �̹��� �ƴ�
	private int noNum;    // notice ���̺��� �۹�ȣ num
	
}
