package com.anonsgroup.anons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Anons {
@Getter @Setter private int profilFotograf;
@Getter @Setter private String metin;
@Getter @Setter private String konum;
@Getter @Setter private String kisi;
@Getter @Setter private String tarih;
@Getter @Setter private int goruldu = 0;     //0 ise okunmadı , 1 ise okundu

}
