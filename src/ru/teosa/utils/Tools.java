package ru.teosa.utils;

import javafx.scene.control.RadioButton;
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
	public void setRadioButtonGroupValue(ToggleGroup group, Object value) {
		
		if(group != null) {
			for(Toggle button : group.getToggles()) {
				if(button.getUserData() == null) { if(value == null) button.setSelected(true); }
				else if(button.getUserData().equals(value)) button.setSelected(true);
			}
		}
	}
	
	/**
	 * ��������� �������� ���������� ������ �� NULL.
	 * @param val ������ ��� ��������
	 * @return ���� �������� �� NULL - ������ � ���������� ����� � ������ ���������, ����� - ������ ������
	 * */
	public String writeText(Object val){
        return val != null? val.toString().trim() : "";
    }
}
