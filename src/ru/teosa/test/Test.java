package ru.teosa.test;

import java.io.ObjectStreamClass;

import ru.teosa.herdSettings.EC_Settings;
import ru.teosa.herdSettings.HerdRunSettings;

public class Test {

	public static void main(String[] args) {
		System.out.println(ObjectStreamClass.lookup(HerdRunSettings.class).getSerialVersionUID());
		System.out.println(ObjectStreamClass.lookup(EC_Settings.class).getSerialVersionUID());

	}

}
