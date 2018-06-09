package ru.teosa.utils;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class Tools {

	/**
	 * Перевод числа в строку с пробелами между разрядами.<br>
	 * В случае перехвата исключения, функция вернет пустую строку.
	 * @param number число или его строковое представление
	 * @return строковое представление числа, с разделенными пробелом разрядами
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
	 * Устанавливает переданное значение в радиогруппе.<br> 
	 * Поиск подходящего значения осуществляетя путем сравнения содержимого UserData каждой кнопки радиогруппы с переданным значением.
	 * @param group радиогруппа
	 * @param value значение для выбора
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
	 * Получение выбранного значения из радиогруппы.<br>
	 * Метод возвращает значение, хранимое в UserData выбранной кнопки радиогруппы.<br>
	 * Если в группе нет выбранных кнопок - функция вернет null.
	 * @param group радиогруппа
	 * @return найденное значение
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
	 * Выполняет проверку переданной строки на NULL.
	 * @param val строка для проверки
	 * @return Если параметр не NULL - строка с удаленными слева и справа пробелами, иначе - пустая строка
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
	
	public static Integer findSimpleComboRecordById() {
		
		return 0;
	}
}
