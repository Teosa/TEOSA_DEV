package ru.teosa.utils.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ru.teosa.herdSettings.HerdRunSettings;


public class RunProgramRecord {

	private SimpleIntegerProperty num;         // Порядковый номер
	private Integer farmID;                    // ID завода
	private SimpleStringProperty  farmName;    // Название завода
	private Integer programID;                 // ID выбранной программы
	private SimpleStringProperty programName;  // Название выбранной программы
	private HerdRunSettings program;           // Выбранная программа
	private SimpleStringProperty  status;      // Статус прогона
	private String lastHorseURL;               //

	@Override
	public String toString() {
		return ""
				+ "NUM: "          + num                        + "; "
			    + "FARM_ID: "      + farmID                     + "; "
			    + "FARM_NAME: "    + farmName.getValueSafe()    + "; "
			    + "PROGRAM_ID: "   + programID                  + "; "
			    + "PROGRAM_NAME: " + programName.getValueSafe() + "; "
			    + "PROGRAM: "      + program                    + "; "
			    + "STATUS: "       + status.getValueSafe()      + "; "
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
}
