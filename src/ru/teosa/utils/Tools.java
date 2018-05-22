package ru.teosa.utils;


public class Tools {

	/**
	 * ѕеревод числа в строку с пробелами между разр€дами.<br>
	 * ¬ случае перехвата исключени€, функци€ вернет пустую строку.
	 * @param number число или его строковое представление
	 * @return строковое представление числа, с разделенными пробелом разр€дами
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
	
	
}
