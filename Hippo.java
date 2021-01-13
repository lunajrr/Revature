package com.daythree.abstraction;

public class Hippo extends Animal{

	@Override
	public void makeNoise() {
		System.out.println("Roar");
	}
	
	@Override
	public void eat() {
		System.out.println("Hippo is eating");
	}
	
}
