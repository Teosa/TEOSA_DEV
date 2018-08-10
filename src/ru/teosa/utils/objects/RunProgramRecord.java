package ru.teosa.utils.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ru.teosa.herdSettings.HerdRunSettings;


public class RunProgramRecord {

	private SimpleIntegerProperty num;         // ���������� �����
	private Integer farmID;                    // ID ������
	private SimpleStringProperty  farmName;    // �������� ������
	private Integer programID;                 // ID ��������� ���������
	private SimpleStringProperty programName;  // �������� ��������� ���������
	private HerdRunSettings program;           // ��������� ���������
	private Integer  statusID;                 // ID ������� �������
	private SimpleStringProperty  statusName;  // �������� ������� �������
	private String lastHorseURL;               // ������ �� ��������� ���������� ������, ���� ������ ������ �����������

	@Override
	public String toString() {
		return ""
				+ "NUM: "            + num                        + "; "
			    + "FARM_ID: "        + farmID                     + "; "
			    + "FARM_NAME: "      + farmName.getValueSafe()    + "; "
			    + "PROGRAM_ID: "     + programID                  + "; "
			    + "PROGRAM_NAME: "   + programName.getValueSafe() + "; "
			    + "PROGRAM: "        + program                    + "; "
			    + "STATUS_ID: "      + statusID                   + "; "
			    + "STATUS_NAME: "    + statusName.getValueSafe()  + "; "
			    + "LAST_HORSE_URL: " + lastHorseURL               + "; "
			    ;
	}

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
		return statusName;
	}
	public void setStatus(String status) {
		this.statusName = new SimpleStringProperty(status);
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
	public Integer getFarmID() {
		return farmID;
	}
	public void setFarmID(Integer farmID) {
		this.farmID = farmID;
	}
	public HerdRunSettings getProgram() {
		return program;
	}
	public void setProgram(HerdRunSettings program) {
		this.program = program;
	}
	public Integer getStatusID() {
		return statusID;
	}
	public void setStatusID(Integer statusID) {
		this.statusID = statusID;
	}
	public String getLastHorseURL() {
		return lastHorseURL;
	}
	public void setLastHorseURL(String lastHorseURL) {
		this.lastHorseURL = lastHorseURL;
	}
}
