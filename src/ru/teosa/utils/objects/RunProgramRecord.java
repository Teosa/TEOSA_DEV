package ru.teosa.utils.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class RunProgramRecord {

	private SimpleIntegerProperty num;         // ���������� �����
	private SimpleStringProperty  farmName;    // �������� ������
	private Integer programID;                 // ID ��������� ���������
	private SimpleStringProperty programName;  // �������� ��������� ���������
	private SimpleStringProperty  status;      // ������ �������

	

	public SimpleIntegerProperty getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = new SimpleIntegerProperty(num);
	}
	public SimpleStringProperty getFarmName() {
		return farmName;
	}
	public void setFarmName(String farmName) {
		this.farmName = new SimpleStringProperty(farmName);
	}
	public SimpleStringProperty getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}
	public Integer getProgramID() {
		return programID;
	}
	public void setProgramID(Integer programID) {
		this.programID = programID;
	}
	public SimpleStringProperty getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = new SimpleStringProperty(programName);;
	}
}
