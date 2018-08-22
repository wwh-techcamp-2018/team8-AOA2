package com.aoa.springwebservice.domain.support;

import com.aoa.springwebservice.domain.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuOutputDTO {
    protected long id;
    protected String name;
    protected int price;
    protected String description;
    protected String imgUrl;

    @Builder
    public MenuOutputDTO(long id, String name, int price, String description, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public static MenuOutputDTO createMenuOutputDTO(Menu menu) {
        return new MenuOutputDTO(menu.getId(), menu.getName(), menu.getPrice(), menu.getDescription(), menu.getImageUrl());
    }
}
