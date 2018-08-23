package com.aoa.springwebservice.dto;

import com.aoa.springwebservice.domain.MaxCount;
import com.aoa.springwebservice.domain.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Boolean lastUsed;
    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected MaxCount maxCount;

    @Builder
    public MenuOutputDTO(long id, String name, int price, String description, String imgUrl, Boolean lastUsed, MaxCount maxCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
        this.lastUsed = lastUsed;
        this.maxCount = maxCount;
    }

    public static MenuOutputDTO createMenuOutputDTO(Menu menu) {
        return MenuOutputDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .imgUrl(menu.getImageUrl())
                .build();
        //new MenuOutputDTO(menu.getId(), menu.getName(), menu.getPrice(), menu.getDescription(), menu.getImageUrl());
    }
    public static MenuOutputDTO createUsedMenuOutputDTO(Menu menu) {
        return MenuOutputDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .imgUrl(menu.getImageUrl())
                .maxCount(menu.getMaxCount())
                .lastUsed(menu.isLastUsed())
                .build();
        //new MenuOutputDTO(menu.getId(), menu.getName(), menu.getPrice(), menu.getDescription(), menu.getImageUrl());
    }

}
