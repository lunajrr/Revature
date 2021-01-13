package com.daythree.abstraction;

public class MainClass {
	public static void main(String[] args) {
		Cat c = new Cat();
		Dog d = new Dog();
		Hippo h = new Hippo();
		
		c.eat();
		c.roam();
		c.makeNoise();
		d.eat();
		d.roam();
		d.makeNoise();
		h.eat();
		h.roam();
		h.makeNoise();
	}

}
