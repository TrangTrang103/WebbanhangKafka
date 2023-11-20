package com.doan.mutual.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class clickDetailList extends BaseEntity {
  private String idProductDetail;

  private String loai;

  @Override
  public String toString() {
    return "{" + '"' + "idProductDetail"+'"'+":" + '"' + idProductDetail + '"'+", "
            +'"'+"loai"+ '"'+":" + '"' + loai + '"'+"}";
  }
}
