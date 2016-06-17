package com.zhanghui.pusher.domain;

import java.util.Set;

import com.google.common.collect.Sets;

public class Rule {
	
	public Rule(Set<Integer> banner, Set<Integer> screen) {
		this.banner = banner;
		this.screen = screen;
	}
	private Set<Integer> banner;
	private Set<Integer> screen;
	
	public Set<Integer> getBanner() {
		return banner;
	}
	public void setBanner(Set<Integer> banner) {
		this.banner = banner;
	}
	public Set<Integer> getScreen() {
		return screen;
	}
	public void setScreen(Set<Integer> screen) {
		this.screen = screen;
	}
	
}
