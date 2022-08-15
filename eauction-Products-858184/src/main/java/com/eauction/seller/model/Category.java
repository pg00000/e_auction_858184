package com.eauction.seller.model;

import com.google.common.base.Enums;

public enum Category {
	PAINTING, SCULPTOR, ORNAMENT;
	
	public static Category getIfPresent(String name) {

        return Enums.getIfPresent(Category.class, name).orNull();

    }
}
