package ru.teosa.utils;

import java.util.HashMap;

public class Tokens {
	
	/** Статусы прогона RU */
	public static final HashMap<Integer, String> HERD_RUN_STATUSES_RU;
	/**  Статусы прогона EN */
	public static final HashMap<Integer, String> HERD_RUN_STATUSES_EN;
	
	static
	{
		HERD_RUN_STATUSES_RU = new HashMap<Integer, String>();
		HERD_RUN_STATUSES_EN = new HashMap<Integer, String>();
		
		HERD_RUN_STATUSES_RU.put(1,  "СОЗДАН");			      HERD_RUN_STATUSES_EN.put(1,  "CREATED");
		HERD_RUN_STATUSES_RU.put(2,  "ЗАВОД ПУСТ");           HERD_RUN_STATUSES_EN.put(2,  "EMPTY FARM");
		HERD_RUN_STATUSES_RU.put(5,  "ВЫПОЛНЯЕТСЯ");          HERD_RUN_STATUSES_EN.put(5,  "IN PROGRESS");
		HERD_RUN_STATUSES_RU.put(10, "ВЫПОЛНЕН");             HERD_RUN_STATUSES_EN.put(10, "DONE");
		HERD_RUN_STATUSES_RU.put(11, "ВЫПОЛНЕНО С ОШИБКАМИ"); HERD_RUN_STATUSES_EN.put(10, "DONE WITH ERRORS");
		HERD_RUN_STATUSES_RU.put(12, "ОШИБКА");               HERD_RUN_STATUSES_EN.put(5,  "ERROR");
	}

	public static enum herdRunStatuses
	{
		CREATED(1),         // Создан
		EMPTYFARM(2),       // Завод пуст
		INPROGRESS(5),      // Выполняется
		DONE(10),           // Выполнено
		DONEWITHERRORS(11), // Выполнено с ошибками
		ERROR(12);          // Ошибка
		
		private int id;
		herdRunStatuses(int val) { this.id = val; };
		
		public int getID() { return id; }
	}

	/*
	************************************************
	*                                              *
	*		СТОРЫ ДЛЯ КОМБОБОКСОВ                  *
	*                                              *
	************************************************
	*/
	/** Стор для покупки зерна */
	public static final int[] WHEAT_STORE = {
			1,2,3,4,5,6,7,8,9,
			10,20,30,40,50,60,70,80,90,
			100,200,300,400,500,600,700,800,900,
			1000,2000,3000,4000,5000,6000,7000,8000,9000,
			10000,20000,30000,40000,50000,60000,70000,80000,90000,
			100000
	};
	
	/** Длительность записи в КСК */
	public static final Integer[] EC_REGISTRATION_TERM = {1, 3, 10, 30, 60};
	
	/** Длительность продления записи в КСК */
	public static final Integer[] EC_EXTEND_TERM = {3, 10, 30, 60, 100};
	
	/** Стор цен за случку у жеребцов */
	public static final Integer[] STALLON_MATING_PRICE = {500, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500};
	
	
	
	
	
	
	
}
