package com.daythree.abstraction;



public abstract class Animal{
	
	String picture;
	String food;
	int hunger;
	String boundaries;
	String location;
	public void makeNoise() {
		System.out.println("Animal makes noise");
	}
	public void eat() {
		System.out.println("Animal eats");
	}
	public void roam() {
		System.out.println("Animal is roaming");
	}
}
