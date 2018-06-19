package ru.teosa.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class Tools {

	/**
	 * ������� ����� � ������ � ��������� ����� ���������.<br>
	 * � ������ ��������� ����������, ������� ������ ������ ������.
	 * @param number ����� ��� ��� ��������� �������������
	 * @return ��������� ������������� �����, � ������������ �������� ���������
	 * */
	public static String numStringWithSpaces(Object number) {
		String result = "";
		
		if(number != null) 
		{
			try {
//				result = String.format("%,d", Integer.parseInt(number.toString()));
				char[] chars = number.toString().trim().toCharArray();
				short numCounter = 0;

				for(int i = chars.length-1; i >= 0; --i) 
				{
					result = numCounter % 3 == 0 ? chars[i] + " " + result : chars[i] + result;
					++numCounter;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	
		return result;
	}
	
	
	public static int getQtyForSale(int integer) {

		int result = 0;
        int mny;
        boolean started = false;
        long divisor = 1000000000;

        for(int i = 8; i >= 0; --i){

        	divisor /= 10;
            mny = (int)(integer / divisor);
            integer %= divisor;
                        
        	if(!started) started = mny > 0;
            
            if(mny != 0){
                result =  Math.toIntExact(mny * divisor);
                break;
            }
            else if(started){
                result = Math.toIntExact(divisor);
                break;
            }
            else  continue;
            
        }

        return result;
	}
	
	/**
	 * ������������� ���������� �������� � �����������.<br> 
	 * ����� ����������� �������� ������������� ����� ��������� ����������� UserData ������ ������ ����������� � ���������� ���������.
	 * @param group �����������
	 * @param value �������� ��� ������
	 * */
	public static void setRadioButtonGroupValue(ToggleGroup group, Object value) {
		
		if(group != null) 
		{
			for(Toggle button : group.getToggles()) 
			{
				if(button.getUserData() == null) 
				{ 
					if(value == null) button.setSelected(true); 
				}
				else if(button.getUserData().equals(value)) button.setSelected(true);
			}
		}
	}
	
	/**
	 * ��������� ���������� �������� �� �����������.<br>
	 * ����� ���������� ��������, �������� � UserData ��������� ������ �����������.<br>
	 * ���� � ������ ��� ��������� ������ - ������� ������ null.
	 * @param group �����������
	 * @return ��������� ��������
	 * */
	public static Object getRadioButtonGroupValue(ToggleGroup group) {
		Object result = null;
		
		if(group != null) 
		{
			for(Toggle button : group.getToggles()) 
			{
				if(button.isSelected()) 
				{
					result = button.getUserData(); 
					break;	
				}
			}
		}
		
		return result;
	}
	
	/**
	 * ��������� �������� ���������� ������ �� NULL.
	 * @param val ������ ��� ��������
	 * @return ���� �������� �� NULL - ������ � ���������� ����� � ������ ���������, ����� - ������ ������
	 * */
	public static String writeText(Object val){
        return val != null? val.toString().trim() : "";
    }
	
	/**
	 * -
	 * @param val
	 * @return -
	 * */
	public static Integer writeInteger(Object val){
		Integer result = null;
		
		try { result = Integer.parseInt(val.toString()); }
		catch(Exception e) { System.out.println("WRITE INTEGER: " + e.getMessage()); }
		
		return result;
    }
	
	/***/
	public static Integer findSimpleComboRecordById() {
		
		return 0;
	}
	
	/**
	 * ������� ��� ������ �� ������� ����������� HashMap.<br>
	 * ������ ������: KEY: <����>  VALUE:  <��������>
	 * @param params - HashMap � �����������
	 * @return void
	 * */
	public void printParamMap(HashMap params) {
		for (Map.Entry el : ((HashMap<String, Object>) params).entrySet()) {
			System.out.println("KEY:  " + el.getKey() + "    VALUE:  " + el.getValue());
			Logger.getLogger("file").debug("KEY:  " + el.getKey() + "    VALUE:  " + el.getValue());
		}
	}
	
	/**
	 * ������� ��� �������������� ������ ������ � null.<br>
	 * ���� ������ ���������� � ������� ��������� ������ ����� �������� �������� ����� ����, ������� ������ null, 
	 * ����� - ���������� �������� � ���������� ����� � ������ ���������.
	 * @param value ������ ��� ��������
	 * @return ���������� ������ ��� �������������� �������� ��� null
	 * */
	public static String replaceEmtyText(Object value) 
	{	
		String result = null;
		
		if(value != null) return value.toString().trim().length() == 0 ? result : value.toString().trim();
		else return result;
	}
}
